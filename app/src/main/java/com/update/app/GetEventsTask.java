package com.update.app;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Windows on 13/2/2016.
 */
public class GetEventsTask extends AsyncTask<Void, Integer, ArrayList<ListItem>> {

    Context context = null;
    String Name;
    Event event = new Event();
    ProgressDialog progressDialog;
    ListView listView;
    public static  ArrayList<ListItem> listMockData;


    public GetEventsTask(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }


    @Override
    protected ArrayList<ListItem> doInBackground(Void... params) {
        String apiUrl = ApiUrl.GetEvents;
        listMockData = new ArrayList<ListItem>();
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

    ImageView ImageUrlView;
    ImageView IconUrlView;
    Bitmap thumbnail;
    TextView NameView;
    TextView HighLightView;
    TextView StartDateView;

    @Override
    protected void onPostExecute(final ArrayList<ListItem> listItems) {
        super.onPostExecute(listItems);

        listView.setAdapter(new ProductListAdapter(context, listItems));

    }

    class ProductListAdapter extends BaseAdapter {
        ArrayList<ListItem> listData;
        LayoutInflater layoutInflater;
        Context context;
        TextView EndDateView;


        public ProductListAdapter(Context context, ArrayList listData) {
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
            View rowView = inflater.inflate(R.layout.list_view_content_item, null, false);

            NameView = (TextView) rowView.findViewById(R.id.title);
            HighLightView = (TextView) rowView.findViewById(R.id.subtitle);
            StartDateView = (TextView) rowView.findViewById(R.id.date);
            ImageUrlView = (ImageView) rowView.findViewById(R.id.bgImage);
            IconUrlView = (ImageView) rowView.findViewById(R.id.logoImage);

            ListItem newsItem = listData.get(position);
            new DownloadImageTask(context, ImageUrlView).execute(newsItem.getImageUrl());
            new DownloadImageTask(context, IconUrlView).execute(newsItem.getLogoUrl());
            NameView.setText(newsItem.getName());
            NameView.bringToFront();
            HighLightView.setText(newsItem.getHighLight());
            HighLightView.bringToFront();
            StartDateView.setText(newsItem.getStartDate());
            StartDateView.bringToFront();

            //  new DownloadText().execute(newsItem.getName(), newsItem.getHighLight(), newsItem.getStartDate(), newsItem.getImageUrl(), newsItem.getIconUrl());

            //  ImageUrlView.setBackgroundResource(R.drawable.taeyeon);
            //  new DownloadImage().execute(newsItem.getImageUrl());
            //  new DownloadLogo().execute(newsItem.getIconUrl());

            // Bitmap thumbnail =  NetworkUtil.getBitmapFromURL(newsItem.getIconUrl());
            //  IconUrlView.setImageBitmap(thumbnail);
            // new DownloadImage().execute(newsItem.getImageUrl());
            // IconUrlView.setBackgroundResource(R.drawable.logolist);
            // imageView.setImageResource();
            return rowView;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {

        ImageView imageView = null;
        Context context = null;

        public DownloadImageTask(Context context, ImageView imageView) {
            this.imageView = imageView;
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... param) {
            Log.i("xyz " + getClass().getSimpleName(), "doInBackground Called");
            Bitmap bitmap = null;
            if (param != null && param.length > 0) {
                String url = param[0];
                bitmap = NetworkUtil.getBitmapFromURL(url);
                if (bitmap != null) {
                    String tmpPath = url.replace("http://", "").replace("/", "");
                    saveImageToInternalStorage(bitmap, tmpPath);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            Log.i("xyz " + getClass().getSimpleName(), "onPreExecute Called");
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            Log.i("xyz " + getClass().getSimpleName(), "onPostExecute Called");
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }

        private boolean saveImageToInternalStorage(Bitmap image, String imageName) {
            boolean success = false;

            File path = Environment.getDataDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            long bytesAvailable = blockSize * availableBlocks;
            long megAvailable = bytesAvailable / (1024 * 1024);
            Log.i("xyz " + getClass().getSimpleName() + "-saveImageToInternalStorage megAvailable : ", megAvailable + "");
            if (megAvailable < 1) {
                return false;
            }

            FileOutputStream fos = null;
            try {
                // Use the compress method on the Bitmap object to write image to the OutputStream
                fos = context.openFileOutput(imageName, Context.MODE_PRIVATE);
                // Writing the bitmap to the output stream
                image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                success = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return success;
        }
    }
}
