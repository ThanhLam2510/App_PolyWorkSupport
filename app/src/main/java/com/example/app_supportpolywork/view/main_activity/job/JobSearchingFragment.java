package com.example.app_supportpolywork.view.main_activity.job;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_supportpolywork.BaseFragment;
import com.example.app_supportpolywork.data.model.Company;
import com.example.app_supportpolywork.data.model.Job;
import com.example.app_supportpolywork.data.model.support_model.ExperienceFilter;
import com.example.app_supportpolywork.data.model.support_model.Filter;
import com.example.app_supportpolywork.data.model.support_model.FilterField;
import com.example.app_supportpolywork.data.model.support_model.Position;
import com.example.app_supportpolywork.data.model.support_model.Salary;
import com.example.app_supportpolywork.data.model.support_model.Technology;
import com.example.app_supportpolywork.data.network.JobManager;
import com.example.app_supportpolywork.databinding.FragmentJobSearchingBinding;
import com.example.app_supportpolywork.util.CommonUtil;
import com.example.app_supportpolywork.util.TaskListener;
import com.example.app_supportpolywork.view.main_activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class JobSearchingFragment extends BaseFragment implements JobFilterContentAdapter.OnClickFilterFieldListener, JobAdapter.JobAdapterListener {
    private FragmentJobSearchingBinding mBinding;

    private static String mQueryString = "";

    private JobAdapter mJobAdapter;
    private List<Job> mFilterJobList;
    private List<Job> mOriginJobList;

    private Filter mFilter;
    private JobFilterContentAdapter mJobFilterContentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentJobSearchingBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) requireActivity()).closeBottomNav();
        loadJobs();
        getFilter();
        setupFilterContentRcv();
        setupToolbar();
    }

    private void setupFilterContentRcv() {
        if (mFilter.getTechnology() != Technology.ALL || mFilter.getPosition() != Position.ALL || mFilter.getSalary() != Salary.ALL || mFilter.getExperienceFilter() != ExperienceFilter.ALL) {
            List<FilterField> filterFieldList = getFilterFields();
            mJobFilterContentAdapter = new JobFilterContentAdapter(this);
            mJobFilterContentAdapter.submitList(filterFieldList);
            mBinding.layoutFilterContent.setVisibility(View.VISIBLE);
            mBinding.rcvFilterContents.setAdapter(mJobFilterContentAdapter);
        } else {
            mBinding.layoutFilterContent.setVisibility(View.GONE);
        }
    }

    private List<FilterField> getFilterFields() {
        List<FilterField> result = new ArrayList<>();

        if (mFilter.getPosition() != Position.ALL) {
            result.add(mFilter.getPosition());
        }

        if (mFilter.getTechnology() != Technology.ALL) {
            result.add(mFilter.getTechnology());
        }

        if (mFilter.getSalary() != Salary.ALL) {
            result.add(mFilter.getSalary());
        }

        if (mFilter.getExperienceFilter() != ExperienceFilter.ALL) {
            result.add(mFilter.getExperienceFilter());
        }
        return result;
    }

    private void getFilter() {
        Filter filter = JobSearchingFragmentArgs.fromBundle(getArguments()).getFilter();
        if (filter != null) {
            mFilter = filter;
        } else {
            mFilter = new Filter(Position.ALL, Technology.ALL, Salary.ALL, ExperienceFilter.ALL);
        }
    }

    private void setupToolbar() {
        mBinding.imvBack.setOnClickListener(v -> mNavController.popBackStack());
        mBinding.imvClear.setOnClickListener(v -> mBinding.edtSearching.setText(""));
        mBinding.imvFilter.setOnClickListener(v -> {
            mNavController.navigate(JobSearchingFragmentDirections.actionJobSearchingFragmentToJobFilterFragment().setFilter(mFilter));
        });

        mBinding.edtSearching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mQueryString = s.toString();
                mFilterJobList = getJobListByString(mQueryString);
                if (mFilterJobList.size() == 0) {
                    mBinding.tvHint.setVisibility(View.VISIBLE);
                } else {
                    mBinding.tvHint.setVisibility(View.GONE);
                }
                mJobAdapter.submitList(mFilterJobList);
            }
        });
    }

    private List<Job> getJobListByString(String s) {
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < mOriginJobList.size(); i++) {
            Job job = mOriginJobList.get(i);
            if (passJobTitle(job, s) && passJobPosition(job) && passJobTechnology(job) && passJobSalary(job) && passJobExp(job)) {
                jobs.add(job);
            }
        }
        return jobs;
    }

    private boolean passJobTechnology(Job job) {
        if (mFilter.getTechnology() == Technology.ALL) return true;
        return job.getTechnology().equals(mFilter.getTechnology().value);
    }

    private boolean passJobPosition(Job job) {
        if (mFilter.getPosition() == Position.ALL) return true;
        return job.getPosition().equals(mFilter.getPosition().value);
    }

    //done
    private boolean passJobSalary(Job job) {
        switch (mFilter.getSalary()) {
            case ALL:
                return true;
            case SELECT1:
                return job.getEndSalary() <= 3000000;
            case SELECT2:
                return job.getStartSalary() >= 3000000 && job.getEndSalary() < 5000000 || job.getStartSalary() == 5000000 || job.getEndSalary() == 5000000 ;
            case SELECT3:
                return job.getStartSalary() >= 5000000 && job.getEndSalary() < 7000000 || job.getStartSalary() == 7000000 ;
            case SELECT4:
                return job.getStartSalary() >= 7000000 && job.getStartSalary() <= 10000000 || job.getEndSalary() == 10000000;
            case SELECT5:
                return job.getStartSalary() >= 10000000 && job.getEndSalary() < 12000000 || job.getStartSalary() == 12000000 ;
            case SELECT6:
                return job.getStartSalary() >= 12000000 && job.getEndSalary() < 15000000 || job.getStartSalary() == 15000000 ;
            case SELECT7:
                return job.getStartSalary() >= 15000000 && job.getEndSalary() < 20000000 || job.getStartSalary() == 20000000 ;
            case SELECT8:
                return job.getStartSalary() >= 20000000 && job.getEndSalary() < 25000000 || job.getStartSalary() == 25000000 ;
            case SELECT9:
                return job.getStartSalary() >= 25000000 && job.getEndSalary() < 30000000 || job.getStartSalary() == 30000000 ;
            case SELECT10:
                return job.getStartSalary() >= 30000000;
        }
        return true;
    }

    //done
    private boolean passJobExp(Job job) {
        switch (mFilter.getExperienceFilter()) {
            case ALL:
                return true;
            case NO_EXP:
                return job.getExperience().toLowerCase().contains("Chưa có kinh nghiệm");
            case LESS_1:
                return job.getExperience().contains("< 1");
            case Y_1:
                return job.getExperience().contains("1");
            case Y_2:
                return job.getExperience().contains("2");
            case Y_3:
                return job.getExperience().contains("3");
            case Y_4:
                return job.getExperience().contains("4");
            case Y_5:
                return job.getExperience().contains("5");
            case OVER_5:
                return job.getExperience().contains("> 5");
        }
        return true;
    }

    private boolean passJobTitle(Job job, String s) {
        return job.getTitle().toLowerCase().contains(s.toLowerCase());
    }

    public void loadJobs() {
        mJobAdapter = new JobAdapter(this);
        mBinding.rcvJobs.setAdapter(mJobAdapter);
        JobManager.getInstance().getJob(new TaskListener() {
            @Override
            public void onSuccess(Object o) {
                mOriginJobList = (List<Job>) o;
                mJobAdapter.submitList(mOriginJobList);
                mBinding.edtSearching.setEnabled(true);
                mBinding.edtSearching.setText(mQueryString);
            }

            @Override
            public void onError(Exception e) {
                CommonUtil.makeToast(requireContext(), e.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClearFilterField(FilterField filterField) {
        if (filterField instanceof Position) {
            mFilter.setPosition(Position.ALL);
        } else if (filterField instanceof Technology) {
            mFilter.setTechnology(Technology.ALL);
        } else if (filterField instanceof Salary) {
            mFilter.setSalary(Salary.ALL);
        } else if (filterField instanceof ExperienceFilter) {
            mFilter.setExperienceFilter(ExperienceFilter.ALL);
        }

        mFilterJobList = getJobListByString(mQueryString);
        if (mFilterJobList.size() == 0) {
            mBinding.tvHint.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvHint.setVisibility(View.GONE);
        }
        mJobAdapter.submitList(mFilterJobList);

        List<FilterField> filterFieldList = getFilterFields();
        if (filterFieldList.size() > 0) {
            mJobFilterContentAdapter.submitList(filterFieldList);
        } else {
            mBinding.layoutFilterContent.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClickJobItem(Job job) {
        mNavController.navigate(JobSearchingFragmentDirections.actionJobSearchingFragmentToJobDetailFragment(job));
    }
}