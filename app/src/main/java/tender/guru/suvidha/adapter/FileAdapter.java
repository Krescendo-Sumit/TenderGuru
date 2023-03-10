package tender.guru.suvidha.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tender.guru.suvidha.R;
import tender.guru.suvidha.ViewPDF;
import tender.guru.suvidha.model.FileModel;
import tender.guru.suvidha.util.Preferences;

public class FileAdapter extends  RecyclerView.Adapter<FileAdapter.DataObjectHolder>{


    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<FileModel> FileModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public FileAdapter(ArrayList<FileModel> productModels, Context context) {

        this.FileModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public FileAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listoffiles, parent, false);

        return new FileAdapter.DataObjectHolder(view);
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
        return FileModelArrayList.size();
        //} else {
        //  return 0;
        // }
    }




    @Override
    public void onBindViewHolder(final FileAdapter.DataObjectHolder holder, final int position) {
        try {
            FileModel FileModel=FileModelArrayList.get(position);
            holder.txt_title.setText("Title : "+FileModel.getTitle());
            holder.txt_amt.setText("Added on "+FileModel.getCdate());
            holder.txt_details.setText(" "+FileModel.getDetails());


            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    Preferences.save(context,Preferences.SELECTEDFILE,FileModel.getFilepath().trim());
                    Intent intent=new Intent(context, ViewPDF.class);
                    context.startActivity(intent);

                }
            });

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_amt, txt_details;

        LinearLayout ll;
        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_title);
            txt_amt = (TextView) itemView.findViewById(R.id.txt_days);
            txt_details = (TextView) itemView.findViewById(R.id.txt_paiddate);

            ll=(LinearLayout)itemView.findViewById(R.id.ll);

        }
    }


}
