package com.dclib.dclib.baselibrary.util;

import android.Manifest;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VideoUtils {
    private static String saveVideoToAlbum(Context context, InputStream inputStream) throws IOException {
        // 创建文件夹
        File albumDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "YourFolderName");
        if (!albumDir.exists()) {
            albumDir.mkdirs();
        }

        // 创建文件
        String fileName = "video" + System.currentTimeMillis() + ".mp4";
        File albumFile = new File(albumDir, fileName);

        // 将输入流写入文件
        byte[] buffer = new byte[4096];
        int bytesRead;
        FileOutputStream outputStream = new FileOutputStream(albumFile);
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.flush();
        outputStream.close();

        // 通知相册更新
        MediaScannerConnection.scanFile(context, new String[]{albumFile.getAbsolutePath()}, null, null);
        return albumFile.getAbsolutePath();
    }


    public interface VideoDownloadListener {
        void onDownloadCompleted(String videoFilePath);

        void onDownloadFailed(String errorMessage);
    }

    public interface VideoSaveToAlbumListener {
        void saveSuccess(String filePath);

        void saveFail();
    }

    /**
     * 保存视频到相册
     */
    public static void saveVideoToAlbumAndPermissions(Context context, String path, VideoSaveToAlbumListener listener) {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        if (!PermissionUtils.isGranted(permissions)) {
//            new AppDialog(context).title("文件存储权限使用说明")
//                    .message("用于保存文件")
//                    .negativeBtn("取消", new AppDialog.OnClickListener() {
//                        @Override
//                        public void onClick(AppDialog appDialog) {
//                            appDialog.cancel();
//                        }
//                    })
//                    .positiveBtn("确定", new AppDialog.OnClickListener() {
//                        @Override
//                        public void onClick(AppDialog appDialog) {
//                            appDialog.cancel();
//
//                            PermissionUtils.permission(permissions)
//                                    .callback(new PermissionUtils.SimpleCallback() {
//                                        @Override
//                                        public void onGranted() {
//                                            saveVideoToAlbum(context, path, listener);
//                                        }
//
//                                        @Override
//                                        public void onDenied() {
//                                            Toast.makeText(context, "请打开相机和文件存储权限", Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                                    .request();
//                        }
//                    }).show();
        } else {
            saveVideoToAlbum(context, path, listener);
        }
    }


    /**
     * 保存视频到相册
     */
    private static void saveVideoToAlbum(Context context, String path, VideoSaveToAlbumListener listener) {
        // 将视频写入文件
        try {
            String newPath = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM) + File.separator + "video" + System.currentTimeMillis() + ".mp4";
            boolean flag = FileUtils.copy(path, newPath);

            if (flag) {
                MediaScannerConnection.scanFile(context, new String[]{newPath}, null, new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        // 刷新相册完成
                        listener.saveSuccess(path);
                    }
                });
            } else {
                listener.saveFail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.saveFail();
        }
    }

}


