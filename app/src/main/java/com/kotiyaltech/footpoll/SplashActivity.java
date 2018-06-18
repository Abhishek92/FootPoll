package com.kotiyaltech.footpoll;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kotiyaltech.footpoll.activity.HomeActivity;
import com.kotiyaltech.footpoll.config.FirebaseConfig;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private LoginButton mFacebookSignInButton;
    private CallbackManager mCallbackManager;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        mFacebookSignInButton = findViewById(R.id.facebook_login_btn);
        ImageView football = findViewById(R.id.football_image);
        //football.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim));
        activiteFirebaseRemoteConfig();
        if(mAuth.getCurrentUser() != null) {
            mFacebookSignInButton.setVisibility(View.GONE);
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    finish();
                }
            }, 2500);
        }
        configureFacebookLoginButton();
    }

    private void configureFacebookLoginButton(){
        mCallbackManager = CallbackManager.Factory.create();
        mFacebookSignInButton.setReadPermissions("email", "public_profile");
        mFacebookSignInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                mFacebookSignInButton.setVisibility(View.GONE);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
                mFacebookSignInButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
                mFacebookSignInButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog("Authentication in progress");
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SplashActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
        }
        else{
            mFacebookSignInButton.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    protected void showProgressDialog(String msg){
        if(null != mProgressDialog && !mProgressDialog.isShowing()) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }
    }

    protected void hideProgressDialog(){
        if(null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void activiteFirebaseRemoteConfig(){
        FirebaseConfig.getInstance().refresh(new OnCompleteListener()
        {
            @Override
            public void onComplete(@NonNull Task task)
            {
                FirebaseConfig.getInstance().getConfig().activateFetched();
            }
        });
    }
}
