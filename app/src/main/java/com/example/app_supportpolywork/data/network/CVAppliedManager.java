package com.example.app_supportpolywork.data.network;

import androidx.annotation.NonNull;

import com.example.app_supportpolywork.App;
import com.example.app_supportpolywork.data.model.CV;
import com.example.app_supportpolywork.data.model.CVApplied.CVApplied;
import com.example.app_supportpolywork.data.model.Job;
import com.example.app_supportpolywork.data.model.User;
import com.example.app_supportpolywork.data.model.CVApplied.UserJobApplied;
import com.example.app_supportpolywork.util.TaskListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class CVAppliedManager {
    private static CVAppliedManager instance;

    private CVAppliedManager() {
    }

    public static CVAppliedManager getInstance() {
        synchronized (CVAppliedManager.class) {
            if (instance == null) {
                instance = new CVAppliedManager();
            }
        }
        return instance;
    }

    public void getCVApplied(TaskListener listener) {
        MyCallback<ResponseBody> myCallback = new MyCallback<ResponseBody>(listener) {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                JSONArray data = getDataJSONArray(response);
                if (data == null) return;
                try {
                    List<CVApplied> cvAppliedList = getUserJobListFromJsonObject(data);
                    listener.onSuccess(cvAppliedList);
                } catch (JSONException e) {
                    listener.onError(new Exception(App.ERROR_MESSAGE));
                }
            }
        };
        Call<ResponseBody> call = Network.mService.getCvApplied();
        call.enqueue(myCallback);
    }

    private List<CVApplied> getUserJobListFromJsonObject(JSONArray data) throws JSONException {
        Gson gson = new Gson();

        List<CVApplied> result = new ArrayList<>();
        int size = data.length();
        for (int i = 0; i < size; i++) {
            JSONObject obj = data.getJSONObject(i);

            UserJobApplied userJobApplied =gson.fromJson (obj.getString("userjob"), UserJobApplied.class);
            User user =gson.fromJson (obj.getString("user"),User.class);
            CV cv =gson.fromJson (obj.getString("documentCV"),CV.class);
            Job job =gson.fromJson (obj.getString("job"),Job.class);

            CVApplied cvApplied = new CVApplied();
            cvApplied.setUserJob_cvApplied(userJobApplied);
            cvApplied.setJob_cvApplied(job);
            cvApplied.setUser_cvApplied(user);
            cvApplied.setCv_cvApplied(cv);
            result.add(cvApplied);
        }
        return result;
    }
}
