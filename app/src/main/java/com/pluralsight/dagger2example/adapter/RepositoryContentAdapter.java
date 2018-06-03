package com.pluralsight.dagger2example.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pluralsight.dagger2example.R;
import com.pluralsight.dagger2example.network.models.Content;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RepositoryContentAdapter extends RecyclerView.Adapter<RepositoryContentAdapter.RepositoryContentViewHolder> {

    public interface RepositoryContentClick {
        void OnRepositoryDetailClick(View view, Content item);
    }

    private List<Content> data;
    private RepositoryContentClick itemClickCallback;

    public RepositoryContentAdapter(List<Content> data, RepositoryContentClick itemClickCallback){
        this.data = data;
        this.itemClickCallback = itemClickCallback;
    }

    public void swapData(List<Content> repositories){
        this.data = repositories;

        Collections.sort(data, new Comparator<Content>() {
            @Override
            public int compare(Content left, Content right) {
                return left.getType().compareTo(right.getType());
            }
        });

        this.notifyDataSetChanged();
    }

    public void setRepositoryItemClick(RepositoryContentClick repositoryDataItemClick){
        this.itemClickCallback = repositoryDataItemClick;
    }

    @NonNull
    @Override
    public RepositoryContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);

        return new RepositoryContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryContentViewHolder holder, int position) {
        Content content = data.get(position);

        holder.title.setText(content.getName());
        if(content.getType().equals("dir")){
            holder.desc.setText("Directory");
            holder.icon.setImageResource(R.drawable.ic_folder);
        }
        else{
            holder.desc.setText("File");
            holder.icon.setImageResource(R.drawable.ic_file);
        }
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class RepositoryContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView title;
        TextView desc;

        RepositoryContentViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.tv_content_title);
            desc = itemView.findViewById(R.id.tv_content_desc);
            icon = itemView.findViewById(R.id.iv_content_icon);
        }

        @Override
        public void onClick(View view) {
            if(itemClickCallback != null){
                itemClickCallback.OnRepositoryDetailClick(view,
                        data.get(getAdapterPosition()));
            }
        }
    }
}