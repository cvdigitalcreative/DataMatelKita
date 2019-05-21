package com.digitalcreative.aplikasidatamining.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.digitalcreative.aplikasidatamining.Controller.DataBaseHelper;
import com.digitalcreative.aplikasidatamining.R;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanPage_addCar extends Fragment {
    TextView title;
    Button simpan;
    EditText unitMobil, noPlat, tahun, ovd, namaFinance, noka, nomorMesin, cabang;
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
                try {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
                    dataBaseHelper.insertdataManual(getData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void getInputFunc() {
        getData = new String[11];
        getData[0]= "";
        getData[1]= noPlat.getText().toString();
        getData[2]= unitMobil.getText().toString();
        getData[3]= namaFinance.getText().toString();
        getData[4]= ovd.getText().toString();
        getData[5]= "";
        getData[6]= cabang.getText().toString();
        getData[7]= noka.getText().toString();
        getData[8]= nomorMesin.getText().toString();
        getData[9]= tahun.getText().toString();
        getData[10]= "";
    }

    private void initObject(View view) {
        //TextView
        title = view.findViewById(R.id.text_title);

        //EditText
        unitMobil = view.findViewById(R.id.add_namamobil);
        noPlat = view.findViewById(R.id.add_noplat);
        tahun = view.findViewById(R.id.add_tahun);
        namaFinance = view.findViewById(R.id.add_finance);
        noka = view.findViewById(R.id.add_noka);
        nomorMesin = view.findViewById(R.id.add_nosin);
        ovd = view.findViewById(R.id.add_OVD);
        cabang = view.findViewById(R.id.add_cabang);

        //Button
        simpan = view.findViewById(R.id.btn_save);

    }

}
