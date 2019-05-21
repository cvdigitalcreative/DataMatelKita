package com.digitalcreative.aplikasidatamining.View.HomePage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.digitalcreative.aplikasidatamining.Controller.Firebase;
import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.View.LoginandRegister.LoginPage;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class LogoutPage extends Fragment {
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    LoginPage loginPage = new LoginPage();

    public LogoutPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout_page, container, false);
        progressBar = view.findViewById(R.id.progressbar_keluar);

        firebaseAuth = FirebaseAuth.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {
                firebaseAuth.signOut();
                progressAnimationbar();
                startApp();
            }
        }).start();
        return view;
    }

    private void startApp() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_base, new LoginPage())
                .commit();
    }

    private void progressAnimationbar() {
        for (int progress = 0; progress < 100; progress += 10) {
            System.out.println("progress = " + progress);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
