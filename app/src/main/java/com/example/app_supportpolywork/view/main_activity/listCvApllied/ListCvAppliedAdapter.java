package com.example.app_supportpolywork.view.main_activity.listCvApllied;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_supportpolywork.R;
import com.example.app_supportpolywork.data.model.CVApplied.CVApplied;
import com.example.app_supportpolywork.data.model.Company;
import com.example.app_supportpolywork.databinding.LayoutJobAppliedBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class ListCvAppliedAdapter extends ListAdapter<CVApplied, ListCvAppliedAdapter.JobViewHolder> {

    private String companyName;

    public ArrayList<Company> listCompany;


    private final CVAppliedAdapterListener mCvAppliedAdapterListener;

    protected ListCvAppliedAdapter(CVAppliedAdapterListener listener) {
        super(CVApplied.sDiffCallback);
        mCvAppliedAdapterListener = listener;
    }

    @NonNull
    @Override
    public ListCvAppliedAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutJobAppliedBinding binding = LayoutJobAppliedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JobViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCvAppliedAdapter.JobViewHolder holder, int position) {
        CVApplied cvApplied = getItem(position);
        if (cvApplied != null) {
            Picasso.get().load(cvApplied.getJob_cvApplied().getAvatar()).placeholder(R.drawable.img_sample).error(R.drawable.img_sample)
                    .into(holder.mBinding.imvJobAvatar);

            holder.mBinding.tvJobTitle.setText(cvApplied.getJob_cvApplied().getTitle());
            if (cvApplied.getUserJob_cvApplied().getStatus() == 1) {
                holder.mBinding.tvExpiry.setText("Đã duyệt !!!");
            } else {
                holder.mBinding.tvExpiry.setText("Chưa xét duyệt !!!");
            }

            listCompany.forEach(company -> {
                if (Objects.equals(company.getCompanyCode(), cvApplied.getUserJob_cvApplied().getCompany_code())) {
                    holder.mBinding.tvNameCompany.setText(company.getCompanyName());
                    companyName = company.getCompanyName();
                }
            });
        }
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        private final LayoutJobAppliedBinding mBinding;

        public JobViewHolder(@NonNull LayoutJobAppliedBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

    public interface CVAppliedAdapterListener {
        void onClickJobItem(CVApplied cvApplied);
    }
}
