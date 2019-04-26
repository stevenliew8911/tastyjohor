package com.update.food.CalculateDistanceFunction;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.update.food.Categories.VegetarianFoodPage;
import com.update.food.Categories.WesternFoodPage;
import com.update.food.GetFunction.GetVegetarianShopListTask;
import com.update.food.GetFunction.GetWesternShopListTask;
import com.update.food.List.ShopList;
import com.update.food.Network.NetworkUtil;
import com.update.food.SearchFunction.SearchableAdapter;

import java.util.ArrayList;

/**
 * Created by Windows on 21/7/2016.
 */
public class CalculateDistanceTaskVegetarian extends AsyncTask<Double, Void, String> {

        double latitude,longtitude;
        double picklatitutde,picklongtitude;
        double deliverylatitude,deliverylongtitude;
        Context context;
        Double tempdistance;
        Double time;
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
        //loadingDialog = ProgressDialog.show(context, "Please wait", "Loading...");
    }

    public CalculateDistanceTaskVegetarian(Context context, ArrayList<ShopList> temp )
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

                    tempdistance=CalculateDistance.roundTwoDecimals(CalculateDistance.distance(picklatitutde,picklongtitude,deliverylatitude,deliverylongtitude,"K"));

                    time= CalculateDistance.roundTwoDecimals(tempdistance);


                    SearchableAdapter.Locationdistance.add(String.valueOf(tempdistance));
                    SearchableAdapter.Locationtime.add(String.valueOf(time));
                    SearchableAdapter.itemlist.add(String.valueOf(tempdistance));

                }
            } catch (Exception e) {
               System.out.println(e);

            }
        }

        return "Distance: "+tempdistance+"\n Duration: "+time;
    }

    @Override
    protected void onPostExecute(String address) {
        super.onPostExecute(address);


        if(GetVegetarianShopListTask.listMockData.size()==  SearchableAdapter.itemlist.size())
        {
            VegetarianFoodPage.adapter = new SearchableAdapter(context, VegetarianFoodPage.shopdata,SearchableAdapter.itemlist, VegetarianFoodPage.currentlatitude, VegetarianFoodPage.currentlongtitude);
            VegetarianFoodPage.list.setAdapter(VegetarianFoodPage.adapter);

        }

        progDailog.dismiss();
    }



}