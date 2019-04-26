package com.update.food.GetFunction;

/**
 * Created by Windows on 1/10/2016.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import com.update.food.ApiUrl;
import com.update.food.CafeTempVariable.CafeTempVariable;
import com.update.food.CalculateDistanceFunction.CalculateDistanceTaskCafe;
import com.update.food.Categories.CafeFoodPage;
import com.update.food.JSON_Function.ShopDatabase_field;
import com.update.food.List.ShopList;
import com.update.food.Network.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windows on 20/9/2016.
 */
public class GetCafeShopListTask extends AsyncTask<Void, Integer, ArrayList<ShopList>> {

    public Context context = null;
    public static ArrayList<ShopList> listMockData;
    ListView list;

    private ProgressDialog loadingDialog;
    ProgressDialog progDailog;

    public GetCafeShopListTask(Context context, ListView list)
    {
        this.context = context;
        this.list=list;


    }

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


    @Override
    protected ArrayList<ShopList> doInBackground(Void... params)
    {
        String apiUrl = ApiUrl.Domain + ApiUrl.GetCafeShopList;
        listMockData = new ArrayList<ShopList>();
        if (NetworkUtil.isNetworkAvailable(context))
        {
            String response = NetworkUtil.sendGet(apiUrl,false);

            try
            {
                JSONObject jObj = new JSONObject(response);
                JSONArray jArray = jObj.getJSONArray(ShopDatabase_field.ShopList);
                for (int i = 0; i < jArray.length(); i++)
                {
                    JSONObject tmp = jArray.getJSONObject(i);
                    String text = jArray.getString(i);
                    ShopList newsData = new ShopList();

                    if (tmp.has(ShopDatabase_field.ShopID))
                    {
                        newsData.setShopID(tmp.getString(ShopDatabase_field.ShopID));
                    }
                    if (tmp.has(ShopDatabase_field.ShopName))
                    {
                        newsData.setShopName(tmp.getString(ShopDatabase_field.ShopName));
                    }
                    if (tmp.has(ShopDatabase_field.ShopLogo))
                    {
                        newsData.setShopLogo(tmp.getString(ShopDatabase_field.ShopLogo));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerName))
                    {
                        newsData.setShopOwnerName(tmp.getString(ShopDatabase_field.ShopOwnerName));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerIc))
                    {
                        newsData.setShopOwnerIc(tmp.getString(ShopDatabase_field.ShopOwnerIc));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerHp))
                    {
                        newsData.setShopOwnerHp(tmp.getString(ShopDatabase_field.ShopOwnerHp));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerFAddress))
                    {
                        newsData.setShopOwnerFAddress(tmp.getString(ShopDatabase_field.ShopOwnerFAddress));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerAddress))
                    {
                        newsData.setShopOwnerAddress(tmp.getString(ShopDatabase_field.ShopOwnerAddress));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerDescription))
                    {
                        newsData.setShopOwnerDescription(tmp.getString(ShopDatabase_field.ShopOwnerDescription));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerLatitude))
                    {
                        newsData.setShopOwnerLatitude(tmp.getString(ShopDatabase_field.ShopOwnerLatitude));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerLongtitude))
                    {
                        newsData.setShopOwnerLongtitude(tmp.getString(ShopDatabase_field.ShopOwnerLongtitude));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerBannerPic))
                    {
                        newsData.setShopOwnerBannerPic(tmp.getString(ShopDatabase_field.ShopOwnerBannerPic));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerPic1))
                    {
                        newsData.setShopOwnerPic1(tmp.getString(ShopDatabase_field.ShopOwnerPic1));
                    }
                    if (tmp.has(ShopDatabase_field.ShopOwnerPic2))
                    {
                        newsData.setShopOwnerPic2(tmp.getString(ShopDatabase_field.ShopOwnerPic2));
                    }

                    if (tmp.has(ShopDatabase_field.ShopOwnerPic3))
                    {
                        newsData.setShopOwnerPic3(tmp.getString(ShopDatabase_field.ShopOwnerPic3));
                    }

                    if (tmp.has(ShopDatabase_field.ShopOwnerPic4))
                    {
                        newsData.setShopOwnerPic4(tmp.getString(ShopDatabase_field.ShopOwnerPic4));
                    }

                    if (tmp.has(ShopDatabase_field.ShopOwnerPic5))
                    {
                        newsData.setShopOwnerPic5(tmp.getString(ShopDatabase_field.ShopOwnerPic5));
                    }
                    if (tmp.has(ShopDatabase_field.ShopRatingHits))
                    {
                        newsData.setShopRatingHits(tmp.getString(ShopDatabase_field.ShopRatingHits));
                    }
                    if (tmp.has(ShopDatabase_field.ShopRatingMarks))
                    {
                        newsData.setShopRatingMarks(tmp.getString(ShopDatabase_field.ShopRatingMarks));
                    }

                    listMockData.add(newsData);

                }



            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

        return listMockData;
    }

    @Override
    protected void onPostExecute(final ArrayList<ShopList> result)
    {
        super.onPostExecute(result);
        CafeFoodPage.shopdata=result;
        try
        {

                new CalculateDistanceTaskCafe(context,GetCafeShopListTask.listMockData).execute(CafeFoodPage.currentlatitude, CafeFoodPage.currentlongtitude);


        }
        catch(Exception e)
        {

        }
        progDailog.dismiss();


    }





}
