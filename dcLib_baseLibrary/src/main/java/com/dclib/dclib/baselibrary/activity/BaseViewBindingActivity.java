package com.dclib.dclib.baselibrary.activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.viewbinding.ViewBinding;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * 基础类Activity
 * Created on 2018/5/10.
 *
 * @author daichao
 */
public abstract class BaseViewBindingActivity<T extends ViewBinding> extends BaseActivity {

    public T viewBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindView();

        initView();
        initClick();
        initData();
    }

    /**
     * 绑定视图
     */
    private void bindView() {
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            Class cls = (Class) type.getActualTypeArguments()[0];
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class);
            viewBinding = (T) inflate.invoke(null, getLayoutInflater());
            setContentView(viewBinding.getRoot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化界面
     */
    public abstract void initView();

    /**
     * 初始化事件
     */
    public abstract void initClick();

    /**
     * 初始化界面
     */
    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewBinding != null) {
            viewBinding = null;
        }
    }
}