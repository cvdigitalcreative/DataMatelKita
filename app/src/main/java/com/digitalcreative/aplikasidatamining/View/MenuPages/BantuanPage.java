package com.digitalcreative.aplikasidatamining.View.MenuPages;


import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.Controller.DataBaseHelper;
import com.digitalcreative.aplikasidatamining.Controller.Firebase;
import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.RealmHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanPage extends Fragment {
    TextView tv1, tv2;
    LinearLayout finished;
    CardView addCar, update;
    ImageView wa_btn, call_tbn, sms_btn;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static String DB_PATH;

    private static String DB_NAME = "tes.db";
    Realm realm;
    RealmHelper realmHelper;

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

    public BantuanPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_bantuan_page, container, false);

        //loadFirebase
        loadtheFirebase();

        //Init the Variable
        sayHelloboys(view);
//        Realm.init(this.getContext());
//        RealmConfiguration config =
//                new RealmConfiguration.Builder()
//                        .name("test.db")
//                        .schemaVersion(1)
//                        .deleteRealmIfMigrationNeeded()
//                        .build();
//        realm = Realm.getInstance(config);
        //Set up Realm
        Realm.init(this.getContext());
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                        .name("test.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
        realm = Realm.getInstance(configuration);

        realmHelper = new RealmHelper(realm);
        //Actions
        doitBoys();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
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
        jumlah_id=new ArrayList<>();
        jumlah__download_id=new ArrayList<>();
        getActivity().registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        return view;
    }

    private void sayHelloboys(View view) {
        //TextView
        tv1 = view.findViewById(R.id.text_bantuan_1);
        tv2 = view.findViewById(R.id.text_bantuan_2);

        //LinearLayout
        finished = view.findViewById(R.id.finish_progresbar3);

        //Cardview
        addCar = view.findViewById(R.id.btn_tambahdatamobil);
        update = view.findViewById(R.id.btn_caraPembayaran);

        //ImageView
        wa_btn = view.findViewById(R.id.wa_btn);
        call_tbn = view.findViewById(R.id.call_btn);
        sms_btn = view.findViewById(R.id.sms_btn);
    }
    String url_t0;
     String subpath_t0;
    String url_t1;
     String subpath_t1;
    String url_t2;
     String subpath_t2;
    String url_t3;
     String subpath_t3;
    String url_t4;
     String subpath_t4;
    String url_t5;
     String subpath_t5;
    String url_data_update;
     String subpath_data_update;
     private ArrayList<Long> jumlah_id;
    private ArrayList<Long> jumlah__download_id;
    private long downloadID;

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            jumlah_id.add(id);
            //Checking if the received broadcast is for our enqueued download by matching download id
            System.out.println(jumlah_id.size());
            System.out.println(jumlah__download_id.size());
            if (jumlah_id.size()==jumlah__download_id.size()) {


//                                                                insert_database(subpath_t1);
//                                                                insert_database(subpath_t2);
//                                                                insert_database(subpath_t3);
//                                                                insert_database(subpath_t4);
//                                                                insert_database(subpath_t5);
//                                                                insert_database(subpath_data_update);
                                                                insert_database(subpath_t0);
//                                                                    update_data();
                                                                progress.dismiss();






                    Toast.makeText(getContext(), "Download Completed", Toast.LENGTH_SHORT).show();
                    finished.setVisibility(View.VISIBLE);
                    finished.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finished.setVisibility(View.INVISIBLE);
                        }
                    });

                    tv2.setText("berhasil");


            }

        }
    };
    private void doitBoys() {
        sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + "+6285268801717"));
                startActivity(intent);
            }
        });

        wa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=" + "+6285268801717";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Whatsapp have not been installed", Toast.LENGTH_LONG).show();
                }
            }
        });

        call_tbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                callIntent.setData(Uri.parse("tel:" + "+6285268801717"));
//                getActivity().startActivity(callIntent);
                int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            62);
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    callIntent.setData(Uri.parse("tel:" + "+6285268801717"));
                    getActivity().startActivity(callIntent);
                }

            }
        });

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new BantuanPage_addCar());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Sedang Update", Toast.LENGTH_LONG).show();
                boolean hasPermission = (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermission) {


                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_STORAGE);
                    Toast.makeText(getActivity(), "Silahkan update kembali", Toast.LENGTH_LONG).show();
//                        //loading Data
//                        BackendFirebase backendFirebase = new BackendFirebase(getContext(), v, finished, tv1, tv2);
//                        backendFirebase.downloadFile(getContext());

                } else {

                    //loading Data


//                        BackendFirebase backendFirebase = new BackendFirebase(getContext(), v, finished, tv1, tv2);
//                    try {
//                        backendFirebase.downloadFile(getContext());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("done");

                    progress = new ProgressDialog(getActivity());
                    progress.setMessage("Updating Data . . .");
                    progress.setIndeterminate(true);
                    progress.setCancelable(false);
                    progress.show();
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseUser = firebaseAuth.getCurrentUser();
                    myRef = firebaseDatabase.getReference();
                    if (Build.VERSION.SDK_INT >= 4.2) {
                        DB_PATH = getContext().getApplicationInfo().dataDir + "/databases/";
                    } else {
                        DB_PATH = "/data/data/" + getContext().getPackageName() + "/databases/";
                    }

                    boolean dbexist = checkDataBase();
                    if (dbexist) {
                        myRef.child("Users").child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String status_download_db = dataSnapshot.child("status_download_db").getValue().toString();

                                final String last_update_data = dataSnapshot.child("last_update_data").getValue().toString();
                                myRef.child("update_data").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshots) {
                                        System.out.println("cuy masuk akal");
                                        String last_update_data_sistem = dataSnapshots.child("update_terakhir").getValue().toString();
                                        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        Date dateobj = Calendar.getInstance().getTime();

                                        Date date = null;
                                        try {
                                            date = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data);
                                            Date date_2 = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data_sistem);
                                            long milliseconds = date_2.getTime() - date.getTime();
                                            long days = milliseconds / (1000 * 60 * 60 * 24);
                                            if (days<=0 && status_download_db.trim().equals("1")) {
                                                progress.dismiss();
                                                Toast.makeText(getActivity(), "Data Terupdate", Toast.LENGTH_LONG).show();

                                            } else {
                                                realm.executeTransaction(new Realm.Transaction() {
                                                    @Override
                                                    public void execute(Realm realm) {
                                                        realm.deleteAll();
                                                    }
                                                });
                                                myRef.child("link").addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {




                                                        String waktu_download=dataSnapshot.child("waktudownload").getValue().toString();
                                                        url_t0 = dataSnapshot.child("link_tes").getValue().toString();
                                                        subpath_t0 = "t0.csv";
                                                        downloadfromdropbox(url_t0, subpath_t0);

                                                        url_t1 = dataSnapshot.child("link_tes1").getValue().toString();
                                                        subpath_t1 = "t1.csv";
                                                        downloadfromdropbox(url_t1, subpath_t1);

                                                        url_t2 = dataSnapshot.child("link_tes2").getValue().toString();
                                                        subpath_t2 = "t2.csv";
                                                        downloadfromdropbox(url_t2, subpath_t2);

                                                        url_t3 = dataSnapshot.child("link_tes3").getValue().toString();
                                                        subpath_t3 = "t3.csv";
                                                        downloadfromdropbox(url_t3, subpath_t3);

                                                        url_t4 = dataSnapshot.child("link_tes4").getValue().toString();
                                                        subpath_t4 = "t4.csv";
                                                        downloadfromdropbox(url_t4, subpath_t4);


                                                        url_t5 = dataSnapshot.child("link_tes5").getValue().toString();
                                                        subpath_t5 = "t5.csv";
                                                        downloadfromdropbox(url_t5, subpath_t5);

                                                        url_data_update = dataSnapshot.child("link_data").getValue().toString();
                                                        subpath_data_update = "dataupdate.csv";
                                                        downloadfromdropbox(url_data_update, subpath_data_update);


//
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
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


                    }else{
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.deleteAll();
                            }
                        });
                        myRef.child("link").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                                url_t0 = dataSnapshot.child("link_tes").getValue().toString();
                                subpath_t0 = "t0.csv";
                                downloadfromdropbox(url_t0, subpath_t0);

                                url_t1 = dataSnapshot.child("link_tes1").getValue().toString();
                                subpath_t1 = "t1.csv";
                                downloadfromdropbox(url_t1, subpath_t1);

                                url_t2 = dataSnapshot.child("link_tes2").getValue().toString();
                                subpath_t2 = "t2.csv";
                                downloadfromdropbox(url_t2, subpath_t2);

                                url_t3 = dataSnapshot.child("link_tes3").getValue().toString();
                                subpath_t3 = "t3.csv";
                                downloadfromdropbox(url_t3, subpath_t3);

                                url_t4 = dataSnapshot.child("link_tes4").getValue().toString();
                                subpath_t4 = "t4.csv";
                                downloadfromdropbox(url_t4, subpath_t4);


                                url_t5 = dataSnapshot.child("link_tes5").getValue().toString();
                                subpath_t5 = "t5.csv";
                                downloadfromdropbox(url_t5, subpath_t5);

                                url_data_update = dataSnapshot.child("link_data").getValue().toString();
                                subpath_data_update = "dataupdate.csv";
                                downloadfromdropbox(url_data_update, subpath_data_update);

//
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                }
            }
        });
    }

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    private String getCurrentDate() {
        String date;

        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = Calendar.getInstance().getTime();
        date = curFormat.format(dateobj);


        return date;
    }

    private void update_data() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        myRef.child("Users").child(firebaseUser.getUid()).child("last_update_data").setValue(getCurrentDate());
        myRef.child("Users").child(firebaseUser.getUid()).child("status_download_db").setValue("1");
    }

    ProgressDialog progress;

    public void downloadfromdropbox(String url, String subpath) {
        System.out.println("cuy");
        if (isDownloadManagerAvailable(getContext())) {
            final File[] file = {new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath)};
            file[0].delete();
            System.out.println("cuy la");
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Some descrition");
            request.setTitle("Some title");
// in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subpath);

// get download service and enqueue file
            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            downloadID=manager.enqueue(request);
            jumlah__download_id.add(downloadID);
            System.out.println("tes"+downloadID);
        }
    }

    public void insert_database(String subpath) {
        System.out.println("kesini cuy");

          insertdata(subpath);
//          file[0].delete();


    }

    public void insertdata(final String subpath) {
        // get writable database as we want to write data
        final File[] file = {new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath)};
        file[0] = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath);
        final String localFile = file[0].toString();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                try (BufferedReader br = new BufferedReader(new FileReader(file[0]))) {

                    String line = "";
                    while ((line = br.readLine()) != null) {


                        // use comma as separator
                        final String[] country = line.split(",");

                        if(country.length==12){
                            final Model_LacakMobil model_lacakMobil = new Model_LacakMobil();
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
                            bgRealm.insertOrUpdate(model_lacakMobil);
                        }



                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
//            fixing_data();

                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                file[0].delete();
                Log.v("TAGGED", "SAVED");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.v("TAGGED", "FAILED");
            }
        });





    }

    public static boolean isDownloadManagerAvailable(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return true;
        }
        return false;
    }

    private void loadtheFirebase() {
        Firebase firebase = new Firebase();
        firebase.loadfirebase(getContext());
    }
}
