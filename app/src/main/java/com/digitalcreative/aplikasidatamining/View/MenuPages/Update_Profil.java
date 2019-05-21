package com.digitalcreative.aplikasidatamining.View.MenuPages;


import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.View.HomePage.Halaman_Utama;

/**
 * A simple {@link Fragment} subclass.
 */
public class Update_Profil extends Fragment {
    TextView nama, kodeIMEI, statusApp, statusPembayaran, tanggalAktif, tanggalBerakhir;
    String string_nama, string_kodeIMEI, string_statusApp, string_statusPembayaran, string_tanggalAktif, string_tanggalBerakhir;
    Button back, edit;
    EditText edit_nama, edit_imei, edit_statusapp, edit_pembayaran, edit_tanggalaktif, edit_tanggalberakhir;

    SharedPreferences mySHPref;

    public Update_Profil() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update__profil, container, false);

        //Init the Variable
        sayHelloboys(view);

        //Get the Value
        getFromThem();

        //Set the Value
        talkTothem();

        //Actions
        doitBoys();

        return view;
    }

    private void sayHelloboys(View view) {
        //TextView
        nama = view.findViewById(R.id.UP_nama);
        kodeIMEI = view.findViewById(R.id.UP_IMEI);
        statusApp = view.findViewById(R.id.UP_status_aplikasi);
        statusPembayaran = view.findViewById(R.id.UP_status_pembayaran);
        tanggalAktif = view.findViewById(R.id.UP_tanggal_aktif);
        tanggalBerakhir = view.findViewById(R.id.UP_tanggal_berakhir);

        //Shared Preferences
        mySHPref = getContext().getSharedPreferences("detailUser", Context.MODE_PRIVATE);

        //Button Back
        back = view.findViewById(R.id.UP_back_button);
        edit = view.findViewById(R.id.UP_edit_button);

        //Edit Text
        edit_nama = view.findViewById(R.id.ED_nama);
        edit_imei = view.findViewById(R.id.ED_imei);
        edit_statusapp = view.findViewById(R.id.ED_aplikasi);
    }

    private void getFromThem() {
        string_kodeIMEI = mySHPref.getString("IMEI", "No name defined");

        string_nama = mySHPref.getString("nama_lengkap", "No name defined");
        string_statusPembayaran = mySHPref.getString("status_pembayaran", "No name defined");
        string_statusApp = mySHPref.getString("status_app", "No name defined");
        string_tanggalAktif = mySHPref.getString("tanggal_aktif", "No name defined");
        string_tanggalBerakhir = mySHPref.getString("tanggal_berakhir", "No name defined");
    }

    private void talkTothem() {
        nama.setText(string_nama);
        kodeIMEI.setText(string_kodeIMEI);
        statusApp.setText(string_statusApp);
        statusPembayaran.setText(string_statusPembayaran);
        tanggalAktif.setText(string_tanggalAktif);
        tanggalBerakhir.setText(string_tanggalBerakhir);

    }

    private void doitBoys() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Halaman_Utama update_data = new Halaman_Utama();
                FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_base, update_data);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog =  new AlertDialog.Builder(getContext());
                View inflater =  getActivity().getLayoutInflater().inflate(R.layout.edit_dialog, null);
                dialog.setView(inflater);

            }
        });
    }

}
