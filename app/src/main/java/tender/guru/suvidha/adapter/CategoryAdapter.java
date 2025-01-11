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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tender.guru.suvidha.OtherUserProfile;
import tender.guru.suvidha.PrizingActivity;
import tender.guru.suvidha.R;
import tender.guru.suvidha.model.CategoryModel;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.util.Preferences;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.DataObjectHolder> {


    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<CategoryModel> bhartiModelArrayList = null;
    EventListener eventListener;

    public interface EventListener {
        void onDelete(int trid, int position);

        void onAddForites(String id, int status);
    }

    public CategoryAdapter(ArrayList<CategoryModel> productModels, EventListener eventListener, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:", ">>" + productModels.size());
        this.context = context;
        this.eventListener = eventListener;

    }

    @NonNull
    @Override
    public CategoryAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_category, parent, false);

        return new CategoryAdapter.DataObjectHolder(view);
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
    public void onBindViewHolder(final CategoryAdapter.DataObjectHolder holder, final int position) {
        try {
            CategoryModel model = bhartiModelArrayList.get(position);
            holder.txt_title.setText(model.getTitle());
            if (!model.getSt().equals("0"))
                holder.chk_isfavorite.setChecked(true);

            holder.chk_isfavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int status = 0;
                    if (isChecked) {
                        status = 1;
                    } else {
                        status = 0;
                    }
                    eventListener.onAddForites(model.getId(), status);
                }
            });


            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(1500);
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);






/*            if(bhartiModel.getTender_type().trim().equals("0"))
            {
                holder.txt_tendertype.setText("Offline");
                holder.txt_tendertype.setTextColor(Color.GREEN);
            }else if(bhartiModel.getTender_type().trim().equals("1")) {
                holder.txt_tendertype.setText("Live");
                holder.txt_tendertype.setTextColor(Color.RED);
            }

            holder.txt_tendertype.startAnimation(anim);

            holder.txt_closedate.setText(bhartiModel.getClosedate());
            holder.txt_opendate.setText(bhartiModel.getOpendate());
            holder.txt_amount.setText(bhartiModel.getAmount());
            holder.txt_city.setText(bhartiModel.getCity());
         //   holder.txt_date.setText("Open Date :"+bhartiModel.getOpendate()+" Close Date :"+bhartiModel.getClosedate());
            holder.txt_detail.setText(bhartiModel.getCat_id());
*/
            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Hi " + model.getId(), Toast.LENGTH_SHORT).show();


                }
            });


        } catch (Exception e) {
            Log.i("Error ", e.getMessage());
        }
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_date, txt_detail;
        TextView txt_opendate, txt_closedate, txt_amount, txt_city, txt_tendertype;
        ImageView imageView;
        LinearLayout ll;
        CheckBox chk_isfavorite;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_message);
            chk_isfavorite = itemView.findViewById(R.id.chk_isfavorite);
 /*            txt_detail = (TextView) itemView.findViewById(R.id.txt_details);
            txt_opendate = (TextView) itemView.findViewById(R.id.txt_opendate);
            txt_closedate = (TextView) itemView.findViewById(R.id.txt_closingdate);
            txt_city = (TextView) itemView.findViewById(R.id.txt_city);
            txt_amount = (TextView) itemView.findViewById(R.id.txt_amount);
            txt_tendertype = (TextView) itemView.findViewById(R.id.txt_tender_type);
            ll = (LinearLayout) itemView.findViewById(R.id.ll);*/

        }
    }


}
