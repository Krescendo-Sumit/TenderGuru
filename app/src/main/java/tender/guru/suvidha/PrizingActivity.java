package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tender.guru.suvidha.adapter.PricingAdapter;
import tender.guru.suvidha.adapter.TenderAdapter;
import tender.guru.suvidha.api_response.PricingAPI;
import tender.guru.suvidha.model.PriceModel;
import tender.guru.suvidha.util.Constants;
import tender.guru.suvidha.util.Preferences;

public class PrizingActivity extends AppCompatActivity implements PricingAPI.ResultOutput {
    WebView web_onemonth, web_oneyear, webtwoyear;
    Button btnonemonth, bntoneyear, btntwoyear;
    Context context;
    PricingAPI pricingAPI;
    PricingAdapter adapter;
    LinearLayoutManager mManager;
    RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prizing);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Membership Prizing");
        context = PrizingActivity.this;
        pricingAPI = new PricingAPI(context, this);
        web_onemonth = findViewById(R.id.web_onemonth);
        web_oneyear = findViewById(R.id.web_oneyear);
        webtwoyear = findViewById(R.id.web_twoyear);
        btnonemonth = findViewById(R.id.btn_onemonth);
        bntoneyear = findViewById(R.id.btnoneyear);
        btntwoyear = findViewById(R.id.btntwoyear);

        String baseurl = Constants.BASE_URL;
        web_onemonth.getSettings().setJavaScriptEnabled(true);
        web_oneyear.getSettings().setJavaScriptEnabled(true);
        webtwoyear.getSettings().setJavaScriptEnabled(true);

        web_onemonth.loadUrl(baseurl + "onemonth.php");
        web_oneyear.loadUrl(baseurl + "oneyear.php");
        webtwoyear.loadUrl(baseurl + "twoyear.php");
        String mobile = Preferences.get(context, Preferences.USER_MOBILE);
        String deviceid = "111";

        String userid = Preferences.get(context, Preferences.USER_ID);
        mManager = new LinearLayoutManager(context);
        list = (RecyclerView) findViewById(R.id.lst);
        mManager = new LinearLayoutManager(context);
        list.setLayoutManager(mManager);
        pricingAPI.getPrizing(mobile, userid);

        btnonemonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.popup_feesalert);

                Button btn_search = dialog.findViewById(R.id.btn_okay);
                TextView txt_details = dialog.findViewById(R.id.txt_details);
                txt_details.setText("Contact admin to activate this account.Pay fees and use this service \\n Rs. 300 / Month .\\n Transfer this amount and share sent payment screenshot with Registered Mobile Number to +91 9673380001 Whatsapp Number.We will activate your account within 12hrs.");
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, PaymentActivity.class);
                        intent.putExtra("sid", Preferences.get(context, Preferences.USER_ID));
                        intent.putExtra("email", Preferences.get(context, Preferences.USER_EMAIL));
                        intent.putExtra("mobile", Preferences.get(context, Preferences.USER_MOBILE));
                        intent.putExtra("name", Preferences.get(context, Preferences.USER_PROFILE_NAME));
                        intent.putExtra("cid", "1");
                        intent.putExtra("amt", "5000");
                        context.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        btntwoyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.popup_feesalert);
                TextView txt_details = dialog.findViewById(R.id.txt_details);
                txt_details.setText("Contact admin to activate this account.Pay fees and use this service \\n Rs. 5000 / 2 Year .\\n Transfer this amount and share sent payment screenshot with Registered Mobile Number to +91 9673380001 Whatsapp Number.We will activate your account within 12hrs.");

                Button btn_search = dialog.findViewById(R.id.btn_okay);

                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, PaymentActivity.class);
                        intent.putExtra("sid", Preferences.get(context, Preferences.USER_ID));
                        intent.putExtra("email", Preferences.get(context, Preferences.USER_EMAIL));
                        intent.putExtra("mobile", Preferences.get(context, Preferences.USER_MOBILE));
                        intent.putExtra("name", Preferences.get(context, Preferences.USER_PROFILE_NAME));
                        intent.putExtra("cid", "2");
                        intent.putExtra("amt", "5000");
                        context.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        bntoneyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.popup_feesalert);
                TextView txt_details = dialog.findViewById(R.id.txt_details);
                txt_details.setText("Contact admin to activate this account.Pay fees and use this service \\n Rs. 3000 / Year .\\n Transfer this amount and share sent payment screenshot with Registered Mobile Number to +91 9673380001 Whatsapp Number.We will activate your account within 12hrs.");

                Button btn_search = dialog.findViewById(R.id.btn_okay);

                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, PaymentActivity.class);
                        intent.putExtra("sid", Preferences.get(context, Preferences.USER_ID));
                        intent.putExtra("email", Preferences.get(context, Preferences.USER_EMAIL));
                        intent.putExtra("mobile", Preferences.get(context, Preferences.USER_MOBILE));
                        intent.putExtra("name", Preferences.get(context, Preferences.USER_PROFILE_NAME));
                        intent.putExtra("cid", "3");
                        intent.putExtra("amt", "5000");
                        context.startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

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

    @Override
    public void onResultPrizing(List<PriceModel> priceModels) {
        Toast.makeText(context, "" + priceModels.size(), Toast.LENGTH_SHORT).show();

        try {
            adapter = new PricingAdapter((ArrayList) priceModels, context);
            list.setAdapter(adapter);
            int resId = R.anim.layout_animation_fall_down;
            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
            list.setLayoutAnimation(animation);
        } catch (Exception e) {


        }
    }
}