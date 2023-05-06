package com.example.app_supportpolywork.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.io.Serializable;
import java.util.Objects;

public class Company implements Serializable {
    private String id;
    private String companyCode;

    private String companyName;

    private String avatar;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company job = (Company) o;
        return Objects.equals(getAvatar(), job.getAvatar()) && Objects.equals(getCompanyName(), job.getCompanyName()) && Objects.equals(getCompanyCode(), job.getCompanyCode());
    }

    public static DiffUtil.ItemCallback<Company> sDiffCallback = new DiffUtil.ItemCallback<Company>() {
        @Override
        public boolean areItemsTheSame(@NonNull Company oldItem, @NonNull Company newItem) {
            return Objects.equals(oldItem.id, newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Company oldItem, @NonNull Company newItem) {
            return oldItem.equals(newItem);
        }
    };
}
