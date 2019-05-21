package com.digitalcreative.aplikasidatamining.Controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BackendFirebase {
    Context context;
    View view;
    TextView tv1, tv2;
    LinearLayout linearLayout;
    ProgressDialog progress, progress2;

    public BackendFirebase(Context context, View view, LinearLayout finished, TextView tv1, TextView tv2) {
        this.context = context;
        this.view = view;
        this.linearLayout = finished;
        this.tv1 = tv1;
        this.tv2 = tv2;
    }



    public void downloadFile(final Context context) throws IOException {

        progress = new ProgressDialog(context);
        progress.setMessage("Updating Data . . .");
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();

        final DataBaseHelper dbhelper = new DataBaseHelper(context);

        update_data();
//        dbhelper.openDatabase();
//        DBHelper mDBHlpr = new DBHelper(context);
//        CommonSQLiteUtilities.logDatabaseInfo(mDBHlpr.getWritableDatabase());
//        final SQLiteDatabase db = mDBHlpr.getWritableDatabase();
//


//        databaseHelper.deletedata();

        //Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/aplikasidatamining.appspot.com/o/tes.csv?alt=media&token=f642cad9-2b79-4272-974a-061f05d2e504");
        System.out.println("tes");
        //Internal Storage Defenition Path Error Make us Badly Think and Thanks to saffan get solve this problem immediately
        final File localFile = new File(context.getExternalFilesDir(null), "tes.csv");
        //This one on top
        System.out.println("cuy");


        storageRef.getFile(localFile).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {

                System.out.println(taskSnapshot.getBytesTransferred());
            }
        }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                progress.dismiss();

               linearLayout.setVisibility(View.VISIBLE);
                tv1.setText("Sedang Menyiapkan Data");
                tv2.setText("Mohon Tunggu Sebentar. .");

//                ArrayList<ArrayList> data=tools.load_excel_format_csv(localFile.toString(),",");
                final Handler handler = new Handler();

                final Runnable r = new Runnable() {
                    public void run() {
                        dbhelper.insertdata(localFile.toString());

                        tv1.setText("Update Mobil Selesai");
                        tv2.setText("Tekan Untuk Kembali..");
                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                linearLayout.setVisibility(View.GONE);
                            }
                        });
                    }
                };
                handler.postDelayed(r, 1000);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                progress.dismiss();
                // Handle any errors
                Log.e("Failed", "Data Gagal di Download" + localFile.toString() + " " + exception.getLocalizedMessage());
            }
        });

    }


    private void update_data() {
        FirebaseAuth firebaseAuth;
        FirebaseUser firebaseUser;
        FirebaseDatabase firebaseDatabase;
        DatabaseReference myRef;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        myRef = firebaseDatabase.getReference();
        myRef.child("Users").child(firebaseUser.getUid()).child("last_update_data").setValue(getCurrentDate());
    }

    private String getCurrentDate() {
        String date;

        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = Calendar.getInstance().getTime();
        date = curFormat.format(dateobj);


        return date;
    }

    private void showUpdateDone() {
        //show


        //action
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
            }
        });
    }
}
