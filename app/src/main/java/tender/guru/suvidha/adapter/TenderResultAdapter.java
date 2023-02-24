package tender.guru.suvidha.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tender.guru.suvidha.OtherUserProfile;
import tender.guru.suvidha.PrizingActivity;
import tender.guru.suvidha.R;
import tender.guru.suvidha.ViewPDFResult;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.util.Preferences;

public class TenderResultAdapter extends  RecyclerView.Adapter<TenderResultAdapter.DataObjectHolder>{


    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<TenderModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public TenderResultAdapter(ArrayList<TenderModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public TenderResultAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tender_result, parent, false);

        return new TenderResultAdapter.DataObjectHolder(view);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        //  if (mSellerProductlist.size() > 0) {
        return bhartiModelArrayList.size();
        //} else {
        //  return 0;
        // }
    }




    @Override
    public void onBindViewHolder(final TenderResultAdapter.DataObjectHolder holder, final int position) {
        try {
            TenderModel bhartiModel=bhartiModelArrayList.get(position);
            holder.txt_title.setText(bhartiModel.getTitle());
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(1500);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);

          //  holder.txt_tendertype.startAnimation(anim);

            holder.txt_closedate.setText(bhartiModel.getCdate());
            holder.txt_opendate.setText(bhartiModel.getClosedate());
            holder.txt_amount.setText(bhartiModel.getAmount());
        //    holder.txt_city.setText(bhartiModel.getCity());
         //   holder.txt_date.setText("Open Date :"+bhartiModel.getOpendate()+" Close Date :"+bhartiModel.getClosedate());
     //       holder.txt_detail.setText(bhartiModel.getCat_id());

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
/*
                    if(bhartiModel.getPaystatus().equals("0"))
                    {

                       *//* Dialog dialog=new Dialog(context);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.popup_feesalert);

                        Button btn_search=dialog.findViewById(R.id.btn_okay);

                        btn_search.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                            }
                        });
                        dialog.show();*//*
Intent intent=new Intent(context, PrizingActivity.class);
context.startActivity(intent);


                    }else {*/


                        Preferences.save(context, Preferences.SELECTEDFILE, bhartiModel.getFilepath());

                        Intent intent = new Intent(context, ViewPDFResult.class);

                        context.startActivity(intent);
               //     }
                }
            });


        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txt_title,txt_date,txt_detail;
        TextView txt_opendate,txt_closedate,txt_amount,txt_city, txt_tendertype;
        ImageView imageView;
        LinearLayout ll;
        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_date = (TextView) itemView.findViewById(R.id.txt_dates);
            txt_detail = (TextView) itemView.findViewById(R.id.txt_details);
            txt_opendate=(TextView) itemView.findViewById(R.id.txt_opendate);
                    txt_closedate=(TextView) itemView.findViewById(R.id.txt_closingdate);
                    txt_city=(TextView) itemView.findViewById(R.id.txt_city);
            txt_amount=(TextView) itemView.findViewById(R.id.txt_amount);
            txt_tendertype=(TextView) itemView.findViewById(R.id.txt_tender_type);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);

        }
    }


}
