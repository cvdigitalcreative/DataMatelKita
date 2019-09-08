package com.digitalcreative.vimatelindonesia;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.Toast;

import com.digitalcreative.vimatelindonesia.Controller.ForegroundService;
import com.digitalcreative.vimatelindonesia.Model.Model_LacakMobil;
import com.digitalcreative.vimatelindonesia.View.MenuPages.AkunPage;
import com.digitalcreative.vimatelindonesia.View.MenuPages.BantuanPage;
import com.digitalcreative.vimatelindonesia.View.MenuPages.PencarianPage;

import java.io.File;

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
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm realm;
        realm = Realm.getInstance(configuration);
        final long count = realm.where(Model_LacakMobil.class).count();
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
