package com.digitalcreative.vimatelindonesia.View.MenuPages;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalcreative.vimatelindonesia.BaseActivity;
import com.digitalcreative.vimatelindonesia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
