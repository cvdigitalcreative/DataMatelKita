package com.digitalcreative.aplikasidatamining.View.HomePage;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.Controller.Firebase;
import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.View.MenuPages.Custumer_Service;
import com.digitalcreative.aplikasidatamining.View.PencarianPage_Activity;
import com.digitalcreative.aplikasidatamining.View.MenuPages.Update_Profil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class Halaman_Utama extends Fragment {
    CoordinatorLayout lacakbtn, updatedatabtn;
    ImageButton updateprofilbtn, csbtn, carapembayaranbtn;
    TextView nama, notelp, firstchar;
    SharedPreferences myPref;
    String nama_u, notelp_u, first_char,last_update_data;
    LinearLayout finished;
    Button logout;

    public Halaman_Utama() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //loadFirebase
        loadtheFirebase();

        //Init
        descTheComponent(view);

        //getValue
        getPref();


        //SetValue
        setTheValue();

        //Actions
        buttonClickonListener();

        check_update_data();
        return view;
    }

    private void loadtheFirebase() {
        Firebase firebase = new Firebase();
        firebase.loadfirebase(getContext());
    }

    private void setTheValue() {
        firstchar.setText(first_char);
        nama.setText(nama_u);
        notelp.setText(notelp_u);
    }

    private void getPref() {
        nama_u = myPref.getString("nama_lengkap", "no define name");
        notelp_u = myPref.getString("no_telepon", "no define no telp");
        last_update_data= myPref.getString("last_update_data", "no define last_update_data");
        first_char = nama_u.substring(0, 1);
    }
    private static final int REQUEST_WRITE_STORAGE = 112;

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //reload my activity with permission granted or use the features what required the permission
                } else
                {
                    Toast.makeText(getActivity(), "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    private void buttonClickonListener() {
        lacakbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress;
                progress=new ProgressDialog(view.getContext());
                progress.setMessage("Please Wait . . .");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                Intent lacakintent = new Intent(getActivity(), PencarianPage_Activity.class);
                startActivity(lacakintent);

            }
        });

//       updatedatabtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
//                if (!hasPermission) {
//
//                    try {
//                        ActivityCompat.requestPermissions(getActivity(),
//                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                REQUEST_WRITE_STORAGE);
//                        //loading Data
//                        BackendFirebase backendFirebase = new BackendFirebase(getContext(), view, finished);
//                        backendFirebase.downloadFile(getContext());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    //loading Data
//
//                    try {
//                        BackendFirebase backendFirebase = new BackendFirebase(getContext(), view, finished);
//                        backendFirebase.downloadFile(getContext());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        });

        updateprofilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_Profil update_profil = new Update_Profil();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_base, update_profil);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        csbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Custumer_Service custumer_service = new Custumer_Service();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_base, custumer_service);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

//        carapembayaranbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Cara_Pembayaran cara_pembayaran = new Cara_Pembayaran();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container_base, cara_pembayaran);
//                fragmentTransaction.addToBackStack(null).commit();
//            }
//        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogoutPage update_profil = new LogoutPage();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_base, update_profil).commit();
            }
        });
    }

    private void descTheComponent(View view) {
        //Button
        logout = view.findViewById(R.id.logout_account);

        //LinearLayout
        lacakbtn = view.findViewById(R.id.lacakmobil_menu);
        updatedatabtn = view.findViewById(R.id.updatedata_menu);
        updateprofilbtn = view.findViewById(R.id.updateImg_btn);
        csbtn = view.findViewById(R.id.callImage_Btn);
        carapembayaranbtn = view.findViewById(R.id.caraImg_Btn);

        //SharedPref
        myPref = getContext().getSharedPreferences("detailUser", Context.MODE_PRIVATE);

        //TextView
        nama = view.findViewById(R.id.nama_user);
        notelp = view.findViewById(R.id.no_telp);
        firstchar = view.findViewById(R.id.text_icon);

        //Linear Layout
        finished =  view.findViewById(R.id.finish_progressbar);
    }

    public void check_update_data(){
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        final DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser =  firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();



        myRef.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String data_update = dataSnapshot.child("last_update_data").getValue().toString();
                final String masa_aktif = dataSnapshot.child("tanggal_berakhir").getValue().toString();
                Toast.makeText(getActivity(), "Masa Aktif Anda Sampai "+masa_aktif, Toast.LENGTH_SHORT).show();

                myRef.child("update_data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String update_terakhir = dataSnapshot.child("update_terakhir").getValue().toString();
                        try {

                            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data_update);
                            Date date_2 = new SimpleDateFormat("dd/MM/yyyy").parse(update_terakhir);

                            long milliseconds = date.getTime() - date_2.getTime();
                            long days = milliseconds / (1000 * 60 * 60 * 24);
                            System.out.println(days);
                            if(days<0){
                                Toast.makeText(getActivity(), "Silahkan Update Data", Toast.LENGTH_SHORT).show();
                            }else{
                                System.out.println("benar    "+days);
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
}