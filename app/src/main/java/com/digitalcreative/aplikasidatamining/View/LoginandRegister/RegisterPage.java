package com.digitalcreative.aplikasidatamining.View.LoginandRegister;


import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.View.HomePage.LoadingPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterPage extends Fragment {
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    EditText regUsername, regEmail, regNama, regAlamat, regPassword;
    Button btn_register;
    TextView btn_login;
    String getUsername, getEmail, getNama, getAlamat, getPassword, getCurrentTanggal;
    LinearLayout pop_up;

    public RegisterPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_page, container, false);

        //init the variable
        sayHelloboy(view);

        //SetValue
        getCurrentTanggal = getCurrentDate();

        //Actions
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop_up.setVisibility(View.VISIBLE);
                Log.d(TAG, "onClick: attempting to signup.");

                getUsername = regUsername.getText().toString().trim();
                getEmail = regEmail.getText().toString().trim();
                getNama = regNama.getText().toString();
                getAlamat = regAlamat.getText().toString();
                getPassword = regPassword.getText().toString().trim();

                //check if Edittext not-null
                if (!isEmpty(getUsername) && !isEmpty(getEmail) && !isEmpty(getNama) && !isEmpty(getAlamat) && !isEmpty(getPassword)) {
                    //Insert Information

                    FirebaseAuth firebaseAuth;
                    FirebaseUser firebaseUser;
                    FirebaseDatabase firebaseDatabase;
                    DatabaseReference myRef;
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    firebaseUser =  firebaseAuth.getCurrentUser();
                    myRef = firebaseDatabase.getReference();

                    myRef.child("Nomor_Imei").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean check=false;
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                            {
                               String unique_id=getDeviceUniqueID(getActivity());
                               if(postSnapshot.child("imei").getValue().toString().equals(unique_id)){
                                   check=true;
                               }

                            }
                            if(check==false){
                                startRegister(getUsername, getEmail, getNama, getAlamat, getPassword);

                            }else{
                                pop_up.setVisibility(View.INVISIBLE);
                                Toast.makeText(getActivity(), "HP Ini Sudah Terdaftar", Toast.LENGTH_LONG).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                } else {
                    pop_up.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Periksa Jika Masih Ada Yang Kosong", Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectScreen();
            }
        });

        return view;
    }

    private void startRegister(final String getUsername, final String getEmail, final String getNama, final String getAlamat, final String getPassword) {
        firebaseAuth.createUserWithEmailAndPassword(getEmail, getPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    myRef = firebaseDatabase.getReference().child("Users").child(user.getUid());
                    myRef.child("username").setValue(getUsername);
                    myRef.child("password").setValue(getPassword);
                    myRef.child("nama_lengkap").setValue(getNama);
                    myRef.child("no_telepon").setValue(getAlamat);
                    myRef.child("email").setValue(getEmail);
                    myRef.child("status_app").setValue("trial");
                    myRef.child("status_download_db").setValue("0");
                    myRef.child("status_pembayaran").setValue("trial");
                    myRef.child("tanggal_aktif").setValue(getCurrentTanggal);
                    myRef.child("tanggal_berakhir").setValue(get_date_after_one_day());
                    myRef.child("imei").setValue(getDeviceUniqueID(getActivity()));
                    myRef.child("last_update_data").setValue(getCurrentTanggal);
                    firebaseDatabase.getReference().child("Nomor_Imei").push().child("imei").setValue(getDeviceUniqueID(getActivity()));

                } else {
                    // If sign in fails, display a message to the user.
                    pop_up.setVisibility(View.INVISIBLE);
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getActivity(), "Email Telah Terdaftar", Toast.LENGTH_LONG).show();
                }
            }
        });
        pop_up.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(), "Pendaftaran Berhasil Masa Trial Anda Satu Hari", Toast.LENGTH_LONG).show();
        redirectScreen();
    }

    public String getDeviceUniqueID(Activity activity){
        String device_unique_id = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    private void sayHelloboy(View view) {
        //Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        //Variable
        regUsername = view.findViewById(R.id.input_username);
        regEmail = view.findViewById(R.id.input_email);
        regNama = view.findViewById(R.id.input_nama);
        regAlamat = view.findViewById(R.id.input_notel);
        regPassword = view.findViewById(R.id.input_password);

        //Button
        btn_register = view.findViewById(R.id.btn_register2);
        btn_login = view.findViewById(R.id.btn_login2);

        //LinearLayout
        pop_up = view.findViewById(R.id.pop_up_register);
    }

    private boolean isEmpty(String string){
        return string.equals("");
    }

    private String getCurrentDate(){
        String date;

        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateobj = Calendar.getInstance().getTime();
        date = curFormat.format(dateobj);



        return date;
    }
    private String get_date_after_one_day(){
        String date;

        SimpleDateFormat curFormat = new SimpleDateFormat("dd/MM/yyyy");
//        Date dateobj = Calendar.getInstance().getTime();

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        date = curFormat.format(dt);



        return date;
    }

    private void redirectScreen() {
        Log.d(TAG, "Redirecting to login screen.");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_base, new LoginPage())
                .addToBackStack(null).commit();
    }
}
