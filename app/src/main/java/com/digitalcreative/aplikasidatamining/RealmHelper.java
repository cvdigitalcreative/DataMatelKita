package com.digitalcreative.aplikasidatamining;
import android.util.Log;

import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;

import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
public class RealmHelper {

    Realm realm;

    public  RealmHelper(Realm realm){
        this.realm = realm;
    }

    // untuk menyimpan data
    public void save(final Model_LacakMobil Model_LacakMobil){

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){

                    Model_LacakMobil model = realm.copyToRealm(Model_LacakMobil);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    // untuk memanggil semua data
    public List<Model_LacakMobil> getAllMahasiswa(String a){
        RealmResults<Model_LacakMobil> results = realm.where(Model_LacakMobil.class).beginsWith("no_plat",a, Case.INSENSITIVE).findAllAsync().sort("no_plat");
        return results;
    }

    // untuk meng-update data
//    public void update(final Integer id, final Integer nim, final String nama){
//        realm.executeTransactionAsync(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                Model_LacakMobil model = realm.where(Model_LacakMobil.class)
//                        .equalTo("id", id)
//                        .findFirst();
//                model.setNim(nim);
//                model.setNama(nama);
//            }
//        }, new Realm.Transaction.OnSuccess() {
//            @Override
//            public void onSuccess() {
//                Log.e("pppp", "onSuccess: Update Successfully");
//            }
//        }, new Realm.Transaction.OnError() {
//            @Override
//            public void onError(Throwable error) {
//                error.printStackTrace();
//            }
//        });
//    }



}
