package com.yodpook.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yodpook.android.Const;
import com.yodpook.android.R;
import com.yodpook.android.adapter.SavingListAdapter;
import com.yodpook.android.adapter.ViewHolder.SavingListViewHolder;
import com.yodpook.android.models.SavingItem;

/**
 * Created by Boonya Kitpitak on 4/8/17.
 */

public class SavingListFragment extends Fragment {
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerView;
    private DatabaseReference mSavingInfoRef;
    private FirebaseRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saving_list_layout, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        mSavingInfoRef = FirebaseDatabase.getInstance().getReference().child(Const.FIREBASE_SAVING_LIST);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mAdapter = new SavingListAdapter(SavingItem.class, R.layout.saving_list_item, SavingListViewHolder.class, mSavingInfoRef, getActivity()) {
            @Override
            protected void onDataChanged() {
                super.onDataChanged();
                if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }

        };
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
