package com.yodpook.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yodpook.android.Const;
import com.yodpook.android.R;
import com.yodpook.android.models.User;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainLogInActivity extends AppCompatActivity {

    @BindView(R.id.login_google)
    RelativeLayout loginGoogle;
    @BindView(R.id.login_facebook)
    RelativeLayout loginFacebook;
    RelativeLayout createAccount;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private CallbackManager mCallbackManager;
    private DatabaseReference mUserReference;
    private static final String FACEBOOK_REQUEST_EMAIL = "email";
    private static final String FACEBOOK_REQUEST_PUBLIC_PROFILE = "public_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        if (mUser != null) {
            startActivity(new Intent(MainLogInActivity.this, MainActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main_log_in);
            ButterKnife.bind(this);
//            setUpFacebookCallback();
            mCallbackManager = CallbackManager.Factory.create();
            mUserReference = FirebaseDatabase.getInstance().getReference().child(Const.FIREBASE_USER_LIST);
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    @OnClick(R.id.login_google)
    public void onGoogleLoginClick() {
        signInWithGoogle();
    }

    @OnClick(R.id.login_facebook)
    public void onFaceBookClick() {
        LoginManager.getInstance().logInWithReadPermissions(MainLogInActivity.this
                , Arrays.asList(FACEBOOK_REQUEST_EMAIL, FACEBOOK_REQUEST_PUBLIC_PROFILE));
    }

    private void signInWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 16);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 16) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(this, "Google sign in fail, please try another", Toast.LENGTH_SHORT).show();
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    /*******************************************
     * Private Method
     *******************************************/

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        onShowProgressDialog(true);
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Log.w("Google auth", "signInWithCredential", task.getException());
                                Toast.makeText(getApplicationContext(), "Fail " + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                onShowProgressDialog(false);
                            } else {
                                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                User user = new User();
                                user.setEmail(firebaseUser.getEmail());
                                user.setDisplayName(firebaseUser.getDisplayName());
                                user.setUid(firebaseUser.getUid());
                                pushUserInfo(user);
                            }
                        }
                    });
        } catch (IllegalArgumentException | NullPointerException ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void onShowProgressDialog(boolean isVisible) {
        if (isVisible) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void pushUserInfo(final User user) {
        mUserReference.push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                startActivity(new Intent(MainLogInActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
