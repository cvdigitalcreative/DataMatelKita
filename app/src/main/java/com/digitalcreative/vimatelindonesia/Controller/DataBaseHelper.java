package com.digitalcreative.vimatelindonesia.Controller;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.digitalcreative.vimatelindonesia.Model.Model_LacakMobil;
import com.digitalcreative.vimatelindonesia.RealmHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import io.realm.Realm;

public class DataBaseHelper {
    private static String DB_PATH;

    private static String DB_NAME = "tes.db";

    private static int DB_VERSION = 4;

    private SQLiteDatabase myDataBase;

    private static Context myContext;

    private static DataBaseHelper instance;
    String line = "";
    Realm realm;
    RealmHelper realmHelper;
    Model_LacakMobil model_lacakMobil;

    public DataBaseHelper(Context context) throws IOException {

    }

    public void insertdata(String file) {
        // get writable database as we want to write data


        final String csvFile = file;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {


            while ((line = br.readLine()) != null) {


                // use comma as separator
                String[] country = line.split(",");

                if (country.length == 12) {
                    model_lacakMobil = new Model_LacakMobil();
                    realmHelper = new RealmHelper(realm);

                    model_lacakMobil.setNama_mobil(country[1]);
                    model_lacakMobil.setNo_plat(country[2]);
                    model_lacakMobil.setNamaunit(country[3]);
                    model_lacakMobil.setFinance(country[4]);
                    model_lacakMobil.setOvd(country[5]);
                    model_lacakMobil.setSaldo(country[6]);
                    model_lacakMobil.setCabang(country[7]);
                    model_lacakMobil.setNoka(country[8]);
                    model_lacakMobil.setNosin(country[9]);
                    model_lacakMobil.setTahun(country[10]);
                    model_lacakMobil.setWarna(country[11]);
                    realmHelper.save(model_lacakMobil);


                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            fixing_data();

        }
    }

    //
    public void insertdataManual(String[] country) {
        // get writable database as we want to write data

        model_lacakMobil = new Model_LacakMobil();
        realmHelper = new RealmHelper(realm);

        model_lacakMobil.setNama_mobil(country[1]);
        model_lacakMobil.setNo_plat(country[2]);
        model_lacakMobil.setNamaunit(country[3]);
        model_lacakMobil.setFinance(country[4]);
        model_lacakMobil.setOvd(country[5]);
        model_lacakMobil.setSaldo(country[6]);
        model_lacakMobil.setCabang(country[7]);
        model_lacakMobil.setNoka(country[8]);
        model_lacakMobil.setNosin(country[9]);
        model_lacakMobil.setTahun(country[10]);
        model_lacakMobil.setWarna(country[11]);
        realmHelper.save(model_lacakMobil);
    }


}
