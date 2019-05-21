package com.digitalcreative.aplikasidatamining.Controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Tools {

    public ArrayList<ArrayList> load_excel_format_csv(String filename, String cvsSplitBy) {
        String csvFile = filename;
        String line = "";
        ArrayList<ArrayList> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            ArrayList<String> angka = new ArrayList<>();
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);

                for (int i = 0; i < country.length; i++) {
                    angka.add(country[i]);
                }

                data.add(angka);
                angka = new ArrayList<>();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
