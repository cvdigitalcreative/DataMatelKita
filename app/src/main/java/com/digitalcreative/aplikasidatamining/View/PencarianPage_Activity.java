package com.digitalcreative.aplikasidatamining.View;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.digitalcreative.aplikasidatamining.BaseActivity;
import com.digitalcreative.aplikasidatamining.Controller.DataBaseHelper;
import com.digitalcreative.aplikasidatamining.Controller.Detail_lacakMobil;
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
    LinearLayout linearLayout, progress;
    LinearLayoutManager linearmanager;
    ProgressBar progressBar,progressbar;
    int lastIndex, index;
    Button backbutton;
    private SearchView sv_search;
    long diffInDays;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lacak_mobil);
        context=this;

        //Init
        descTheComponent();
        setStyleText();
        //Actions
        //getData();
        doThesearch();
        //goBack();
        //goBack();
    }

    private void setStyleText() {
        Typeface title = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Bold.ttf");
        toolbarText.setTypeface(title);
    }

    private void goBack() {
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent =  new Intent(getApplicationContext(), BaseActivity.class);
              startActivity(intent);
            }
        });
    }
    int indexing_tes=1;

    private void doThesearch() {
        sv_search.setActivated(false);
        sv_search.onActionViewExpanded();
        sv_search.onActionViewExpanded();
        sv_search.clearFocus();
        sv_search.setIconified(false);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                emptyText.setVisibility(View.INVISIBLE);
                list.clear();
                recyclerView.removeAllViewsInLayout();
                progressbar.setVisibility(View.VISIBLE);
                data_=new ArrayList<>();
                DataBaseHelper dbhelper = null;
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
                        getSearchMobil = s;

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
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String new_text) {
                emptyText.setVisibility(View.INVISIBLE);
                list.clear();
                recyclerView.removeAllViewsInLayout();
                progressbar.setVisibility(View.VISIBLE);
                    lastIndex=0;
                    getSearchMobil = new_text;
                    DataBaseHelper dbhelper;
                    try {
                        dbhelper = new DataBaseHelper(context);
                        data_=dbhelper.getAllData(getSearchMobil,getSearchMobil);
                        dbhelper.close();
                        performSearch();
                        progressbar.setVisibility(View.INVISIBLE);
                        if(list.size()<1){
                            emptyText.setVisibility(View.VISIBLE);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                return false;
            }
        });

    }



    private void descTheComponent() {
        //Toolbar Text
        toolbarText = findViewById(R.id.text_title);
        //Title
        //titleText = findViewById(R.id.text_pencarianmobil);

        //Search
        sv_search = findViewById(R.id.search_mobil_act);
        //RecyclerView
        recyclerView = findViewById(R.id.recycler_view_act);
        detaillacakMobil = new Detail_lacakMobil(list, getApplicationContext(), recyclerView);
        linearmanager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearmanager);
        //LinearLayout
        linearLayout =  findViewById(R.id.lineargone);
        //Button
        //backbutton = findViewById(R.id.lacak_backbutton);
        //progress bar
        progressBar = findViewById(R.id.progressBar);
        progressbar = findViewById(R.id.progressbar);
        emptyText = findViewById(R.id.tv_no_data);
    }

    private void performSearch() {
        sv_search.requestFocus();
        execute1stdataSearch(data_, lastIndex);
        recyclerView.setAdapter(detaillacakMobil);
    }

//    RecyclerView.OnScrollListener paginationAdapter = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            int visibleItemCount = linearmanager.getChildCount();
//            int totalItemCount = linearmanager.getItemCount();
//            final int firstVisibleItemPosition = linearmanager.findFirstVisibleItemPosition();
//            int lastdata = data_.size()-1;
//            if (index <= lastdata){
//                if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
//                    progressBar.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            execute1stdataSearch(data_, lastIndex);
//                            detaillacakMobil.notifyItemInserted(firstVisibleItemPosition);
//                            progressBar.setVisibility(View.INVISIBLE);
//                        }
//                    }, 1000);
////
//                }
//            }
//        }
//    };



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
                    list.add(model);


        }
    }

    @Override
    public void onBackPressed() {
        // do something on back.
        Intent intent =  new Intent(getApplicationContext(), BaseActivity.class);
        startActivity(intent);
        return;
    }

}





