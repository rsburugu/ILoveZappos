package com.zappos.consumer.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zappos.consumer.R;
import com.zappos.consumer.services.models.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ramya on 9/2/17.
 */
public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Result> productsList = new ArrayList<>();
    private Context mContext;
    OnProductItemListener onProductItemListener;

    public ProductsAdapter(Context context, OnProductItemListener onProductItemListener) {
        this.mContext = context;
        this.onProductItemListener = onProductItemListener;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_name)
        public TextView mTitle;
        @BindView(R.id.product_price)
        public TextView mPrice;
        @BindView(R.id.product_image)
        public ImageView mImage;
        @BindView(R.id.product_card_view)
        public LinearLayout mCardView;

        public ProductViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mCardView.setOnClickListener(onClickListener);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onProductItemListener.onProductClick(v, (Result) v.getTag());
        }
    };


    public void updateData(List<Result> productsList) {
        this.productsList = productsList;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        Result product = productsList.get(position);
        productViewHolder.mTitle.setText(product.getProductName());
        productViewHolder.mPrice.setText(product.getPrice());
        Picasso.with(mContext).load(product.getThumbnailImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(productViewHolder.mImage);
        productViewHolder.mCardView.setTag(product);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

}
