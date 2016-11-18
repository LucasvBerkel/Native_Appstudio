package nl.yrck.mprog_watchlist;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by dinie on 25-9-2016.
 */

public class Utill {
    public static void aboutDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("By Yorick de Boer")
                .setTitle("About")
                .setCancelable(true)
                .setNeutralButton("DISMISS", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
