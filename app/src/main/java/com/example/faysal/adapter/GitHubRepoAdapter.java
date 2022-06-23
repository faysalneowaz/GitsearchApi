package com.example.faysal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.faysal.R;
import com.example.faysal.model.GithubRepoModel;

import java.util.List;

public class GitHubRepoAdapter extends RecyclerView.Adapter<GitHubRepoAdapter.ViewHolderDeliver>{

    List<GithubRepoModel> githubRepoModelList;
    Context context;

    public GitHubRepoAdapter(List<GithubRepoModel> githubRepoModelList, Context context) {
        this.githubRepoModelList = githubRepoModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public GitHubRepoAdapter.ViewHolderDeliver onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_recycleview, parent, false);
        ViewHolderDeliver viewHolderDeliver = new ViewHolderDeliver(view);
        return viewHolderDeliver;
    }

    @Override
    public void onBindViewHolder(@NonNull GitHubRepoAdapter.ViewHolderDeliver holder, int position) {

//        startCount, watchCount, language, createdAt, updatedAt,pushedAt, openIssues
        holder.startCount.setText(String.valueOf(githubRepoModelList.get(position).getStarCount()));
        holder.watchCount.setText(String.valueOf(githubRepoModelList.get(position).getWatchCount()));
        holder.language.setText(githubRepoModelList.get(position).getLanguage());
        holder.createdAt.setText(githubRepoModelList.get(position).getCreatedAt());
        holder.updatedAt.setText(githubRepoModelList.get(position).getUpdatedAt());
        holder.pushedAt.setText(githubRepoModelList.get(position).getPushedAt());
        holder.openIssues.setText(String.valueOf(githubRepoModelList.get(position).getOpenIssues()));

    }

    @Override
    public int getItemCount() {
        return githubRepoModelList.size();
    }

    public class ViewHolderDeliver extends RecyclerView.ViewHolder {

        TextView startCount, watchCount, language, createdAt, updatedAt,pushedAt, openIssues;

        public ViewHolderDeliver(@NonNull View itemView) {
            super(itemView);
            startCount = itemView.findViewById(R.id.STARCOUNT);
            watchCount = itemView.findViewById(R.id.WATCHCOUNT);
            language = itemView.findViewById(R.id.LANGUAGE);
            createdAt = itemView.findViewById(R.id.CREATEDAT);
            updatedAt = itemView.findViewById(R.id.UPDATEDAT);
            pushedAt = itemView.findViewById(R.id.PUSHEDAT);
            openIssues = itemView.findViewById(R.id.OPENISSUE);
        }
    }
}
