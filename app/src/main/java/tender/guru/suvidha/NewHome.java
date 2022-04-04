package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Locale;

import tender.guru.suvidha.fragments.HomeFragment;
import tender.guru.suvidha.fragments.MatchFragment;
import tender.guru.suvidha.fragments.ProfileFragment;

public class NewHome extends AppCompatActivity {
LinearLayout ll;
Context context;
Locale myLocale;
     long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        context=NewHome.this;
        ll=findViewById(R.id.ll);
        try{
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.ll,new ProfileFragment());
            //  Toast.makeText(this, "Fragment is replaced!!!!!!", Toast.LENGTH_SHORT).show();
            ft.addToBackStack(null);
            ft.commit();
        }catch (Exception e)
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
    public void feesdetails(View view) {
        try{

            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.ll,new MatchFragment());
           // Toast.makeText(this, "Fragment is replaced!!!!!!", Toast.LENGTH_SHORT).show();
            ft.addToBackStack(null);
            ft.commit();

        }catch (Exception e)
        {

        }

    }

    public void files(View view) {
        try{

            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.ll,new ProfileFragment());
           // Toast.makeText(this, "Fragment is replaced!!!!!!", Toast.LENGTH_SHORT).show();
            ft.addToBackStack(null);
            ft.commit();

        }catch (Exception e)
        {

        }
    }

    public void home(View view) {
        try{

            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.ll,new HomeFragment());
          //  Toast.makeText(this, "Fragment is replaced!!!!!!", Toast.LENGTH_SHORT).show();
            ft.addToBackStack(null);
            ft.commit();

        }catch (Exception e)
        {

        }
    }

    public void marathi(View v) {
        try {
            setLocale("mr");
            finish();
            startActivity(getIntent());
        } catch (Exception e) {

        }
    }

    public void english(View v) {
        try {
            setLocale("en");
            finish();
            startActivity(getIntent());
        } catch (Exception e) {

        }
    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        onConfigurationChanged(conf);
/*Intent refresh = new Intent(this, AndroidLocalize.class);
startActivity(refresh);*/
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        //  lblLang.setText(R.string.langselection);
        super.onConfigurationChanged(newConfig);
        Toast.makeText(context, "Languange Changed", Toast.LENGTH_SHORT).show();

        // Checks the active language
        if (newConfig.locale == Locale.ENGLISH) {
            Toast.makeText(this, "English", Toast.LENGTH_SHORT).show();
        } else if (newConfig.locale == Locale.FRENCH) {
            Toast.makeText(this, "French", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    /*    if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();*/
    }
}