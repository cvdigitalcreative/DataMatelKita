package com.digitalcreative.aplikasidatamining.Controller;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;

    private static String DB_NAME = "tes.db";

    private static int DB_VERSION = 4;

    private SQLiteDatabase myDataBase;

    private static Context myContext;

    private static DataBaseHelper instance;
    String line = "";

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;

        if (Build.VERSION.SDK_INT >= 4.2) {
            DB_PATH = myContext.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + myContext.getPackageName() + "/databases/";
        }

        boolean dbexist = checkDataBase();
        if (dbexist) {
            System.out.println("Database  exist");

        } else {
            System.out.println("Database doesn't exist");
            createDataBase();

        }

    }


    public void createDataBase() throws IOException {
        boolean dbexist = checkDataBase();
        if (dbexist) {
            // System.out.println(" Database exists.");
        } else {
            this.getReadableDatabase();
            this.close();
            try {
                System.out.println("copy database");
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myinput = myContext.getAssets().open("databases/" + DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(DB_PATH + DB_NAME);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        // Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();

        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
        {
            Log.v("Database Upgrade", "Database version higher than old.");

        }

    }
    public long count_data(){
        SQLiteDatabase db = this.getReadableDatabase();
        long taskCount = DatabaseUtils.queryNumEntries(db, "data");
        return taskCount;
    }


    public void insertdata(String file) {
        // get writable database as we want to write data

        openDataBase();

        myDataBase.beginTransaction();

        final String csvFile = file;
        DatabaseUtils.InsertHelper ih=new DatabaseUtils.InsertHelper(myDataBase,"data");
        final int nama_column=ih.getColumnIndex("nama");
        final int nopol_column=ih.getColumnIndex("nopol");
        final int unit_column=ih.getColumnIndex("unit");
        final int finance_column=ih.getColumnIndex("finance");
        final int ovd_column=ih.getColumnIndex("ovd");
        final int sipok_column=ih.getColumnIndex("sipok");
        final int cabang_column=ih.getColumnIndex("cabang");
        final int noka_column=ih.getColumnIndex("noka");
        final int nosin_column=ih.getColumnIndex("nosin");
        final int tahun_column=ih.getColumnIndex("tahun");
        final int warna_column=ih.getColumnIndex("warna");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {


            while ((line = br.readLine()) != null) {


                // use comma as separator
                String[] country = line.split(";");

                if(country.length==12){

                    ih.prepareForReplace();
                    ih.bind(nama_column, country[1]);
                    ih.bind(nopol_column, country[2]);
                    ih.bind(unit_column, country[3]);
                    ih.bind(finance_column, country[4]);
                    ih.bind(ovd_column, country[5]);
                    ih.bind(sipok_column, country[6]);
                    ih.bind(cabang_column, country[7]);
                    ih.bind(noka_column, country[8]);
                    ih.bind(nosin_column, country[9]);
                    ih.bind(tahun_column, country[10]);
                    ih.bind(warna_column, country[11]);
                    ih.execute();
//
//                    ContentValues valuesa = new ContentValues();
//
//                    valuesa.put("nama", country[1].toString());
//                    valuesa.put("nopol", country[2].toString());
//                    valuesa.put("unit", country[3].toString());
//                    valuesa.put("finance", country[4].toString());
//                    valuesa.put("ovd", country[5].toString());
//                    valuesa.put("sipok",  country[6].toString());
//                    valuesa.put("cabang", country[7].toString());
//                    valuesa.put("noka",  country[8].toString());
//                    valuesa.put("nosin", country[9].toString());
//                    valuesa.put("tahun",  country[10].toString());
//                    valuesa.put("warna", country[11].toString());
//                    // this will insert if record is new, update otherwise
//
//                    myDataBase.insertWithOnConflict("data", null, valuesa, SQLiteDatabase.CONFLICT_REPLACE);


                }



            }
            myDataBase.setTransactionSuccessful();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            fixing_data();
            ih.close();
            myDataBase.endTransaction();
            myDataBase.close();
        }
    }
//
    public void insertdataManual(String[] country) {
        // get writable database as we want to write data

        openDataBase();
        myDataBase.beginTransaction();
        ContentValues valuesa = new ContentValues();

        valuesa.put("nama", country[0].toString());
        valuesa.put("nopol", country[1].toString());
        valuesa.put("unit", country[2].toString());
        valuesa.put("finance", country[3].toString());
        valuesa.put("ovd", country[4].toString());
        valuesa.put("sipok",  country[5].toString());
        valuesa.put("cabang", country[6].toString());
        valuesa.put("noka",  country[7].toString());
        valuesa.put("nosin", country[8].toString());
        valuesa.put("tahun",  country[9].toString());
        valuesa.put("warna", country[10].toString());
        // this will insert if record is new, update otherwise
        myDataBase.insertWithOnConflict("data", null, valuesa, SQLiteDatabase.CONFLICT_REPLACE);
        myDataBase.setTransactionSuccessful();

//            fixing_data();
            myDataBase.endTransaction();
            myDataBase.close();
    }

    public void fixing_data(){
        myDataBase.execSQL("UPDATE data SET nama='-' WHERE nama IS NULL");
        myDataBase.execSQL("UPDATE data SET nopol='-' WHERE nopol IS NULL");
        myDataBase.execSQL("UPDATE data SET unit='-' WHERE unit IS NULL");
        myDataBase.execSQL("UPDATE data SET finance='-' WHERE finance IS NULL");
        myDataBase.execSQL("UPDATE data SET ovd='-' WHERE ovd IS NULL");
        myDataBase.execSQL("UPDATE data SET sipok='-' WHERE sipok IS NULL");
        myDataBase.execSQL("UPDATE data SET cabang='-' WHERE cabang IS NULL");
        myDataBase.execSQL("UPDATE data SET noka='-' WHERE noka IS NULL");
        myDataBase.execSQL("UPDATE data SET nosin='-' WHERE nosin IS NULL");
        myDataBase.execSQL("UPDATE data SET tahun='-' WHERE tahun IS NULL");
        myDataBase.execSQL("UPDATE data SET warna='-' WHERE warna IS NULL");

    }

    public void reset_data(){
        SQLiteDatabase db = this.getReadableDatabase();
        int affectedRows = db.delete("data", null, null);

    }

    public ArrayList<ArrayList>  getAllData(String nopol, String nosin) {
        openDataBase();
        myDataBase.beginTransaction();

        ArrayList<ArrayList> data = new ArrayList<>();
        Cursor c = myDataBase.rawQuery("SELECT DISTINCT nama,nopol,unit,finance,ovd,sipok,cabang,noka,nosin,tahun,warna FROM data " +
                " where nopol"   + " like '%" + nopol
                + "%'"+" or "+ "  nosin"   + " like '" + nosin
                + "%'"+" or "+ "  noka"   + " like '" + nosin
                + "%' limit 0,10 ", null);

        if (c.moveToFirst()){
            do {
                // Passing values
                ArrayList<String> datas = new ArrayList<>();
                datas.add(c.getString(0));
                datas.add(c.getString(1));
                datas.add(c.getString(2));
                datas.add(c.getString(3));
                datas.add(c.getString(4));
                datas.add(c.getString(5));
                datas.add(c.getString(6));
                datas.add(c.getString(7));
                datas.add(c.getString(8));
                datas.add(c.getString(9));
                datas.add(c.getString(10));
//                datas.add(c.getString(11));
                data.add(datas);

            }
            while(c.moveToNext());
        }
        c.close();
        myDataBase.endTransaction();
        myDataBase.close();

        return data;
    }
}
