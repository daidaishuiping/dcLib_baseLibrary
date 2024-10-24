package com.dclib.dclib.baselibrary.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dclib.dclib.baselibrary.R;


/**
 * 自定义对话框
 * Created by sts on 2018/5/16.
 *
 * @author daichao
 */
public class AppDialog extends Dialog {

    private TextView titleTv;
    private TextView messageTv;

    private LinearLayout btnLl;
    private Button negativeBtn;
    private Button centerBtn;
    private Button positiveBtn;

    private View bottomLineView;
    private View line1View;
    private View line2View;

    private final Context context;

    public AppDialog(Context context) {
        super(context, R.style.my_dialog);
        this.context = context;
        initView();
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public TextView getMessageTv() {
        return messageTv;
    }

    public Button getCenterBtn() {
        return centerBtn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().getAttributes().width = dm.widthPixels * 4 / 5;
        this.setCanceledOnTouchOutside(false);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_app, null);
        setContentView(view);

        titleTv = view.findViewById(R.id.titleTV);
        messageTv = view.findViewById(R.id.messageTV);

        btnLl = view.findViewById(R.id.btnLL);
        negativeBtn = view.findViewById(R.id.negativeBtn);
        centerBtn = view.findViewById(R.id.centerBtn);
        positiveBtn = view.findViewById(R.id.positiveBtn);

        bottomLineView = view.findViewById(R.id.bottomLineView);
        line1View = view.findViewById(R.id.line1View);
        line2View = view.findViewById(R.id.line2View);

        negativeBtn.setOnClickListener(cancelListener);
        negativeBtn.setVisibility(View.GONE);
        centerBtn.setVisibility(View.GONE);
        positiveBtn.setVisibility(View.GONE);
    }

    /**
     * 标题
     */
    public AppDialog title(String title) {
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText(title);
        return this;
    }

    /**
     * 标题
     */
    public AppDialog title(int title) {
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText(title);
        return this;
    }

    /**
     * 信息
     */
    public AppDialog message(String message) {
        messageTv.setVisibility(View.VISIBLE);
        messageTv.setText(message);
        return this;
    }

    /**
     * 信息
     */
    public AppDialog message(int message) {
        messageTv.setVisibility(View.VISIBLE);
        messageTv.setText(message);
        return this;
    }

    /**
     * 消极按钮
     */
    public AppDialog negativeBtn(String negative, final OnClickListener onClickListener) {
        if (negative != null && negative.length() > 0) {
            negativeBtn.setText(negative);
        }
        negativeBtn.setVisibility(View.VISIBLE);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 消极按钮
     */
    public AppDialog negativeBtn(int negative, final OnClickListener onClickListener) {
        negativeBtn.setText(negative);
        negativeBtn.setVisibility(View.VISIBLE);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 中间按钮
     */
    public AppDialog centerBtn(String center, final OnClickListener onClickListener) {
        if (center != null && center.length() > 0) {
            centerBtn.setText(center);
        }
        centerBtn.setVisibility(View.VISIBLE);
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 中间按钮
     */
    public AppDialog centerBtn(int center, final OnClickListener onClickListener) {
        centerBtn.setText(center);
        centerBtn.setVisibility(View.VISIBLE);
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 积极按钮
     */
    public AppDialog positiveBtn(String positive, final OnClickListener onClickListener) {
        if (positive != null && positive.length() > 0) {
            positiveBtn.setText(positive);
        }
        positiveBtn.setVisibility(View.VISIBLE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 积极按钮
     */
    public AppDialog positiveBtn(int positive, final OnClickListener onClickListener) {
        positiveBtn.setText(positive);
        positiveBtn.setVisibility(View.VISIBLE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(AppDialog.this);
            }
        });
        return this;
    }

    /**
     * 积极按钮颜色
     */
    public AppDialog positiveBtnColor(String color) {
        positiveBtn.setTextColor(Color.parseColor(color));
        return this;
    }

    @Override
    public void show() {
        super.show();
        if (titleTv.getText().toString().isEmpty()) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setVisibility(View.VISIBLE);
        }

        if (messageTv.getText().toString().isEmpty()) {
            messageTv.setVisibility(View.GONE);
        } else {
            messageTv.setVisibility(View.VISIBLE);
        }

        int negativeState = negativeBtn.getVisibility();
        int positiveState = positiveBtn.getVisibility();
        int centerState = centerBtn.getVisibility();

        btnLl.setVisibility(View.VISIBLE);
        if (negativeState == View.GONE && centerState == View.GONE && positiveState == View.GONE) {
            btnLl.setVisibility(View.GONE);
            bottomLineView.setVisibility(View.GONE);
        } else {
            btnLl.setVisibility(View.VISIBLE);
            bottomLineView.setVisibility(View.VISIBLE);
        }

        if (negativeState == View.VISIBLE && centerState == View.GONE && positiveState == View.GONE
                || negativeState == View.GONE && centerState == View.VISIBLE && positiveState == View.GONE
                || negativeState == View.GONE && centerState == View.GONE && positiveState == View.VISIBLE) {
            line1View.setVisibility(View.GONE);
            line2View.setVisibility(View.GONE);
        } else if (negativeState == View.VISIBLE && centerState == View.VISIBLE && positiveState == View.GONE) {
            line1View.setVisibility(View.VISIBLE);
            line2View.setVisibility(View.GONE);
        } else if (negativeState == View.GONE && centerState == View.VISIBLE && positiveState == View.VISIBLE
                || negativeState == View.VISIBLE && centerState == View.GONE && positiveState == View.VISIBLE) {
            line1View.setVisibility(View.GONE);
            line2View.setVisibility(View.VISIBLE);
        } else {
            line1View.setVisibility(View.VISIBLE);
            line2View.setVisibility(View.VISIBLE);
        }

    }

    private View.OnClickListener cancelListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            AppDialog.this.dismiss();
        }
    };

    /**
     * 自定义对话框点击监听
     */
    public interface OnClickListener {

        /**
         * 点击
         */
        void onClick(AppDialog appDialog);
    }

}
