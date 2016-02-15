package com.update.app;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbarMain;
    DrawerLayout drawerLayoutMain;
    ImageButton imgBtnSearch;
    ImageView imgViewLogo;
    EditText editTextSeach;
    ImageView ImageUrlView;
    ImageView IconUrlView;
    Bitmap thumbnail;
    TextView NameView;
    TextView HighLightView;
    TextView StartDateView;
    Button btnme;

    private boolean isSearchOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            new GetEventsTask(getApplicationContext()).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //  new UpdateItem(getApplicationContext()).execute();
//        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        toolbarMain = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.search_bar);

        editTextSeach = (EditText) findViewById(R.id.et_search);
        imgViewLogo = (ImageView) findViewById(R.id.img_view_logo);
        imgBtnSearch = (ImageButton) findViewById(R.id.img_btn_search);
        imgViewLogo.setVisibility(View.VISIBLE);

        drawerLayoutMain = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayoutMain.setDrawerListener(new DrawerLayout.DrawerListener() {
            //  @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            //     @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbarMain, R.string.app_name, R.string.app_name);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_main);
        //navigationView.setNavigationItemSelectedListener(this);

        ArrayList<ListItem> listData = GetEventsTask.listMockData;

        final ListView listView = (ListView) findViewById(R.id.content_list);
        listView.setAdapter(new ProductListAdapter(this, listData));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                ListItem newsData = (ListItem) listView.getItemAtPosition(position);

            }
        });

    }

    public void imgBtnSearchClicked(View view) {
        Log.i("onClick", "onClick: " + isSearchOpened);

        if (isSearchOpened) { //test if the search is open

            editTextSeach.setVisibility(View.INVISIBLE);
            imgViewLogo.setVisibility(View.VISIBLE);

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            imgBtnSearch.setImageResource(R.drawable.ic_action_search);

            // isSearchOpened = false;
        } else {
            imgViewLogo.setVisibility(View.INVISIBLE);
            editTextSeach.setVisibility(View.VISIBLE);

            editTextSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editTextSeach, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            imgBtnSearch.setImageResource(R.drawable.ic_action_cart);

            isSearchOpened = true;
        }
    }

    public void imgBtnThreeClicked(View view) {
        if (drawerLayoutMain != null) {
            drawerLayoutMain.openDrawer(GravityCompat.START);
        }
    }


    class ProductListAdapter extends BaseAdapter {
        private ArrayList<ListItem> listData;
        private LayoutInflater layoutInflater;


        TextView EndDateView;


        public ProductListAdapter(Context context, ArrayList listData) {
            this.listData = listData;
            layoutInflater = LayoutInflater.from(context);
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

            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_view_content_item, null);

                NameView = (TextView) convertView.findViewById(R.id.title);
                HighLightView = (TextView) convertView.findViewById(R.id.subtitle);
                StartDateView = (TextView) convertView.findViewById(R.id.date);
                ImageUrlView = (ImageView) convertView.findViewById(R.id.bgImage);
                IconUrlView = (ImageView) convertView.findViewById(R.id.logoImage);

            }
            ListItem newsItem = listData.get(position);
            new DownloadImageTask(getApplicationContext(), ImageUrlView).execute(newsItem.getImageUrl());
            new DownloadImageTask(getApplicationContext(), IconUrlView).execute(newsItem.getIconUrl());
            NameView.setText(newsItem.getName());
            NameView.bringToFront();
            HighLightView.setText( newsItem.getHighLight());
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
            return convertView;
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




        public void navlistitem() {
            btnme = (Button) findViewById(R.id.btn_me);
            btnme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayoutMain.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
            });
        }


    }
}