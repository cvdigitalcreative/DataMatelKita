package com.digitalcreative.aplikasidatamining.View;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.BaseActivity;
import com.digitalcreative.aplikasidatamining.MainActivity;
import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.View.HomePage.LogoutPage;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunPage extends Fragment {
    Button logout;
    TextView nama, kodeIMEI, statusApp, statusPembayaran, tanggalAktif, tanggalBerakhir, notelp, email;
    String string_nama, string_kodeIMEI, string_statusApp, string_statusPembayaran, string_tanggalAktif, string_tanggalBerakhir, string_email, string_notelp;
    Button back, edit;
    EditText edit_nama, edit_imei, edit_statusapp,edit_notel;

    SharedPreferences mySHPref;

    public AkunPage() {
        // Required empty public constructor
    }
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_akun_page, container, false);

        //Init the Variable
        sayHelloboys(view);

        //Get the Value
        getFromThem();

        //Set the Value
        talkTothem();

        //do Function
        btnlogoutFunc();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser =  firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        myRef.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String last_update_data= dataSnapshot.child("last_update_data").getValue().toString();
                myRef.child("update_data").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                        String last_update_data_sistem= dataSnapshots.child("update_terakhir").getValue().toString();
                        System.out.println("cuy masuk akal");
                        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date dateobj = Calendar.getInstance().getTime();

                        Date date = null;
                        try {
                            date = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data);
                            Date date_2 = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data_sistem);
                            long milliseconds = date_2.getTime() - date.getTime();
                            long days = milliseconds / (1000 * 60 * 60 * 24);
                            if (days>0 ) {
                                Toast.makeText(getActivity(), "Silahkan Update Data", Toast.LENGTH_LONG).show();

                            }
                        }  catch (ParseException e) {
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
        return view;
    }

    private void btnlogoutFunc() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BaseActivity.class);
                startActivity(intent);
//                LogoutPage update_profil = new LogoutPage();
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container_base, update_profil).commit();
            }
        });
    }
    private void sayHelloboys(View view) {
        //TextView
        nama = view.findViewById(R.id.akun_tv_nama);
        email = view.findViewById(R.id.akun_tv_mail);
        notelp = view.findViewById(R.id.akun_tv_nomortlp);
        //kodeIMEI = view.findViewById(R.id.akun_tv_kodeimei);
        statusApp = view.findViewById(R.id.akun_tv_status);
        statusPembayaran = view.findViewById(R.id.akun_tv_statuspembayaran);
        tanggalAktif = view.findViewById(R.id.akun_tv_tanggalaktif);
        tanggalBerakhir = view.findViewById(R.id.akun_tv_tanggalakhir);

        //Shared Preferences
        mySHPref = getContext().getSharedPreferences("detailUser", Context.MODE_PRIVATE);

        //Button Back
        back = view.findViewById(R.id.UP_back_button);
        edit = view.findViewById(R.id.UP_edit_button);

        //Edit Text
        edit_nama = view.findViewById(R.id.ED_nama);
        edit_imei = view.findViewById(R.id.ED_imei);

        edit_statusapp = view.findViewById(R.id.ED_aplikasi);
        logout = view.findViewById(R.id.btn_keluar);
    }

    private void getFromThem() {
        string_kodeIMEI = mySHPref.getString("IMEI", "No name defined");
        string_email = mySHPref.getString("email", "No name defined");
        string_notelp = mySHPref.getString("no_telepon", "No name defined");
        string_nama = mySHPref.getString("nama_lengkap", "No name defined");
        string_statusPembayaran = mySHPref.getString("status_pembayaran", "No name defined");
        string_statusApp = mySHPref.getString("status_app", "No name defined");
        string_tanggalAktif = mySHPref.getString("tanggal_aktif", "No name defined");
        string_tanggalBerakhir = mySHPref.getString("tanggal_berakhir", "No name defined");
    }

    private void talkTothem() {
        nama.setText(string_nama);
        statusApp.setText(string_statusApp);

        notelp.setText(string_notelp);
        statusPembayaran.setText(string_statusPembayaran);
        tanggalAktif.setText(string_tanggalAktif);
        tanggalBerakhir.setText(string_tanggalBerakhir);
        email.setText(string_email);

    }

}
