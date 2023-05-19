package com.example.app_supportpolywork.view.main_activity.listCvApllied;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_supportpolywork.databinding.ActivityLoginBinding;
import com.example.app_supportpolywork.databinding.FragmentCreateCvRootBinding;

public class ListCvAppliedActivity extends AppCompatActivity {

    private FragmentCreateCvRootBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = FragmentCreateCvRootBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }
}