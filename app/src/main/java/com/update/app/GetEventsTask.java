package com.update.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windows on 13/2/2016.
 */
public class GetEventsTask extends AsyncTask<Void, Integer, Void> {

    Context context = null;
    String Name;
    Event event = new Event();
    ProgressDialog progressDialog;

    public static ArrayList<ListItem> listMockData = new ArrayList<ListItem>();


    public GetEventsTask(Context context) {
        this.context = context;
    }




    @Override
    protected Void doInBackground(Void... params) {
        String apiUrl = ApiUrl.GetEvents;

        if (NetworkUtil.isNetworkAvailable(context)) {
            String response = NetworkUtil.getStringFromURL(apiUrl);

            try {
                JSONObject jObj = new JSONObject(response);
                JSONArray jArray = jObj.getJSONArray("data");
                for (int i = 0; i < jArray.length(); i++)
                {
                    JSONObject tmp = jArray.getJSONObject(i);
                    String text = jArray.getString(i);
                    ListItem newsData = new ListItem();

                    if (tmp.has(Event.Field_Id)) {
                        event.Id = tmp.getString(Event.Field_Id);
                        newsData.setId(tmp.getString(Event.Field_Id));
                      //  Log.d("GetEventsTask", "doInBackground() called with: " + event.Id.toString());
                    }
                     if(tmp.has(Event.Field_Name))
                    {
                        event.Name = tmp.getString(Event.Field_Name);
                        newsData.setName(tmp.getString(Event.Field_Name));
                       // Log.d("GetEventsTask", "doInBackground() called with: " + event.Name.toString());
                    }
                     if(tmp.has(Event.Field_StartPostDate))
                    {
                        event.StartPostDate = tmp.getString(Event.Field_StartPostDate);
                        newsData.setStartDate(tmp.getString(Event.Field_StartPostDate));
                      //  Log.d("GetEventsTask", "doInBackground() called with: " + event.StartPostDate.toString());

                    }
                     if(tmp.has(Event.Field_StartEndDate))
                    {
                        event.StartEndDate = tmp.getString(Event.Field_StartEndDate);
                        newsData.setEndDate(tmp.getString(Event.Field_StartEndDate));
                      // Log.d("GetEventsTask", "doInBackground() called with: " + event.StartEndDate.toString());
                    }
                    if(tmp.has(Event.Field_Logo))
                    {
                        event.Logo = tmp.getString(Event.Field_Logo);
                        newsData.setIconUrl(tmp.getString(Event.Field_Logo));
                      //  Log.d("GetEventsTask", "doInBackground() called with: " + event.Logo.toString());
                    }
                    if(tmp.has(Event.Field_Image))
                    {
                        event.Image = tmp.getString(Event.Field_Image);
                        newsData.setImageUrl(tmp.getString(Event.Field_Image));
                      //  Log.d("GetEventsTask", "doInBackground() called with: " + event.Image.toString());
                    }
                     if(tmp.has(Event.Field_Highlight))
                    {
                        event.Highlight = tmp.getString(Event.Field_Highlight);
                        newsData.setHighLight(tmp.getString(Event.Field_Highlight));
                     //   Log.d("GetEventsTask", "doInBackground() called with: " + event.Highlight.toString());
                    }
                    listMockData.add(newsData);

               //     Log.d("GetEventsTask", "doInBackground() called with: " + text);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        return null;
    }



}
