package tender.guru.suvidha;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.adapter.CategoryAdapter;
import tender.guru.suvidha.adapter.NoValuesPresentAdapter;
import tender.guru.suvidha.adapter.TenderAdapter;
import tender.guru.suvidha.model.CategoryModel;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;

public class FavoriteCategory extends AppCompatActivity implements CategoryAdapter.EventListener {
    Context context;

    LinearLayoutManager mManager;

    ProgressDialog progressDialog;


    CategoryAdapter adapter11;
    NoValuesPresentAdapter adapter_Novalue;
    RecyclerView recyclerView_bhartilist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_tenders);
        context= FavoriteCategory.this;
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        recyclerView_bhartilist = (RecyclerView) findViewById(R.id.rc_tenders);
        mManager = new LinearLayoutManager(context);
        recyclerView_bhartilist.setLayoutManager(mManager);
        setTitle("Set Category");
        getBhartiList("");
    }

    private void getBhartiList(String srcString) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String deviceid = "111";
            String userid = Preferences.get(context, Preferences.USER_ID);

            Call<List<CategoryModel>> call = RetrofitClient.getInstance().getMyApi().getTenderCategory(mobile, userid, deviceid,srcString);
            call.enqueue(new Callback<List<CategoryModel>>() {
                @Override
                public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                    if(response.body()!=null) {
                        List<CategoryModel> videos = response.body();
                        try {

                            adapter11 = new CategoryAdapter((ArrayList) videos,FavoriteCategory.this, context);
                            recyclerView_bhartilist.setAdapter(adapter11);
                            int resId = R.anim.layout_animation_fall_down;
                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
                            recyclerView_bhartilist.setLayoutAnimation(animation);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else
                    {
                        TenderModel t=new TenderModel();
                        t.setTitle("No Value Found.");
                        ArrayList arrayList=new ArrayList();
                        arrayList.add(t);
                        adapter_Novalue =  new NoValuesPresentAdapter((ArrayList) arrayList, context);;
                        recyclerView_bhartilist.setAdapter(adapter_Novalue);
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public void onDelete(int trid, int position) {

    }

    @Override
    public void onAddForites(String id, int status) {
        Toast.makeText(context, "Category : "+id+" Status "+status, Toast.LENGTH_SHORT).show();

        addForites(id,status);
    }

    void addForites(String cataid, int status) {


        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String deviceid = "111";
            String userid = Preferences.get(context, Preferences.USER_ID);

            Call<String> call = RetrofitClient.getInstance().getMyApi().addFevorites(mobile, userid, cataid,status);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                    if(response.body()!=null) {
                        String videos = response.body();
                        try {
                            Toast.makeText(FavoriteCategory.this, ""+videos, Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else
                    {

                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }

    }
}