package com.example.openweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{
    public List<String> Daylist;
    public List<String> Timelist;
    public List<Float> Templist;
    public List<String> Desclist;
    private LayoutInflater mInflater;
    private MainActivity mainActivity;

    RVAdapter(Context context, List<String> dy, List<String> tm, List<Float> tmp, List<String> desc) {
        this.mInflater = LayoutInflater.from(context);
        this.Daylist = dy;
        this.Timelist = tm;
        this.Templist = tmp;
        this.Desclist = desc;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String dy = Daylist.get(position);
        String tm = Timelist.get(position);
        Float tmp = Templist.get(position);
        String desc = Desclist.get(position);

        holder.dyView.setText(dy);
        holder.tmView.setText(tm);
        holder.tmpView.setText(String.valueOf(tmp));
        holder.descView.setText(desc);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //View myView;
        TextView dyView, tmView, tmpView, descView;

        ViewHolder(View itemView) {
            super(itemView);
            dyView = itemView.findViewById(R.id.DayRV);
            tmView = itemView.findViewById(R.id.TimeRV);
            tmpView = itemView.findViewById(R.id.TempRV);
            descView = itemView.findViewById(R.id.DescRV);
            //itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
    }

    @Override
    public int getItemCount() {
        return Daylist.size();
    }
}
