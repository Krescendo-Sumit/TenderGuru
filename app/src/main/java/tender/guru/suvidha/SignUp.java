package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    String name, email, mobile, course, password, confirmpassword, address;
    RadioGroup rbg;
    RadioButton rb1, rb2, rb3;
    Context context;
    ProgressDialog progressDialog;
    Locale myLocale;
    int mYear, mMonth, mDay;
    Spinner et_color,et_month_income;
    EditText
            et_name,
            et_dob,
            et_age,
            et_email,
            et_mobile1,
            et_mobile2,
            et_height,

            et_gotra,
            et_qualification,
            et_jobdetails,
            et_joblocation,

            et_localadress,
            et_premanent_address,
            et_hobbies,
            et_parner_requirment,
            et_fathername,
            et_father_ocupation,
            et_mothername,
            et_totalbrother,
            et_brotherstatus,
            et_totalsister,
            et_sisterstatus,
            et_mamachekul,
            et_password,
            et_confirmpassword;
    RadioButton rb_cata_bride, rb_catagroom;
    RadioButton rb_janmapatrikaYes, rb_janmapatrikaNo;
    RadioButton rb_isChasmaYes, rb_isChashmaNo;
    RadioButton rb_wishToSeeJPYes, rb_wishToSeeJPNo;
    RadioButton rb_isMangalYes, rb_isMangalNo;
String color[]={"Light","Fair","Medium","Dark"};
String anulaIncome[]={
        "1-2 Lakh",
        "2-4 Lakh",
        "4-6 Lakh",
        "6-8 Lakh",
        "8-10 Lakh",
        "10-Above Lakh"
};

    String
            str_category = "0",
            str_et_name,
            str_et_dob,
            str_et_age,
            str_et_email,
            str_et_mobile1,
            str_et_mobile2,
            str_isJanmpatrika,
            str_et_height,
            str_et_color,
            str_et_gotra,
            str_isChashma,
            str_et_qualification,
            str_et_jobdetails,
            str_et_joblocation,
            str_et_month_income,
            str_et_localadress,
            str_et_premanent_address,
            str_isWishToSeeJamnpatrika,
            str_isMangal,
            str_et_hobbies,
            str_et_parner_requirment,
            str_et_fathername,
            str_et_father_ocupation,
            str_et_mothername,
            str_et_totalbrother,
            str_et_brotherstatus,
            str_et_totalsister,
            str_et_sisterstatus,
            str_et_mamachekul,
            str_et_password,
            str_et_confirmpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        context = SignUp.this;
        init();
    }

    private void init() {

        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_dob = findViewById(R.id.et_dob);
        et_age = findViewById(R.id.et_age);
        et_mobile1 = findViewById(R.id.et_mobile1);
        et_mobile2 = findViewById(R.id.et_mobile2);
        et_height = findViewById(R.id.et_height);
        et_color = findViewById(R.id.et_color);

        ArrayAdapter arrayAdapter_color=new ArrayAdapter(context,R.layout.type_item,color);
        et_color.setAdapter(arrayAdapter_color);


        et_gotra = findViewById(R.id.et_gotra);
        et_qualification = findViewById(R.id.et_qualification);
        et_jobdetails = findViewById(R.id.et_jobdetails);
        et_joblocation = findViewById(R.id.et_joblocation);
        et_month_income = findViewById(R.id.et_month_income);
        ArrayAdapter arrayAdapter_anual=new ArrayAdapter(context,R.layout.type_item,anulaIncome);
        et_month_income.setAdapter(arrayAdapter_anual);

        et_localadress = findViewById(R.id.et_localadress);
        et_premanent_address = findViewById(R.id.et_premanent_address);
        et_hobbies = findViewById(R.id.et_hobbies);
        et_parner_requirment = findViewById(R.id.et_parner_requirment);
        et_fathername = findViewById(R.id.et_fathername);
        et_father_ocupation = findViewById(R.id.et_father_ocupation);
        et_mothername = findViewById(R.id.et_mothername);
        et_totalbrother = findViewById(R.id.et_totalbrother);
        et_brotherstatus = findViewById(R.id.et_brotherstatus);
        et_totalsister = findViewById(R.id.et_totalsister);
        et_sisterstatus = findViewById(R.id.et_sisterstatus);
        et_mamachekul = findViewById(R.id.et_mamachekul);
        et_password = findViewById(R.id.et_password);
        et_confirmpassword = findViewById(R.id.et_confirmpassword);

        rb_cata_bride = findViewById(R.id.rb_bride);
        rb_catagroom = findViewById(R.id.rb_groom);

        rb_janmapatrikaYes = findViewById(R.id.rb_seetojanmpatrikayes);
        rb_janmapatrikaNo = findViewById(R.id.rb_seetojanmpatrikano);

        rb_isChasmaYes = findViewById(R.id.rb_chamayes);
        rb_isChashmaNo = findViewById(R.id.rb_chamano);

        rb_wishToSeeJPYes = findViewById(R.id.rb_seetojanmpatrikayes);
        rb_wishToSeeJPNo = findViewById(R.id.rb_seetojanmpatrikano);

        rb_isMangalYes = findViewById(R.id.rb_ismangalyes);
        rb_isMangalNo = findViewById(R.id.rb_ismangalno);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Wait..");



        et_dob.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */

                        String ssm="",ssd="";
                        if((selectedmonth+1)<10)
                            ssm="0"+(selectedmonth+1);
                        else
                            ssm=""+(selectedmonth+1);
                        if((selectedday)<10)
                            ssd="0"+selectedday;
                        else
                            ssd=""+selectedday;

                        String dd = ssd + "-" + (ssm) + "-" + selectedyear;
                        et_dob.setText(dd);
                        et_age.setText(getAge(selectedyear,selectedmonth,selectedday));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis()-573948000000L);
                mDatePicker.setTitle(getResources().getString(R.string.dob));
                mDatePicker.show();

            }
        });

    }

    public void goback(View view) {
        finish();
    }

    private String getAge(int year, int month, int day){
        Log.i("Date ",year+":"+month+":"+day);


        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
    public void signup(View view) {

        try {
            str_category="";
            if (rb_catagroom.isChecked())
                str_category = "1";
            if (rb_cata_bride.isChecked())
                str_category = "0";
            str_et_name = et_name.getText().toString().trim();
            str_et_dob = et_dob.getText().toString().trim();
            str_et_age = et_age.getText().toString().trim();
            str_et_email = et_email.getText().toString().trim();
            str_et_mobile1 = et_mobile1.getText().toString().trim();
            str_et_mobile2 = et_mobile2.getText().toString().trim();
            str_isJanmpatrika = "";
            if (rb_janmapatrikaYes.isChecked())
                str_isJanmpatrika = "1";
            if (rb_janmapatrikaNo.isChecked())
                str_isJanmpatrika = "0";
            str_et_height = et_height.getText().toString().trim();
            str_et_color = et_color.getSelectedItem().toString().trim();
            str_et_gotra = et_gotra.getText().toString().trim();

            str_isChashma = "";
            if (rb_isChasmaYes.isChecked())
                str_isChashma = "1";
            if (rb_isChashmaNo.isChecked())
                str_isChashma = "0";

            str_et_qualification = et_qualification.getText().toString().trim();
            str_et_jobdetails = et_jobdetails.getText().toString().trim();
            str_et_joblocation = et_joblocation.getText().toString().trim();
            str_et_month_income = et_month_income.getSelectedItem().toString().trim();
            str_et_localadress = et_localadress.getText().toString().trim();
            str_et_premanent_address = et_premanent_address.getText().toString().trim();
            str_isWishToSeeJamnpatrika = "";
            if (rb_wishToSeeJPYes.isChecked())
                str_isWishToSeeJamnpatrika = "1";
            if (rb_wishToSeeJPNo.isChecked())
                str_isWishToSeeJamnpatrika = "0";

            str_isMangal = "";
            if (rb_isMangalYes.isChecked())
                str_isMangal = "1";
            if (rb_isMangalNo.isChecked())
                str_isMangal = "0";

            str_et_hobbies = et_hobbies.getText().toString().trim();
            str_et_parner_requirment = et_parner_requirment.getText().toString().trim();
            str_et_fathername = et_fathername.getText().toString().trim();
            str_et_father_ocupation = et_father_ocupation.getText().toString().trim();
            str_et_mothername = et_mothername.getText().toString().trim();
            str_et_totalbrother = et_totalbrother.getText().toString().trim();
            str_et_brotherstatus = et_brotherstatus.getText().toString().trim();
            str_et_totalsister = et_totalsister.getText().toString().trim();
            str_et_sisterstatus = et_sisterstatus.getText().toString().trim();
            str_et_mamachekul = et_mamachekul.getText().toString().trim();
            str_et_password = et_password.getText().toString().trim();
            str_et_confirmpassword = et_confirmpassword.getText().toString().trim();


            int flag = 0;
            if (str_category.isEmpty()) {
                flag++;
                Toast.makeText(context, "Please select Category.", Toast.LENGTH_SHORT).show();
            }
            if (str_et_name.isEmpty()) {
                flag++;
                et_name.setError("Enter Name");
                et_name.requestFocus();
            }
            if (str_et_dob.isEmpty()) {
                flag++;
                et_dob.setError("Choose DOB");
                et_dob.requestFocus();
            }
            if (str_et_age.isEmpty()) {
                flag++;
                et_age.setError("Enter Age");
                et_age.requestFocus();
            }
            if (str_et_email.isEmpty()) {
                flag++;
                et_email.setError("Enter Email");
                et_email.requestFocus();
            }
            if (str_et_mobile1.isEmpty()) {
                flag++;
                et_email.setError("Enter Email");
                et_email.requestFocus();
            }/*if(str_et_mobile2.isEmpty()){
                et_email.setError("Enter Email");
                et_email.requestFocus();
            }*/
           /* if (str_isJanmpatrika.isEmpty()) {
                flag++;
                Toast.makeText(context, "Choose Is Janmapatrika Available.", Toast.LENGTH_SHORT).show();
            }
            if (str_et_height.isEmpty()) {
                flag++;
                et_height.setError("Enter Height ");
                et_height.requestFocus();
            }
            if (str_et_color.isEmpty()) {
                flag++;
                Toast.makeText(context, "Please Choose your Color", Toast.LENGTH_SHORT).show();
                et_color.requestFocus();
            }
            if (str_et_gotra.isEmpty()) {
                flag++;
                et_gotra.setError("Enter Value");
                et_gotra.requestFocus();
            }
            if (str_isChashma.isEmpty()) {
                flag++;
                et_gotra.setError("Enter Value");
                et_gotra.requestFocus();
            }*/
            if (str_et_qualification.isEmpty()) {
                flag++;
                et_qualification.setError("Enter Value");
                et_qualification.requestFocus();

            }
            if (str_et_jobdetails.isEmpty()) {
                flag++;
                et_jobdetails.setError("Enter Value");
                et_jobdetails.requestFocus();
            }
           /* if (str_et_joblocation.isEmpty()) {
                flag++;
                et_joblocation.setError("Enter Value");
                et_joblocation.requestFocus();
            }
            if (str_et_month_income.isEmpty()) {
                flag++;
                Toast.makeText(context, "Please choose annual income.", Toast.LENGTH_SHORT).show();
                et_month_income.requestFocus();
            }
            if (str_et_localadress.isEmpty()) {
                flag++;
                et_localadress.setError("Enter Value");
                et_localadress.requestFocus();
            }*/
            if (str_et_premanent_address.isEmpty()) {
                flag++;
                et_premanent_address.setError("Enter Value");
                et_premanent_address.requestFocus();
            }
          /*  if (str_isWishToSeeJamnpatrika.isEmpty()) {
                Toast.makeText(context, "Please select is your are intrested to see Jamnpatrika.", Toast.LENGTH_SHORT).show();
                flag++;

            }
            if (str_isMangal.isEmpty()) {
                Toast.makeText(context, "Please select is You have Magal.", Toast.LENGTH_SHORT).show();
                flag++;
            }
            if (str_et_hobbies.isEmpty()) {
                flag++;
                et_hobbies.setError("Enter Value");
                et_hobbies.requestFocus();
            }
            if (str_et_parner_requirment.isEmpty()) {
                flag++;
                et_parner_requirment.setError("Enter Value");
                et_parner_requirment.requestFocus();
            }
            if (str_et_fathername.isEmpty()) {
                flag++;
                et_fathername.setError("Enter Value");
                et_fathername.requestFocus();
            }
            if (str_et_father_ocupation.isEmpty()) {
                flag++;
                et_father_ocupation.setError("Enter Value");
                et_father_ocupation.requestFocus();
            }
            if (str_et_mothername.isEmpty()) {
                flag++;
                et_mothername.setError("Enter Value");
                et_mothername.requestFocus();
            }
            if (str_et_totalbrother.isEmpty()) {
                flag++;
                et_totalbrother.setError("Enter Value");
                et_totalbrother.requestFocus();
            }
            if (str_et_brotherstatus.isEmpty()) {
                flag++;
                et_brotherstatus.setError("Enter Value");
                et_brotherstatus.requestFocus();
            }
            if (str_et_totalsister.isEmpty()) {
                flag++;
                et_totalsister.setError("Enter Value");
                et_totalsister.requestFocus();
            }
            if (str_et_sisterstatus.isEmpty()) {
                flag++;
                et_sisterstatus.setError("Enter Value");
                et_sisterstatus.requestFocus();
            }
            if (str_et_mamachekul.isEmpty()) {
                flag++;
                et_mamachekul.setError("Enter Value");
                et_mamachekul.requestFocus();
            }*/
            if (str_et_password.isEmpty()) {
                flag++;
                et_password.setError("Enter Value");
                et_password.requestFocus();
            }


            if (flag > 0) {
                Toast.makeText(context, "You have missing " + flag + " values to enter.", Toast.LENGTH_SHORT).show();

            } else {


                if (CheckConnection.checkInternet(SignUp.this)) {
                    signingup();
                } else {
                    //  Toast.makeText(Flash.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    Dialog dialog = new Dialog(SignUp.this);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    dialog.setContentView(R.layout.popup_retry);
                    Button btn_retry = dialog.findViewById(R.id.btn_retry);
                    btn_retry.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            //  checkValid();
                        }
                    });
                    dialog.show();

                }


            }


        } catch (Exception e) {
            Log.i("Error in Onclick ", "" + e.getMessage());
        }
    }


    private void signingup() {
       // Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();


            Call<String> call = RetrofitClient.getInstance().getMyApi().insertUser(
                    "0",
                    str_category,
                    str_et_name,
                    str_et_dob,
                    str_et_age,
                    str_et_email,
                    str_et_mobile1,
                    str_et_mobile2,
                    str_isJanmpatrika,
                    str_et_height,
                    str_et_color,
                    str_et_gotra,
                    str_isChashma,
                    str_et_qualification,
                    str_et_jobdetails,
                    str_et_joblocation,
                    str_et_month_income,
                    str_et_localadress,
                    str_et_premanent_address,
                    str_isWishToSeeJamnpatrika,
                    str_isMangal,
                    str_et_hobbies,
                    str_et_parner_requirment,
                    str_et_fathername,
                    str_et_father_ocupation,
                    str_et_mothername,
                    str_et_totalbrother,
                    str_et_brotherstatus,
                    str_et_totalsister,
                    str_et_sisterstatus,
                    str_et_mamachekul,
                    "",
                    "",
                    str_et_password);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        String saravMenuModels = response.body();
                        try {
                            //==============
                            if (saravMenuModels.contains("Mobile Number is already Exist")) {
                                Toast.makeText(SignUp.this, "User Already Registered.", Toast.LENGTH_SHORT).show();
                            } else {

                                JSONArray jsonArray = new JSONArray(saravMenuModels.trim());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("fullname");
                                    String mobile = jsonObject.getString("mobile1");
                                    String status = jsonObject.getString("userstatus");
                                    String userid = jsonObject.getString("id");
                                    String usercategory = jsonObject.getString("usercategory");
                                    String email = jsonObject.getString("email");

                                    Preferences.save(context, Preferences.USER_PROFILE_NAME, name);
                                    Preferences.save(context, Preferences.USER_ID, userid);
                                    Preferences.save(context, Preferences.USER_MOBILE, mobile);
                                    Preferences.save(context, Preferences.USERCATEGORY, usercategory);
                                    Preferences.save(context, Preferences.USERSTATUS, status);
                                    Preferences.save(context, Preferences.USER_EMAIL, email);

                                    Intent intent = new Intent(context, MasterScreen.class);
                                    startActivity(intent);

                                }
                            }

                            } catch(NullPointerException e){
                                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch(Exception e){
                                Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                      //=======



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
    public void marathi(View v) {
        try {
            setLocale("mr");
            startActivity(getIntent());
        } catch (Exception e) {

        }
    }

    public void english(View v) {
        try {
            setLocale("en");
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

}