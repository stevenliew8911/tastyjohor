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
        String apiUrl = ApiUrl.GetEventById;




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
         //   HighLightView = (TextView) rowView.findViewById(R.id.subtitle);
         //   StartDateView = (TextView) rowView.findViewById(R.id.date);
           // ImageUrlView = (ImageView) rowView.findViewById(R.id.bgImage);
          //  IconUrlView = (ImageView) rowView.findViewById(R.id.logoImage);

            ListItem newsItem = listData.get(position);
         //   new DownloadImageTask(context, ImageUrlView).execute(newsItem.getImageUrl());
         //   new DownloadImageTask(context, IconUrlView).execute(newsItem.getLogoUrl());

            NameView.setText("AHHAHAHAHAHAHHA");
            NameView.bringToFront();
         //   HighLightView.setText(newsItem.getHighLight());
         //   HighLightView.bringToFront();
          //  StartDateView.setText(newsItem.getStartDate());
          //  StartDateView.bringToFront();


            return rowView;
        }
    }
}
