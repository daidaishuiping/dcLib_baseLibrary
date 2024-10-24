package com.dclib.dclib.baselibrary;

import android.view.View;

import com.dclib.dclib.baselibrary.activity.BaseViewBindingActivity;
import com.dclib.dclib.baselibrary.databinding.ActivityMainBinding;
import com.dclib.dclib.baselibrary.view.dialog.AppDialog;

public class MainActivity extends BaseViewBindingActivity<ActivityMainBinding> {

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {
        viewBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        viewBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AppDialog(mContext).title("请确认信息").message("是否删除")
                        .negativeBtn("取消", new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {

                            }
                        })
                        .positiveBtn("确定", new AppDialog.OnClickListener() {
                            @Override
                            public void onClick(AppDialog appDialog) {

                            }
                        })
                        .show();
            }
        });
    }
}