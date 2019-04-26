package com.update.food.CalculateDistanceFunction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.update.food.Categories.CafeFoodPage;
import com.update.food.GetFunction.GetCafeShopListTask;
import com.update.food.List.ShopList;
import com.update.food.Network.NetworkUtil;
import com.update.food.SearchFunction.SearchableAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Windows on 21/7/2016.
 */
public class CalculateDistanceTaskCafe extends AsyncTask<Double, Void, String> {

        double latitude,longtitude;
        double picklatitutde,picklongtitude;
        double deliverylatitude,deliverylongtitude;
        Context context;
        double tempdistance;
        double time;
        ArrayList<ShopList> temp;
        ProgressDialog progDailog;
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        progDailog = new ProgressDialog(context);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }

    public CalculateDistanceTaskCafe(Context context, ArrayList<ShopList> temp )
    {
        this.context = context;
        this.temp = temp;

    }

    protected String doInBackground(Double... params)
    {
        latitude = params[0];
        longtitude = params[1];
        picklatitutde= latitude;
        picklongtitude = longtitude;

        if (NetworkUtil.isNetworkAvailable(context))
        {
            try {
                for(int i=0; i<temp.size(); i++) {
                    deliverylatitude=Double.parseDouble(temp.get(i).getShopOwnerLatitude());
                    deliverylongtitude=Double.parseDouble(temp.get(i).getShopOwnerLongtitude());

                    tempdistance= CalculateDistance.roundTwoDecimals(CalculateDistance.distance(picklatitutde,picklongtitude,deliverylatitude,deliverylongtitude,"K"));

                    time=CalculateDistance.roundDurationDecimals(tempdistance);

                    SearchableAdapter.Locationdistance.add(String.valueOf(tempdistance));
                    SearchableAdapter.Locationtime.add(String.valueOf(time));
                    SearchableAdapter.itemlist.add(String.valueOf(tempdistance));
                }
            } catch (Exception e) {
               System.out.println(e);

            }
        }
//

        return "Distance: "+tempdistance+"\n Duration: "+time;
    }

    @Override
    protected void onPostExecute(String address) {
        super.onPostExecute(address);


        if(GetCafeShopListTask.listMockData.size()==  SearchableAdapter.itemlist.size())
        {


            CafeFoodPage.adapter = new SearchableAdapter(context, CafeFoodPage.shopdata,SearchableAdapter.itemlist, CafeFoodPage.currentlatitude, CafeFoodPage.currentlongtitude);
            CafeFoodPage.list.setAdapter(CafeFoodPage.adapter);

        }

        progDailog.dismiss();
    }



}
