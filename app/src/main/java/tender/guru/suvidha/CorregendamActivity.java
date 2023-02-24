package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.adapter.NoValuesPresentAdapter;
import tender.guru.suvidha.adapter.TenderResultAdapter;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.util.Constants;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;

public class CorregendamActivity extends AppCompatActivity {

    Context context;

    LinearLayoutManager mManager;

    ProgressDialog progressDialog;


    TenderResultAdapter adapter11;
    NoValuesPresentAdapter adapter_Novalue;
    RecyclerView recyclerView_bhartilist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corregendam);
        context=CorregendamActivity.this;
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        recyclerView_bhartilist = (RecyclerView) findViewById(R.id.rc_tenders);
        mManager = new LinearLayoutManager(context);
        recyclerView_bhartilist.setLayoutManager(mManager);
        getBhartiList("");

    }
    private void getBhartiList(String srcString) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String deviceid = "111";
            String userid = Preferences.get(context, Preferences.USER_ID);

            Call<List<TenderModel>> call = RetrofitClient.getInstance().getMyApi().getTendersCorrigendum(mobile, userid, deviceid,srcString);
            call.enqueue(new Callback<List<TenderModel>>() {
                @Override
                public void onResponse(Call<List<TenderModel>> call, Response<List<TenderModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                    if(response.body()!=null) {
                        List<TenderModel> videos = response.body();
                        try {

                            adapter11 = new TenderResultAdapter((ArrayList) videos, context);
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
                public void onFailure(Call<List<TenderModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

}