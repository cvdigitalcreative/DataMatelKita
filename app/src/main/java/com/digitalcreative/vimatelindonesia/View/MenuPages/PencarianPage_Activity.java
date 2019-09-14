package com.digitalcreative.vimatelindonesia.View.MenuPages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.vimatelindonesia.Controller.Detail_lacakMobil;
import com.digitalcreative.vimatelindonesia.Controller.KeyboardMethod;
import com.digitalcreative.vimatelindonesia.MainActivity;
import com.digitalcreative.vimatelindonesia.Model.Model_LacakMobil;
import com.digitalcreative.vimatelindonesia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PencarianPage_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Model_LacakMobil> list = new ArrayList<>();

    Detail_lacakMobil detaillacakMobil;

    LinearLayout linearLayout, back;
    LinearLayoutManager linearmanager;
    ProgressBar progressbar;

    private YourAsyncTask mTask;
    private EditText search;
    private TextView emptyText;

    Context context;
    KeyboardMethod keyboard;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacak_mobil);
        context=this;

        initObejct();

        //do Fucntion
        searchFunc();
        backFunc();



        Realm.init(getApplicationContext());
        RealmConfiguration configuration= new RealmConfiguration.Builder()
                .name("vimatel.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        final Realm realm= Realm.getInstance(configuration);
        long count = realm.where(Model_LacakMobil.class).count();
        realm.close();

        Realm.init(getApplicationContext());
        RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                .name("vimatel2.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();

        final Realm realm2 = Realm.getInstance(configuration2);
        count = realm2.where(Model_LacakMobil.class).count()+count;
        realm2.close();

        if(count<=3300000 ){
            Toast.makeText(context, "Data anda belum lengkap silahkan tgg beberapa saat sistem sedang memasukan data ", Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(context, "Jumlah Data = " + String.valueOf(count), Toast.LENGTH_LONG).show();
        }
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
            List<Model_LacakMobil> temp1,temp2,temp3,temp4,temp5,temp6,temp7,safeWords;
            Realm.init(context);
            RealmConfiguration configuration = new RealmConfiguration.Builder()
                    .name("vimatel.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            realm = Realm.getInstance(configuration);
            list= realm.where(Model_LacakMobil.class).limit(5).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();

            temp1 = realm.copyFromRealm(list);
            System.out.println(temp1);
            realm.close();

            Realm.init(context);
            RealmConfiguration configuration2 = new RealmConfiguration.Builder()
                    .name("vimatel2.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            final Realm realm2 = Realm.getInstance(configuration2);
            list= realm2.where(Model_LacakMobil.class).limit(5).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();
            temp2 = realm2.copyFromRealm(list);
            System.out.println(temp2);
            realm2.close();

            Realm.init(context);
            RealmConfiguration configuration3 = new RealmConfiguration.Builder()
                    .name("vimatel3.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            final Realm realm3 = Realm.getInstance(configuration3);
            list= realm3.where(Model_LacakMobil.class).limit(5).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();
            temp3 = realm2.copyFromRealm(list);
            System.out.println(temp2);
            realm3.close();

            Realm.init(context);
            RealmConfiguration configuration4 = new RealmConfiguration.Builder()
                    .name("vimatel4.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            final Realm realm4 = Realm.getInstance(configuration4);
            list= realm4.where(Model_LacakMobil.class).limit(5).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();
            temp4 = realm2.copyFromRealm(list);
            System.out.println(temp2);
            realm4.close();

            Realm.init(context);
            RealmConfiguration configuration5 = new RealmConfiguration.Builder()
                    .name("vimatel5.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            final Realm realm5 = Realm.getInstance(configuration5);
            list= realm5.where(Model_LacakMobil.class).limit(5).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();
            temp5 = realm2.copyFromRealm(list);
            System.out.println(temp2);
            realm5.close();

            Realm.init(context);
            RealmConfiguration configuration6 = new RealmConfiguration.Builder()
                    .name("vimatel6.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            final Realm realm6 = Realm.getInstance(configuration6);
            list= realm6.where(Model_LacakMobil.class).limit(5).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();
            temp6 = realm2.copyFromRealm(list);
            System.out.println(temp2);
            realm6.close();

            Realm.init(context);
            RealmConfiguration configuration7 = new RealmConfiguration.Builder()
                    .name("vimatel7.db")
                    .schemaVersion(1)
                    .deleteRealmIfMigrationNeeded()
                    .build();
            final Realm realm7 = Realm.getInstance(configuration7);
            list= realm7.where(Model_LacakMobil.class).limit(5).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("noka",search.getText().toString(), Case.INSENSITIVE).or().beginsWith("nosin",search.getText().toString(), Case.INSENSITIVE).findAll();
            temp7 = realm2.copyFromRealm(list);
            System.out.println(temp2);
            realm7.close();

            if(!temp1.isEmpty()){
                safeWords=temp1;
            }else if(!temp2.isEmpty()){
                safeWords=temp2;
            }else if(!temp3.isEmpty()){
                safeWords=temp3;
            }else if(!temp4.isEmpty()){
                safeWords=temp4;
            }else if(!temp5.isEmpty()){
                safeWords=temp5;
            }else if(!temp6.isEmpty()){
                safeWords=temp6;
            }else if(!temp7.isEmpty()){
                safeWords=temp7;
            }else {
                safeWords=temp1;
            }

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
            if(words.size()==0 ){
                emptyText.setVisibility(View.VISIBLE);
            }else{
                list=words;
                emptyText.setVisibility(View.INVISIBLE);

            }
            detaillacakMobil = new Detail_lacakMobil(list, getApplicationContext(), recyclerView);
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
                mHandler.postDelayed(mFilterTask, 50);
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


//

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





