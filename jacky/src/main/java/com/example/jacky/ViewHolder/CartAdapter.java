package com.example.jacky.ViewHolder;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.jacky.Cart;
import com.example.jacky.Database.Database;
import com.example.jacky.Model.Order;
import com.example.jacky.R;

import java.util.ArrayList;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txt_cart_name;
    public FloatingActionButton btnDeleteItem;
    public ElegantNumberButton btnItemNumber;

    private AdapterView.OnItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    public CartViewHolder(View itemView){
        super(itemView);
        txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
        btnItemNumber = (ElegantNumberButton)itemView.findViewById(R.id.item_number_button);
        btnDeleteItem = (FloatingActionButton) itemView.findViewById(R.id.item_delete);
    }

    @Override
    public void onClick(View view){
    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{   //show from sql
    private ArrayList<Order> list = new ArrayList<>();
    private Cart cart;

    public CartAdapter(ArrayList<Order> list, Cart cart){
        this.list = list;
        this.cart = cart;
    }

    @Override
    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(cart);  //載入cart_layout.xml
        View itemView = inflater.inflate(R.layout.item_cart, parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CartViewHolder holder, final int position){
        holder.btnItemNumber.setNumber(list.get(position).getQuantity());  //set numberButton quantity
        holder.btnItemNumber.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Order order = list.get(position);
                order.setQuantity(String.valueOf(newValue));
                new Database(cart).updateCart(order);

                countTotal();
            }
        });

        holder.btnDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = list.get(position);
                new Database(cart).deleteOrder(order);

                list.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                notifyItemRangeChanged(position, list.size());

                countTotal();
            }
        });

        holder.txt_cart_name.setText(list.get(position).getProductName());
    }

    @Override
    public int getItemCount(){
        return list.size();
    }

    public void countTotal(){   //count total price
        int total = 0;
        ArrayList<Order> orders = new Database(cart).getCart();
        for(Order item:orders){
            total = total + (Integer.parseInt(item.getPrice()))*(Integer.parseInt(item.getQuantity()));
        }
        cart.txtTotalPrice.setText(Integer.toString(total));
    }
}
