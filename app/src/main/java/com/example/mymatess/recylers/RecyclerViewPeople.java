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
import java.util.List;

public class RecyclerViewPeople extends RecyclerView.Adapter<RecyclerViewPeople.ViewHolder> {

    private List<String> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;


    public RecyclerViewPeople(Context context, RecyclerView recyclerView) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.row_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameOfEmployee.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfEmployee;

        ViewHolder(View itemView) {
            super(itemView);
            nameOfEmployee = itemView.findViewById(R.id.person_name);
        }

    }

    public void setNewList(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }
}
