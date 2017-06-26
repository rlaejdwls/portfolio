package com.example.demo.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo.R;
import com.example.demo.event.OnListItemClickListener;
import com.example.demo.event.OnListItemLongClickListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Hwang on 2017-06-13.
 * 작성자 : 황의택
 */
public class SquareGridAdapter extends RecyclerView.Adapter<SquareGridAdapter.ViewHolder> {
    private ArrayList<Map> items = new ArrayList<>();

    private OnListItemClickListener onListItemClickListener;
    private OnListItemLongClickListener onListItemLongClickListener;

    public SquareGridAdapter() {
    }

    public ArrayList<Map> getItems() {
        return items;
    }
    public void setItems(ArrayList<Map> items) {
        this.items = items;
    }
    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        this.onListItemClickListener = onListItemClickListener;
    }
    public void setOnListItemLongClickListener(OnListItemLongClickListener onListItemLongClickListener) {
        this.onListItemLongClickListener = onListItemLongClickListener;
    }

    @Override
    public SquareGridAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square_grid, parent, false);
        return new SquareGridAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SquareGridAdapter.ViewHolder holder, int position) {
        holder.imgScene.setImageBitmap((Bitmap) items.get(position).get("imgScene"));
        holder.lblTime.setText((String) items.get(position).get("lblTime"));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgScene;
        private TextView lblTime;

        public ViewHolder(View itemView) {
            super(itemView);
            imgScene = (ImageView) itemView.findViewById(R.id.imgScene);
            lblTime = (TextView) itemView.findViewById(R.id.lblTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onListItemClickListener != null) {
                        onListItemClickListener.onListItemClick(getAdapterPosition(), items.get(getAdapterPosition()));
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onListItemLongClickListener != null) {
                        onListItemLongClickListener.onListItemLongClick(getAdapterPosition(), items.get(getAdapterPosition()));
                    }
                    return false;
                }
            });
        }
    }
}
