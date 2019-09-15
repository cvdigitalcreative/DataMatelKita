package com.digitalcreative.vimatelindonesia.Controller;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.digitalcreative.vimatelindonesia.MainActivity;
import com.digitalcreative.vimatelindonesia.Model.Model_LacakMobil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

public class ForegroundService_t6 extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel_t5";

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
        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("android.intent.action.ACTION_DOWNLOAD_COMPLETE");

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Download Data")
                .setContentText("sedang mendownload File")
                .build();

        startForeground(1, notification);
        subpath_t0 = "t6.csv";
        insertdata(subpath_t0);
        return START_NOT_STICKY;
    }

    public void insertdata(final String subpath) {
        System.out.println("insert t6");
        System.out.println("jumlah file "+jumlah_file);
        final Model_LacakMobil model_lacakMobil = new Model_LacakMobil();
        // get writable database as we want to write data
        final File[] file = {new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath)};
        file[0] = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath);
        if(!file[0].exists()){

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            int status=pref.getInt("key_name2", 0);
            System.out.println("status download "+status);
            editor.clear();
            editor.commit(); // commit changes
            editor.putInt("key_name2", 0);
            editor.apply();
            System.out.println("nama file "+file[0].getAbsolutePath());
            update_data_s();
            stopForegroundService();
            DowloadFile_t6 dowloadFile_t6=new DowloadFile_t6();
            dowloadFile_t6.download(getApplication());

//            String path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
//            File directory = new File(path);
//            File[] files = directory.listFiles();
//            for (int i = 0; i < files.length; i++)
//            {
//                if(files[i].getName().contains("t0")
//                        || files[i].getName().contains("t1")
//                        || files[i].getName().contains("t2")
//                        || files[i].getName().contains("t3")
//                        || files[i].getName().contains("t4")
//                        || files[i].getName().contains("t5")
//                        || files[i].getName().contains("t6")
//                ) {
//                    files[i].delete();
//                }
//            }

        }
        final String localFile = file[0].toString();
        Realm.init(ForegroundService_t6.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("vimatel7.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(configuration);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                try (BufferedReader br = new BufferedReader(new FileReader(file[0]))) {
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
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                realm.close();
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                int status=pref.getInt("key_name2", 0);
                System.out.println("status download "+status);
                editor.clear();
                editor.commit(); // commit changes
                editor.putInt("key_name2", 0);
                editor.apply();
                System.out.println("nama file "+file[0].getAbsolutePath());
                file[0].delete();
                String path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File directory = new File(path);
                File[] files = directory.listFiles();
                for (int i = 0; i < files.length; i++)
                {
                    if(files[i].getName().contains("t0")
                            || files[i].getName().contains("t1")
                            || files[i].getName().contains("t2")
                            || files[i].getName().contains("t3")
                            || files[i].getName().contains("t4")
                            || files[i].getName().contains("t5")
                            || files[i].getName().contains("t6")
                    ) {
                        files[i].delete();
                    }
                }
                update_data_s();
                stopForegroundService();

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

                DowloadFile_t6 dowloadFile_t6=new DowloadFile_t6();
                dowloadFile_t6.download(getApplication());
                realm.close();
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
//        Intent broadcastIntent = new Intent(this, BroadcastReceiverForegroundServiceRestart_t6.class);
//
//        sendBroadcast(broadcastIntent);
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
