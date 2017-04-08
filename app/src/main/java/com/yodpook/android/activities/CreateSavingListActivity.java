package com.yodpook.android.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yodpook.android.Const;
import com.yodpook.android.R;
import com.yodpook.android.helper.DialogHelper;
import com.yodpook.android.helper.TimeOperationHelper;
import com.yodpook.android.models.SavingItem;

import java.text.ParseException;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateSavingListActivity extends AppCompatActivity {
    @BindView(R.id.car_target)
    LinearLayout mCarTarget;
    @BindView(R.id.home_target)
    LinearLayout mHomeTarget;
    @BindView(R.id.condo_target)
    LinearLayout mCondoTarget;
    @BindView(R.id.land_target)
    LinearLayout mLandTarget;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.deadline_text)
    TextView mDeadLineText;
    @BindView(R.id.price_text)
    TextView mPriceText;
    @BindView(R.id.member_text)
    TextView mMemberText;
    @BindView(R.id.car_bottom)
    FrameLayout mCarBottom;
    @BindView(R.id.house_bottom)
    FrameLayout mHouseBottom;
    @BindView(R.id.land_bottom)
    FrameLayout mLandBottom;
    @BindView(R.id.condo_bottom)
    FrameLayout mCondoBottom;

    private DatePickerDialog mDatePickerDialog;
    private AlertDialog mPriceListDialog;
    private String mSelectedDate;
    private String mSelectedTarget;
    private String mSelectedTitle;
    private Integer mSelectedPrice;
    private DatabaseReference mSavingInfoRef;

    private static final int[] priceList = {500000, 1000000, 1500000, 2000000, 2500000, 3000000, 3500000, 4000000, 4500000, 5000000};
    private String[] mPriceListString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_saving_list);
        ButterKnife.bind(this);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Create saving");

        mSavingInfoRef = FirebaseDatabase.getInstance().getReference().child(Const.FIREBASE_SAVING_LIST);
        mPriceListString = getResources().getStringArray(R.array.action_select_money_amount);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatePickerDialog = DialogHelper.getDatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);

                mSelectedDate = TimeOperationHelper.getPushStringFromDate(selectedDate.getTime());
                try {
                    mDeadLineText.setText(TimeOperationHelper.getDisplayDate(selectedDate.getTime()));
                } catch (ParseException ex) {
                    mDeadLineText.setText(mSelectedDate);
                }
            }
        });
        mPriceListDialog = DialogHelper.createListItemDialog(this, "Select the saving amount"
                , R.array.action_select_money_amount
                , new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSelectedPrice = priceList[i];
                        mPriceText.setText(mPriceListString[i]);
                        dialogInterface.dismiss();
                    }
                });
    }

    @OnClick(R.id.select_deadline_layout)
    public void onDeadLineLayoutClick() {
        mDatePickerDialog.show();
    }

    @OnClick(R.id.select_price_layout)
    public void onPriceLayoutClick() {
        mPriceListDialog.show();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        if (mSavingInfoRef != null && mSelectedDate != null
                && mSelectedPrice != null && mSelectedTarget != null) {
            SavingItem savingItem = new SavingItem();
            savingItem.setAmount(mSelectedPrice);
            savingItem.setDeadLine(mSelectedDate);
            savingItem.setTarget(mSelectedTarget);
            savingItem.setTitle(mSelectedTitle);
            mSavingInfoRef.push().setValue(savingItem);
            finish();
        }
    }

    @OnClick(R.id.car_target)
    public void onCarTargetClick() {
        if (mCarBottom.getVisibility() == View.VISIBLE) {
            mCarBottom.setVisibility(View.INVISIBLE);
        } else {
            mSelectedTitle = Const.TITLE_CAR;
            mSelectedTarget = Const.TARGET_CAR;

            mCarBottom.setVisibility(View.VISIBLE);
            mHouseBottom.setVisibility(View.INVISIBLE);
            mCondoBottom.setVisibility(View.INVISIBLE);
            mLandBottom.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.home_target)
    public void onHomeTargetClick() {
        if (mHouseBottom.getVisibility() == View.VISIBLE) {
            mHouseBottom.setVisibility(View.INVISIBLE);
        } else {
            mSelectedTitle = Const.TITLE_HOUSE;
            mSelectedTarget = Const.TARGET_HOUSE;

            mCarBottom.setVisibility(View.INVISIBLE);
            mHouseBottom.setVisibility(View.VISIBLE);
            mCondoBottom.setVisibility(View.INVISIBLE);
            mLandBottom.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.land_target)
    public void onLandTargetClick() {
        if (mLandBottom.getVisibility() == View.VISIBLE) {
            mLandBottom.setVisibility(View.INVISIBLE);
        } else {
            mSelectedTitle = Const.TITLE_LAND;
            mSelectedTarget = Const.TARGET_LAND;

            mCarBottom.setVisibility(View.INVISIBLE);
            mHouseBottom.setVisibility(View.INVISIBLE);
            mCondoBottom.setVisibility(View.INVISIBLE);
            mLandBottom.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.condo_target)
    public void onCondoTargetClick() {
        if (mCondoBottom.getVisibility() == View.VISIBLE) {
            mCondoBottom.setVisibility(View.INVISIBLE);
        } else {
            mSelectedTitle = Const.TITLE_CONDO;
            mSelectedTarget = Const.TARGET_CONDO;

            mCarBottom.setVisibility(View.INVISIBLE);
            mHouseBottom.setVisibility(View.INVISIBLE);
            mCondoBottom.setVisibility(View.VISIBLE);
            mLandBottom.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
