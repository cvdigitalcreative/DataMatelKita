package com.digitalcreative.aplikasidatamining.View.MenuPages;


import android.Manifest;
import android.app.ActivityManager;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.Controller.Firebase;
import com.digitalcreative.aplikasidatamining.Controller.ForegroundService_t0;
import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
import com.digitalcreative.aplikasidatamining.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BantuanPage extends Fragment {
    TextView tv1, tv2,tv0,tv3;
    LinearLayout finished;
    CardView addCar, update;
    ImageView wa_btn, call_tbn, sms_btn;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static String DB_PATH;

    private static String DB_NAME = "tes.db";
    Realm realm;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
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
    private int total_file=0;
    private ArrayList<String> path_file;
    private ArrayList<String>  url_file;
    private int jumlah_file;

    ProgressDialog progress;
    SharedPreferences mSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_bantuan_page, container, false);

        //loadFirebase
        loadtheFirebase();

        //Init the Variable
        sayHelloboys(view);
//

        //Actions
        doitBoys();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        jumlah_id=new ArrayList<>();
        jumlah__download_id=new ArrayList<>();
        jumlah_id=new ArrayList<>();
        path_file=new ArrayList<>();
        url_file=new ArrayList<>();
        jumlah__download_id=new ArrayList<>();
        jumlah_file=7;
         mSettings = getActivity().getSharedPreferences("Settings", MODE_PRIVATE);
        if(isMyServiceRunning(ForegroundService_t0.class)
               || checkStatus(getContext() , DownloadManager.STATUS_RUNNING)
        ){
            Toast.makeText(getContext(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
        }
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
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finished.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
    public void startService_t0() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        String uid=firebaseUser.getUid();
        Intent serviceIntent = new Intent(getContext(), ForegroundService_t0.class);
        serviceIntent.putExtra("inputExtra", uid);

        ContextCompat.startForegroundService(getContext(), serviceIntent);
    }

    private void sayHelloboys(View view) {
        //TextView
        tv1 = view.findViewById(R.id.text_bantuan_1);
        tv2 = view.findViewById(R.id.text_bantuan_2);
        tv3 = view.findViewById(R.id.text_bantuan_3);
        tv0=view.findViewById(R.id.text_bantuan_cuy);
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


    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            jumlah_id.add(id);
            System.out.println("download selesai "+id);
            System.out.println("id download "+jumlah__download_id);
            System.out.println("jumlah id "+jumlah_id.size());
            System.out.println("jumlah download "+jumlah__download_id.size());
            Toast.makeText(getActivity(), "Data berhasil didownload tunggu beberapa saat lagi untuk lengkapi data", Toast.LENGTH_LONG).show();





        }
    };
    private void doitBoys() {
        sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + "+6281292983143"));
                startActivity(intent);
            }
        });

        wa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=" + "+6281292983143";
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
//                callIntent.setData(Uri.parse("tel:" + "+6281292983143"));
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
                    callIntent.setData(Uri.parse("tel:" + "+6281292983143"));
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

                Realm.init(getContext());
                RealmConfiguration configuration= new RealmConfiguration.Builder()
                        .name("datamatel.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm= Realm.getInstance(configuration);
                long count = realm.where(Model_LacakMobil.class).count();
                realm.close();
                System.out.println("t0 ="+count);

                Realm.init(getContext());
                RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                        .name("datamatel2.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm2 = Realm.getInstance(configuration2);
                count = realm2.where(Model_LacakMobil.class).count()+count;
                System.out.println("t1 ="+ realm2.where(Model_LacakMobil.class).count());
                realm2.close();


                Realm.init(getContext());
                RealmConfiguration configuration3 = new RealmConfiguration.Builder()
                        .name("datamatel3.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm3 = Realm.getInstance(configuration3);
                count = realm3.where(Model_LacakMobil.class).count()+count;
                System.out.println("t2 ="+realm3.where(Model_LacakMobil.class).count());
                realm3.close();


                Realm.init(getContext());
                RealmConfiguration configuration4 = new RealmConfiguration.Builder()
                        .name("datamatel4.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm4 = Realm.getInstance(configuration4);
                count = realm4.where(Model_LacakMobil.class).count()+count;
                System.out.println("t3 ="+ realm4.where(Model_LacakMobil.class).count());
                realm4.close();


                Realm.init(getContext());
                RealmConfiguration configuration5 = new RealmConfiguration.Builder()
                        .name("datamatel5.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm5 = Realm.getInstance(configuration5);
                System.out.println("t4 ="+realm5.where(Model_LacakMobil.class).count());
                count = realm5.where(Model_LacakMobil.class).count()+count;
                realm5.close();


                Realm.init(getContext());
                RealmConfiguration configuration6 = new RealmConfiguration.Builder()
                        .name("datamatel6.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm6 = Realm.getInstance(configuration6);
                count = realm6.where(Model_LacakMobil.class).count()+count;
                System.out.println("t5 ="+realm6.where(Model_LacakMobil.class).count());
                realm6.close();


                Realm.init(getContext());
                RealmConfiguration configuration7 = new RealmConfiguration.Builder()
                        .name("datamatel7.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                final Realm realm7 = Realm.getInstance(configuration7);
                count = realm7.where(Model_LacakMobil.class).count()+count;
                System.out.println("t6 ="+realm7.where(Model_LacakMobil.class).count());
                realm7.close();

                //realm


                firebaseAuth = FirebaseAuth.getInstance();
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                myRef = firebaseDatabase.getReference();
                final long finalCount = count;
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
                                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                int status=pref.getInt("key_name2", 0);
                                Date date = null;
                                try {
                                    date = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data);
                                    Date date_2 = new SimpleDateFormat("dd/MM/yyyy").parse(last_update_data_sistem);
                                    long milliseconds = date_2.getTime() - date.getTime();
                                    long days = milliseconds / (1000 * 60 * 60 * 24);



                                    if(isMyServiceRunning(ForegroundService_t0.class)
                                            || checkStatus(getContext() , DownloadManager.STATUS_RUNNING)
                                    ){
                                        String infomarsi="Tanggal Update Data Anda " +last_update_data_sistem;
                                        String infomarsis="Tanggal Data Terbaru Sistem " +last_update_data_sistem;
                                        String informasi="Kode "+ finalCount;
                                        long progress=(finalCount *100)/400000;
                                        finished.setVisibility(View.VISIBLE);
                                        tv0.setText(infomarsis);
                                        tv1.setText(infomarsi);
                                        tv2.setText(informasi);
                                        if(progress>=100){
                                            tv3.setText("Progress Data anda 100 %");
                                        }else{
                                            tv3.setText("Progress Data anda "+progress+ "%");
                                        }
                                        Toast.makeText(getActivity(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        if (days<=0 && status_download_db.trim().equals("1") && finalCount >400000) {
                                            String infomarsi="Tanggal Update Data Anda " +last_update_data_sistem;
                                            String infomarsis="Tanggal Data Terbaru Sistem " +last_update_data_sistem;
                                            String informasi="Kode "+ finalCount;
                                            long progress=(finalCount *100)/400000;
                                            finished.setVisibility(View.VISIBLE);
                                            tv0.setText(infomarsis);
                                            tv1.setText(infomarsi);
                                            tv2.setText(informasi);
                                            if(progress>=100){
                                                tv3.setText("Progress Data anda 100 %");
                                            }else{
                                                tv3.setText("Progress Data anda "+progress+ "%");
                                            }
                                            Toast.makeText(getActivity(), "Data Terupdate", Toast.LENGTH_LONG).show();
                                        }else{
                                            String infomarsi="Tanggal Update Data Anda " +last_update_data_sistem;
                                            String infomarsis="Tanggal Data Terbaru Sistem " +last_update_data_sistem;
                                            String informasi="Kode "+ finalCount;
                                            long progress=(finalCount *100)/400000;
                                            finished.setVisibility(View.VISIBLE);
                                            tv0.setText(infomarsis);
                                            tv1.setText(infomarsi);
                                            tv2.setText(informasi);
                                            if(progress>=100){
                                                tv3.setText("Progress Data anda 100 %");
                                            }else{
                                                tv3.setText("Progress Data anda "+progress+ "%");
                                            }
                                            Toast.makeText(getActivity(), "Sedang mengupdate data silahkan check beberapa saat lagi", Toast.LENGTH_LONG).show();
                                        }
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
        });
    }
    public static boolean checkStatus(Context context , int status) {
        DownloadManager downloadManager = (DownloadManager)
                context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();

        query.setFilterByStatus(status);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            c.close();

            return true;
        }

        return false;
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

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



    public void downloadfromdropbox(String url, String subpath) {

        if (isDownloadManagerAvailable(getContext())) {

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setDescription("Some descrition");
            request.setTitle("Some title");
// in order for this if to run, you must use the android 3.2 to compile your app
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            }
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subpath);
            System.out.println("sub path download "+subpath);
// get download service and enqueue file
            DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
            downloadID=manager.enqueue(request);
            jumlah__download_id.add(Long.valueOf(downloadID));

        }
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
