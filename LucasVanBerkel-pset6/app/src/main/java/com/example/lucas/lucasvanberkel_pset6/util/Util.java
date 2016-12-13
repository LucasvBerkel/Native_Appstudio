package com.example.lucas.lucasvanberkel_pset6.util;

import android.app.AlertDialog;
import android.content.Context;

public class Util {
    public static void aboutDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("By Lucas van Berkel")
                .setTitle("About")
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
