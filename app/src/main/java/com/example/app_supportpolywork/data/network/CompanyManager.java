package com.example.app_supportpolywork.data.network;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;

import com.example.app_supportpolywork.App;
import com.example.app_supportpolywork.data.model.Company;
import com.example.app_supportpolywork.data.model.Job;
import com.example.app_supportpolywork.data.model.User;
import com.example.app_supportpolywork.util.TaskListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CompanyManager {
    private static CompanyManager instance;

    private CompanyManager() {
    }

    public static CompanyManager getInstance() {
        synchronized (CompanyManager.class) {
            if (instance == null) {
                instance = new CompanyManager();
            }
        }
        return instance;
    }

    private List<Company> getCompanyFromJson(JSONArray data) throws JSONException {
        List<Company> result = new ArrayList<>();
        int size = data.length();
        for (int i = 0; i < size; i++) {
            JSONObject obj = data.getJSONObject(i);
            Company company = new Company();
            company.setId(obj.getString(Network.ID_KEY));

            if (obj.has("company_code")) {
                company.setCompanyCode(obj.getString("company_code"));
            }
            if (obj.has("company_name")) {
                company.setCompanyName(obj.getString("company_name"));

            }
            if (obj.has("logo")) {
                company.setAvatar(obj.getString("logo"));
            }

            result.add(company);
        }
        return result;
    }

    public void getCompany(TaskListener listener) {
        MyCallback<ResponseBody> myCallback = new MyCallback<ResponseBody>(listener) {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                JSONArray data = getDataJSONArray(response);
                if (data == null) return;
                try {
                    List<Company> company = getCompanyFromJson(data);
                    listener.onSuccess(company);
                } catch (JSONException e) {
                    listener.onError(new Exception(App.ERROR_MESSAGE));
                }
            }
        };
        Call<ResponseBody> call = Network.mService.getCompany();
        call.enqueue(myCallback);
    }
}
