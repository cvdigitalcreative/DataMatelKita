package com.digitalcreative.aplikasidatamining.Controller;

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

import com.digitalcreative.aplikasidatamining.MainActivity;
import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
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

public class ForegroundService_t1 extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel_t1";

    Realm realm;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    String url_t0;
    String subpath_t0;
    int status_foreground;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        status_foreground=1;
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
        System.out.println("masuk t1");
        startForeground(1, notification);
        subpath_t0 = "t1.csv";
        insertdata(subpath_t0);
        return START_NOT_STICKY;
    }

    public void insertdata(final String subpath) {
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
            editor.putInt("key_name2", 2);
            editor.apply();
            System.out.println("selesai t1 ");
            System.out.println("nama file "+file[0].getAbsolutePath());

            update_data_s();
            status_foreground=1;
            stopForegroundService();
            DowloadFile_t1 dowloadFile_t1=new DowloadFile_t1();
            dowloadFile_t1.download(getApplication());
        }
        final String localFile = file[0].toString();
        Realm.init(ForegroundService_t1.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("datamatel2.db")
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
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                int status=pref.getInt("key_name2", 0);
                System.out.println("status download "+status);
                editor.clear();
                editor.commit(); // commit changes
                editor.putInt("key_name2", 1);
                editor.apply();

                status_foreground=1;
                stopForegroundService();
                update_data_s();

                //realm
                final long count_t0;
                final long count_t1;
                final long count_t2;
                final long count_t3;
                final long count_t4;
                final long count_t5;
                final long count_t6;


                Realm.init(getApplicationContext());
                final RealmConfiguration configuration= new RealmConfiguration.Builder()
                        .name("datamatel.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm= Realm.getInstance(configuration);
                count_t0=realm.where(Model_LacakMobil.class).count();
                realm.close();

                Realm.init(getApplicationContext());
                RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                        .name("datamatel2.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm2 = Realm.getInstance(configuration2);
                count_t1=realm2.where(Model_LacakMobil.class).count();
                realm2.close();

                Realm.init(getApplicationContext());
                RealmConfiguration configuration3 = new RealmConfiguration.Builder()
                        .name("datamatel3.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm3 = Realm.getInstance(configuration3);
                count_t2=realm3.where(Model_LacakMobil.class).count();
                realm3.close();

                Realm.init(getApplicationContext());
                RealmConfiguration configuration4 = new RealmConfiguration.Builder()
                        .name("datamatel4.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm4 = Realm.getInstance(configuration4);
                count_t3=realm4.where(Model_LacakMobil.class).count();
                realm4.close();

                Realm.init(getApplicationContext());
                RealmConfiguration configuration5 = new RealmConfiguration.Builder()
                        .name("datamatel5.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm5 = Realm.getInstance(configuration5);
                count_t4=realm5.where(Model_LacakMobil.class).count();
                realm5.close();

                Realm.init(getApplicationContext());
                RealmConfiguration configuration6 = new RealmConfiguration.Builder()
                        .name("datamatel6.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm6 = Realm.getInstance(configuration6);
                count_t5=realm6.where(Model_LacakMobil.class).count();
                realm6.close();

                Realm.init(getApplicationContext());
                RealmConfiguration configuration7 = new RealmConfiguration.Builder()
                        .name("datamatel7.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm7 = Realm.getInstance(configuration7);

                count_t6=realm7.where(Model_LacakMobil.class).count();
                realm7.close();
                if(count_t0==0){
                    file[0].delete();
                    DowloadFile_t0 dowloadFile_t0=new DowloadFile_t0();
                    dowloadFile_t0.download(getApplication());
                }else if(count_t1==0)
                {
                    DowloadFile_t1 dowloadFile_t1=new DowloadFile_t1();
                    dowloadFile_t1.download(getApplication());
                }else if(count_t2==0)
                {
                    file[0].delete();
                    DowloadFile_t2 dowloadFile_t2=new DowloadFile_t2();
                    dowloadFile_t2.download(getApplication());
                }else if(count_t3==0)
                {
                    file[0].delete();
                    DowloadFile_t3 dowloadFile_t3=new DowloadFile_t3();
                    dowloadFile_t3.download(getApplication());
                }else if(count_t4==0)
                {
                    file[0].delete();
                    DowloadFile_t4 dowloadFile_t4=new DowloadFile_t4();
                    dowloadFile_t4.download(getApplication());
                }else if(count_t5==0)
                {
                    file[0].delete();
                    DowloadFile_t5 dowloadFile_t5=new DowloadFile_t5();
                    dowloadFile_t5.download(getApplication());
                }else if(count_t6==0)
                {
                    file[0].delete();
                    DowloadFile_t6 dowloadFile_t6=new DowloadFile_t6();
                    dowloadFile_t6.download(getApplication());
                }
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                status_foreground=1;
                DowloadFile_t1 dowloadFile_t1=new DowloadFile_t1();
                dowloadFile_t1.download(getApplication());
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
        Log.d("stop service", "Stop foreground service t1");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(status_foreground!=1) {
            Intent broadcastIntent = new Intent(this, BroadcastReceiverForegroundServiceRestart_t1.class);

            sendBroadcast(broadcastIntent);
            status_foreground=0;
        }

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
