package com.yodpook.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;
import com.yodpook.android.activities.SavingActivity;
import com.yodpook.android.adapter.ViewHolder.SavingListViewHolder;
import com.yodpook.android.models.SavingItem;

/**
 * Created by Boonya Kitpitak on 3/30/17.
 */

public class SavingListAdapter extends FirebaseRecyclerAdapter<SavingItem, SavingListViewHolder> {
    private Context context;

    public SavingListAdapter(Class<SavingItem> modelClass, int modelLayout, Class<SavingListViewHolder> viewHolderClass, Query ref, Context context) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
    }

    @Override
    protected void populateViewHolder(SavingListViewHolder viewHolder, SavingItem model, int position) {
        viewHolder.setSavingAmount(model.getAmount());
        viewHolder.setSavingTitle(model.getTitle());
        viewHolder.loadImage(model.getTarget());
        viewHolder.setSavingDeadline(model.getDeadLine());
        viewHolder.setItemOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SavingActivity.class));
            }
        });
    }
}
