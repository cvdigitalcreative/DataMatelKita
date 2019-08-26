package com.digitalcreative.aplikasidatamining.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.digitalcreative.aplikasidatamining.R;

public class ChangeKeyboardAlert {
    private AlertDialog.Builder alertBuilder;
    private View alertView;
    private AlertDialog alertDialog;

    public ChangeKeyboardAlert(Context context){
        alertBuilder = new AlertDialog.Builder(context);
        alertView = LayoutInflater.from(context).inflate(R.layout.choose_keyboard_alert, null);
        alertBuilder.setCancelable(false);
        alertBuilder.setView(alertView);
        alertDialog = alertBuilder.create();
    }

    public void showAlert(){
        alertDialog.show();
    }

    public void hideAlert(){
        alertDialog.hide();
    }


    public AlertDialog.Builder getAlertBuilder() {
        return alertBuilder;
    }

    public View getAlertView() {
        return alertView;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }
}
