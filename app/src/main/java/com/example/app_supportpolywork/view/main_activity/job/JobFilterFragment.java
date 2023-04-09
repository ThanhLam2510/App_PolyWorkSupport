package com.example.app_supportpolywork.view.main_activity.job;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_supportpolywork.BaseFragment;
import com.example.app_supportpolywork.data.model.support_model.ExperienceFilter;
import com.example.app_supportpolywork.data.model.support_model.Filter;
import com.example.app_supportpolywork.data.model.support_model.Position;
import com.example.app_supportpolywork.data.model.support_model.Salary;
import com.example.app_supportpolywork.data.model.support_model.Technology;
import com.example.app_supportpolywork.databinding.FragmentJobFilterBinding;


public class JobFilterFragment extends BaseFragment {
    private FragmentJobFilterBinding mBinding;

    private SpinnerAdapter mTechnologyAdapter;
    private SpinnerAdapter mPositionAdapter;
    private SpinnerAdapter mSalaryAdapter;
    private SpinnerAdapter mExpAdapter;
    private Filter mFilter;
    private String[] mArrPosition;
    private String[] mArrTechnology;
    private String[] mArrSalary;
    private String[] mArrExp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentJobFilterBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFilter = JobFilterFragmentArgs.fromBundle(getArguments()).getFilter();
        setupToolbar();
        setupSpnExp();
        setupSpnSalary();
        setupSpnPosition();
        setupSpnTechnology();
        setupSearchBtn();
        setupCancelBtn();
        fillJobFilter();
    }

    private void fillJobFilter() {
        mBinding.spnPosition.setSelection(getPositionOfJobPosition(mFilter.getPosition()));
        mBinding.spnTechnology.setSelection(getPositionOfJobTechnology(mFilter.getTechnology()));
        mBinding.spnSalary.setSelection(getPositionOfJobSalary(mFilter.getSalary()));
        mBinding.spnExp.setSelection(getExpOfJobSalary(mFilter.getExperienceFilter()));
    }

    private int getExpOfJobSalary(ExperienceFilter experienceFilter) {
        if (experienceFilter == null) {
            return 0;
        }
        for (int i = 0; i < mArrExp.length; i++) {
            if (mArrExp[i].equals(experienceFilter.value)) {
                return i;
            }
        }
        return 0;
    }

    private int getPositionOfJobSalary(Salary salary) {
        if (salary == null) {
            return 0;
        }
        for (int i = 0; i < mArrSalary.length; i++) {
            if (mArrSalary[i].equals(salary.value)) {
                return i;
            }
        }
        return 0;
    }

    private int getPositionOfJobTechnology(Technology technology) {
        if (technology == null) {
            return 0;
        }
        for (int i = 0; i < mArrTechnology.length; i++) {
            if (mArrTechnology[i].equals(technology.value)) {
                return i;
            }
        }
        return 0;
    }

    private int getPositionOfJobPosition(Position position) {
        if (position == null) {
            return 0;
        }
        for (int i = 0; i < mArrPosition.length; i++) {
            if (mArrPosition[i].equals(position.value)) {
                return i;
            }
        }
        return 0;
    }

    private void setupCancelBtn() {
        mBinding.btnCancel.setOnClickListener(v -> {
            mFilter.setPosition(Position.ALL);
            mFilter.setTechnology(Technology.ALL);
            mFilter.setSalary(Salary.ALL);
            mFilter.setExperienceFilter(ExperienceFilter.ALL);
            mNavController.navigate(JobFilterFragmentDirections.actionJobFilterFragmentToJobSearchingFragment2().setFilter(mFilter));
        });
    }

    private void setupSearchBtn() {
        mBinding.btnOk.setOnClickListener(v -> {
            mNavController.navigate(JobFilterFragmentDirections.actionJobFilterFragmentToJobSearchingFragment2().setFilter(mFilter));
        });
    }

    private void setupSpnExp() {
        mArrExp = getStringExp();
        mExpAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, mArrExp);
        mBinding.spnExp.setAdapter(mExpAdapter);
        mBinding.spnExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFilter.setExperienceFilter(getExpFromString(mArrExp[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFilter.setExperienceFilter(ExperienceFilter.ALL);
            }
        });
    }

    private ExperienceFilter getExpFromString(String s) {
        for (int i = 0; i < ExperienceFilter.values().length; i++) {
            if (s.equals(ExperienceFilter.values()[i].value)) {
                return ExperienceFilter.values()[i];
            }
        }
        return ExperienceFilter.ALL;
    }

    private String[] getStringExp() {
        String[] strings = new String[ExperienceFilter.values().length];
        for (int i = 0; i < ExperienceFilter.values().length; i++) {
            strings[i] = ExperienceFilter.values()[i].value;
        }
        return strings;
    }

    private void setupSpnSalary() {
        mArrSalary = getStringSalary();
        mSalaryAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, mArrSalary);
        mBinding.spnSalary.setAdapter(mSalaryAdapter);
        mBinding.spnSalary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFilter.setSalary(getSalaryFromString(mArrSalary[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFilter.setSalary(Salary.ALL);
            }
        });
    }

    private Salary getSalaryFromString(String s) {
        for (int i = 0; i < Salary.values().length; i++) {
            if (s.equals(Salary.values()[i].value)) {
                return Salary.values()[i];
            }
        }
        return Salary.ALL;
    }

    private String[] getStringSalary() {
        String[] strings = new String[Salary.values().length];
        for (int i = 0; i < Salary.values().length; i++) {
            strings[i] = Salary.values()[i].value;
        }
        return strings;
    }
    private void setupSpnPosition() {
        mArrPosition = getStringPosition();
        mPositionAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, mArrPosition);
        mBinding.spnPosition.setAdapter(mPositionAdapter);
        mBinding.spnPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = mArrPosition[position];
                mFilter.setPosition(getPositionFromString(s));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFilter.setPosition(Position.ALL);
            }
        });
    }

    private Position getPositionFromString(String s) {
        for (int i = 0; i < Position.values().length; i++) {
            if (s.equals(Position.values()[i].value)) {
                return Position.values()[i];
            }
        }
        return Position.ALL;
    }

    private String[] getStringPosition() {
        String[] strings = new String[Position.values().length];
        for (int i = 0; i < Position.values().length; i++) {
            strings[i] = Position.values()[i].value;
        }
        return strings;
    }

    private void setupSpnTechnology() {
        mArrTechnology = getStringTechnology();
        mTechnologyAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, mArrTechnology);
        mBinding.spnTechnology.setAdapter(mTechnologyAdapter);
        mBinding.spnTechnology.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFilter.setTechnology(getTechnologyFromString(mArrTechnology[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mFilter.setTechnology(Technology.ALL);
            }
        });
    }

    private Technology getTechnologyFromString(String s) {
        for (int i = 0; i < Technology.values().length; i++) {
            if (s.equals(Technology.values()[i].value)) {
                return Technology.values()[i];
            }
        }
        return Technology.ALL;
    }

    private String[] getStringTechnology() {
        String[] strings = new String[Technology.values().length];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = Technology.values()[i].value;
        }
        return strings;
    }

    private void setupToolbar() {
        mBinding.toolbar.setNavigationOnClickListener(v -> mNavController.popBackStack());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}