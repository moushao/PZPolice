package com.pvirtech.pzpolice.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.ui.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/3/26.
 * 我的任务-任务内容
 */

public class TaskTrajectoryFragment extends BaseFragment {


    Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_task_trajectory, container, false);
        mContext = getActivity();
        initView(view);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
