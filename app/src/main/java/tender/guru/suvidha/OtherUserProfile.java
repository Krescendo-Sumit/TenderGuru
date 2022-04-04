package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.model.UserModel;
import tender.guru.suvidha.util.Constants;
import tender.guru.suvidha.util.DownloadTask;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class OtherUserProfile extends AppCompatActivity {
    Context context;
    ProgressDialog progressDialog;
    TextView txt_name, txt_mobile, txt_batch, txt_email, txt_dhyaasid, txt_address;
    RelativeLayout rl;
    WebView dp;
    int onResumecnt = 0;
    long pressedTime;
    String url;
    static URLConnection conn, conexion;

    String interestedUser = "";
    DownloadManager manager;

    TextView txt_title,txt_date,txt_detail,txt_opendate,txt_closedate,txt_amount,txt_city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        init();

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
    private void init() {
        try {

            context = OtherUserProfile.this;
            progressDialog = new ProgressDialog(context);
            txt_name = findViewById(R.id.txt_name);
            txt_mobile = findViewById(R.id.txt_mobile);
            txt_batch = findViewById(R.id.txt_batch);
            txt_email = findViewById(R.id.txt_email);
            txt_address = findViewById(R.id.txt_address);
            txt_dhyaasid = findViewById(R.id.txt_dhyassid);


            txt_title= findViewById(R.id.txt_title);
                    txt_date= findViewById(R.id.txt_date);
            txt_detail= findViewById(R.id.txt_details);
                    txt_opendate= findViewById(R.id.txt_opendate);
            txt_closedate= findViewById(R.id.txt_closingdate);
                    txt_amount= findViewById(R.id.txt_amount);
            txt_city= findViewById(R.id.txt_city);



            dp = findViewById(R.id.dp);
            dp.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            dp.getSettings().setAppCacheEnabled(false);
            dp.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


            checkValid();


        } catch (Exception e) {
            Toast.makeText(context, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkValid() {
        try {

            if (CheckConnection.checkInternet(context)){
              //  Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();
                getUserProfile();}
            else {
                //  Toast.makeText(Flash.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.popup_retry);
                Button btn_retry = dialog.findViewById(R.id.btn_retry);
                btn_retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        checkValid();
                    }
                });
                dialog.show();

            }

        } catch (Exception e) {

        }
    }

    public void getUserProfile() {
        try {
            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String userid = Preferences.get(context, Preferences.USER_ID);
            String deviceid = "111";

            String tenderid = Preferences.get(context, Preferences.SELECTEDTENDER);
            ;
            Call<List<TenderModel>> call = RetrofitClient.getInstance().getMyApi().getTenderDetails(userid, tenderid);
            call.enqueue(new Callback<List<TenderModel>>() {
                @Override
                public void onResponse(Call<List<TenderModel>> call, Response<List<TenderModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                   //  Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    try {
                        List<TenderModel> MyProfiles = response.body();

                        for (TenderModel v : MyProfiles) {
                            interestedUser = v.getId();
                            Log.i("Dp Path", Constants.BASE_URL + "files/" + v.getFilepath());
                             Preferences.save(context,Preferences.SELECTEDFILE,v.getFilepath());
                            String erurl = Constants.BASE_URL + "dp/noimg.jpg";

                            try {
                                String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
                                data = data + "<body><span styele='text-align:justify;'>" +
                                        "" + v.getDetails() +
                                        "</span></body></html>";
                                dp.getSettings().setJavaScriptEnabled(true);
                                dp.loadData(data, "text/html", "UTF-8");
                            } catch (Exception e) {
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                       /*     txt_dhyaasid.setText(" TG-" + v.getId());
                            txt_name.setText(" " + v.getTitle());
                            txt_batch.setText("Open Date :" + v.getOpendate() + " Close Date :" + v.getClosedate());
                           */ txt_title.setText(v.getTitle());
                            txt_amount.setText(v.getAmount());
                            txt_closedate.setText(v.getClosedate());
                            txt_opendate.setText(v.getOpendate());
                            txt_detail.setText(v.getCat_id());
                            txt_city.setText(v.getCity());
                        }
                    } catch (Exception e) {
                        Toast.makeText(OtherUserProfile.this, "Something went wrong : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    try {
                        // getMenuList();

                    } catch (NullPointerException e) {
                        Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<List<TenderModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (
                Exception e) {

        }

    }

    public void showinterest(View view) {

        showInterest();
     //   Toast.makeText(context, "Show interest", Toast.LENGTH_SHORT).show();
    }

    public void chat(View view) {
        Intent intent = new Intent(context, Chating.class);
        intent.putExtra("oid", interestedUser);
        startActivity(intent);
        Toast.makeText(context, "Chating", Toast.LENGTH_SHORT).show();
    }


    public void download(View view) {
        String fname= Preferences.get(context,Preferences.SELECTEDFILE).toString().trim();
   /*   //  fname=fname.replace(" ","%20");
        //String pdfurl= Constants.BASE_URL+"files/"+fname;
        String pdfurl= "http://tenderguru.krescendo.co.in/files/01.pdf";

      //  new DownloadTask(OtherUserProfile.this, pdfurl);
        new LongOperation_Download().execute("http://tenderguru.krescendo.co.in/files/01.pdf");
//        Log.i("PDFURL",pdfurl);
//        manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//        Uri uri = Uri.parse(pdfurl);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
//        long reference = manager.enqueue(request);*/

        Intent intent = new Intent(context, DownloadPDF.class);
        startActivity(intent);
    }


    private void showInterest() {
        //  Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
            String userid = Preferences.get(context, Preferences.USER_ID).toString().trim();
            //   String interestid=Preferences.get(context,Preferences.USER_INTERESTED_ID).toString().trim();
            String interestid = interestedUser.trim();

            Call<String> call = RetrofitClient.getInstance().getMyApi().addInterest(
                    userid, interestid);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        String saravMenuModels = response.body();
                        try {
                            Toast.makeText(OtherUserProfile.this, "" + saravMenuModels, Toast.LENGTH_SHORT).show();

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
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
            Log.i("Error is ", e.getMessage());
        }


    }


    public void showimages(View view) {
        try {

            Intent intent = new Intent(context, ViewPDF.class);
            startActivity(intent);

        } catch (Exception e) {

        }
    }


    private class LongOperation_Download extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        String result = "";
        private String Error = null;
        //private ProgressDialog Dialog = new ProgressDialog(ListAudioList.this);
        double total = 0.0;
        int lenghtOfFile = 0;
        File file;
        int size;

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            // Dialog.setMessage("Please Wait ");
            //Dialog.show();
            Log.i("Start","Start");
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // downloadFileFromServer(fname,furl);
                Log.i("Start","Start"+urls[0]);
                int count;
                try {
                    URL url = new URL(urls[0]);
                    conexion = url.openConnection();
                    conexion.setRequestProperty("Accept-Encoding", "identity");
                    conexion.connect();
                    // this will be useful so that you can show a tipical 0-100% progress bar


                    // downlod the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/chetana/sumit.pdf");
                    file = new File(Environment.getExternalStorageDirectory().getPath() + "/chetana/sumit.pdf");
                    byte data[] = new byte[1024];
                    lenghtOfFile = conexion.getContentLength();
                    total = 0;
                    size = conexion.getContentLength();

                    while ((count = input.read(data)) != -1) {
                        total += count;

                       Log.i("-------------------->", lenghtOfFile + "    ------ >" + (int) (total * 100 / lenghtOfFile));
                      //  publishProgress();
                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                }
                return null;

            } catch (Exception e) {
                Error = e.getMessage();
                Log.i("-------------------->", e.getMessage());

                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            // Dialog.dismiss();
            Log.i("-------------------->", "err"+Error);

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);

            } else {
                //Toast.makeText(SignUp.this, "" + result, Toast.LENGTH_SHORT).show();
                // finish();

            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            double bytes = file.length();
            double kilobytes = (bytes / 1024);

            int a = ((int)(kilobytes * 100) / (lenghtOfFile / 1024));
            Log.i("Status","Done "+a);
            if (a==100)
            {Log.i("Status","Done Success");
            }

        }
    }



}