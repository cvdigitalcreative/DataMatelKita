package com.digitalcreative.aplikasidatamining.View.HomePage;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.digitalcreative.aplikasidatamining.Controller.Firebase;
import com.digitalcreative.aplikasidatamining.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoadingPage extends Fragment {
    ProgressBar progressBar;
    Halaman_Utama halaman_utama =  new Halaman_Utama();

    public LoadingPage() {
        // Required empty public constructor
    }
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this.getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this.getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("tes", "This device is not supported.");

            }
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_loading_page, container, false);
        progressBar = view.findViewById(R.id.progressbar);



        new Thread(new Runnable() {
            @Override
            public void run() {
                if (checkPlayServices()) {
                    loadIMEI();
                    loadtheFirebase();
                    progressAnimationbar();
                    startApp();
                }

            }
        }).start();

        return view;
    }


    private void loadIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        telephonyManager.getDeviceId();

        SharedPreferences.Editor chaching = getContext().getSharedPreferences("detailUser", Context.MODE_PRIVATE).edit();
        chaching.putString("IMEI", String.valueOf(telephonyManager.getDeviceId()));
        chaching.commit();
    }

    private void loadtheFirebase() {
        Firebase firebase = new Firebase();
        firebase.loadfirebase(getContext());
    }

    private void startApp() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_base, halaman_utama)
                .commit();
    }

    private void progressAnimationbar() {
        for (int progress=0; progress<100; progress+=10) {
            System.out.println("progress = "+progress);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
