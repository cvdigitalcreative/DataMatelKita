package com.digitalcreative.vimatelindonesia;

import android.Manifest;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.digitalcreative.vimatelindonesia.Controller.DowloadFile_t0;
import com.digitalcreative.vimatelindonesia.Controller.DowloadFile_t1;
import com.digitalcreative.vimatelindonesia.Controller.DowloadFile_t2;
import com.digitalcreative.vimatelindonesia.Controller.DowloadFile_t3;
import com.digitalcreative.vimatelindonesia.Controller.DowloadFile_t4;
import com.digitalcreative.vimatelindonesia.Controller.DowloadFile_t5;
import com.digitalcreative.vimatelindonesia.Controller.DowloadFile_t6;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t0;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t1;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t2;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t3;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t4;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t5;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t6;
import com.digitalcreative.vimatelindonesia.Model.Model_LacakMobil;
import com.digitalcreative.vimatelindonesia.View.MenuPages.AkunPage;
import com.digitalcreative.vimatelindonesia.View.MenuPages.BantuanPage;
import com.digitalcreative.vimatelindonesia.View.MenuPages.PencarianPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView nav;
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_base);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        //Init
        descComponent();
        //do some function here
        goButtonNavigationBar();
        int REQUEST_WRITE_STORAGE = 112;
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
            Toast.makeText(this, "Silahkan update kembali", Toast.LENGTH_LONG).show();
//                        //loading Data
//                        BackendFirebase backendFirebase = new BackendFirebase(getContext(), v, finished, tv1, tv2);
//                        backendFirebase.downloadFile(getContext());
        }
        final long count_t0;
        final long count_t1;
        final long count_t2;
        final long count_t3;
        final long count_t4;
        final long count_t5;
        final long count_t6;
        //realm
        Realm.init(getApplicationContext());
        final RealmConfiguration configuration= new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm= Realm.getInstance(configuration);
        long count = realm.where(Model_LacakMobil.class).count();
        count_t0=realm.where(Model_LacakMobil.class).count();
        realm.close();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                .name("vimatel2.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm2 = Realm.getInstance(configuration2);
        count = realm2.where(Model_LacakMobil.class).count()+count;
        count_t1=realm2.where(Model_LacakMobil.class).count();
        realm2.close();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration3 = new RealmConfiguration.Builder()
                .name("vimatel3.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm3 = Realm.getInstance(configuration3);
        count = realm3.where(Model_LacakMobil.class).count()+count;
        count_t2=realm3.where(Model_LacakMobil.class).count();
        realm3.close();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration4 = new RealmConfiguration.Builder()
                .name("vimatel4.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm4 = Realm.getInstance(configuration4);
        count = realm4.where(Model_LacakMobil.class).count()+count;
        count_t3=realm4.where(Model_LacakMobil.class).count();
        realm4.close();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration5 = new RealmConfiguration.Builder()
                .name("vimatel5.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm5 = Realm.getInstance(configuration5);
        count = realm5.where(Model_LacakMobil.class).count()+count;
        count_t4=realm5.where(Model_LacakMobil.class).count();
        realm5.close();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration6 = new RealmConfiguration.Builder()
                .name("vimatel6.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm6 = Realm.getInstance(configuration6);
        count = realm6.where(Model_LacakMobil.class).count()+count;
        count_t5=realm6.where(Model_LacakMobil.class).count();
        realm6.close();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration7 = new RealmConfiguration.Builder()
                .name("vimatel7.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm7 = Realm.getInstance(configuration7);
        count = realm7.where(Model_LacakMobil.class).count()+count;
        count_t6=realm7.where(Model_LacakMobil.class).count();
        realm7.close();
        //realm
        String path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File directory = new File(path);
        final File[] files = directory.listFiles();
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
                // Get length of file in bytes
                long fileSizeInBytes = files[i].length();
                // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
                long fileSizeInKB = fileSizeInBytes / 1024;
                //  Convert the KB to MegaBytes (1 MB = 1024 KBytes)
                long fileSizeInMB = fileSizeInKB / 1024;
                if (fileSizeInMB < 27) {
                    files[i].delete();
                }

            }
        }
        if(count>=3700000){
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
        }else{
            for (int i = 0; i < files.length; i++)
            {
                if(files[i].getName().contains("t0-")
                        || files[i].getName().contains("t1-")
                        || files[i].getName().contains("t2-")
                        || files[i].getName().contains("t3-")
                        || files[i].getName().contains("t4-")
                        || files[i].getName().contains("t5-")
                        || files[i].getName().contains("t6-")
                ) {
                    files[i].delete();
                }
            }
        }


        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        final DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        final long finalCount = count;
        myRef.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String status_download_db = dataSnapshot.child("status_download_db").getValue().toString();

                final String last_update_data = dataSnapshot.child("last_update_data").getValue().toString();
                myRef.child("update_data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                        System.out.println("cuy masuk akal");
                        String last_update_data_sistem = dataSnapshots.child("update_terakhir").getValue().toString();
                        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date dateobj = Calendar.getInstance().getTime();

                        Date date = null;
                        try {
                            date = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data);
                            Date date_2 = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data_sistem);
                            long milliseconds = date_2.getTime() - date.getTime();
                            long days = milliseconds / (1000 * 60 * 60 * 24);
                            String infomarsi="Tanggal Update Data Anda " +last_update_data_sistem;
                            String infomarsis="Tanggal Data Terbaru Sistem " +last_update_data_sistem;
                            String informasi="Jumlah data anda "+ finalCount;
                            SharedPreferences prefs = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            SharedPreferences.Editor editors = prefs.edit();
                            int status=prefs.getInt("key_name2", 0);
                            System.out.println("status "+status);
                            System.out.println("days "+days);
                            System.out.println( count_t0>=300000
                                    );
                            if(isMyServiceRunning(ForegroundService_t0.class)
                                    ||isMyServiceRunning(ForegroundService_t1.class)
                                    ||isMyServiceRunning(ForegroundService_t2.class)
                                    ||isMyServiceRunning(ForegroundService_t3.class)
                                    ||isMyServiceRunning(ForegroundService_t4.class)
                                    ||isMyServiceRunning(ForegroundService_t5.class)  ||isMyServiceRunning(ForegroundService_t6.class) ||isMyServiceRunning(ForegroundService_t6.class)|| checkStatus(getApplication() , DownloadManager.STATUS_RUNNING)
                            ){
                                Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                            }else{
                                if (days<=0 && status_download_db.trim().equals("1")
                                        && count_t0>=500000
                                        && count_t1>=500000
                                        && count_t2>=500000
                                        && count_t3>=500000
                                        && count_t4>=500000
                                        && count_t5>=500000
                                        && count_t6>=500000
                                ) {

                                    Toast.makeText(getApplication(), "Data Terupdate", Toast.LENGTH_LONG).show();
                                }else{
//
                                    System.out.println("masuk sinilah coy");
                                    if
                                    (       days>0
                                            && status_download_db.trim().equals("1")
                                            && count_t0>=500000
                                            && count_t1>=500000
                                            && count_t2>=500000
                                            && count_t3>=500000
                                            && count_t4>=500000
                                            && count_t5>=500000
                                            && count_t6>=500000){
//                                        clearApplicationData();
                                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.clear();
                                        editor.commit(); // commit changes
                                        editor.putInt("key_name2", 0);
                                        editor.apply();
                                        System.out.println("status download "+status);
                                        Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                        Realm.init(getApplicationContext());
                                        RealmConfiguration configuration= new RealmConfiguration.Builder()
                                                .name("vimatel.db")
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
                                        Realm.deleteRealm(configuration);
                                        final Realm realm= Realm.getInstance(configuration);
                                        realm.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm.deleteAll();

                                            }
                                        });
                                        realm.close();


                                        Realm.init(getApplicationContext());
                                        RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                                                .name("vimatel2.db")
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
                                        Realm.deleteRealm(configuration2);
                                        final Realm realm2 = Realm.getInstance(configuration2);
                                        realm2.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm2.deleteAll();
                                            }
                                        });
                                        realm2.close();


                                        Realm.init(getApplicationContext());
                                        RealmConfiguration configuration3 = new RealmConfiguration.Builder()
                                                .name("vimatel3.db")
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
                                        Realm.deleteRealm(configuration3);
                                        final Realm realm3 = Realm.getInstance(configuration3);
                                        realm3.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm3.deleteAll();
                                            }
                                        });
                                        realm3.close();


                                        Realm.init(getApplicationContext());
                                        RealmConfiguration configuration4 = new RealmConfiguration.Builder()
                                                .name("vimatel4.db")
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
                                        Realm.deleteRealm(configuration4);
                                        final Realm realm4 = Realm.getInstance(configuration4);
                                        realm4.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm4.deleteAll();
                                            }
                                        });
                                        realm4.close();


                                        Realm.init(getApplicationContext());
                                        RealmConfiguration configuration5 = new RealmConfiguration.Builder()
                                                .name("vimatel5.db")
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
                                        Realm.deleteRealm(configuration5);
                                        final Realm realm5 = Realm.getInstance(configuration5);
                                        realm5.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm5.deleteAll();
                                            }
                                        });
                                        realm5.close();


                                        Realm.init(getApplicationContext());
                                        RealmConfiguration configuration6 = new RealmConfiguration.Builder()
                                                .name("vimatel6.db")
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
                                        Realm.deleteRealm(configuration6);
                                        final Realm realm6 = Realm.getInstance(configuration6);
                                        realm6.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm6.deleteAll();
                                            }
                                        });
                                        realm6.close();


                                        Realm.init(getApplicationContext());
                                        RealmConfiguration configuration7 = new RealmConfiguration.Builder()
                                                .name("vimatel7.db")
                                                .schemaVersion(1)
                                                .deleteRealmIfMigrationNeeded()
                                                .build();
                                        Realm.deleteRealm(configuration7);
                                        final Realm realm7 = Realm.getInstance(configuration7);
                                        realm7.executeTransaction(new Realm.Transaction() {
                                            @Override
                                            public void execute(Realm realm) {
                                                realm7.deleteAll();
                                            }
                                        });
                                        realm7.close();

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

                                            DowloadFile_t0 dowloadFile_t0=new DowloadFile_t0();
                                            dowloadFile_t0.download(getApplication());
                                    }else{
                                        if(count_t0<=500000){
                                            Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            Realm.init(getApplicationContext());
                                            RealmConfiguration configuration= new RealmConfiguration.Builder()
                                                    .name("vimatel.db")
                                                    .schemaVersion(1)
                                                    .deleteRealmIfMigrationNeeded()
                                                    .build();
                                            final Realm realm= Realm.getInstance(configuration);
                                            realm.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm.deleteAll();
                                                }
                                            });
                                            realm.close();
                                            DowloadFile_t0 dowloadFile_t0=new DowloadFile_t0();
                                            dowloadFile_t0.download(getApplication());
                                        }else if(count_t1<=500000)
                                        {
                                            Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            Realm.init(getApplicationContext());
                                            RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                                                    .name("vimatel2.db")
                                                    .schemaVersion(1)
                                                    .deleteRealmIfMigrationNeeded()
                                                    .build();
                                            final Realm realm2 = Realm.getInstance(configuration2);
                                            realm2.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm2.deleteAll();
                                                }
                                            });
                                            realm2.close();
                                            DowloadFile_t1 dowloadFile_t1=new DowloadFile_t1();
                                            dowloadFile_t1.download(getApplication());
                                        }else if(count_t2<=500000)
                                        {
                                            Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            Realm.init(getApplicationContext());
                                            RealmConfiguration configuration3 = new RealmConfiguration.Builder()
                                                    .name("vimatel3.db")
                                                    .schemaVersion(1)
                                                    .deleteRealmIfMigrationNeeded()
                                                    .build();
                                            final Realm realm3 = Realm.getInstance(configuration3);
                                            realm3.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm3.deleteAll();
                                                }
                                            });
                                            realm3.close();
                                            DowloadFile_t2 dowloadFile_t2=new DowloadFile_t2();
                                            dowloadFile_t2.download(getApplication());
                                        }else if(count_t3<=500000)
                                        {
                                            Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            Realm.init(getApplicationContext());
                                            RealmConfiguration configuration4 = new RealmConfiguration.Builder()
                                                    .name("vimatel4.db")
                                                    .schemaVersion(1)
                                                    .deleteRealmIfMigrationNeeded()
                                                    .build();
                                            final Realm realm4 = Realm.getInstance(configuration4);
                                            realm4.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm4.deleteAll();
                                                }
                                            });
                                            realm4.close();
                                            DowloadFile_t3 dowloadFile_t3=new DowloadFile_t3();
                                            dowloadFile_t3.download(getApplication());
                                        }else if(count_t4<=500000)
                                        {
                                            Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            Realm.init(getApplicationContext());
                                            RealmConfiguration configuration5 = new RealmConfiguration.Builder()
                                                    .name("vimatel5.db")
                                                    .schemaVersion(1)
                                                    .deleteRealmIfMigrationNeeded()
                                                    .build();
                                            final Realm realm5 = Realm.getInstance(configuration5);
                                            realm5.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm5.deleteAll();
                                                }
                                            });
                                            realm5.close();
                                            DowloadFile_t4 dowloadFile_t4=new DowloadFile_t4();
                                            dowloadFile_t4.download(getApplication());
                                        }else if(count_t5<=500000)
                                        {
                                            Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            Realm.init(getApplicationContext());
                                            RealmConfiguration configuration6 = new RealmConfiguration.Builder()
                                                    .name("vimatel6.db")
                                                    .schemaVersion(1)
                                                    .deleteRealmIfMigrationNeeded()
                                                    .build();
                                            final Realm realm6 = Realm.getInstance(configuration6);
                                            realm6.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm6.deleteAll();
                                                }
                                            });
                                            realm6.close();
                                            DowloadFile_t5 dowloadFile_t5=new DowloadFile_t5();
                                            dowloadFile_t5.download(getApplication());
                                        }else if(count_t6<=500000)
                                        {
                                            Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                            Realm.init(getApplicationContext());
                                            RealmConfiguration configuration7 = new RealmConfiguration.Builder()
                                                    .name("vimatel7.db")
                                                    .schemaVersion(1)
                                                    .deleteRealmIfMigrationNeeded()
                                                    .build();
                                            final Realm realm7 = Realm.getInstance(configuration7);
                                            realm7.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    realm7.deleteAll();
                                                }
                                            });
                                            realm7.close();

                                            DowloadFile_t6 dowloadFile_t6=new DowloadFile_t6();
                                            dowloadFile_t6.download(getApplication());
                                        }else{
                                            Toast.makeText(getApplication(), "Data Paling Update", Toast.LENGTH_LONG).show();
                                        }
                                    }



                                }
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void clearApplicationData() {
        File cacheDirectory = getCacheDir();
        File applicationDirectory = new File(cacheDirectory.getParent());
        if (applicationDirectory.exists()) {
            String[] fileNames = applicationDirectory.list();
            for (String fileName : fileNames) {
                if (!fileName.equals("lib")) {
                    deleteFile(new File(applicationDirectory, fileName));
                }
            }
        }
    }

    public static boolean deleteFile(File file) {
        boolean deletedAll = true;
        if (file != null) {
            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    deletedAll = deleteFile(new File(file, children[i])) && deletedAll;
                }
            } else {
                deletedAll = file.delete();
            }
        }

        return deletedAll;
    }
    public static boolean checkStatus(Context context , int status) {
        DownloadManager downloadManager = (DownloadManager)
                context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();

        query.setFilterByStatus(status);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            c.close();

            return true;
        }

        return false;
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getApplication().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void goButtonNavigationBar() {
        //here default load page
        nav.setSelectedItemId(R.id.pencarian);
        loadPage(new PencarianPage());

        //then when selected page
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment page;
                switch (menuItem.getItemId()) {
                    case R.id.bantuan:
                        page = new BantuanPage();
                        loadPage(page);
                        return true;
                    case R.id.pencarian:
                        page = new PencarianPage();
                        loadPage(page);
                        return true;
                    case R.id.akun:
                        page = new AkunPage();
                        loadPage(page);
                        return true;
                }
                return false;
            }
        });
    }

    private void descComponent() {
        nav = findViewById(R.id.bottomNavigationView);
    }

    private void loadPage(Fragment page) {
        //load from here
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, page);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
