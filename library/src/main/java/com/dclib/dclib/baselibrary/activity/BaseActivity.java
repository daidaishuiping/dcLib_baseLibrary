package com.dclib.dclib.baselibrary.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.dclib.dclib.baselibrary.R;
import com.dclib.dclib.baselibrary.util.AppManager;

/**
 * 基础类Activity
 * Created on 2018/5/10.
 *
 * @author daichao
 */
public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.i("BaseActivity->" + getClass().getSimpleName());

        mContext = this;
        AppManager.getAppManager().addActivity(this);
        setStatusBar();
    }

    /**
     * 设置状态栏
     */
    public void setStatusBar() {
        BarUtils.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white), false);
        BarUtils.setStatusBarLightMode(this, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }
}