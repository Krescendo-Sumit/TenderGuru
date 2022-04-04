package tender.guru.suvidha.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import tender.guru.suvidha.PaymentActivity;
import tender.guru.suvidha.ProductImageViwer;
import tender.guru.suvidha.R;
import tender.guru.suvidha.SubCourseList;
import tender.guru.suvidha.model.BhartiModel;
import tender.guru.suvidha.model.PriceModel;
import tender.guru.suvidha.util.Preferences;

public class PricingAdapter  extends  RecyclerView.Adapter<PricingAdapter.DataObjectHolder> {
    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<PriceModel> priceModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public PricingAdapter(ArrayList<PriceModel> productModels, Context context) {
        this.priceModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_prizing, parent, false);

        return new DataObjectHolder(view);
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
        return priceModelArrayList.size();
        //} else {
        //  return 0;
        // }
    }




    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            PriceModel priceModel=priceModelArrayList.get(position);
            holder.txt_title.setText(priceModel.getTitle());
            holder.btn_prize.setText("Buy in just @Rs."+priceModel.getPrice());
            String data = priceModel.getDetails();
            holder.webView.loadData(data, "text/html", "UTF-8");

            holder.btn_prize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PaymentActivity.class);
                    intent.putExtra("sid", Preferences.get(context, Preferences.USER_ID));
                    intent.putExtra("email", Preferences.get(context, Preferences.USER_EMAIL));
                    intent.putExtra("mobile", Preferences.get(context, Preferences.USER_MOBILE));
                    intent.putExtra("name", Preferences.get(context, Preferences.USER_PROFILE_NAME));
                    intent.putExtra("cid", ""+priceModel.getId());
                    intent.putExtra("amt", ""+priceModel.getPrice());
                    context.startActivity(intent);
                }
            });

            /*holder.price.setText(bhartiModel.getTitle());

            Glide.with(context)
                    .load(bhartiModel.getImagepath())
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .into(holder.imageView);

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    Preferences.save(context,Preferences.SELECTEDEXAMID,bhartiModel.getId());
                    Intent intent=new Intent(context, SubCourseList.class);
                    context.startActivity(intent);

                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ProductImageViwer.class);
                    context.startActivity(intent);
                }
            });*/

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        WebView webView;
        Button btn_prize;
        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            webView=(WebView)itemView.findViewById(R.id.web);
            btn_prize=(Button)itemView.findViewById(R.id.btn_price);

        }
    }

}
