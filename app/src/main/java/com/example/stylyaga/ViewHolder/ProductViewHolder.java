package com.example.stylyaga.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stylyaga.Interface.ItemClickListner;
import com.example.stylyaga.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView ProductName, ProductDescription;
    public ImageView ProductImage;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        ProductName = (TextView) itemView.findViewById(R.id.product_name);
        ProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        ProductImage = (ImageView) itemView.findViewById(R.id.product_image);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}