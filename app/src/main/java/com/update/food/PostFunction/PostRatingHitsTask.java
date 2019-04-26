package com.update.food.PostFunction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.update.food.ApiUrl;
import com.update.food.HomePage;
import com.update.food.Network.NetworkUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Windows on 10/8/2016.
 */
public class PostRatingHitsTask extends AsyncTask<String, Void, String> {

    private Dialog loadingDialog;
    private Context context;
    String ShopID,ShopRatingHits,ShopRatingMarks,userid,usertype,user_name;
    String ShopType;
    public static boolean mobilestatus;

    public PostRatingHitsTask(Context c) {
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
        ShopID = params[0];
        ShopRatingHits = params[1];
        ShopRatingMarks = params[2];
        ShopType = params[3];

        String result = null;

        Map<String, String> tmp = new HashMap<String, String>();
        tmp.put("ShopType",ShopType);
        tmp.put("ShopID", ShopID);
        tmp.put("ShopRatingHits",ShopRatingHits);
        tmp.put("ShopRatingMarks",ShopRatingMarks);
      //  tmp.put("password", password);
      //  tmp.put("userrole",userrole);
        result = NetworkUtil.sendPost(ApiUrl.Domain + ApiUrl.PostRatingHits, NetworkUtil.createQueryStringForParameters(tmp));

        return result;
    }

    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
        loadingDialog.dismiss();
        System.out.println(result);
//        if (result.contains("1"))
//        {
//            Intent intent = new Intent(context, HomePage.class);
//            SavedSharedPreference(username, password, userrole,"mobile");
//            context.startActivity(intent);
//            mobilestatus=false;
//        }
//        else
//        {
//            mobilestatus=true;
//        }
    }



}

