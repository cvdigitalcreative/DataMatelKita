package com.digitalcreative.aplikasidatamining;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.View.HomePage.Halaman_Utama;
import com.digitalcreative.aplikasidatamining.View.HomePage.LoadingPage;
import com.digitalcreative.aplikasidatamining.View.LoginandRegister.LoginPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class BaseActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //check when user had login

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            FirebaseAuth firebaseAuth;
            FirebaseUser firebaseUser;
            FirebaseDatabase firebaseDatabase;
            DatabaseReference myRef;
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseUser =  firebaseAuth.getCurrentUser();
            myRef = firebaseDatabase.getReference();

            myRef.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String tanggal_berakhir = dataSnapshot.child("tanggal_berakhir").getValue().toString();



                    try {
                        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date dateobj = Calendar.getInstance().getTime();
                        String date_s = curFormat.format(dateobj);
                        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(date_s);
                        Date date_2 = new SimpleDateFormat("dd/MM/yyyy").parse(tanggal_berakhir);

                        long milliseconds = date_2.getTime() - date.getTime();
                        long days = milliseconds / (1000 * 60 * 60 * 24);
                        if(days>=0){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
//                            Fragment fragment = new Halaman_Utama();
//                            FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.add(R.id.container_base, fragment);
//                            fragmentTransaction.commit();
                        }else{
                            Fragment fragment = new LoginPage();
                            FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.add(R.id.container_base, fragment);
                            fragmentTransaction.commit();
                            Toast.makeText(getApplicationContext(), "Silahkan Perpanjang Masa Aktif Anda", Toast.LENGTH_SHORT).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Fragment fragment = new LoginPage();
            FragmentTransaction fragmentTransaction =  getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.container_base, fragment);
            fragmentTransaction.commit();
        }
    }
}
