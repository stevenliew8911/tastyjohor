package com.update.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Windows on 7/2/2016.
 */

public class UpdateItem extends AsyncTask<String, Integer, Void> {

    ProgressDialog progressDialog;
    Context context = null;

    public UpdateItem(Context c) {
        this.context = c;
    }

    @Override
    protected Void doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Downloading data...");
        progressDialog.show();
//            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                public void onCancel(DialogInterface arg0) {
//                    UpdateNewsTask.this.cancel(true);
//                }
//            });
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    }