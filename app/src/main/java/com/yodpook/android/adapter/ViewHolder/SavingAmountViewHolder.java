package com.yodpook.android.adapter.ViewHolder;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yodpook.android.R;

/**
 * Created by Boonya Kitpitak on 4/8/17.
 */

public class SavingAmountViewHolder extends RecyclerView.ViewHolder{
    private ImageView mTargetImage;
    private TextView mTitle;
    private TextView mAmount;

    public SavingAmountViewHolder(View itemView) {
        super(itemView);
        mTargetImage = (ImageView) itemView.findViewById(R.id.circleImageView);
        mTitle = (TextView) itemView.findViewById(R.id.saving_title);
        mAmount = (TextView) itemView.findViewById(R.id.saving_amount);
    }

    public void loadImage(String type) {
        int drawable = 0;
        switch (type) {
            case "visa_img":
                drawable = R.drawable.visa_img;
                break;
            case "MASTER":
                drawable = R.drawable.mastercard;
                break;
            case "PAYPAL":
                drawable = R.drawable.paypal;
                break;
            case "RABBIT":
                drawable = R.drawable.rabbit;
                break;
            default:
                break;
        }
        Glide.with(mTargetImage.getContext())
                .load(drawable)
                .placeholder(new ColorDrawable(ContextCompat.getColor(mTargetImage.getContext(), R.color.dividerText)))
                .centerCrop()
                .dontAnimate()
                .into(mTargetImage);
    }

    public void setSavingTitle(String title) {
        mTitle.setText(title);
    }

    public void setSavingAmount(int amount) {
        String price = amount + " ฿";
        mAmount.setText(price);
    }
}
