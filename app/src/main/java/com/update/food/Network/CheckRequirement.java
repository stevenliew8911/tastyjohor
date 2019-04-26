package com.update.food.Network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import com.update.food.R;

/**
 * Created by Windows on 25/4/2019.
 */

public class CheckRequirement {


    public static void CheckProvider(final Context context)
    {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}


        if(!network_enabled)
        {
            new AlertDialog.Builder(context)
                    .setMessage("Network Service Disable , Please enable it")
                    .setPositiveButton("Open Network Setting", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    context.startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                                }
                            }
                    )
                    .setNegativeButton("cancel",null)
                    .show();
        }
        if(!gps_enabled ) {
            // notify user
            new AlertDialog.Builder(context)
                    .setMessage("Location Service Disable , Please enable it")
                    .setPositiveButton("Open Location Setting", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }
                    )
                            .setNegativeButton("cancel",null)
                            .show();
        }
    }
}
