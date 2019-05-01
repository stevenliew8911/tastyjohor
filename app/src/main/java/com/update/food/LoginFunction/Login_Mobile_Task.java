package com.update.food.LoginFunction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.update.food.ApiUrl;
import com.update.food.HomePage;
import com.update.food.MainActivity;
import com.update.food.Network.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Windows on 10/8/2016.
 */
public class Login_Mobile_Task extends AsyncTask<String, Void, String> {

    private Dialog loadingDialog;
    private Context context;
    String username,password,userrole,userid,usertype,user_name;
    public static boolean mobilestatus;

    public Login_Mobile_Task(Context c) {
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
        userrole = params[2];
        String result = null;

        Map<String, String> tmp = new HashMap<String, String>();
        tmp.put("username", username);
        tmp.put("password", password);
        tmp.put("userrole",userrole);
        result = NetworkUtil.sendPost(ApiUrl.Domain + ApiUrl.Mobile_Login, NetworkUtil.createQueryStringForParameters(tmp));

        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        loadingDialog.dismiss();
        if (result.contains("1"))
        {
            Intent intent = new Intent(context, HomePage.class);
            SavedSharedPreference(username, password, userrole,"mobile");
            context.startActivity(intent);
            mobilestatus=false;
        }
        else
        {
            mobilestatus=true;
        }
    }

    public void SavedSharedPreference(String username,String password,String userrole,String loginmethod)
    {
//        SharedPreferences settings = context.getSharedPreferences("logged", 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("logged","logged");
//        editor.putString("username", username);
//        editor.putString("password", password);
//        editor.putString("userrole", userrole);
//        editor.putString("loginmethod", loginmethod);
//        editor.commit();
    }

}

