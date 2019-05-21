package com.digitalcreative.aplikasidatamining.View.MenuPages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.R;
import com.digitalcreative.aplikasidatamining.View.HomePage.Halaman_Utama;

/**
 * A simple {@link Fragment} subclass.
 */
public class Custumer_Service extends Fragment {
    Button back;
    ImageView wa_btn, call_tbn, sms_btn;

    public Custumer_Service() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_custumer_service, container, false);
            //Init the Variable
            sayHelloboys(view);

            //Actions
            doitBoys();
        return view;
    }

    private void sayHelloboys(View view) {
        //Button
        back = view.findViewById(R.id.button_back);

        //ImageView
        wa_btn = view.findViewById(R.id.wa_btn);
        call_tbn = view.findViewById(R.id.call_btn);
        sms_btn = view.findViewById(R.id.sms_btn);
    }

    private void doitBoys() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Halaman_Utama update_data = new Halaman_Utama();
                FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_base, update_data);
                fragmentTransaction.addToBackStack(null).commit();
            }
        });

        sms_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" +"+62811787843"));
                startActivity(intent);
            }
        });

        wa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=" +"+62811787843";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                try {
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getActivity(), "Whatsapp have not been installed", Toast.LENGTH_LONG).show();
                }
            }
        });

        call_tbn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + "+62811787843"));
                getActivity().startActivity(callIntent);
            }
        });
    }

}
