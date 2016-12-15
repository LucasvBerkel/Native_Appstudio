package com.example.lucas.lucasvanberkel_pset6.util;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Class to add a dialog screen in every activity if user selects 'about' in the menu.
 */
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
