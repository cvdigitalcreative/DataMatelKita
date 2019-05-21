package com.digitalcreative.aplikasidatamining.View.LoginandRegister;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.BaseActivity;
import com.digitalcreative.aplikasidatamining.Controller.Firebase;
import com.digitalcreative.aplikasidatamining.MainActivity;
import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.View.HomePage.Halaman_Utama;
import com.digitalcreative.aplikasidatamining.View.HomePage.LoadingPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginPage extends Fragment {
    FirebaseAuth firebaseAuth;
    EditText email, pass;
    String getemail, getpass;
    LinearLayout pop_up;

    public LoginPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);
            firebaseAuth = FirebaseAuth.getInstance();
            email = view.findViewById(R.id.username);
            pass = view.findViewById(R.id.password);
            pop_up = view.findViewById(R.id.pop_up_login);

            //Button Register
            final TextView btn_registrasi =  view.findViewById(R.id.btn_register);
            btn_registrasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.container_base, new RegisterPage())
                            .addToBackStack(null).commit();
                }
            });

            //Button Login
            final Button btn_login = view.findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkemail_instance();

                }
            });
        return view;
    }

    private void checkemail_instance() {
        pop_up.setVisibility(View.VISIBLE);
        getemail = email.getText().toString();
        getpass = pass.getText().toString();
        if (getemail.matches("") && getpass.matches("")) {
            Toast.makeText(getActivity(), "Login Gagal - Email atau Password Kosong", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.signInWithEmailAndPassword(getemail, getpass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //success login
                        //loadtheFirebase();
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
                                    System.out.println("sisa hari "+days);
                                    if(days>=0){
                                        Log.d(TAG, "signInWithEmail:success");
                                        String imei=dataSnapshot.child("imei").getValue().toString();
                                        String imei_device=getDeviceUniqueID(getActivity());
                                        if(imei.equals(imei_device)){
                                            pop_up.setVisibility(View.INVISIBLE);
//                                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                                            transaction.replace(R.id.container_base, new LoadingPage())
//                                                    .commit();
                                            Intent intent = new Intent(getActivity(), MainActivity.class);
                                            startActivity(intent);

                                        }else{
                                            pop_up.setVisibility(View.INVISIBLE);
                                            Toast.makeText(getActivity(), "Username dan HP Tidak Sama", Toast.LENGTH_SHORT).show();

                                        }

                                    }else{
                                        pop_up.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getActivity(), "Silahkan Perpanjang Masa Aktif Anda", Toast.LENGTH_SHORT).show();
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
                        //fail for login
                        pop_up.setVisibility(View.INVISIBLE);
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getActivity(), "Login Gagal - Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public String getDeviceUniqueID(Activity activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void loadtheFirebase() {
        Firebase firebase = new Firebase();
        firebase.loadfirebase(getContext());
    }



}
