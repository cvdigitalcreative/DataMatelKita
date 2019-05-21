package com.digitalcreative.aplikasidatamining.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.digitalcreative.aplikasidatamining.View.HomePage.Halaman_Utama;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    //Init the Variable
    private void sayHello(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser =  firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
    }

    public void loadfirebase(final Context context){
        //Init
        sayHello();

        myRef.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String nama_lengkap = dataSnapshot.child("nama_lengkap").getValue().toString();
                String no_telepon = dataSnapshot.child("no_telepon").getValue().toString();
                String password = dataSnapshot.child("password").getValue().toString();
                String status_app = dataSnapshot.child("status_app").getValue().toString();
                String status_pembayaran = dataSnapshot.child("status_pembayaran").getValue().toString();
                String tanggal_aktif = dataSnapshot.child("tanggal_aktif").getValue().toString();
                String tanggal_berakhir = dataSnapshot.child("tanggal_berakhir").getValue().toString();
                String imei = dataSnapshot.child("imei").getValue().toString();
                String last_update_data= dataSnapshot.child("last_update_data").getValue().toString();

                sendBundle(nama_lengkap, no_telepon);

                SharedPreferences.Editor chaching = context.getSharedPreferences("detailUser", Context.MODE_PRIVATE).edit();
                chaching.putString("username", username);
                chaching.putString("email", email);
                chaching.putString("nama_lengkap", nama_lengkap);
                chaching.putString("no_telepon", no_telepon);
                chaching.putString("status_app", status_app);
                chaching.putString("status_pembayaran", status_pembayaran);
                chaching.putString("password", password);
                chaching.putString("tanggal_aktif", tanggal_aktif);
                chaching.putString("tanggal_berakhir", tanggal_berakhir);
                chaching.putString("imei", imei);
                chaching.putString("last_update_data", last_update_data);
                chaching.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendBundle(String nama_lengkap, String no_telepon) {
        Halaman_Utama frg =  new Halaman_Utama();
        Bundle bundle =  new Bundle();
        bundle.putString("namakite", nama_lengkap);
        bundle.putString("notelpkite", no_telepon);
        frg.setArguments(bundle);

    }

}
