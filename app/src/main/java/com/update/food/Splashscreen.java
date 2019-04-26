package com.update.food;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.update.food.List.ProfileList;
import com.update.food.LoginFunction.Login_Facebook_Task;
import com.update.food.LoginFunction.Login_Google_Task;
import com.update.food.LoginFunction.Login_Mobile_Task;

import java.util.concurrent.ExecutionException;

/**
 * Created by Windows on 12/9/2016.
 */
public class Splashscreen extends AppCompatActivity
{
    public static final int REQUEST_LOCATION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

//        SharedPreferences settings = getSharedPreferences("logged", 0);
//        if (settings.getString("logged", "").toString().equals("logged"))
//        {
//            String username = settings.getString("username", "").toString();
//            String password = settings.getString("password", "").toString();
//            String userrole = settings.getString("userrole", "").toString();
//            String loginmethod = settings.getString("loginmethod", "").toString();
//
//
//            if (loginmethod.equals("facebook"))
//            {
//                loginfacebook(username, password, userrole);
//                ProfileList.setbitmap(password);
//                ProfileList.setprofilename(username);
//                Thread background = new Thread()
//                {
//                    public void run()
//                    {
//                        try
//                        {
//                            //sleep(1*1000);
//                            Intent i = new Intent(getBaseContext(), HomePage.class);
//                            startActivity(i);
//                            finish();
//                           // finish();
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//                    }
//                };
//                background.start();
//
//            }
//            else  if (loginmethod.equals("google"))
//            {
//                logingoogle(username, password, userrole);
//                Thread background = new Thread()
//                {
//                    public void run()
//                    {
//                        try
//                        {
//                            finish();
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//                    }
//                };
//                background.start();
//            }
//            else  if (loginmethod.equals("mobile"))
//            {
//                loginmobile(username, password, userrole);
//                Thread background = new Thread()
//                {
//                    public void run()
//                    {
//                        try
//                        {
//                            finish();
//                        }
//                        catch (Exception e)
//                        {
//
//                        }
//                    }
//                };
//                background.start();
//            }
//        }
//        else {

            Thread background = new Thread() {
                public void run() {
                    try {
                        sleep(3 * 1000);
                        Intent i = new Intent(getBaseContext(), HomePage.class);
                        startActivity(i);
                        finish();
                    } catch (Exception e) {

                    }
                }
            };
            background.start();
     //   }
    }

    public void loginfacebook(String username,String password,String userrole)
    {
        Login_Facebook_Task la = new Login_Facebook_Task(this);
        try {
            la.execute(username, password, userrole).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void logingoogle(String username,String password,String userrole)
    {
        Login_Google_Task la = new Login_Google_Task(this);
        try {
            la.execute(username, password, userrole).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void loginmobile(String username,String password,String userrole)
    {
        Login_Mobile_Task la = new Login_Mobile_Task(this);
        try {
            la.execute(username, password, userrole).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }




}
