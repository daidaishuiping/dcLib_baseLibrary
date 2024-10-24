package com.dclib.dclib.baselibrary.view.popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.dclib.dclib.baselibrary.R;

/**
 * PopupWindow基类
 * Created on 2018/5/10.
 *
 * @author daichao
 */
public abstract class BasePopupWindow extends PopupWindow {

    public Context context;
    public View contentView;
    public LayoutInflater inflater;

    public BasePopupWindow(Context context) {
        super(context);
        this.context = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(contentViewResId(), null);
        //设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        initViewAndData();
    }

    /**
     * 布局文件id
     *
     * @return 布局文件
     */
    public abstract int contentViewResId();

    /**
     * 初始化视图和数据
     */
    public abstract void initViewAndData();

    /**
     * 底部显示
     */
    public void showBottom(View view) {
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.popup_window_animation);

        this.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        //设置activity为半透明
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 0.6f;
        ((Activity) context).getWindow().setAttributes(params);
    }

    /**
     * 顶部显示
     */
    public void showTop(View view) {
        this.showAtLocation(view, Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    @Override
    public void dismiss() {
        super.dismiss();
        //设置activity为不透明
        WindowManager.LayoutParams params = ((Activity) context).getWindow().getAttributes();
        params.alpha = 1.0f;
        ((Activity) context).getWindow().setAttributes(params);
    }
}
