package com.dclib.dclib.baselibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dclib.dclib.baselibrary.activity.BaseViewBindingActivity;
import com.dclib.dclib.baselibrary.databinding.ActivityMainBinding;
import com.dclib.dclib.baselibrary.view.dialog.AppDialog;
import com.dclib.dclib.baselibrary.vo.EventBusVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseViewBindingActivity<ActivityMainBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {

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
                                EventBus.getDefault().post(new EventBusVo<>("abc", "测试content"));
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void initData() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void watchEvent(EventBusVo<String> eventBusVo) {
        Toast.makeText(mContext, eventBusVo.getContent(), Toast.LENGTH_SHORT).show();
    }
}