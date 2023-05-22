package com.example.app_supportpolywork.view.main_activity.listCvApllied;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_supportpolywork.BaseFragment;
import com.example.app_supportpolywork.data.model.CVApplied.CVApplied;
import com.example.app_supportpolywork.data.model.Company;
import com.example.app_supportpolywork.data.model.User;
import com.example.app_supportpolywork.data.network.CVAppliedManager;
import com.example.app_supportpolywork.data.network.CompanyManager;
import com.example.app_supportpolywork.databinding.ActivityListCvAppliedBinding;
import com.example.app_supportpolywork.util.CommonUtil;
import com.example.app_supportpolywork.util.ShareFileUtil;
import com.example.app_supportpolywork.util.TaskListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ListCvAppliedFrg extends BaseFragment {

    private ActivityListCvAppliedBinding mBinding;

    private ListCvAppliedAdapter mListCvAppliedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = ActivityListCvAppliedBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListCvAppliedAdapter = new ListCvAppliedAdapter(cvApplied -> {});
        mBinding.rcvCV.setAdapter(mListCvAppliedAdapter);

        onClickView();
        setUpCompany();
    }

    private void setUpCompany() {
        CompanyManager.getInstance().getCompany(new TaskListener() {
            @Override
            public void onSuccess(Object o) {
                mListCvAppliedAdapter.listCompany = (ArrayList<Company>) o;
                setupCVApplied();
            }

            @Override
            public void onError(Exception e) {
                CommonUtil.makeToast(getContext(), e.getMessage());
            }
        });
    }

    private void setupCVApplied() {
        CVAppliedManager.getInstance().getCVApplied(new TaskListener() {
            @Override
            public void onSuccess(Object o) {
                List<CVApplied> cvApplieds = (List<CVApplied>) o;
                Collections.reverse(cvApplieds);
                User user = ShareFileUtil.getUser(requireContext());

                List<CVApplied> cvAppliedListAdapter = new  ArrayList();

                cvApplieds.forEach(cvApplied -> {
                    if (Objects.equals(user.getId(), cvApplied.getUser_cvApplied().getId())){
                        cvAppliedListAdapter.add(cvApplied);
                    }
                });


                mListCvAppliedAdapter.submitList(cvAppliedListAdapter);
            }

            @Override
            public void onError(Exception e) {
                CommonUtil.makeToast(requireContext(), e.getMessage());
            }
        });
    }

    private void onClickView(){
        mBinding.buttonBack.setOnClickListener(view -> {
            mNavController.popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}