package com.digitalcreative.aplikasidatamining.View.MenuPages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
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

import com.digitalcreative.aplikasidatamining.BaseActivity;
import com.digitalcreative.aplikasidatamining.Controller.DataBaseHelper;
import com.digitalcreative.aplikasidatamining.Controller.Detail_lacakMobil;
import com.digitalcreative.aplikasidatamining.Controller.KeyboardMethod;
import com.digitalcreative.aplikasidatamining.MainActivity;
import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.RealmHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    RealmHelper realmHelper;

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
                .name("test.db")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(configuration);
        long count = realm.where(Model_LacakMobil.class).count();
        Toast.makeText(context, "Jumlah Data = " + String.valueOf(count), Toast.LENGTH_LONG).show();

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

        ProgressDialog progressDialog;
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
                    .name("test.db")
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


                list= realm.where(Model_LacakMobil.class).limit(30).beginsWith("no_plat",search.getText().toString(), Case.INSENSITIVE).findAll().sort("no_plat");
                List<Model_LacakMobil> safeWords = realm.copyFromRealm(list);
                realm.close();
                return safeWords;

        }

        @Override
        protected void onPostExecute(List<Model_LacakMobil> words) {
//            progressDialog.dismiss();

            // Please note here MyAdaptor constructor will now take the
            // list of words directly and not RealmResults so you slightly
            // modify the MyAdapter constructor.
            System.out.println(words.size());
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
//

//                list = new ArrayList<>();
//
//                list = realmHelper.getAllMahasiswa(s.toString());

                mTask = (YourAsyncTask) new YourAsyncTask().execute(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputConnection ic = search.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

    }

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
        progressBar = findViewById(R.id.progressBar);
        progressbar = findViewById(R.id.progressbar);
        emptyText = findViewById(R.id.tv_no_data);

        //Keyboard
        keyboard = findViewById(R.id.mykeyboard);
    }

    private void performSearch(){
        detaillacakMobil = new Detail_lacakMobil(list, getApplicationContext(), recyclerView);
        recyclerView.setAdapter(detaillacakMobil);

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





