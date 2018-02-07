package overload.drmed.overload.drmed.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import overload.drmed.CadastroPacActivity;

/**
 * Created by Dalbo on 09/04/2017.
 */

public class Dialog {

    public static void callOfTheDialog(String titulo, String msg, AppCompatActivity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void callOfTheDialog(String titulo, String msg, Context activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static ProgressDialog mProgressDialog;
    public static void callOfTheProgressBar(Context context){
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Carregando dados...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public static void dismissOfTheProgressBar(){
        if (mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
}
