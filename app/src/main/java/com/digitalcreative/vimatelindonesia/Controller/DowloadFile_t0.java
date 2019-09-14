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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class DowloadFile_t0 {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    Context context;
    String url_t0;
    String subpath_t0= "t0.csv";
    long id_download=0;
    private ArrayList<String>  url_file;
    private int jumlah_file=0;
    String link_tes="link_tes";
    private ArrayList<Long> jumlah__download_id;
    private long downloadID;
    private BroadcastReceiver onDownloadComplete_t0 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(onDownloadComplete_t0);
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(id==downloadID){
                startService_t0();
            }

//            if(jumlah_file!=5){
//                jumlah_file=jumlah_file+1;
//                link_tes ="link_tes"+jumlah_file;
//                System.out.println(link_tes);
//                subpath_t0 = "t"+jumlah_file+".csv";
//                System.out.println(subpath_t0);
//                download(context,jumlah_file,link_tes,subpath_t0);
//            }else{
//                startService_t0();
//



        }
    };
    public void startService_t0() {
        System.out.println("masuk sini service t0");
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        String uid=firebaseUser.getUid();
        Intent serviceIntent = new Intent(context, ForegroundService_t0.class);
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


        }
    }
    public static boolean isDownloadManagerAvailable(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }
    public void download(Context context){

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();

        jumlah__download_id=new ArrayList<>();

        jumlah__download_id=new ArrayList<>();

        context.registerReceiver(onDownloadComplete_t0,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        myRef.child("link").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                url_t0 = dataSnapshot.child("link_test").getValue().toString();
                subpath_t0="t0.csv";
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t0);

                    System.out.println("insert url");
                    downloadfromdropbox(url_t0, subpath_t0);











//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
