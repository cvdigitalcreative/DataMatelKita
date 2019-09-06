package com.digitalcreative.vimatelindonesia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.digitalcreative.vimatelindonesia.View.MenuPages.AkunPage;
import com.digitalcreative.vimatelindonesia.View.MenuPages.BantuanPage;
import com.digitalcreative.vimatelindonesia.View.MenuPages.PencarianPage;
import com.digitalcreative.vimatelindonesia.View.MenuPages.PencarianPage_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        }else{
            startService();
        }


    }
    public void startService() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        String uid=firebaseUser.getUid();
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        serviceIntent.putExtra("inputExtra", uid);

        ContextCompat.startForegroundService(this, serviceIntent);
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
