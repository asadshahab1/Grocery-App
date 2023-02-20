package com.example.grocera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyCartViewHolder> {
    ArrayList<CartStructure> cartStructureArrayList;
    Context context;
    public ListAdapter(Context context, ArrayList<CartStructure> cartStructures){
        this.cartStructureArrayList = cartStructures;
        this.context = context;
    }
    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.objcard,parent,false);
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartViewHolder holder, int position) {
        holder.product_name.setText(cartStructureArrayList.get(position).getName());
        holder.company_name.setText(cartStructureArrayList.get(position).getCompany_name());
        holder.quantity.setText(Integer.toString(cartStructureArrayList.get(position).getQuantity()));
        holder.price.setText((cartStructureArrayList.get(position).getPrice()));
        Glide.with(context)
                .load(cartStructureArrayList.get(position).getImage())
                .into(holder.item_pic);
    }


    @Override
    public int getItemCount() {
        if (cartStructureArrayList==null)
            return 0;
        return cartStructureArrayList.size();
    }
    public static class MyCartViewHolder extends RecyclerView.ViewHolder{
        ImageView item_pic;
        TextView product_name, company_name,quantity,price;
        public MyCartViewHolder(@NonNull View itemView) {
            super(itemView);
            item_pic = itemView.findViewById(R.id.item_pic);
            product_name = itemView.findViewById(R.id.product_title);
            company_name = itemView.findViewById(R.id.company_id);
            quantity = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price_id);
        }
    }
}
