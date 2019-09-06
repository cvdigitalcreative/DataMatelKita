package com.digitalcreative.vimatelindonesia.Controller;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.digitalcreative.vimatelindonesia.MainActivity;
import com.digitalcreative.vimatelindonesia.Model.Model_LacakMobil;
import com.digitalcreative.vimatelindonesia.View.MenuPages.PencarianPage_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ForegroundService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    Realm realm;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    String url_t0;
    String subpath_t0;
    String url_t1;
    String subpath_t1;
    String url_t2;
    String subpath_t2;
    String url_t3;
    String subpath_t3;
    String url_t4;
    String subpath_t4;
    String url_t5;
    String subpath_t5;
    String url_data_update;
    String subpath_data_update;
    private ArrayList<Long> jumlah_id;
    private ArrayList<String> path_file;
    private ArrayList<String>  url_file;
    private ArrayList<Long> jumlah__download_id;
    private int jumlah_file;
    private long downloadID;

    ProgressDialog progressDialog;
    private static final int REQUEST_WRITE_STORAGE = 112;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Download Data")
                .setContentText("sedang mendownload File")
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        //do heavy work on a background thread
        Realm.init(ForegroundService.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(configuration);
        long count = realm.where(Model_LacakMobil.class).count();

        if(count<=0){
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    realm.deleteAll();
//                }
//            });

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            myRef = firebaseDatabase.getReference();
            jumlah_id=new ArrayList<>();
            path_file=new ArrayList<>();
            url_file=new ArrayList<>();
            jumlah__download_id=new ArrayList<>();
            jumlah_file=7;
            ForegroundService.this.registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            update_data();

        }else{
            stopForegroundService();
        }

        //stopSelf();

        return START_NOT_STICKY;
    }
    public void update_data(){

            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name("vimatel.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    realm.deleteAll();
//                }
//            });
            myRef.child("link").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                    url_t0 = dataSnapshot.child("link_tes").getValue().toString();
                    subpath_t0 = "t0.csv";
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t0);
                    if(file.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t0);
//                        file.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t0);
                        url_file.add(url_t0);


                    }


                    url_t1 = dataSnapshot.child("link_tes1").getValue().toString();
                    subpath_t1 = "t1.csv";
                    File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t1);
                    if(file2.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t1);
//                        file2.delete();
                    }else{
                        System.out.println("insert url");

                        path_file.add(subpath_t1);
                        url_file.add(url_t1);

                    }


                    url_t2 = dataSnapshot.child("link_tes2").getValue().toString();
                    subpath_t2 = "t2.csv";
                    File file3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t2);
                    if(file3.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t2);

//                        file3.delete();
                    }else{
                        System.out.println("insert url");

                        path_file.add(subpath_t2);
                        url_file.add(url_t2);


                    }


                    url_t3 = dataSnapshot.child("link_tes3").getValue().toString();
                    subpath_t3 = "t3.csv";
                    File file4 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t3);
                    if(file4.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t3);
//                        file4.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t3);
                        url_file.add(url_t3);

                    }




                    url_t4 = dataSnapshot.child("link_tes4").getValue().toString();
                    subpath_t4 = "t4.csv";
                    File file5 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t4);
                    if(file5.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t4);
//                        file5.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t4);
                        url_file.add(url_t4);

                    }



                    url_t5 = dataSnapshot.child("link_tes5").getValue().toString();
                    subpath_t5 = "t5.csv";
                    File file6 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t5);
                    if(file6.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t5);
//                        file6.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t5);
                        url_file.add(url_t5);

                    }


                    url_data_update = dataSnapshot.child("link_data").getValue().toString();
                    subpath_data_update = "dataupdate.csv";
                    File file7 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_data_update);
                    if(file7.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_data_update);
//                        file7.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_data_update);
                        url_file.add(url_data_update);

                    }

                    for(int i=0; i<url_file.size(); i++){
                        downloadfromdropbox(url_file.get(i), path_file.get(i));
                    }




//
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    public void insert_database(String subpath) {


        insertdata(subpath);
//          file[0].delete();


    }

    public void insertdata(final String subpath) {
        System.out.println("jumlah file "+jumlah_file);
        final Model_LacakMobil model_lacakMobil = new Model_LacakMobil();
        // get writable database as we want to write data
        final File[] file = {new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath)};
        file[0] = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath);
        final String localFile = file[0].toString();
        Realm.init(ForegroundService.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(configuration);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                try (BufferedReader br = new BufferedReader(new FileReader(file[0]))) {
                    file[0].delete();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        // use comma as separator
                        final String[] country = line.split(",");
                        if(country.length==12){

                            model_lacakMobil.setNama_mobil(country[1]);
                            model_lacakMobil.setNo_plat(country[2]);
                            model_lacakMobil.setNamaunit(country[3]);
                            model_lacakMobil.setFinance(country[4]);
                            model_lacakMobil.setOvd(country[5]);
                            model_lacakMobil.setSaldo(country[6]);
                            model_lacakMobil.setCabang(country[7]);
                            model_lacakMobil.setNoka(country[8]);
                            model_lacakMobil.setNosin(country[9]);
                            model_lacakMobil.setTahun(country[10]);
                            model_lacakMobil.setWarna(country[11]);
                            bgRealm.insertOrUpdate(model_lacakMobil);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("nama file "+file[0].getAbsolutePath());
                    System.out.println("jumlah file diolah "+jumlah_file);

                    if( jumlah_file==1){
                        System.out.println("masuk sini");
                        update_data_s();
//                    tv2.setText("Jumlah Data = " + String.valueOf(count));

                    }else{
                        jumlah_file=jumlah_file-1;
                        update_data_s();
                        System.out.println("file exist "+file[0].exists());
                        stopForegroundService();
                    }






//            fixing_data();

                }
            }
        });





    }
    private void update_data_s() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        myRef.child("Users").child(firebaseUser.getUid()).child("last_update_data").setValue(getCurrentDate());
        myRef.child("Users").child(firebaseUser.getUid()).child("status_download_db").setValue("1");
    }
    private String getCurrentDate() {
        String date;

        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = Calendar.getInstance().getTime();
        date = curFormat.format(dateobj);


        return date;
    }


    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            jumlah_id.add(id);
            System.out.println("download selesai "+id);
            System.out.println("id download "+jumlah__download_id);
            System.out.println("jumlah id "+jumlah_id.size());
            System.out.println("jumlah download "+jumlah__download_id.size());
            int index=0;
            for(int i=0; i<jumlah__download_id.size(); i++){
                if(id==jumlah__download_id.get(i)){
                    index=i;
                    break;
                }
            }
            if(!path_file.isEmpty()){
                insert_database(path_file.get(index));
            }

            //Checking if the received broadcast is for our enqueued download by matching download id

//            if (jumlah_id.size()==jumlah__download_id.size()) {
//                System.out.println("mulai memasukan data");
//                for(int i=0; i<path_file.size(); i++){
//                    insert_database(path_file.get(i));
//                }
//                Realm.init(context);
//                RealmConfiguration configuration = new RealmConfiguration.Builder()
//                        .name("vimatel.db")
//                        .schemaVersion(1)
//                        .deleteRealmIfMigrationNeeded()
//                        .build();
//                realm = Realm.getInstance(configuration);
//                long count = realm.where(Model_LacakMobil.class).count();
////                    tv2.setText("Jumlah Data = " + String.valueOf(count));
//
//                Toast.makeText(context, "Berhasil download data jumlah data "+count, Toast.LENGTH_LONG).show();
//
//            }

        }
    };

    public void downloadfromdropbox(String url, String subpath) {

        if (isDownloadManagerAvailable(ForegroundService.this)) {

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
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
            DownloadManager manager = (DownloadManager) ForegroundService.this.getSystemService(Context.DOWNLOAD_SERVICE);
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

    private void stopForegroundService() {
        Log.d("stop service", "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
