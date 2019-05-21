package com.digitalcreative.aplikasidatamining;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.digitalcreative.aplikasidatamining.View.AkunPage;
import com.digitalcreative.aplikasidatamining.View.BantuanPage;
import com.digitalcreative.aplikasidatamining.View.PencarianPage;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView nav;

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
