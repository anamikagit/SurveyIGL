package com.example.anamika.surveyigl.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anamika.surveyigl.R;

import java.util.List;

public class GridCardAdapter extends RecyclerView.Adapter<GridCardAdapter.MyViewHolder> {

    private Context mContext;
    private List<Data> dataList;

    public GridCardAdapter(Context mContext, List<Data> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvLabel ;
        public ImageView cardImage;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvLabel = itemView.findViewById(R.id.tv_label);
            cardImage = itemView.findViewById(R.id.card_image);
        }
    }

    @Override
    public GridCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GridCardAdapter.MyViewHolder holder, int position) {
        Data data = dataList.get(position);
        holder.tvLabel.setText(data.getLabel());
        //holder.tvDisplay.setText(data.getDisplay());

        Glide.with(mContext).load(data.getImageResource()).into(holder.cardImage);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
