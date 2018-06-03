package com.pluralsight.dagger2example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pluralsight.dagger2example.R;
import com.pluralsight.dagger2example.network.models.Repository;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    public interface RepositoryItemClick{
        void OnRepositoryItemClick(View view, Repository item);
    }

    android.text.format.DateFormat df = new android.text.format.DateFormat();

    private List<Repository> repositories;
    private RepositoryItemClick itemClickCallback;

    public RepositoryAdapter(List<Repository> repositories, RepositoryItemClick itemClickCallback){
        this.repositories = repositories;
        this.itemClickCallback = itemClickCallback;
    }

    public void swapData(List<Repository> repositories){
        this.repositories = repositories;
        this.notifyDataSetChanged();
    }

    public void setRepositoryItemClick(RepositoryItemClick repositoryItemClick){
        this.itemClickCallback = repositoryItemClick;
    }

    public List<Repository> getRepositories() {
        return repositories;
    }

    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo, parent, false);

        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {
        Repository repository = repositories.get(position);
        holder.repoName.setText(repository.getName());
        holder.repoDesc.setText(repository.getDescription());

        if(repository.getLanguage() != null){
            holder.repoProgrammingLanguage.setText(repository.getLanguage());
        }
        else{
            holder.repoProgrammingLanguage.setText("");
        }

        if(repository.getStargazers_count() != null){
            holder.repoStarCount.setText(repository.getStargazers_count().toString());
        }
        else{
            holder.repoStarCount.setText(0);
        }
    }

    @Override
    public int getItemCount() {
        return repositories != null ? repositories.size() : 0;
    }

    class RepositoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView container;
        TextView repoName;
        TextView repoDesc;
        TextView repoProgrammingLanguage;
        TextView repoStarCount;
        View languageBorder;

        RepositoryViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.cv_container);
            container.setOnClickListener(this);

            repoName = itemView.findViewById(R.id.tv_repoName);
            repoDesc = itemView.findViewById(R.id.tv_repoDesc);
            repoProgrammingLanguage = itemView.findViewById(R.id.tv_repo_lang);
            repoStarCount = itemView.findViewById(R.id.tv_stars);

            languageBorder = itemView.findViewById(R.id.vw_language_border);
        }

        @Override
        public void onClick(View view) {
            if(itemClickCallback != null){
                itemClickCallback.OnRepositoryItemClick(view,
                        repositories.get(getAdapterPosition()));
            }
        }
    }
}