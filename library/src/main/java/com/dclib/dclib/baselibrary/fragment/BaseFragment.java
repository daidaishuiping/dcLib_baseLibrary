package com.dclib.dclib.baselibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.LogUtils;

/**
 * fragment基类
 * Created on 2018/5/10.
 *
 * @author dc
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.i("BaseFragment->" + getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}