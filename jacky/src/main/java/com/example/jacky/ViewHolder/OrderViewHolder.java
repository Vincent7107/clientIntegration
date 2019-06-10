package com.example.jacky.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jacky.Interface.ItemClickListener;
import com.example.jacky.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtOrderId, txtOrderStatus;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView){
        super(itemView);
        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);

        //itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    @Override
    //觸發activity的 button
    public void onClick(View view){
        //itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
