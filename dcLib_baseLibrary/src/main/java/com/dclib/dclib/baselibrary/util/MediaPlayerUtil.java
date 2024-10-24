package com.dclib.dclib.baselibrary.util;

import android.media.MediaPlayer;

import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 音频播放器
 * Created on 2018/9/6
 *
 * @author daichao
 */
public class MediaPlayerUtil {

    private static MediaPlayerUtil instance;

    private ExecutorService executorService;

    /**
     * 播放器
     */
    private MediaPlayer mediaPlayer;

    private IPlayState iPlayState;

    public void setiPlayState(IPlayState iPlayState) {
        this.iPlayState = iPlayState;
    }

    private MediaPlayerUtil() {
        //录音JNI函数不具备线程安全性，所以要用单线程
        executorService = Executors.newSingleThreadExecutor();
    }

    public static MediaPlayerUtil getInstance() {

        if (instance == null) {
            synchronized (MediaPlayerUtil.class) {
                if (instance == null) {
                    instance = new MediaPlayerUtil();
                }
            }
        }
        return instance;
    }

    //--------------------------播放功能开始--------------------------

    /**
     * 开始播放
     *
     * @param audioFile 播放文件
     */
    public void startPlay(final File audioFile) {
        //提交后台任务，开始播放
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                doPlay(audioFile);
            }
        });
    }

    /**
     * 实际播放逻辑
     *
     * @param audioFile 播放文件
     */
    private void doPlay(File audioFile) {
        //配置播放器MediaPlayer
        mediaPlayer = new MediaPlayer();
        try {

            //设置声音文件
            mediaPlayer.setDataSource(audioFile.getAbsolutePath());

            //设置监听回调
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //播放结束，释放播放器
                    stopPlay();

                    if (iPlayState != null) {
                        iPlayState.complete();
                    }
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {

                    //提示用户
                    playFail();
                    //释放播放器
                    stopPlay();

                    //错误已经处理
                    return true;
                }
            });
            //配置音量，是否循环播放
            mediaPlayer.setVolume(1, 1);
            mediaPlayer.setLooping(false);

            //准备开始
            mediaPlayer.prepare();

            //播放总时长
            if (iPlayState != null) {
                int duration = mediaPlayer.getDuration();
                iPlayState.prepare(duration);
            }

            mediaPlayer.start();

        } catch (Exception e) {
            //异常处理，防止闪退
            e.printStackTrace();
            //提示用户
            playFail();
            //释放播放器
            stopPlay();

            if (iPlayState != null) {
                iPlayState.complete();
            }
        }
    }

    /**
     * 暂停播放
     */
    public void pausePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();

            if (iPlayState != null) {
                iPlayState.pause();
            }
        }
    }

    /**
     * 继续播放
     */
    public void continuePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            mediaPlayer.start();

            //播放总时长
            if (iPlayState != null) {
                int duration = mediaPlayer.getDuration();
                int current = mediaPlayer.getCurrentPosition();
                iPlayState.prepare(duration - current);
            }
        }
    }

    /**
     * 停止播放逻辑
     */
    public void stopPlay() {
        //释放播放器
        if (mediaPlayer != null) {
            //重置监听器，防止内存泄露
            mediaPlayer.setOnCompletionListener(null);
            mediaPlayer.setOnErrorListener(null);

            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    /**
     * 提示用户播放失败
     */
    private void playFail() {
        ToastUtils.showLong("播放失败");
    }

    /**
     * 播放状态
     */
    public interface IPlayState {

        /**
         * 播放准备
         *
         * @param duration 播放时长
         */
        void prepare(int duration);

        /**
         * 播放暂停
         */
        void pause();

        /**
         * 播放完成
         */
        void complete();
    }
}
