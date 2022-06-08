package com.brayo.allparks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brayo.allparks.R;
import com.brayo.allparks.models.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPageAdapter extends RecyclerView.Adapter<ViewPageAdapter.ImageSlider> {
    private List<Images> imagesList;

    public ViewPageAdapter(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_pager_row, parent, false);

        return new ImageSlider(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSlider holder, int position) {

        Picasso.get()
                .load(imagesList.get(position).getUrl())
                .fit()
                .error(android.R.drawable.stat_notify_error)
                .placeholder(android.R.drawable.stat_sys_download)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ImageSlider extends RecyclerView.ViewHolder{
        public ImageView imageView;

        public ImageSlider(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_pager_imageview);
        }
    }
}
