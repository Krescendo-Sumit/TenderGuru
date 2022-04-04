package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import tender.guru.suvidha.util.Constants;

public class PaymentActivity extends AppCompatActivity {
    String sid;
    String email;
    String mobile;
    String name;
    String cid;
    String amt;
    WebView web;
    String uid="";
    ProgressBar pb;
    String urrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFeatureInt( Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setTitle("  Payment Page ");
        sid = getIntent().getExtras().getString("sid");
        email = getIntent().getExtras().getString("email");
        mobile = getIntent().getExtras().getString("mobile");
        name = getIntent().getExtras().getString("name");
        cid = getIntent().getExtras().getString("cid");
        amt = getIntent().getExtras().getString("amt");
        name=name+"_"+cid;
        pb=(ProgressBar)findViewById(R.id.pb);
        web=(WebView)findViewById(R.id.web);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebChromeClient(new WebChromeClient());
        //  web.getSettings().setBuiltInZoomControls(true);

        urrl= Constants.BASE_URL+"insta/order.php?sid="+sid+"&email="+email+"&mobile="+mobile+"&name="+name+"&cid="+cid+"&amt="+amt;
        Log.i("Url",urrl);
        web.loadUrl(urrl);
        web.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                //Make the bar disappear after URL is loaded, and changes string to Loading...
                //  setTitle("Loading...");
                setProgress(progress * 100); //Make the bar disappear after URL is loaded
                pb.setProgress(progress);
                // Return the app name after finish loading
                if (progress == 100) {

                    pb.setVisibility(View.GONE);
                }}
        });

        web.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                // view.loadUrl(url);
                Toast.makeText(PaymentActivity.this,url,Toast.LENGTH_LONG).show();
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
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
//                Toast.makeText(PaymentActivity.this, "Error Occur", Toast.LENGTH_SHORT).show();
//                finish();
//                Intent intent=new Intent(PaymentActivity.this,ErroActivity.class);
//                startActivity(intent);
                // view.loadData(data, "text/html", "UTF-8");
             //   super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        web.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        web.setLongClickable(false);
        web.setWebViewClient(new PaymentActivity.MyWebViewClient(this));

    }

    private void updateWeb() {
        try{

        }catch(Exception e)
        {

        }
    }
    public class MyWebViewClient extends WebViewClient {

        private Context context;

        public MyWebViewClient(Context context) {
            this.context = context;
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
           /* Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
            Toast.makeText(PaymentActivity.this, "Error Occur", Toast.LENGTH_SHORT).show();
            finish();
            Intent intent=new Intent(PaymentActivity.this,ErroActivity.class);
            startActivity(intent);
            // view.loadData(data, "text/html", "UTF-8");
            super.onReceivedError(view, errorCode, description, failingUrl);*/
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            int li = url.lastIndexOf("/");
            String str = "0";
            try {

                if(url.contains("JhalPayment"))
                {
                    Intent intent = new Intent(PaymentActivity.this, MasterScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
              //  view.loadUrl(url);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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


}