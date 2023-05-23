package com.example.app_supportpolywork.view.main_activity.listCvApllied;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

public class ListCvAppliedActivtiy extends AppCompatActivity {

    private ActivityListCvAppliedBinding mBinding;

    private ListCvAppliedAdapter mListCvAppliedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityListCvAppliedBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

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
                CommonUtil.makeToast(ListCvAppliedActivtiy.this, e.getMessage());
            }
        });
    }

    private void setupCVApplied() {
        CVAppliedManager.getInstance().getCVApplied(new TaskListener() {
            @Override
            public void onSuccess(Object o) {
                List<CVApplied> cvApplieds = (List<CVApplied>) o;
                Collections.reverse(cvApplieds);
                User user = ShareFileUtil.getUser(ListCvAppliedActivtiy.this);

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
                CommonUtil.makeToast(ListCvAppliedActivtiy.this, e.getMessage());
            }
        });
    }

    private void onClickView(){
        mBinding.buttonBack.setOnClickListener(view -> {
            finish();
        });
    }
}