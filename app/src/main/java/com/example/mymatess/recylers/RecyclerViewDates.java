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

import com.example.mymatess.R;
import com.example.mymatess.interfaces.DateChangeListener;
import com.example.mymatess.model.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RecyclerViewDates extends RecyclerView.Adapter<RecyclerViewDates.ViewHolder> {

    private List<Date> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    private DateChangeListener mListener;


    public RecyclerViewDates(Context context, RecyclerView recyclerView, DateChangeListener dateChangeListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
        Calendar calendar = Calendar.getInstance();
        mData.add(new Date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR), true));
        calendar.add(Calendar.DATE, 1);
        int thuresdayFlag = 0;
        for (int i = 0; i < 15; i++) { //rest of the week
            System.out.println("The date is  " + calendar.getTime());
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String dayOfMonthStr = String.valueOf(dayOfMonth);
            System.out.println("The day is  " + dayOfMonthStr);
            int MonthOfYear = calendar.get(Calendar.MONTH) + 1;
            String MonthOfYearSTR = String.valueOf(MonthOfYear);
            System.out.println("The Month is  " + MonthOfYearSTR);
            int Year = calendar.get(Calendar.YEAR);
            String YearSTR = String.valueOf(Year);
            String CutString = YearSTR.substring(2, 4);
            System.out.println("The year is  " + CutString);
            calendar.add(Calendar.DATE, 1);
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 == calendar.FRIDAY)
                thuresdayFlag++;
            if (calendar.get(Calendar.DAY_OF_WEEK) - 1 != 0) {
                mData.add(new Date(dayOfMonth, MonthOfYear, Integer.valueOf(CutString), false));
            }
            if (thuresdayFlag == 2) break;
        }


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

    public Date getFirstPlace() {
        return mData.get(0);
    }
}
