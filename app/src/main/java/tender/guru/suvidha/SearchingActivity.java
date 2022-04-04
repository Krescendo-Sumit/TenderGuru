package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.adapter.FileAdapter;
import tender.guru.suvidha.model.FileModel;
import tender.guru.suvidha.model.TypeModel;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;

public class SearchingActivity extends AppCompatActivity {
String type;
ListView lst;
ArrayAdapter adapter;
Context context;
TypeModel typeModel[];

String titles[];

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context=SearchingActivity.this;
        type=getIntent().getStringExtra("type");
      //  Toast.makeText(SearchingActivity.this, ""+type, Toast.LENGTH_SHORT).show();
        lst=(ListView) findViewById(R.id.lst);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        Preferences.save(context, Preferences.TYPE_SEARCHTYPE,"");
        Preferences.save(context, Preferences.TYPE_SEARCHRESULT,"");

        if (type.equals("1")) {
            setTitle("By Preferences");
        } else if (type.equals("2")) {
            setTitle("By Category");
        } else if (type.equals("3")) {
            setTitle("By City");
        } else if (type.equals("4")) {
            setTitle("Modules");
        }



        getValues(type);

    }

    public void getValues(String type) {
        try{
            getPrerences(type);




        }catch(Exception e)
        {

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void getPrerences(String type) {

        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
            String mobile = Preferences.get(context,Preferences.USER_MOBILE);
            String userid = Preferences.get(context,Preferences.USER_ID);

            Call<String> call = RetrofitClient.getInstance().getMyApi().getPreference(mobile,userid,type);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //    Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if(response.body()!=null) {
                       String saravMenuModels = response.body();
                        try {
                            JSONArray  jsonArray=new JSONArray(saravMenuModels.trim());
                            typeModel=new TypeModel[jsonArray.length()];
                            titles=new String[jsonArray.length()];
                            JSONObject jsonObject;
                            for(int i=0;i<jsonArray.length();i++)
                            {

                                jsonObject=jsonArray.getJSONObject(i);
                                typeModel[i]=new TypeModel();
                                typeModel[i].setTitle(jsonObject.getString("title"));
                                typeModel[i].setDetails(jsonObject.getString("details"));
                                typeModel[i].setId(jsonObject.getString("id"));
                                titles[i]=typeModel[i].getTitle();
                            }
                        adapter=new ArrayAdapter(context,R.layout.type_item,titles);
                        lst.setAdapter(adapter);

                        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                               Preferences.save(context, Preferences.TYPE_SEARCHTYPE,type);
                               Preferences.save(context, Preferences.TYPE_SEARCHRESULT,typeModel[i].getId());
                                finish();
                            }
                        });

                     } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(context, "Text Application.", Toast.LENGTH_SHORT).show();
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
/*    private void getPrerences() {

        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
            String mobile = Preferences.get(context,Preferences.USER_MOBILE);
            String userid = Preferences.get(context,Preferences.USER_ID);

            Call<String> call = RetrofitClient.getInstance().getMyApi().getPreference(mobile,userid,type);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //    Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if(response.body()!=null) {
                        String saravMenuModels = response.body();
                        try {

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(context, "Text Application.", Toast.LENGTH_SHORT).show();
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
    private void getCategories() {

        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
            String mobile = Preferences.get(context,Preferences.USER_MOBILE);
            String userid = Preferences.get(context,Preferences.USER_ID);

            Call<String> call = RetrofitClient.getInstance().getMyApi().getCategory(mobile,userid,type);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //    Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if(response.body()!=null) {
                        String saravMenuModels = response.body();
                        try {

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(context, "Text Application.", Toast.LENGTH_SHORT).show();
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
    private void getKeyword() {

        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
            String mobile = Preferences.get(context,Preferences.USER_MOBILE);
            String userid = Preferences.get(context,Preferences.USER_ID);

            Call<String> call = RetrofitClient.getInstance().getMyApi().getCity(mobile,userid,type);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //    Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if(response.body()!=null) {
                        String saravMenuModels = response.body();
                        try {

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(context, "Text Application.", Toast.LENGTH_SHORT).show();
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

    }*/
}