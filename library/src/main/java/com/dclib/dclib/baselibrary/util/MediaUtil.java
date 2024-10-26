package com.dclib.dclib.baselibrary.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;

/**
 * 多媒体工具类
 * Created on 2019/6/13
 *
 * @author daichao
 */
public class MediaUtil {

    /**
     * 拍照返回code
     */
    public static final int RESULT_CODE_TAKE_PHOTO = 998;
    /**
     * 相机返回code
     */
    public static final int RESULT_CODE_RECORD_VIDEO = 999;

    private Context context;

    public MediaUtil(Context context) {
        this.context = context;
    }

    /**
     * 拍照
     *
     * @param photoFile 相片文件
     */
    public void takePhoto(File photoFile) {
        Intent intent;

        Uri pictureUri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
            pictureUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(photoFile);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, RESULT_CODE_TAKE_PHOTO);
        }
    }

    /**
     * 录制视频
     *
     * @param videoFile 视频文件
     */
    public void recordVideo(File videoFile) {
        Intent intent;

        Uri recordVideoUri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Video.Media.DATA, videoFile.getAbsolutePath());
            recordVideoUri = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            recordVideoUri = Uri.fromFile(videoFile);
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, recordVideoUri);

        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, RESULT_CODE_RECORD_VIDEO);
        }
    }
}
