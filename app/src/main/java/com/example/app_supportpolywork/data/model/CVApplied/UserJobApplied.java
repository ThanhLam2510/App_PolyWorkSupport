package com.example.app_supportpolywork.data.model.CVApplied;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

public class UserJobApplied implements Serializable {
    @SerializedName("_id")
    private String _id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("cv_id")

    private String cv_id;
    @SerializedName("job_id")
    private String job_id;

    @SerializedName("company_code")

    private String company_code;

    @SerializedName("__v")

    private int __v;

    @SerializedName("status")

    private int status;

    @SerializedName("updated_at")

    private String updated_at;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCv_id() {
        return cv_id;
    }

    public void setCv_id(String cv_id) {
        this.cv_id = cv_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public UserJobApplied() {
    }

    public UserJobApplied(String _id, String user_id, String cv_id, String job_id, String company_code, int __v, int status, String updated_at) {
        this._id = _id;
        this.user_id = user_id;
        this.cv_id = cv_id;
        this.job_id = job_id;
        this.company_code = company_code;
        this.__v = __v;
        this.status = status;
        this.updated_at = updated_at;
    }
}