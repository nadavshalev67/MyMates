package com.example.mymatess.recylers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymatess.Date;
import com.example.mymatess.R;
import com.example.mymatess.interfaces.DateChangeListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDates extends RecyclerView.Adapter<RecyclerViewDates.ViewHolder> {

    private List<Date> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private DateChangeListener mListener;


    public RecyclerViewDates(Context context, RecyclerView recyclerView, DateChangeListener dateChangeListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
        mData.add(new Date(9, 12, 20, true));
        mData.add(new Date(10, 12, 20, false));
        mData.add(new Date(11, 12, 20, false));
        mData.add(new Date(12, 12, 20, false));
        mData.add(new Date(13, 12, 20, false));
        mData.add(new Date(14, 12, 20, false));
        mData.add(new Date(15, 12, 20, false));
        mData.add(new Date(15, 12, 20, false));
        mData.add(new Date(15, 12, 20, false));
        mData.add(new Date(15, 12, 20, false));
        mData.add(new Date(15, 12, 20, false));
        mData.add(new Date(15, 12, 20, false));
        mContext = context;
        mListener = dateChangeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.square_day, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                for (Date date : mData) {
                    date.mIsChecked = false;
                }
                Date date = mData.get(itemPosition);
                date.mIsChecked = true;
                mListener.onDateChangeListener(date);
                notifyDataSetChanged();
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Date date = mData.get(position);
        if (date.mIsChecked) {
            holder.date.setTextColor(Color.WHITE);
            holder.relativeLayout.setBackgroundResource(R.color.green);
        } else {
            holder.date.setTextColor(Color.GREEN);
            holder.relativeLayout.setBackgroundResource(R.color.white);
        }
        holder.date.setText(String.format("%d/%d", date.day, date.month));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.relative_layout_date);
            date = itemView.findViewById(R.id.date);
        }

    }

    public void setNewList(List<Date> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
