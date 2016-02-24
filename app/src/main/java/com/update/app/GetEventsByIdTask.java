package com.update.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Windows on 21/2/2016.
 */
public class GetEventsByIdTask extends AsyncTask<Void, Integer, ArrayList<ListItem>> {
        String eventid;
        Context context = null;
        String Name;
        Event event = new Event();
        ProgressDialog progressDialog;
        ListView listView;


public GetEventsByIdTask(Context context, ListView listView, String eventid) {
        this.context = context;
        this.listView = listView;
        this.eventid=eventid;






        }

    @Override
    protected ArrayList<ListItem> doInBackground(Void... params) {
        ArrayList<ListItem> listMockData = new ArrayList<ListItem>();
        String apiUrl = ApiUrl.GetEvents;

        if (NetworkUtil.isNetworkAvailable(context)) {
            String response = NetworkUtil.getStringFromURL(apiUrl);

            try {
                JSONObject jObj = new JSONObject(response);
                JSONArray jArray = jObj.getJSONArray("data");
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject tmp = jArray.getJSONObject(i);
                    String text = jArray.getString(i);
                    ListItem newsData = new ListItem();

                    if (tmp.has(Event.Field_Id)) {
                        event.Id = tmp.getString(Event.Field_Id);
                        newsData.setId(tmp.getString(Event.Field_Id));
                        //  Log.d("GetEventsTask", "doInBackground() called with: " + event.Id.toString());
                    }
                    if (tmp.has(Event.Field_Name)) {
                        event.Name = tmp.getString(Event.Field_Name);
                        newsData.setName(tmp.getString(Event.Field_Name));
                        // Log.d("GetEventsTask", "doInBackground() called with: " + event.Name.toString());
                    }
                    if (tmp.has(Event.Field_StartPostDate)) {
                        event.StartPostDate = tmp.getString(Event.Field_StartPostDate);
                        newsData.setStartDate(tmp.getString(Event.Field_StartPostDate));
                        //  Log.d("GetEventsTask", "doInBackground() called with: " + event.StartPostDate.toString());

                    }
                    if (tmp.has(Event.Field_StartEndDate)) {
                        event.StartEndDate = tmp.getString(Event.Field_StartEndDate);
                        newsData.setEndDate(tmp.getString(Event.Field_StartEndDate));
                        // Log.d("GetEventsTask", "doInBackground() called with: " + event.StartEndDate.toString());
                    }
                    if (tmp.has(Event.Field_Logo)) {
                        event.Logo = tmp.getString(Event.Field_Logo);
                        newsData.setLogoUrl(tmp.getString(Event.Field_Logo));
                        //  Log.d("GetEventsTask", "doInBackground() called with: " + event.Logo.toString());
                    }
                    if (tmp.has(Event.Field_Image)) {
                        event.Image = tmp.getString(Event.Field_Image);
                        newsData.setImageUrl(tmp.getString(Event.Field_Image));
                        //  Log.d("GetEventsTask", "doInBackground() called with: " + event.Image.toString());
                    }
                    if (tmp.has(Event.Field_Highlight)) {
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



        return listMockData;
    }

    @Override
    protected void onPostExecute(final ArrayList<ListItem> listItems)
    {
        super.onPostExecute(listItems);


        listView.setAdapter(new ProductListDetailAdapter(context, listItems));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
           //     ListItem newsItem = listItems.get(position);
                // listView.removeAllViews();
            //    new GetEventsByIdTask(context, listView, newsItem.getId());

            }
        });
    }

          class ProductListDetailAdapter extends BaseAdapter {
                ArrayList<ListItem> listData;
                LayoutInflater layoutInflater;
                Context context;
                TextView EndDateView;
                TextView NameView;
                ImageView ImageUrlView;
                ImageView IconUrlView;
                TextView HighLightView;
                 TextView StartDateView;


                public ProductListDetailAdapter(Context context, ArrayList listData) {
                    this.context = context;
                    this.listData = listData;
                    this.layoutInflater = LayoutInflater.from(context);

                }

                @Override
                public int getCount() {
                    return listData.size();
                }

                @Override
                public Object getItem(int position) {
                    return listData.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                public View getView(int position, View convertView, ViewGroup parent) {

                    LayoutInflater inflater = LayoutInflater.from(context);

                    View rowView = inflater.inflate(R.layout.list_view_content_item_detail, null, false);

                    NameView = (TextView) rowView.findViewById(R.id.title);
                    HighLightView = (TextView) rowView.findViewById(R.id.subtitle);
                    StartDateView = (TextView) rowView.findViewById(R.id.date);
                    ImageUrlView = (ImageView) rowView.findViewById(R.id.bgImage);
                    IconUrlView = (ImageView) rowView.findViewById(R.id.logoImage);

           ListItem newsItem = listData.get(position);
         //   new DownloadImageTask(context, ImageUrlView).execute(newsItem.getImageUrl());
         //   new DownloadImageTask(context, IconUrlView).execute(newsItem.getLogoUrl());

          //  NameView.setText("AHHAHAHAHAHAHHA");
           // NameView.bringToFront();
         //   HighLightView.setText(newsItem.getHighLight());
         //   HighLightView.bringToFront();
          //  StartDateView.setText(newsItem.getStartDate());
          //  StartDateView.bringToFront();


            return rowView;
        }
    }
}
