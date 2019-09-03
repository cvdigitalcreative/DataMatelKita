package com.digitalcreative.vimatelindonesia.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
