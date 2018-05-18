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

public class HomeCardAdapter extends RecyclerView.Adapter<HomeCardAdapter.MyViewHolder> {


        private Context mContext;
        private List<MenuData> dataList;

    public HomeCardAdapter(Context mContext, List<MenuData> dataList) {
            this.mContext = mContext;
            this.dataList = dataList;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView tvLabel, tvDisplay;
            public ImageView cardImage;

            public MyViewHolder(View itemView) {
                super(itemView);
                //tvDisplay = itemView.findViewById(R.id.tv_display);
                tvLabel = itemView.findViewById(R.id.tv_label);
                cardImage = itemView.findViewById(R.id.card_image);
            }
        }

        @Override
        public HomeCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_single_row, parent,false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(HomeCardAdapter.MyViewHolder holder, int position) {
            MenuData data = dataList.get(position);
            holder.tvLabel.setText(data.getLabel());
            //holder.tvDisplay.setText(data.getDisplay());

            Glide.with(mContext).load(data.getImageResource()).into(holder.cardImage);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }
