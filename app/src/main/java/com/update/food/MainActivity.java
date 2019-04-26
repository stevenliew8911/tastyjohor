package com.update.food;

import android.app.ProgressDialog;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;
import com.update.food.List.ProfileList;
import com.update.food.LoginFunction.Login_Facebook_Task;
import com.update.food.LoginFunction.Login_Google_Task;
import com.update.food.LoginFunction.Login_Mobile_Task;
import com.update.food.RegisterFunction.Register_Facebook_Task;
import com.update.food.RegisterFunction.Register_Google_Task;
import com.update.food.RegisterFunction.Register_Mobile_Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener{
    LoginButton login_fb_button;
    CallbackManager callbackManager;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    GraphRequest request;
    String username;
    String password;
    String imageurl;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInOptions gso;
    SignInButton btnSignIn;
    Button hp_btn;
    EditText usernameedit,passwordedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //IMPLEMENT WITHOUT LOGIN
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
        finish();
        //
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.main_page);


        hp_btn=(Button)findViewById(R.id.hp_button);
        usernameedit=(EditText)findViewById(R.id.username_edit);
        passwordedit=(EditText)findViewById(R.id.password_edit);


        hp_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                usernameedit.getText().toString();
                passwordedit.getText().toString();
                loginmobile(usernameedit.getText().toString(), passwordedit.getText().toString(), "user");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (Login_Mobile_Task.mobilestatus == true)
                        {
                            registermobile( usernameedit.getText().toString(),  passwordedit.getText().toString(), "Mobile");
                        }
                    }
                }, 50);
            }
        });



        login_fb_button = (LoginButton) findViewById(R.id.login_fb_button);
        login_fb_button.setBackgroundResource(R.drawable.com_facebook_button_background);
        login_fb_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                String accessToken = loginResult.getAccessToken().getToken();

                request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        try {
                            username = object.getString("name").toString();
                            password = object.getString("id").toString();
                            loginfacebook(username, password, "user");
                            ProfileList.setbitmap(password);
                            ProfileList.setprofilename(username);

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    if (Login_Facebook_Task.facebookstatus == true)
                                    {
                                        facebookregister(username, password, "facebook");
                                    }
                                }
                            }, 50);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        progressDialog.dismiss();
                        disconnectFromFacebook();
                    }

                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                System.out.println("onCancel");
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }


        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                     .requestEmail()
                    .build();

             mGoogleApiClient = new GoogleApiClient.Builder(this)
                     .enableAutoManage(this, this)
                     .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                     .build();

//           btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
//           btnSignIn.setOnClickListener(new View.OnClickListener()
//           {
//                   public void onClick(View v)
//                   {
//                      GoogleSignIn();
//                   }
//            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch (requestCode)
        {
            case 0:
            if (resultCode == RESULT_OK)
            {
                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleResult(result);
            }
            break;
            default:
            {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    private void GoogleResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            GoogleSignInAccount acct = result.getSignInAccount();
            username = acct.getEmail().toString();
            password = acct.getId().toString();
            imageurl = acct.getPhotoUrl().toString();

            logingoogle(username, password,"user");
            ProfileList.setgooglebitmap(imageurl);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    if (Login_Google_Task.googlestatus == true)
                    {
                        registergoogle(username, password, "google");
                    }
                }
            }, 50);
            disconnectFromGoogle();
            progressDialog.dismiss();
        }
        else
        {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }



    public void loginmobile(String username, String password, String userrole)
    {
        Login_Mobile_Task la = new Login_Mobile_Task(this);
        try
        {
            la.execute(username,password,userrole).get();

        }
        catch (Exception e)
        {

        }
    }
    public void registermobile(String username, String password, String loginby)
    {
        Register_Mobile_Task la= new Register_Mobile_Task(this);
        la.execute(username, password, loginby);
    }

    public void logingoogle(String username,String password,String userrole)
    {
        Login_Google_Task la = new Login_Google_Task(this);
        try
        {
            la.execute(username,password,userrole).get();
        }
        catch (Exception e)
        {
        }
    }

    public void registergoogle(String username, String password, String loginby)
    {
        Register_Google_Task la= new Register_Google_Task(this);
        la.execute(username, password, loginby);
    }

    private void GoogleSignIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, 0);
    }

    public void disconnectFromGoogle()
    {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status)
                    {

                    }
                });
        mGoogleApiClient.disconnect();

    }

    public void loginfacebook(String username, String password, String userrole)
    {
        Login_Facebook_Task la = new Login_Facebook_Task(this);

        try
        {
            la.execute(username,password,userrole).get();
        }
        catch (Exception e)
        {

        }
    }

    public void facebookregister(String username, String password, String loginby)
    {
        Register_Facebook_Task la= new Register_Facebook_Task(this);
        la.execute(username, password, loginby);
    }

    public void disconnectFromFacebook()
    {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest.Callback()
        {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
    }
}