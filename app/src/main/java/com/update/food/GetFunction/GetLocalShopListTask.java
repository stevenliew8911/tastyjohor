package com.update.food.GetFunction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.squareup.picasso.Picasso;
import com.update.food.ApiUrl;
import com.update.food.CalculateDistanceFunction.CalculateDistanceTaskLocal;
import com.update.food.CalculateDistanceFunction.CalculateDistanceTaskWestern;
import com.update.food.Categories.LocalFoodPage;
import com.update.food.Categories.WesternFoodPage;
import com.update.food.JSON_Function.ShopDatabase_field;
import com.update.food.List.ShopList;
import com.update.food.Network.NetworkUtil;
import com.update.food.R;
import com.update.food.SearchFunction.SearchableAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Windows on 20/9/2016.
 */
public class GetLocalShopListTask extends AsyncTask<Void, Integer, ArrayList<ShopList>> {

    public Context context = null;
    public static ArrayList<ShopList> listMockData;
    ListView list;
    ProgressDialog progDailog;


    public GetLocalShopListTask(Context context, ListView list) {
        this.context = context;
        this.list = list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(context);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }


    @Override
    protected ArrayList<ShopList> doInBackground(Void... params) {
        String apiUrl = ApiUrl.Domain + ApiUrl.GetLocalShopList;
        listMockData = new ArrayList<ShopList>();
        if (NetworkUtil.isNetworkAvailable(context)) {
            String response = NetworkUtil.sendGet(apiUrl, false);

            try {
                JSONObject jObj = new JSONObject(response);
                JSONArray jArray = jObj.getJSONArray(ShopDatabase_field.ShopList);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject tmp = jArray.getJSONObject(i);
                    String text = jArray.getString(i);
                    ShopList newsData = new ShopList();

                    if (tmp.has(ShopDatabase_field.ShopID)) {
                        newsData.setShopID(tmp.getString(ShopDatabase_field.ShopID));
                    }
                    if (tmp.has(ShopDatabase_field.ShopName)) {
                        newsData.setShopName(tmp.getString(ShopDatabase_field.ShopName));
                    }
                    if (tmp.has(ShopDatabase_field.ShopLogo)) {
                        newsData.setShopLogo(tmp.getString(ShopDatabase_field.ShopLogo));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerName)) {
                        newsData.setShopOwnerName(tmp.getString(ShopDatabase_field.ShopOwnerName));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerIc)) {
                        newsData.setShopOwnerIc(tmp.getString(ShopDatabase_field.ShopOwnerIc));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerHp)) {
                        newsData.setShopOwnerHp(tmp.getString(ShopDatabase_field.ShopOwnerHp));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerFAddress)) {
                        newsData.setShopOwnerFAddress(tmp.getString(ShopDatabase_field.ShopOwnerFAddress));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerAddress)) {
                        newsData.setShopOwnerAddress(tmp.getString(ShopDatabase_field.ShopOwnerAddress));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerDescription)) {
                        newsData.setShopOwnerDescription(tmp.getString(ShopDatabase_field.ShopOwnerDescription));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerLatitude)) {
                        newsData.setShopOwnerLatitude(tmp.getString(ShopDatabase_field.ShopOwnerLatitude));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerLongtitude)) {
                        newsData.setShopOwnerLongtitude(tmp.getString(ShopDatabase_field.ShopOwnerLongtitude));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerBannerPic)) {
                        newsData.setShopOwnerBannerPic(tmp.getString(ShopDatabase_field.ShopOwnerBannerPic));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerPic1)) {
                        newsData.setShopOwnerPic1(tmp.getString(ShopDatabase_field.ShopOwnerPic1));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerPic2)) {
                        newsData.setShopOwnerPic2(tmp.getString(ShopDatabase_field.ShopOwnerPic2));
                    }

                    if (tmp.has(ShopDatabase_field.ShopOwnerPic3)) {
                        newsData.setShopOwnerPic3(tmp.getString(ShopDatabase_field.ShopOwnerPic3));
                    }

                    if (tmp.has(ShopDatabase_field.ShopOwnerPic4)) {
                        newsData.setShopOwnerPic4(tmp.getString(ShopDatabase_field.ShopOwnerPic4));
                    }

                    if (tmp.has(ShopDatabase_field.ShopOwnerPic5)) {
                        newsData.setShopOwnerPic5(tmp.getString(ShopDatabase_field.ShopOwnerPic5));
                    }

                    listMockData.add(newsData);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return listMockData;
    }

    @Override
    protected void onPostExecute(final ArrayList<ShopList> result) {
        super.onPostExecute(result);
        LocalFoodPage.shopdata = result;
        try {

            new CalculateDistanceTaskLocal(context, GetLocalShopListTask.listMockData).execute(LocalFoodPage.currentlatitude, LocalFoodPage.currentlongtitude);


        } catch (Exception e) {

        }
        progDailog.dismiss();

    }
}

