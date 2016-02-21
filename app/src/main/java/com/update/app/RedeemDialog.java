package com.update.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Windows on 18/2/2016.
 */
public class RedeemDialog {
    EditText mEditText;
    public RedeemDialog()
    {

    }
    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.redeem_dialog);



     //   Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
     //   dialogButton.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
      //          dialog.dismiss();
       //     }
      //  });

        dialog.show();

    }

}
