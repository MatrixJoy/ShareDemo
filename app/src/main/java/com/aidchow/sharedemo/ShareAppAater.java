package com.aidchow.sharedemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Created by 78537 on 2017/1/17.
 */

public class ShareAppAater extends RecyclerView.Adapter<ShareAppAater.APPViewHolder> {
    LinkedList<ShareAppItem> shareAppItems;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        onItemClickListener = clickListener;
    }

    public ShareAppAater(LinkedList<ShareAppItem> shareAppItems) {

        this.shareAppItems = shareAppItems;
    }

    @Override
    public APPViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.share_app_layout, parent, false);
        return new APPViewHolder(v);
    }

    @Override
    public void onBindViewHolder(APPViewHolder holder, final int position) {
        holder.appLabel.setText(shareAppItems.get(position).getAppLabel());
        holder.appIcon.setImageDrawable(shareAppItems.get(position).getAppIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shareAppItems.size();
    }


    static class APPViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appLabel;

        public APPViewHolder(View itemView) {
            super(itemView);
            appIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            appLabel = (TextView) itemView.findViewById(R.id.app_label);
        }
    }
}



