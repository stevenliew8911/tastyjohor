package com.update.food.RegisterFunction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.update.food.ApiUrl;
import com.update.food.LoginFunction.Login_Google_Task;
import com.update.food.Network.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Windows on 10/8/2016.
 */
public class Register_Google_Task extends AsyncTask<String, Void, String> {

    private Dialog loadingDialog;
    private Context context;
    String username;
    String password;
    String signupbygoogle;

    public Register_Google_Task(Context c) {
        this.context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = ProgressDialog.show(context, "Please wait", "Loading...");
    }

    @Override
    protected String doInBackground(String... params)
    {

        username = params[0];
        password = params[1];
        signupbygoogle = params [2];
        String result = null;

        Map<String, String> tmp = new HashMap<String, String>();
        tmp.put("username", username);
        tmp.put("password", password);
        tmp.put("login_by", signupbygoogle);
        result = NetworkUtil.sendPost(ApiUrl.Domain + ApiUrl.Google_Register, NetworkUtil.createQueryStringForParameters(tmp));

        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        loadingDialog.dismiss();
        logingoogle(username, password, "user");

    }

    public void logingoogle(String username, String password, String userrole)
    {
        Login_Google_Task la = new Login_Google_Task(context);
        try
        {
            la.execute(username,password,userrole).get();

        }
        catch (Exception e)
        {

        }
    }

}

