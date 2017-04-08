package com.yodpook.android.adapter.ViewHolder;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yodpook.android.Const;
import com.yodpook.android.R;
import com.yodpook.android.helper.TimeOperationHelper;

import java.text.ParseException;


/**
 * Created by Boonya Kitpitak on 4/8/17.
 */

public class SavingListViewHolder extends RecyclerView.ViewHolder {
    private ImageView mTargetImage;
    private TextView mTitle;
    private TextView mAmount;
    private TextView mDeadLine;
    private View view;

    public SavingListViewHolder(View itemView) {
        super(itemView);
        mTargetImage = (ImageView) itemView.findViewById(R.id.circleImageView);
        mTitle = (TextView) itemView.findViewById(R.id.saving_title);
        mAmount = (TextView) itemView.findViewById(R.id.saving_amount);
        mDeadLine = (TextView) itemView.findViewById(R.id.saving_deadline);
        this.view = itemView;
    }

    public void loadImage(String type) {
        int drawable = 0;
        switch (type) {
            case Const.TARGET_CAR:
                drawable = R.drawable.dummy_car;
                break;
            case Const.TARGET_CONDO:
                drawable = R.drawable.dummy_condominium;
                break;
            case Const.TARGET_HOUSE:
                drawable = R.drawable.dummy_house;
                break;
            case Const.TARGET_LAND:
                drawable = R.drawable.dummy_land;
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

    public void setSavingDeadline(String deadline) {
        try {
            String displayText = "Deadline: " + TimeOperationHelper.getDisplayDate(
                    TimeOperationHelper.getDateFromPushString(deadline));
            mDeadLine.setText(displayText);
        } catch (ParseException e) {
            mDeadLine.setText(deadline);
            e.printStackTrace();
        }
    }

    public void setItemOnClick(View.OnClickListener clickListener) {
        this.view.setOnClickListener(clickListener);
    }
}
