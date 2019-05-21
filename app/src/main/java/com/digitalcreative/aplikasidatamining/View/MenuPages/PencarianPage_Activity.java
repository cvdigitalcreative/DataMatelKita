package com.digitalcreative.aplikasidatamining.View.MenuPages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.digitalcreative.aplikasidatamining.BaseActivity;
import com.digitalcreative.aplikasidatamining.Controller.DataBaseHelper;
import com.digitalcreative.aplikasidatamining.Controller.Detail_lacakMobil;
import com.digitalcreative.aplikasidatamining.Controller.KeyboardMethod;
import com.digitalcreative.aplikasidatamining.MainActivity;
import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
import com.digitalcreative.aplikasidatamining.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    String recentWord;
    Button backbutton;
    private EditText search;
    long diffInDays;
    Context context;
    KeyboardMethod keyboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacak_mobil);
        context=this;

        initObejct();

        //do Fucntion
        searchFunc();
        backFunc();

        try {
            dbhelper = new DataBaseHelper(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    DataBaseHelper dbhelper;
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
                emptyText.setVisibility(View.INVISIBLE);
                list.clear();
                recyclerView.removeAllViewsInLayout();
                progressbar.setVisibility(View.VISIBLE);
                data_=new ArrayList<>();

                try {
                    dbhelper = new DataBaseHelper(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                final DataBaseHelper finalDbhelper = dbhelper;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lastIndex=1;
                        //getSearchMobil = s;

                        data_= finalDbhelper.getAllData(getSearchMobil,getSearchMobil);
                        System.out.println("load data done");
                        finalDbhelper.close();
                        System.out.println("close");
                        performSearch();
                        System.out.println("searching done");
                        progressbar.setVisibility(View.INVISIBLE);
                        if(list.size()<1){
                            emptyText.setVisibility(View.VISIBLE);
                        }

                    }
                }, 1000);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emptyText.setVisibility(View.INVISIBLE);
                list.clear();
                recyclerView.removeAllViewsInLayout();
                progressbar.setVisibility(View.VISIBLE);
                lastIndex=0;
                //getSearchMobil = new_text;

                data_=dbhelper.getAllData(getSearchMobil,getSearchMobil);
                dbhelper.close();
                performSearch();
                progressbar.setVisibility(View.INVISIBLE);
                if(list.size()<1){
                    emptyText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputConnection ic = search.onCreateInputConnection(new EditorInfo());
        keyboard.setInputConnection(ic);

//        sv_search.setActivated(false);
//        sv_search.clearFocus();
//        sv_search.onActionViewExpanded();
//        sv_search.setQueryHint("Masukan Noka atau Nosin atau Nopol");
//        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(final String s) {
//                keyboard.setVisibility(View.VISIBLE);
//                emptyText.setVisibility(View.INVISIBLE);
//                list.clear();
//                recyclerView.removeAllViewsInLayout();
//                progressbar.setVisibility(View.VISIBLE);
//                data_=new ArrayList<>();
//
//                try {
//                    dbhelper = new DataBaseHelper(context);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                final DataBaseHelper finalDbhelper = dbhelper;
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        lastIndex=1;
//                        getSearchMobil = s;
//
//                        data_= finalDbhelper.getAllData(getSearchMobil,getSearchMobil);
//                        System.out.println("load data done");
//                        finalDbhelper.close();
//                        System.out.println("close");
//                        performSearch();
//                        System.out.println("searching done");
//                        progressbar.setVisibility(View.INVISIBLE);
//                        if(list.size()<1){
//                            emptyText.setVisibility(View.VISIBLE);
//                        }
//
//                    }
//                }, 1000);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(final String new_text) {
//                emptyText.setVisibility(View.INVISIBLE);
//                list.clear();
//                recyclerView.removeAllViewsInLayout();
//                progressbar.setVisibility(View.VISIBLE);
//                lastIndex=0;
//                getSearchMobil = new_text;
//
//
//
//                data_=dbhelper.getAllData(getSearchMobil,getSearchMobil);
//                dbhelper.close();
//                performSearch();
//                progressbar.setVisibility(View.INVISIBLE);
//                if(list.size()<1){
//                    emptyText.setVisibility(View.VISIBLE);
//                }
//
//                return false;
//            }
//        });
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
        execute1stdataSearch(data_, lastIndex);
        recyclerView.setAdapter(detaillacakMobil);
    }

    private void execute1stdataSearch(ArrayList<ArrayList> data_, int current) {
        int count = 0;

        list.clear();
        recyclerView.removeAllViewsInLayout();
        for (index = 0; index < data_.size(); index++) {
            final Model_LacakMobil model = new Model_LacakMobil();
            model.setNama(data_.get(index).get(0).toString());
            model.setNo_plat(data_.get(index).get(1).toString());
            model.setNama_mobil(data_.get(index).get(2).toString());
            model.setFinance(data_.get(index).get(3).toString());
            model.setOvd(data_.get(index).get(4).toString());
            model.setSaldo(data_.get(index).get(5).toString());
            model.setCabang(data_.get(index).get(6).toString());
            model.setNoka(data_.get(index).get(7).toString());
            model.setNosin(data_.get(index).get(8).toString());
            model.setTahun(data_.get(index).get(9).toString());
            model.setWarna(data_.get(index).get(10).toString());
            list.add(model);
        }
    }

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

}





