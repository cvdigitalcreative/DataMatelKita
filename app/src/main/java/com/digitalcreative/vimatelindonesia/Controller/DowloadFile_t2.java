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

public class DowloadFile_t2 {
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

    private BroadcastReceiver onDownloadComplete_t2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(onDownloadComplete_t2);
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(id==downloadID){
                startService_t0();
            }




        }
    };
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
        Intent serviceIntent = new Intent(context, ForegroundService_t2.class);
        serviceIntent.putExtra("inputExtra", uid);

        ContextCompat.startForegroundService(context, serviceIntent);
    }
    public void downloadfromdropbox(String url, String subpath) {

        if (isDownloadManagerAvailable(context)) {

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
            request.setDescription("Some descrition");
            request.setTitle("Some title");
// in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subpath);
            System.out.println("sub path download "+subpath);
// get download service and enqueue file
            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            downloadID=manager.enqueue(request);
            jumlah__download_id.add(Long.valueOf(downloadID));

        }
    }
    public static boolean isDownloadManagerAvailable(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
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

        this.context.registerReceiver(onDownloadComplete_t2,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        myRef.child("link").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                url_t0 = dataSnapshot.child("link_tes2").getValue().toString();
                subpath_t0 = "t2.csv";
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t0);
                if(file.exists()){
                    System.out.println("insert db");
                    startService_t0();
//                    insert_database(subpath_t0);
//                        file.delete();
                }else{
                    System.out.println("insert url");
                    downloadfromdropbox(url_t0, subpath_t0);



                }







//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
