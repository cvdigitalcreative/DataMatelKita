package com.digitalcreative.vimatelindonesia.View.MenuPages;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.vimatelindonesia.BaseActivity;
import com.digitalcreative.vimatelindonesia.Controller.DataBaseHelper;
import com.digitalcreative.vimatelindonesia.Controller.Detail_lacakMobil;
import com.digitalcreative.vimatelindonesia.Controller.ForegroundService;
import com.digitalcreative.vimatelindonesia.Controller.KeyboardMethod;
import com.digitalcreative.vimatelindonesia.MainActivity;
import com.digitalcreative.vimatelindonesia.Model.Model_LacakMobil;
import com.digitalcreative.vimatelindonesia.R;
import com.digitalcreative.vimatelindonesia.RealmHelper;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class PencarianPage_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Model_LacakMobil> list = new ArrayList<>();
    ArrayList<ArrayList> data_;
    Detail_lacakMobil detaillacakMobil;
    TextView emptyText, toolbarText, titleText;
    String getSearchMobil;
    LinearLayout linearLayout, progress, back;
    LinearLayoutManager linearmanager;
    ProgressBar progressBar,progressbar;
    int lastIndex, index;
    private YourAsyncTask mTask;
    private EditText search;
    long diffInDays;
    Context context;
    KeyboardMethod keyboard;

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
    private ArrayList<String> path_file;
    private ArrayList<String>  url_file;
    private ArrayList<Long> jumlah__download_id;
    private int jumlah_file;
    private long downloadID;

    ProgressDialog progressDialog;
    private static final int REQUEST_WRITE_STORAGE = 112;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacak_mobil);
        context=this;

        initObejct();

        //do Fucntion
        searchFunc();
        backFunc();



        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(configuration);
        long count = realm.where(Model_LacakMobil.class).count();

        if(count<=2900000 ){
            Toast.makeText(context, "Data anda belum lengkap silahkan tgg beberapa saat sistem sedang memasukan data ", Toast.LENGTH_LONG).show();
//
//            progressDialog = ProgressDialog.show(PencarianPage_Activity.this,
//                    "Loading",
//                    "Sedang mengupdate data harap di tgg!");
//            firebaseAuth = FirebaseAuth.getInstance();
//            firebaseDatabase = FirebaseDatabase.getInstance();
//            firebaseUser = firebaseAuth.getCurrentUser();
//            myRef = firebaseDatabase.getReference();
//            jumlah_id=new ArrayList<>();
//            path_file=new ArrayList<>();
//            url_file=new ArrayList<>();
//            jumlah__download_id=new ArrayList<>();
//            jumlah_file=7;
//            PencarianPage_Activity.this.registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
//            update_data();

        }else{
            Toast.makeText(context, "Jumlah Data = " + String.valueOf(count), Toast.LENGTH_LONG).show();
        }
    }

    public void update_data(){
        Toast.makeText(context, "Sedang Update", Toast.LENGTH_LONG).show();
        boolean hasPermission = (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(PencarianPage_Activity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
            Toast.makeText(context, "Silahkan update kembali", Toast.LENGTH_LONG).show();
//                        //loading Data
//                        BackendFirebase backendFirebase = new BackendFirebase(getContext(), v, finished, tv1, tv2);
//                        backendFirebase.downloadFile(getContext());
            progressDialog.dismiss();
        } else {
            Realm.init(context);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name("vimatel.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    realm.deleteAll();
//                }
//            });
            myRef.child("link").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {


                    url_t0 = dataSnapshot.child("link_tes").getValue().toString();
                    subpath_t0 = "t0.csv";
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t0);
                    if(file.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t0);
//                        file.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t0);
                        url_file.add(url_t0);


                    }


                    url_t1 = dataSnapshot.child("link_tes1").getValue().toString();
                    subpath_t1 = "t1.csv";
                    File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t1);
                    if(file2.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t1);
//                        file2.delete();
                    }else{
                        System.out.println("insert url");

                        path_file.add(subpath_t1);
                        url_file.add(url_t1);

                    }


                    url_t2 = dataSnapshot.child("link_tes2").getValue().toString();
                    subpath_t2 = "t2.csv";
                    File file3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t2);
                    if(file3.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t2);

//                        file3.delete();
                    }else{
                        System.out.println("insert url");

                        path_file.add(subpath_t2);
                        url_file.add(url_t2);


                    }


                    url_t3 = dataSnapshot.child("link_tes3").getValue().toString();
                    subpath_t3 = "t3.csv";
                    File file4 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t3);
                    if(file4.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t3);
//                        file4.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t3);
                        url_file.add(url_t3);

                    }




                    url_t4 = dataSnapshot.child("link_tes4").getValue().toString();
                    subpath_t4 = "t4.csv";
                    File file5 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t4);
                    if(file5.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t4);
//                        file5.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t4);
                        url_file.add(url_t4);

                    }



                    url_t5 = dataSnapshot.child("link_tes5").getValue().toString();
                    subpath_t5 = "t5.csv";
                    File file6 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_t5);
                    if(file6.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_t5);
//                        file6.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_t5);
                        url_file.add(url_t5);

                    }


                    url_data_update = dataSnapshot.child("link_data").getValue().toString();
                    subpath_data_update = "dataupdate.csv";
                    File file7 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath_data_update);
                    if(file7.exists()){
                        System.out.println("insert db");
                        insert_database(subpath_data_update);
//                        file7.delete();
                    }else{
                        System.out.println("insert url");
                        path_file.add(subpath_data_update);
                        url_file.add(url_data_update);

                    }

                    for(int i=0; i<url_file.size(); i++){
                        downloadfromdropbox(url_file.get(i), path_file.get(i));
                    }




//
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
    public void insert_database(String subpath) {


        insertdata(subpath);
//          file[0].delete();


    }

    public void insertdata(final String subpath) {
        System.out.println("jumlah file "+jumlah_file);
        final Model_LacakMobil model_lacakMobil = new Model_LacakMobil();
        // get writable database as we want to write data
        final File[] file = {new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath)};
        file[0] = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), subpath);
        final String localFile = file[0].toString();
        Realm.init(context);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(configuration);
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                try (BufferedReader br = new BufferedReader(new FileReader(file[0]))) {
                    file[0].delete();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        // use comma as separator
                        final String[] country = line.split(",");
                        if(country.length==12){

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
                    System.out.println("nama file "+file[0].getAbsolutePath());
                    System.out.println("jumlah file diolah "+jumlah_file);

                    if( jumlah_file==1){
                        System.out.println("masuk sini");
                        progressDialog.dismiss();
                        update_data_s();
//                    tv2.setText("Jumlah Data = " + String.valueOf(count));

                    }else{
                        jumlah_file=jumlah_file-1;
                        progressDialog.dismiss();

                        PencarianPage_Activity.this
                                .runOnUiThread(new Runnable() {
                            public void run() {
                                Realm.init(context);
                                RealmConfiguration configuration = new RealmConfiguration.Builder()
                                        .name("vimatel.db")
                                        .schemaVersion(1)
                                        .deleteRealmIfMigrationNeeded()
                                        .build();
                                realm = Realm.getInstance(configuration);
                                long count = realm.where(Model_LacakMobil.class).count();

                                Toast.makeText(context, "Berhasil download data ke "+(8-jumlah_file) +" jumlah data "+count, Toast.LENGTH_LONG).show();
                            }
                        });

                        update_data_s();
                        System.out.println("file exist "+file[0].exists());
                    }






//            fixing_data();

                }
            }
        });





    }
    private void update_data_s() {
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
    private String getCurrentDate() {
        String date;

        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = Calendar.getInstance().getTime();
        date = curFormat.format(dateobj);


        return date;
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
            int index=0;
            for(int i=0; i<jumlah__download_id.size(); i++){
                if(id==jumlah__download_id.get(i)){
                    index=i;
                    break;
                }
            }
            if(!path_file.isEmpty()){
                insert_database(path_file.get(index));
            }

            //Checking if the received broadcast is for our enqueued download by matching download id

//            if (jumlah_id.size()==jumlah__download_id.size()) {
//                System.out.println("mulai memasukan data");
//                for(int i=0; i<path_file.size(); i++){
//                    insert_database(path_file.get(i));
//                }
//                Realm.init(context);
//                RealmConfiguration configuration = new RealmConfiguration.Builder()
//                        .name("vimatel.db")
//                        .schemaVersion(1)
//                        .deleteRealmIfMigrationNeeded()
//                        .build();
//                realm = Realm.getInstance(configuration);
//                long count = realm.where(Model_LacakMobil.class).count();
////                    tv2.setText("Jumlah Data = " + String.valueOf(count));
//
//                Toast.makeText(context, "Berhasil download data jumlah data "+count, Toast.LENGTH_LONG).show();
//
//            }

        }
    };

    public void downloadfromdropbox(String url, String subpath) {

        if (isDownloadManagerAvailable(context)) {

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
            DownloadManager manager = (DownloadManager) PencarianPage_Activity.this.getSystemService(Context.DOWNLOAD_SERVICE);
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

    private void backFunc() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private class YourAsyncTask extends AsyncTask<String, String, List<Model_LacakMobil>> {

        //        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            // start loading animation maybe?
//            progressDialog = ProgressDialog.show(PencarianPage_Activity.this,
//                    "ProgressDialog",
//                    "Loading!");
        }

        @Override
        protected List<Model_LacakMobil> doInBackground(String... params) {
            Realm.init(context);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name("vimatel.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
            // This is where the magic happens. realm.copyFromRealm() takes
            // a RealmResult and essentially returns a deep copy of the
            // list that it contains. The elements of this list is however
            // completely detached from realm and is not monitored by realm
            // for changes. Thus this list of values is free to move around
            // inside any thread.
            list= realm.where(Model_LacakMobil.class).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();
            List<Model_LacakMobil> safeWords;
            System.out.println("search done");
            if(list.size()>6){
                safeWords = realm.copyFromRealm(list.subList(0,5));
            }else{
                safeWords = realm.copyFromRealm(list);
            }

            realm.close();
            return safeWords;

        }

        @Override
        protected void onPostExecute(List<Model_LacakMobil> words) {
//            progressDialog.dismiss();

            // Please note here MyAdaptor constructor will now take the
            // list of words directly and not RealmResults so you slightly
            // modify the MyAdapter constructor.

            list=new ArrayList<>();
//            progressbar.setVisibility(View.INVISIBLE);
            if(words.size()==0){
                emptyText.setVisibility(View.VISIBLE);
            }else{

                emptyText.setVisibility(View.INVISIBLE);

            }

            detaillacakMobil = new Detail_lacakMobil(words, getApplicationContext(), recyclerView);
            recyclerView.setAdapter(detaillacakMobil);

        }
    }

    private void searchFunc() {
        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = search.getInputType();
                search.setInputType(InputType.TYPE_NULL);
                search.onTouchEvent(event);
                search.setInputType(inType);

                keyboard.setVisibility(View.VISIBLE);
                return true;
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                Realm.init(context);
                RealmConfiguration configuration = new RealmConfiguration.Builder()
                        .name("vimatel.db")
                        .schemaVersion(1)
                        .deleteRealmIfMigrationNeeded()
                        .build();
                realm = Realm.getInstance(configuration);
                if(mTask!=null){
                    mTask.cancel(true);
                }
                list = new ArrayList<>();
                detaillacakMobil = new Detail_lacakMobil(list, getApplicationContext(), recyclerView);
                recyclerView.setAdapter(detaillacakMobil);
//
//                list = realmHelper.getAllMahasiswa(s.toString());

                emptyText.setVisibility(View.INVISIBLE);
//                progressbar.setVisibility(View.VISIBLE);

//
                mHandler.removeCallbacks(mFilterTask);
                mHandler.postDelayed(mFilterTask, 300);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputConnection ic = search.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

    }
    private Handler mHandler = new Handler();

    Runnable mFilterTask = new Runnable() {

        @Override
        public void run() {

            mTask = (YourAsyncTask) new YourAsyncTask().execute(search.getText().toString());

        }
    };

    private void initObejct() {
        //Search
        search = findViewById(R.id.search_mobil_act);
        //RecyclerView
        recyclerView = findViewById(R.id.recycler_view_act);
        detaillacakMobil = new Detail_lacakMobil(list, getApplicationContext(), recyclerView);
        linearmanager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearmanager);
        //LinearLayout
        linearLayout =  findViewById(R.id.lineargone);
        back = findViewById(R.id.btn_pencarian_back);
        //Button
        //backbutton = view.findViewById(R.id.lacak_backbutton);
        //progress bar
        progressbar = findViewById(R.id.progressbarwait);
        emptyText = findViewById(R.id.tv_no_data);

        //Keyboard
        keyboard = findViewById(R.id.mykeyboard);
    }


//    private void execute1stdataSearch(ArrayList<ArrayList> data_, int current) {
//        int count = 0;
//
//        list.clear();
//        recyclerView.removeAllViewsInLayout();
//        for (index = 0; index < data_.size(); index++) {
//            final Model_LacakMobil model = new Model_LacakMobil();
//            model.setNama(data_.get(index).get(0).toString());
//            model.setNo_plat(data_.get(index).get(1).toString());
//            model.setNama_mobil(data_.get(index).get(2).toString());
//            model.setFinance(data_.get(index).get(3).toString());
//            model.setOvd(data_.get(index).get(4).toString());
//            model.setSaldo(data_.get(index).get(5).toString());
//            model.setCabang(data_.get(index).get(6).toString());
//            model.setNoka(data_.get(index).get(7).toString());
//            model.setNosin(data_.get(index).get(8).toString());
//            model.setTahun(data_.get(index).get(9).toString());
//            model.setWarna(data_.get(index).get(10).toString());
//            list.add(model);
//        }
//    }

    @Override
    public void onBackPressed() {
        // do something on back.
        if (keyboard.getVisibility()==View.VISIBLE){
            keyboard.setVisibility(View.GONE);
        } else{
            super.onBackPressed();
            Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Keyboard
        keyboard = findViewById(R.id.mykeyboard);
    }
}





