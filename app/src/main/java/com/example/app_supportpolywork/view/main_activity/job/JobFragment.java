package com.example.app_supportpolywork.view.main_activity.job;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavDirections;

import com.example.app_supportpolywork.BaseFragment;
import com.example.app_supportpolywork.R;
import com.example.app_supportpolywork.data.model.Company;
import com.example.app_supportpolywork.data.model.Job;
import com.example.app_supportpolywork.data.network.CompanyManager;
import com.example.app_supportpolywork.data.network.JobManager;
import com.example.app_supportpolywork.databinding.FragmentJobBinding;
import com.example.app_supportpolywork.util.CommonUtil;
import com.example.app_supportpolywork.util.TaskListener;
import com.example.app_supportpolywork.view.main_activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class JobFragment extends BaseFragment implements JobAdapter.JobAdapterListener {
    private FragmentJobBinding mBinding;
    private JobAdapter mJobAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentJobBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).openBottomNav();
        mJobAdapter = new JobAdapter(this);
        mBinding.rcvJobs.setAdapter(mJobAdapter);
        setUpCompany();
        setupSearchJobs();
    }

    private void setupSearchJobs() {
        mBinding.edtSearchText.setOnClickListener(v ->
                mNavController.navigate(R.id.action_jobFragment_to_jobSearchingFragment));
    }

    private void setupJobs() {
        JobManager.getInstance().getJob(new TaskListener() {
            @Override
            public void onSuccess(Object o) {
                List<Job> listJob = (List<Job>) o;
                Collections.reverse(listJob);
                mJobAdapter.submitList(listJob);
            }

            @Override
            public void onError(Exception e) {
                CommonUtil.makeToast(getContext(), e.getMessage());
            }
        });
    }

    private void setUpCompany() {
        CompanyManager.getInstance().getCompany(new TaskListener() {
            @Override
            public void onSuccess(Object o) {
                mJobAdapter.listCompany = (ArrayList<Company>) o;
                setupJobs();
            }

            @Override
            public void onError(Exception e) {
                CommonUtil.makeToast(getContext(), e.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClickJobItem(Job job, String companyName) {
        mNavController.navigate(JobFragmentDirections.actionJobFragmentToJobDetailFragment(job, companyName));
    }
}