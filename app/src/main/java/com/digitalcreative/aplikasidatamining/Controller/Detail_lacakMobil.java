package com.digitalcreative.aplikasidatamining.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalcreative.aplikasidatamining.Model.Model_LacakMobil;
import com.digitalcreative.aplikasidatamining.R;

import java.util.List;

public class Detail_lacakMobil extends RecyclerView.Adapter<Detail_lacakMobil.ViewHolder> {
    List<Model_LacakMobil> list;
    Context context;
    Button share, back;
    //private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 5;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public Detail_lacakMobil(List<Model_LacakMobil> list, Context context, RecyclerView recyclerView) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public Detail_lacakMobil.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Detail_lacakMobil.ViewHolder holder, int i) {

        if (holder instanceof ViewHolder){
            final Model_LacakMobil model = list.get(i);
            holder.nama_pemilik.setText(model.getNama());
            holder.no_plat.setText(model.getNo_plat());
            holder.mobil.setText(model.getNama_mobil());
            holder.tahun.setText(model.getFinance());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                    final View inflater = ((AppCompatActivity)v.getContext()).getLayoutInflater().inflate(R.layout.lacak_mobil_detail, null);
                    alertDialog.setView(inflater);

                    //Init the component
                    sayHello(model, inflater, alertDialog);

                    final AlertDialog dialog =  alertDialog.create();
                    dialog.show();

                    share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            try {
                                intent.setType("text/plain");
                                intent.setPackage("com.whatsapp");
                                intent.putExtra(Intent.EXTRA_TEXT,
                                        "Dikirim melalui aplikasi data matel nusantara ->"
                                                +"Link : https://play.google.com/store/apps/details?id=com.digitalcreative.aplikasidatamining "+"\n"
                                                +"Nomor Polisi : " +model.getNo_plat() +"\n"
                                                +"Model Unit : " +model.getNama_mobil() +"\n"
                                                +"Nomor Rangka : " +model.getNoka() +"\n"
                                                +"Nomor Mesin : " +model.getNosin() +"\n"
                                                +"Finance : " +model.getFinance() +"\n"
                                                +"INI BUKAN ALAT SAH PENARIKAN UNIT"
                                              );
                                v.getContext().startActivity(intent);
                            } catch (android.content.ActivityNotFoundException ex){
                                Toast.makeText(v.getContext(), "Whatsapp have not been installed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
            });
        }

    }

    private void sayHello(Model_LacakMobil model, final View inflater, final AlertDialog.Builder alertDialog) {
        TextView pemilikmobil, no_plat, namaunit, dinance, ovd, detailcabang, noka, nosin, tahun, warna;

        warna = inflater.findViewById(R.id.detail_warna);
        pemilikmobil = inflater.findViewById(R.id.detail_pemilikmobil);
        no_plat = inflater.findViewById(R.id.detail_noplat);
        namaunit = inflater.findViewById(R.id.detail_namaunit);
        dinance = inflater.findViewById(R.id.detail_finance);
        ovd = inflater.findViewById(R.id.detail_ovd);
        detailcabang = inflater.findViewById(R.id.detail_cabang);
        noka = inflater.findViewById(R.id.detail_noka);
        nosin = inflater.findViewById(R.id.detail_nosin);
        tahun = inflater.findViewById(R.id.detail_tahun);

        pemilikmobil.setText(model.getNama());
        no_plat.setText(model.getNo_plat());
        namaunit.setText(model.getNama_mobil());
        dinance.setText(model.getFinance());
        ovd.setText(model.getOvd());
        detailcabang.setText(model.getCabang());
        noka.setText(model.getNoka());
        nosin.setText(model.getNosin());
        tahun.setText(model.getTahun());
        warna.setText(model.getWarna());

        share = inflater.findViewById(R.id.detail_btn_share);
        back = inflater.findViewById(R.id.detail_btn_kembali);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView no_plat, nama_pemilik, mobil, tahun;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_pemilik = itemView.findViewById(R.id.pemilik_mobil);
            no_plat = itemView.findViewById(R.id.no_pol);
            mobil = itemView.findViewById(R.id.nama_unit);
            tahun = itemView.findViewById(R.id.tahun);

        }
    }
}
