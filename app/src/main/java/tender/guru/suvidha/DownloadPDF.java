package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import tender.guru.suvidha.util.Constants;
import tender.guru.suvidha.util.Preferences;

public class DownloadPDF extends AppCompatActivity {
WebView web;
String fname;
Context context;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_p_d_f);
        context=DownloadPDF.this;
        web=findViewById(R.id.web);
        fname= Preferences.get(context,Preferences.SELECTEDFILE).toString().trim();
       // fname="01.pdf";
        loadData();

    }

    public class MyWebViewClient extends WebViewClient {

        private Context context;

        public MyWebViewClient(Context context) {
            this.context = context;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

   /*         //  Toast.makeText(context,url,Toast.LENGTH_LONG).show();
            String str = "";
            int li = url.lastIndexOf("/");

            try {
                str = url.substring(li + 1, url.length());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
            web.loadUrl("http://192.168.0.5/sankalpana/download.php?download_file=" + str);
      /*  if(url.equals("hrupin://activity_second")){
            Intent i = new Intent(context, Second.class);
            context.startActivity(i);
            return true;
        }else
        {
            Intent i = new Intent(context, Second.class);
            context.startActivity(i);
            return true;

        }*/
            return true;
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

    public void loadData() {
        //  Toast.makeText(getApplicationContext(), "Load Data is Called " + v[0], Toast.LENGTH_SHORT).show();
        String tbl = "";
        try {
            web.loadUrl(Constants.BASE_URL+"downloadpdf.php?fname="+fname);
            web.getSettings().setJavaScriptEnabled(true);
            web.setWebChromeClient(new WebChromeClient());


            web.setWebViewClient(new MyWebViewClient(DownloadPDF.this));

            web.getSettings().setJavaScriptEnabled(true);
            web.setWebChromeClient(new WebChromeClient());
            //  Toast.makeText(this, "Type :"+type, Toast.LENGTH_SHORT).show();

            web.setDownloadListener(new DownloadListener() {

                public void onDownloadStart(String url, String userAgent,
                                            String contentDisposition, String mimetype,
                                            long contentLength) {
                    DownloadManager.Request request = new DownloadManager.Request(
                            Uri.parse(url));

                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fname);
                    DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    dm.enqueue(request);
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT); //This is important!
                    intent.addCategory(Intent.CATEGORY_OPENABLE); //CATEGORY.OPENABLE
                    intent.setType("*/*");//any application,any extension
                    Toast.makeText(getApplicationContext(), "Downloading File", //To notify the Client that the file is being downloaded
                            Toast.LENGTH_LONG).show();

                }
            });
            web.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    //Make the bar disappear after URL is loaded, and changes string to Loading...
                    setTitle("Loading...");
                    setProgress(progress * 100); //Make the bar disappear after URL is loaded

                    // Return the app name after finish loading
                    if (progress == 100)
                        setTitle("Downloads");
                }
            });

            web.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    // do your handling codes here, which url is the requested url
                    // probably you need to open that url rather than redirect:

                    return false; // then it is not handled by default action
                }
            });

            web.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        WebView webView = (WebView) v;

                        switch (keyCode) {
                            case KeyEvent.KEYCODE_BACK:
                                if (webView.canGoBack()) {
                                  /*  webView.goBack();
                                    loadData(vr);
                                    */
                                    finish();
                                    return true;
                                }
                                break;
                        }
                    }

                    return false;
                }
            });


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class LongOperation extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(DownloadPDF.this);


        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            //UI Element
            //   uiUpdate.setText("Output : ");
            Dialog.setMessage("Please Wait..");
            Dialog.show();
            //pb.setVisibility(View.VISIBLE);
        }

        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {
            try {

                // Call long running operations here (perform background computation)
                // NOTE: Don't call UI Element here.

                // Server url call by GET method
                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);

            } catch (ClientProtocolException e) {
                Error = e.getMessage();
                cancel(true);
            } catch (IOException e) {
                Error = e.getMessage();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {
            // NOTE: You can call UI Element here.

            // Close progress dialog
            Dialog.dismiss();

            if (Error != null) {

                //  uiUpdate.setText("Output : "+Error);

            } else {
                //pb.setVisibility(View.GONE);
                //   uiUpdate.setText("Output : "+Content);
                // loadFromServer(Content.toString().trim());

                //  showData(Content);

            }
        }

    }
}