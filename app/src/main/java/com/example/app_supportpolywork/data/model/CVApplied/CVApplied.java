package com.example.app_supportpolywork.data.model.CVApplied;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.app_supportpolywork.data.model.CV;
import com.example.app_supportpolywork.data.model.Job;
import com.example.app_supportpolywork.data.model.User;

import java.io.Serializable;
import java.util.Objects;

public class CVApplied implements Serializable {
    private UserJobApplied userJob_Applied_cvApplied;
    private User user_cvApplied;
    private CV cv_cvApplied;
    private Job job_cvApplied;

    public UserJobApplied getUserJob_cvApplied() {
        return userJob_Applied_cvApplied;
    }

    public void setUserJob_cvApplied(UserJobApplied userJob_Applied_cvApplied) {
        this.userJob_Applied_cvApplied = userJob_Applied_cvApplied;
    }

    public User getUser_cvApplied() {
        return user_cvApplied;
    }

    public void setUser_cvApplied(User user_cvApplied) {
        this.user_cvApplied = user_cvApplied;
    }

    public CV getCv_cvApplied() {
        return cv_cvApplied;
    }

    public void setCv_cvApplied(CV cv_cvApplied) {
        this.cv_cvApplied = cv_cvApplied;
    }

    public Job getJob_cvApplied() {
        return job_cvApplied;
    }

    public void setJob_cvApplied(Job job_cvApplied) {
        this.job_cvApplied = job_cvApplied;
    }

    public CVApplied() {
    }

    public CVApplied(UserJobApplied userJob_Applied_cvApplied, User user_cvApplied, CV cv_cvApplied, Job job_cvApplied) {
        this.userJob_Applied_cvApplied = userJob_Applied_cvApplied;
        this.user_cvApplied = user_cvApplied;
        this.cv_cvApplied = cv_cvApplied;
        this.job_cvApplied = job_cvApplied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CVApplied cvApplied = (CVApplied) o;
        return Objects.equals(getCv_cvApplied(), cvApplied.getCv_cvApplied()) && Objects.equals(getJob_cvApplied(), cvApplied.getJob_cvApplied()) && Objects.equals(getUser_cvApplied(), cvApplied.getUser_cvApplied()) && Objects.equals(getUserJob_cvApplied(), cvApplied.getUser_cvApplied());
    }

    public static DiffUtil.ItemCallback<CVApplied> sDiffCallback = new DiffUtil.ItemCallback<CVApplied>() {

        @Override
        public boolean areItemsTheSame(@NonNull CVApplied oldItem, @NonNull CVApplied newItem) {
            return Objects.equals(oldItem.getUser_cvApplied().getId(), newItem.getUser_cvApplied().getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull CVApplied oldItem, @NonNull CVApplied newItem) {
            return oldItem.equals(newItem);
        }
    };
}
