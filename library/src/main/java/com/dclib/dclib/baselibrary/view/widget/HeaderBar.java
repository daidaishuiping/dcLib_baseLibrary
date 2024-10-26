package com.dclib.dclib.baselibrary.view.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.dclib.dclib.baselibrary.R;
import com.dclib.dclib.baselibrary.util.AppManager;

/**
 * 自定义头部控件
 * Created on 2018/5/10.
 *
 * @author dc
 */
public class HeaderBar extends FrameLayout {

    private ImageView backIV;
    private TextView titleTV;
    private TextView rightTV;
    private TextView leftTV;
    private ImageView rightIV;

    /**
     * 是否显示返回
     */
    private boolean isShowBack;
    /**
     * 标题
     */
    private String titleText;
    private int backgroundColor;
    /**
     * 标题颜色
     */
    private int titleColor;
    /**
     * 右边文字
     */
    private String rightText;
    /**
     * 右边文字颜色
     */
    private int rightColor;
    /**
     * 右边文字大小
     */
    private float rightTextSize;
    /**
     * 右边图标
     */
    private int rightImageRes;
    /**
     * 左边图标
     */
    private int leftImageRes;
    /**
     * 左边文字
     */
    private String leftText;

    /**
     * 左边边文字颜色
     */
    private int leftColor;

    public HeaderBar(@NonNull Context context) {
        this(context, null);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);

        backgroundColor = typedArray.getColor(R.styleable.HeaderBar_backgroundColor
                , ContextCompat.getColor(context, R.color.white));
        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true);
        titleText = typedArray.getString(R.styleable.HeaderBar_titleText);
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText);
        leftText = typedArray.getString(R.styleable.HeaderBar_leftText);
        titleColor = typedArray.getColor(R.styleable.HeaderBar_titleColor
                , ContextCompat.getColor(context, R.color.text_thin_black));
        rightColor = typedArray.getColor(R.styleable.HeaderBar_rightColor
                , ContextCompat.getColor(context, R.color.text_thin_black));
        leftColor = typedArray.getColor(R.styleable.HeaderBar_leftColor
                , ContextCompat.getColor(context, R.color.text_thin_black));
        rightImageRes = typedArray.getResourceId(R.styleable.HeaderBar_rightImageRes, 0);
        leftImageRes = typedArray.getResourceId(R.styleable.HeaderBar_leftImageRes, R.drawable.icon_back);

        rightTextSize = typedArray.getDimension(R.styleable.HeaderBar_rightTextSize, SizeUtils.sp2px(14));

        initView(context);

        typedArray.recycle();
    }

    private void initView(Context context) {

        View view = View.inflate(context, R.layout.view_header_bar, this);
        backIV = view.findViewById(R.id.backIV);
        titleTV = view.findViewById(R.id.titleTV);
        rightTV = view.findViewById(R.id.rightTV);
        leftTV = view.findViewById(R.id.leftTV);
        rightIV = view.findViewById(R.id.rightIV);

        view.setBackgroundColor(backgroundColor);

        backIV.setImageResource(leftImageRes);

        if (isShowBack) {
            backIV.setVisibility(VISIBLE);
        } else {
            backIV.setVisibility(GONE);
        }

        titleTV.setText(titleText);
        titleTV.setTextColor(titleColor);

        if (StringUtils.isTrimEmpty(rightText)) {
            rightTV.setVisibility(GONE);
        } else {
            rightTV.setVisibility(VISIBLE);
            rightTV.setText(rightText);
            rightTV.setTextColor(rightColor);
            rightTV.setTextSize((int) (rightTextSize / Resources.getSystem().getDisplayMetrics().density + 0.5f));
        }

        if (StringUtils.isTrimEmpty(leftText)) {
            leftTV.setVisibility(GONE);
        } else {
            leftTV.setVisibility(VISIBLE);
            leftTV.setText(leftText);
            leftTV.setTextColor(leftColor);
        }

        if (rightImageRes == 0) {
            rightIV.setVisibility(GONE);
        } else {
            rightIV.setVisibility(VISIBLE);
            rightIV.setImageResource(rightImageRes);
        }

        //统一处理点击返回关闭当前activity，特殊处理的话，重写点击事件
        backIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity();
            }
        });
    }


    public ImageView getBackIV() {
        return backIV;
    }

    public TextView getTitleTV() {
        return titleTV;
    }

    public TextView getRightTV() {
        return rightTV;
    }

    public ImageView getRightIV() {
        return rightIV;
    }
}