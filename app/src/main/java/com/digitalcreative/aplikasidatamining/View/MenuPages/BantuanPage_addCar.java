package com.digitalcreative.aplikasidatamining.View.MenuPages;


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

import com.digitalcreative.aplikasidatamining.Controller.DataBaseHelper;
import com.digitalcreative.aplikasidatamining.Controller.DowloadFile_t0;
import com.digitalcreative.aplikasidatamining.Controller.DowloadFile_t1;
import com.digitalcreative.aplikasidatamining.Controller.DowloadFile_t2;
import com.digitalcreative.aplikasidatamining.Controller.DowloadFile_t3;
import com.digitalcreative.aplikasidatamining.Controller.DowloadFile_t4;
import com.digitalcreative.aplikasidatamining.Controller.DowloadFile_t5;
import com.digitalcreative.aplikasidatamining.Controller.DowloadFile_t6;
import com.digitalcreative.aplikasidatamining.Controller.ForegroundService_t0;
import com.digitalcreative.aplikasidatamining.MainActivity;
import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
import com.digitalcreative.aplikasidatamining.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanPage_addCar extends Fragment {
    TextView title;
    Button simpan;
    EditText pemilik_mobil,unitMobil, noPlat, tahun, ovd, namaFinance, noka, nomorMesin, cabang,saldo,warna;
    String[] getData;

    public BantuanPage_addCar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bantuan_page_add_car, container, false);

        //inisialisasi object
        initObject(view);

        //set title
        title.setText("Tambah Data Mobil");

        //do Function
        saveButtonFunc();

        return view;
    }

    private void saveButtonFunc() {
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get Input Text
                getInputFunc();
                for (int i = 0; i < getData.length;i++){
                    if (getData[i].equals("")){
                        getData[i]="-";
                    }
                }

                //do Save the data
                final Model_LacakMobil model_lacakMobil = new Model_LacakMobil();

                Realm.init(getContext());
                RealmConfiguration configuration = new RealmConfiguration.Builder()
                        .name("datamatel_manual.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                Realm realm = Realm.getInstance(configuration);
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        model_lacakMobil.setNama_mobil(getData[0]);
                        model_lacakMobil.setNo_plat(getData[1]);
                        model_lacakMobil.setNamaunit(getData[2]);
                        model_lacakMobil.setFinance(getData[3]);
                        model_lacakMobil.setOvd(getData[4]);
                        model_lacakMobil.setSaldo(getData[5]);
                        model_lacakMobil.setCabang(getData[6]);
                        model_lacakMobil.setNoka(getData[7]);
                        model_lacakMobil.setNosin(getData[8]);
                        model_lacakMobil.setTahun(getData[9]);
                        model_lacakMobil.setWarna(getData[10]);
                        bgRealm.insertOrUpdate(model_lacakMobil);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });
    }

    private void getInputFunc() {
        getData = new String[11];
        getData[0]= pemilik_mobil.getText().toString();
        getData[1]= noPlat.getText().toString();
        getData[2]= unitMobil.getText().toString();
        getData[3]= namaFinance.getText().toString();
        getData[4]= ovd.getText().toString();
        getData[5]= saldo.getText().toString();;
        getData[6]= cabang.getText().toString();
        getData[7]= noka.getText().toString();
        getData[8]= nomorMesin.getText().toString();
        getData[9]= tahun.getText().toString();
        getData[10]= warna.getText().toString();
    }

    private void initObject(View view) {
        //TextView
        title = view.findViewById(R.id.text_title);

        //EditText
        pemilik_mobil = view.findViewById(R.id.pemilik_mobil);
        unitMobil = view.findViewById(R.id.add_namamobil);
        noPlat = view.findViewById(R.id.add_noplat);
        tahun = view.findViewById(R.id.add_tahun);
        namaFinance = view.findViewById(R.id.add_finance);
        noka = view.findViewById(R.id.add_noka);
        nomorMesin = view.findViewById(R.id.add_nosin);
        ovd = view.findViewById(R.id.add_OVD);
        cabang = view.findViewById(R.id.add_cabang);
        saldo = view.findViewById(R.id.add_saldo);
        warna = view.findViewById(R.id.add_warna);


        //Button
        simpan = view.findViewById(R.id.btn_save);

    }

}
