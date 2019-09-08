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
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t0;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t1;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t2;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t3;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t4;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService_t5;
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
        Realm.init(getApplicationContext());
        final RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm[] realm = new Realm[1];
        realm[0] = Realm.getInstance(configuration);
        final long count = realm[0].where(Model_LacakMobil.class).count();
        String path= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        File directory = new File(path);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            if(files[i].getName().contains("t0-")
                    || files[i].getName().contains("t1-")
                    || files[i].getName().contains("t2-")
                    || files[i].getName().contains("t3-")
                    || files[i].getName().contains("t4-")
                    || files[i].getName().contains("t5-")
            ) {
                files[i].delete();
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
                            String informasi="Jumlah data anda "+count;

                            if(isMyServiceRunning(ForegroundService_t0.class)
                                    ||isMyServiceRunning(ForegroundService_t1.class)
                                    ||isMyServiceRunning(ForegroundService_t2.class)
                                    ||isMyServiceRunning(ForegroundService_t3.class)
                                    ||isMyServiceRunning(ForegroundService_t4.class)
                                    ||isMyServiceRunning(ForegroundService_t5.class) || checkStatus(getApplication() , DownloadManager.STATUS_RUNNING)
                            ){
                                Toast.makeText(getApplication(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                            }else{
                                if (days<=0 && status_download_db.trim().equals("1") && count>2900000) {
                                    Toast.makeText(getApplication(), "Data Terupdate", Toast.LENGTH_LONG).show();
                                }else{
//                                    realm[0] = Realm.getInstance(configuration);
//                                    realm[0].executeTransaction(new Realm.Transaction() {
//                                        @Override
//                                        public void execute(Realm realm) {
//                                            realm.deleteAll();
//                                        }
//                                    });
                                    Toast.makeText(getApplication(), "Update Data dimulai", Toast.LENGTH_LONG).show();
                                    DowloadFile_t0 dowloadFile_t0=new DowloadFile_t0();
                                    dowloadFile_t0.download(getApplication(),0,"link_tes","t0.csv");

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
