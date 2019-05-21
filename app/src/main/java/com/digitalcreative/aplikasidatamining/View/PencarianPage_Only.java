package com.digitalcreative.aplikasidatamining.View;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.digitalcreative.aplikasidatamining.Controller.DataBaseHelper;
import com.digitalcreative.aplikasidatamining.Controller.Detail_lacakMobil;
import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
import com.digitalcreative.aplikasidatamining.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.flags.impl.SharedPreferencesFactory.getSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 */
public class PencarianPage_Only extends Fragment {
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
    private SearchView sv_search;
    long diffInDays;
    Context context;

    public PencarianPage_Only() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pencarian_page_only, container, false);
        
        //inisialisasi Object
        initObejct(view);

        //do Fucntion
        searchFunc();
        backFunc();

        //save Data
        sharedPrefFucn();
        try {
            dbhelper = new DataBaseHelper(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void sharedPrefFucn() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("my Pref", MODE_PRIVATE).edit();
        Gson gson = new Gson();
        //String recentSearch = gson.toJson();
        editor.apply();
    }

    private void backFunc() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sv_search.clearFocus();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new PencarianPage());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
    DataBaseHelper dbhelper;
    private void searchFunc() {
        sv_search.setActivated(false);
        sv_search.onActionViewExpanded();
        sv_search.setQueryHint("Masukan Noka atau Nosin atau Nopol");
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
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



                    data_=dbhelper.getAllData(getSearchMobil,getSearchMobil);
                    dbhelper.close();
                    performSearch();
                    progressbar.setVisibility(View.INVISIBLE);
                    if(list.size()<1){
                        emptyText.setVisibility(View.VISIBLE);
                    }

                return false;
            }
        });
    }

    private void initObejct(View view) {
        //Search
        sv_search = view.findViewById(R.id.search_mobil_act);
        //RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_act);
        detaillacakMobil = new Detail_lacakMobil(list, getActivity().getApplicationContext(), recyclerView);
        linearmanager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearmanager);
        //LinearLayout
        linearLayout =  view.findViewById(R.id.lineargone);
        back = view.findViewById(R.id.btn_pencarian_back);
        //Button
        //backbutton = view.findViewById(R.id.lacak_backbutton);
        //progress bar
        progressBar = view.findViewById(R.id.progressBar);
        progressbar = view.findViewById(R.id.progressbar);
        emptyText = view.findViewById(R.id.tv_no_data);
    }

    private void performSearch(){
        sv_search.requestFocus();
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

}
