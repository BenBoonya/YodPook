package com.yodpook.android.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yodpook.android.Const;
import com.yodpook.android.R;

public class AddSavingActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView mVisaImage;
    private ImageView mMasterCardImage;
    private ImageView mPaypalImage;
    private ImageView mRabbitImage;
    private FloatingActionButton fab;
    private String moneyAmount;
    private DatabaseReference mSavingAmountRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_saving);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSavingAmountRef = FirebaseDatabase.getInstance().getReference().child(Const.FIREBASE_SAVING_AMOUNT);

        editText = (EditText) findViewById(R.id.money_amount);
        mVisaImage = (ImageView) findViewById(R.id.visa_image);
        mMasterCardImage = (ImageView) findViewById(R.id.mastercard_image);
        mPaypalImage = (ImageView) findViewById(R.id.paypal_image);
        mRabbitImage = (ImageView) findViewById(R.id.rabbit_image);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SavingAmount savingAmount = new SavingAmount();
//                savingAmount.setAmount(editText.getText().toString());
//                savingAmount.setPaymentType("visa_img");
//                savingAmount.setUserDisplay(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
//                mSavingAmountRef.push().setValue(savingAmount);
                finish();
            }
        });
    }
}
