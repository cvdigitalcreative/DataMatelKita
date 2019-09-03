package com.digitalcreative.vimatelindonesia.Model;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class Model_LacakMobil extends RealmObject {
    String nama;
    String nama_mobil;
    String cabang;
    String tahun;
    String finance;
    String namaunit;
    String ovd;
    String noka;
    String nosin;
    String warna;
    String saldo;
   @PrimaryKey @Index
    String no_plat;





    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }



    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }



    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getNamaunit() {
        return namaunit;
    }

    public void setNamaunit(String namaunit) {
        this.namaunit = namaunit;
    }

    public String getOvd() {
        return ovd;
    }

    public void setOvd(String ovd) {
        this.ovd = ovd;
    }

    public String getNoka() {
        return noka;
    }

    public void setNoka(String noka) {
        this.noka = noka;
    }

    public String getNosin() {
        return nosin;
    }

    public void setNosin(String nosin) {
        this.nosin = nosin;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_plat() {
        return no_plat;
    }

    public void setNo_plat(String no_plat) {
        this.no_plat = no_plat;
    }

    public String getNama_mobil() {
        return nama_mobil;
    }

    public void setNama_mobil(String nama_mobil) {
        this.nama_mobil = nama_mobil;
    }

}
