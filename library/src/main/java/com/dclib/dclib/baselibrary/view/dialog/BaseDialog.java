package com.dclib.dclib.baselibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.dclib.dclib.baselibrary.R;

/**
 * BaseDialog
 * Created on 2021/7/13
 *
 * @author dc
 */
public abstract class BaseDialog extends Dialog {

    public Context context;
    public View contentView;
    public LayoutInflater inflater;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.my_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        //设置弹出窗体的背景
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        this.setCanceledOnTouchOutside(false);

        inflater = LayoutInflater.from(context);
        contentView = inflater.inflate(contentViewResId(), null);
        setContentView(contentView);

        initView();
        initData();
        initClick();
    }

    /**
     * 布局文件id
     *
     * @return 布局文件
     */
    public abstract int contentViewResId();

    /**
     * 初始化界面
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 初始化事件
     */
    public abstract void initClick();
}