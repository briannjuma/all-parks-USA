package com.brayo.allparks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brayo.allparks.R;
import com.brayo.allparks.models.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ParkRecyclerViewAdapter extends RecyclerView.Adapter<ParkRecyclerViewAdapter.ViewHolder> {
    private final List<Park> parkList;

    public ParkRecyclerViewAdapter(List<Park> parkList) {
        this.parkList = parkList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.park_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Park park = parkList.get(position);
        holder.parkName.setText(park.getName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());
        if (park.getImages().size() > 0) {
            Picasso.get()
                    .load(park.getImages().get(0).getUrl()) // load image
                    .placeholder(android.R.drawable.stat_sys_download)  //  placeholder to show before image is retrieved
                    .error(android.R.drawable.stat_notify_error)    //  system image to show image retrival failed
                    .resize(100,100)    //  ensures we have a uniform image set
                    .centerCrop()
                    .into(holder.parkImage);
        }

    }

    @Override
    public int getItemCount() {
        return parkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            parkImage = itemView.findViewById(R.id.row_park_imageview);
            parkName = itemView.findViewById(R.id.row_park_name_textview);
            parkType = itemView.findViewById(R.id.row_park_type_textview);
            parkState = itemView.findViewById(R.id.row_park_state);
        }
    }
}
