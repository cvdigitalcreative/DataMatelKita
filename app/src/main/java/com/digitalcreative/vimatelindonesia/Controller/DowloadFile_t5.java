package com.digitalcreative.vimatelindonesia.Controller;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class DowloadFile_t5 {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    Context context;
    String url_t0;
    String subpath_t0;

    private ArrayList<Long> jumlah_id;
    private ArrayList<Long> jumlah__download_id;
    private long downloadID;

    private ArrayList<String> path_file;
    private ArrayList<String>  url_file;


    public void startService_t0() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        String uid=firebaseUser.getUid();
        Intent serviceIntent = new Intent(context, ForegroundService_t5.class);
        serviceIntent.putExtra("inputExtra", uid);

        ContextCompat.startForegroundService(context, serviceIntent);
    }

    public void download(Context context){

        this.context=context ;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        jumlah_id=new ArrayList<>();
        jumlah__download_id=new ArrayList<>();
        jumlah_id=new ArrayList<>();
        path_file=new ArrayList<>();
        url_file=new ArrayList<>();
        jumlah__download_id=new ArrayList<>();
        myRef.child("link").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                url_t0 = dataSnapshot.child("link_tes4").getValue().toString();
                subpath_t0 = "t5.csv";
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t0);
                startService_t0();







//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
