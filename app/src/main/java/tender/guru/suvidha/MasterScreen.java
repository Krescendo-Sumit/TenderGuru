package tender.guru.suvidha;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.adapter.NoValuesPresentAdapter;
import tender.guru.suvidha.adapter.TenderAdapter;
import tender.guru.suvidha.api_response.MasterScreenAPI;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.ResultOutput;
import tender.guru.suvidha.util.RetrofitClient;

public class MasterScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ResultOutput {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    Context context;
    LinearLayoutManager mManager;
    ProgressDialog progressDialog;
    TenderAdapter adapter11;
    NoValuesPresentAdapter adapter_Novalue;
    RecyclerView recyclerView_bhartilist;
    EditText et_searchText;
    TextView txt_slogan;
    private long pressedTime;
    String data = "";
    int cnt = 0;
    Button btn_go;
    MasterScreenAPI masterScreenAPI;
    TextView txt_notification_cnt;
    String mobile,deviceid,userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_screen);
        context = MasterScreen.this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
/*
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

*/


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         mobile = Preferences.get(context, Preferences.USER_MOBILE);
         deviceid = "111";
         userid = Preferences.get(context, Preferences.USER_ID);

        masterScreenAPI = new MasterScreenAPI(context,this);

        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        recyclerView_bhartilist = (RecyclerView) findViewById(R.id.rc_tenderlist);
        mManager = new LinearLayoutManager(context);
        recyclerView_bhartilist.setLayoutManager(mManager);
        setTitle("Tender Guru");
        Preferences.save(context, Preferences.TYPE_SEARCHTYPE, "");
        Preferences.save(context, Preferences.TYPE_SEARCHRESULT, "");
        et_searchText = findViewById(R.id.et_searchtext);
        btn_go = findViewById(R.id.btn_go);
        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = et_searchText.getText().toString().trim();
               // getBhartiList(" where title like '%" + str + "%'");
                masterScreenAPI.getTender(mobile,userid,deviceid," where title like '%" + str + "%'");
            }
        });
        View headerView = navigationView.getHeaderView(0);
        txt_slogan = headerView.findViewById(R.id.txt_slogan);
        txt_slogan.setSelected(true);
        masterScreenAPI.getTender(mobile,userid,deviceid,data);
        txt_notification_cnt=toolbar.findViewById(R.id.txt_notificationcnt);
        txt_notification_cnt.setText("");
        showNotificationCount();
        if (isWriteExtStorageAllow()) {
            //   share();
        } else {
            requestStoragePermission();
        }
    }

    private void showNotificationCount() {
    try{
        String mobile = Preferences.get(context, Preferences.USER_MOBILE);
        String deviceid = "111";
        String userid = Preferences.get(context, Preferences.USER_ID);
        masterScreenAPI.getNotificationCount(mobile,userid);
//        Toast.makeText(context,"On View Page : "+str,Toast.LENGTH_LONG).show();
//        txt_notification_cnt.setText(""+str);

    }catch(Exception e)
    {
        txt_notification_cnt.setText(""+e.getMessage());
    }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.master_screen, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
/*        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();*/
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        Intent intent = new Intent(context, SearchingActivity.class);
        switch (id) {
            case R.id.nav_by_prizing:
                intent.setClass(context, PrizingActivity.class);

                startActivity(intent);
                break;
          /*  case R.id.nav_by_preference:
             intent.putExtra("type","1");
                startActivity(intent);
                break;*/
            case R.id.nav_by_category:
                intent.putExtra("type", "2");
                startActivity(intent);
                break;
            case R.id.nav_by_city:
                intent.putExtra("type", "3");
                startActivity(intent);
                break;
            case R.id.nav_by_keyword:

                Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.popup_keywords);

                Button btn_search = dialog.findViewById(R.id.btn_search);
                EditText et_text = dialog.findViewById(R.id.et_searchtext);

                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String str = et_text.getText().toString().trim();
                        if (str.equals("")) {
                            et_text.setError("Please Enter Text.");
                        } else {
                            masterScreenAPI.getTender(mobile,userid,deviceid," where title like '%" + str + "%'");

                        }
                    }
                });
                dialog.show();

                break;

            case R.id.nav_by_helpdesk:

                intent.setClass(context, HelpDesk.class);
                startActivity(intent);
                break;
            case R.id.nav_by_faq:

                intent.setClass(context, FAQ.class);
                startActivity(intent);
                break;
            case R.id.nav_by_suggest:

                intent.setClass(context, Suggestions.class);
                startActivity(intent);
                break;
            case R.id.nav_by_module:

                intent.setClass(context, FreeTenders.class);
                startActivity(intent);
                break;
            case R.id.nav_by_aboutus:

                intent.setClass(context, AboutUs.class);
                startActivity(intent);
                break;
            case R.id.nav_by_testimonials:

                intent.setClass(context, Testomonial.class);
                startActivity(intent);
                break;
            case R.id.nav_by_education:

                intent.setClass(context, Education.class);
                startActivity(intent);
                break;
            case R.id.nav_by_registration:

                intent.setClass(context, Registration.class);
                startActivity(intent);
                break;
            case R.id.nav_by_result:

                intent.setClass(context, TenderResult.class);
                startActivity(intent);
                break;
            case R.id.nav_by_corrigerdum:

                intent.setClass(context, CorregendamActivity.class);
                startActivity(intent);
                break;


        }

        drawer.close();

        return false;
    }

  /*  private void getBhartiList(String srcString) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String deviceid = "111";

            String userid = Preferences.get(context, Preferences.USER_ID);
            ;
            Call<List<TenderModel>> call = RetrofitClient.getInstance().getMyApi().getTenders(mobile, userid, deviceid, srcString);
            call.enqueue(new Callback<List<TenderModel>>() {
                @Override
                public void onResponse(Call<List<TenderModel>> call, Response<List<TenderModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                    if (response.body() != null) {
                        List<TenderModel> videos = response.body();
                        try {

                            adapter11 = new TenderAdapter((ArrayList) videos, context);
                            recyclerView_bhartilist.setAdapter(adapter11);
                            int resId = R.anim.layout_animation_fall_down;
                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
                            recyclerView_bhartilist.setLayoutAnimation(animation);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        TenderModel t = new TenderModel();
                        t.setTitle("No Value Found.");
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(t);
                        adapter_Novalue = new NoValuesPresentAdapter((ArrayList) arrayList, context);
                        ;
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
*/
    @Override
    protected void onResume() {
        super.onResume();
        if (cnt > 1) {
            String type = Preferences.get(context, Preferences.TYPE_SEARCHTYPE);
            String result = Preferences.get(context, Preferences.TYPE_SEARCHRESULT);
            if (type.equals("1")) {
                data = " where preferenceid=" + result;
            } else if (type.equals("2")) {
                data = " where cat_id=" + result;
            } else if (type.equals("3")) {
                data = " where city=" + result;
            } else if (type.equals("4")) {
                data = " where moduleid=" + result;
            }
            // Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
            //getBhartiList(data);
            masterScreenAPI.getTender(mobile,userid,deviceid,data);


        }
        cnt++;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Toast.makeText(context, "My Profile", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, NewHome.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void wa(View view) {
        try {

            try {
                String text = "Tender Guru Support : User ID :TG0002022" + Preferences.get(context, Preferences.USER_ID);// Replace with your message.
                text += "\n Message : ";
                String toNumber = "919673380001"; // Replace with mobile phone number without +Sign or leading zeros, but with country code
                //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.


                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {

        }
    }

    public void notification(View v) {
        try {

            Intent intent = new Intent(context, Notifications.class);
            startActivity(intent);

        } catch (Exception e) {

        }
    }

    public void call(View v) {
        try {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:9673380001"));//change the number
            startActivity(callIntent);

        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResult(String result) {
      //  Toast.makeText(context, "From Interface Call."+result, Toast.LENGTH_SHORT).show();
        txt_notification_cnt.setText(result);
    }

    @Override
    public void onListResponce(List result) {
        try{
            List<TenderModel> videos = result;
            try {
                adapter11 = new TenderAdapter((ArrayList) videos, context);
                recyclerView_bhartilist.setAdapter(adapter11);
                int resId = R.anim.layout_animation_fall_down;
                LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
                recyclerView_bhartilist.setLayoutAnimation(animation);
            } catch (NullPointerException e) {
                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }catch(Exception e)
        {

        }
    }

    private boolean isWriteExtStorageAllow() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestStoragePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission

            //   requestStoragePermission();
        }
        if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
            //  Toast.makeText(this, "TRue", Toast.LENGTH_SHORT).show();
            // requestStoragePermission();
        }


        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CALL_PHONE}, 111);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isWriteExtStorageAllow()) {

        } else {
            requestStoragePermission();
        }
    }

}