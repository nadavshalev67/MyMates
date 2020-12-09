package com.example.mymatess.recylers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymatess.Hobbie;
import com.example.mymatess.R;

import java.util.ArrayList;
import java.util.List;

public class RecylerViewHobbies extends RecyclerView.Adapter<RecylerViewHobbies.ViewHolder> {

    private List<Hobbie> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private RecyclerView mRecyclerView;


    public RecylerViewHobbies(Context context, RecyclerView recyclerView) {
        this.mInflater = LayoutInflater.from(context);
        this.mRecyclerView = recyclerView;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.row_hobbie, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                int itemPosition = mRecyclerView.getChildLayoutPosition(v);
                mData.get(itemPosition).isChecked = mData.get(itemPosition).isChecked ? false : true;
                notifyDataSetChanged();

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.checkedImageView.setVisibility(mData.get(position).isChecked ? View.VISIBLE : View.INVISIBLE);
        holder.hobbieName.setText(mData.get(position).name + "");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hobbieName;
        ImageView checkedImageView;

        ViewHolder(View itemView) {
            super(itemView);
            hobbieName = itemView.findViewById(R.id.hobbie);
            checkedImageView = itemView.findViewById(R.id.checked);
        }

    }

    public void setNewList(List<Hobbie> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public List<Hobbie> getList(){
        return mData;
    }
}
