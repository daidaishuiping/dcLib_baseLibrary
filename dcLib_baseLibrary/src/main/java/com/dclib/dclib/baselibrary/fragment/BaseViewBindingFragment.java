package com.dclib.dclib.baselibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * fragment基类
 * Created on 2018/5/10.
 *
 * @author dc
 */
public abstract class BaseViewBindingFragment<T extends ViewBinding> extends BaseFragment {

    public T viewBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        bindView(inflater, container);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initClick();
    }

    /**
     * 绑定视图
     */
    private void bindView(LayoutInflater inflater, ViewGroup container) {
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            Class<ViewBinding> cls = (Class) type.getActualTypeArguments()[0];
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            viewBinding = (T) inflate.invoke(null, inflater, container, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (viewBinding != null) {
            viewBinding = null;
        }
    }
}