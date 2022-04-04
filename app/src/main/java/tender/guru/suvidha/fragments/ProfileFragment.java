package tender.guru.suvidha.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.CheckConnection;
import tender.guru.suvidha.CourseList;
import tender.guru.suvidha.MorePhotoActivity;
import tender.guru.suvidha.R;
import tender.guru.suvidha.Signin;
import tender.guru.suvidha.WebViewFileUploadTest;
import tender.guru.suvidha.model.UserModel;
import tender.guru.suvidha.util.Constants;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context context;
    ProgressDialog progressDialog;
    TextView txt_name, txt_mobile, txt_batch, txt_email, txt_dhyaasid, txt_address;
    TextView txt_logout;
    RelativeLayout rl;

    WebView dp;
    int onResumecnt=0;
    long pressedTime;
    View baseView;

    EditText
            et_name,
            et_dob,
            et_age,
            et_email,
            et_mobile1,
            et_mobile2,
            et_height,
            et_color,
            et_gotra,
            et_qualification,
            et_jobdetails,
            et_joblocation,
            et_month_income,
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
            et_mamachekul;
    Button btn_update;
    RadioButton rb_cata_bride, rb_catagroom;
    RadioButton rb_janmapatrikaYes, rb_janmapatrikaNo;
    RadioButton rb_isChasmaYes, rb_isChashmaNo;
    RadioButton rb_wishToSeeJPYes, rb_wishToSeeJPNo;
    RadioButton rb_isMangalYes, rb_isMangalNo;
    ImageView img_dp;
    TextView txt_add_more_photo;
    LinearLayout ll1,ll2;

    AlertDialog.Builder builder;

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



    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        baseView= inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();
        progressDialog = new ProgressDialog(context);
        txt_name = baseView.findViewById(R.id.txt_name);
        txt_logout = baseView.findViewById(R.id.txt_logout);
        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Do you want logout?") .setTitle("Permission");
                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to Signout this application ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Preferences.save(getContext(),Preferences.USER_ID,"");
                                Preferences.save(getContext(),Preferences.USER_MOBILE,"");
                                Preferences.save(getContext(),Preferences.USER_PROFILE_NAME,"");
                                getActivity().finish();
                                Intent intent=new Intent(getActivity(), Signin.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Sign out");
                alert.show();
            }
        });

        txt_mobile = baseView.findViewById(R.id.txt_mobile);
        txt_batch = baseView.findViewById(R.id.txt_batch);
        txt_email = baseView.findViewById(R.id.txt_email);
        txt_address = baseView.findViewById(R.id.txt_address);
        txt_dhyaasid = baseView.findViewById(R.id.txt_dhyassid);
        rl = baseView.findViewById(R.id.rl);
        ll1 = baseView.findViewById(R.id.ll1);
        ll2 = baseView.findViewById(R.id.ll2);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Uplaod Image", Toast.LENGTH_SHORT).show();

                try{


                    String ff= Preferences.get(context, Preferences.USER_MOBILE);
                    String url= Constants.BASE_URL +"dp/uploadphoto.php?fname="+ff;
                    Preferences.save(context,Preferences.PHOTOURL,url);

                    Intent intent=new Intent(context, WebViewFileUploadTest.class);
                    startActivity(intent);

                }catch (Exception e)
                {
                    Toast.makeText(context, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CourseList.class);
                intent.putExtra("type","1");
                startActivity(intent);
                Toast.makeText(context, "Comming Interest", Toast.LENGTH_SHORT).show();
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CourseList.class);
                intent.putExtra("type","0");
                startActivity(intent);
                Toast.makeText(context, "Sent Interest", Toast.LENGTH_SHORT).show();
            }
        });



        dp =  baseView.findViewById(R.id.dp);
        dp.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        dp.getSettings().setAppCacheEnabled(false);
        dp.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);






        et_name = baseView.findViewById(R.id.et_name);
        et_email = baseView.findViewById(R.id.et_email);
        et_dob = baseView.findViewById(R.id.et_dob);
        et_age = baseView.findViewById(R.id.et_age);
        et_mobile1 = baseView.findViewById(R.id.et_mobile1);
        et_mobile2 = baseView.findViewById(R.id.et_mobile2);
        et_height = baseView.findViewById(R.id.et_height);
        et_color = baseView.findViewById(R.id.et_color);
        et_gotra = baseView.findViewById(R.id.et_gotra);
        et_qualification = baseView.findViewById(R.id.et_qualification);
        et_jobdetails = baseView.findViewById(R.id.et_jobdetails);
        et_joblocation = baseView.findViewById(R.id.et_joblocation);
        et_month_income = baseView.findViewById(R.id.et_month_income);
        et_localadress = baseView.findViewById(R.id.et_localadress);
        et_premanent_address = baseView.findViewById(R.id.et_premanent_address);
        et_hobbies = baseView.findViewById(R.id.et_hobbies);
        et_parner_requirment = baseView.findViewById(R.id.et_parner_requirment);
        et_fathername = baseView.findViewById(R.id.et_fathername);
        et_father_ocupation = baseView.findViewById(R.id.et_father_ocupation);
        et_mothername = baseView.findViewById(R.id.et_mothername);
        et_totalbrother = baseView.findViewById(R.id.et_totalbrother);
        et_brotherstatus = baseView.findViewById(R.id.et_brotherstatus);
        et_totalsister = baseView.findViewById(R.id.et_totalsister);
        et_sisterstatus = baseView.findViewById(R.id.et_sisterstatus);
        et_mamachekul = baseView.findViewById(R.id.et_mamachekul);
        btn_update = baseView.findViewById(R.id.btn_update);

        rb_cata_bride = baseView.findViewById(R.id.rb_bride);
        rb_catagroom = baseView.findViewById(R.id.rb_groom);

        rb_janmapatrikaYes = baseView.findViewById(R.id.rb_seetojanmpatrikayes);
        rb_janmapatrikaNo = baseView.findViewById(R.id.rb_seetojanmpatrikano);

        rb_isChasmaYes = baseView.findViewById(R.id.rb_chamayes);
        rb_isChashmaNo = baseView.findViewById(R.id.rb_chamano);

        rb_wishToSeeJPYes = baseView.findViewById(R.id.rb_seetojanmpatrikayes);
        rb_wishToSeeJPNo = baseView.findViewById(R.id.rb_seetojanmpatrikano);

        rb_isMangalYes = baseView.findViewById(R.id.rb_ismangalyes);
        rb_isMangalNo = baseView.findViewById(R.id.rb_ismangalno);
        txt_add_more_photo = baseView.findViewById(R.id.txt_add_more_photos);

        txt_add_more_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MorePhotoActivity.class);
                startActivity(intent);

            }
        });

        img_dp=baseView.findViewById(R.id.dp_iv);
        img_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{


                    String ff= Preferences.get(context, Preferences.USER_MOBILE);
                    String url= Constants.BASE_URL +"dp/uploadphoto.php?fname="+ff;
                    Preferences.save(context,Preferences.PHOTOURL,url);

                    Intent intent=new Intent(context, WebViewFileUploadTest.class);
                    startActivity(intent);

                }catch (Exception e)
                {
                    Toast.makeText(context, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

dp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try{
            String ff= Preferences.get(context, Preferences.USER_MOBILE);
            String url= Constants.BASE_URL +"dp/uploadphoto.php?fname="+ff;
            Preferences.save(context,Preferences.PHOTOURL,url);
            Intent intent=new Intent(context, WebViewFileUploadTest.class);
            startActivity(intent);

        }catch (Exception e)
        {
            Toast.makeText(context, "Error is "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
});






           btn_update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   updateDetails();
               }
           });



        checkValid();


        return baseView;
    }

    private void updateDetails() {

        signup();

    }

    private void checkValid() {
        try{

            if (CheckConnection.checkInternet(context))
                getUserProfile();
            else {
                //  Toast.makeText(Flash.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                Dialog dialog=new Dialog(context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.popup_retry);
                Button btn_retry=dialog.findViewById(R.id.btn_retry);
                btn_retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        checkValid();
                    }
                });
                dialog.show();

            }

        }catch (Exception e)
        {

        }
    }
    public void getUserProfile() {
        try {
            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String deviceid = "111";

            String userid = Preferences.get(context, Preferences.USER_ID);
            ;
            Call<List<UserModel>> call = RetrofitClient.getInstance().getMyApi().getUserDetails(mobile, userid, deviceid);
            call.enqueue(new Callback<List<UserModel>>() {
                @Override
                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();

                    if(response.body()!=null) {

                        List<UserModel> MyProfiles = response.body();
                        for (UserModel v : MyProfiles) {
                            Log.i("Dp Path", Constants.BASE_URL + "dp/" + v.getPhotopath());
                            String url = Constants.BASE_URL + "dp/" + v.getPhotopath();
                            String erurl = Constants.BASE_URL + "dp/noimg.jpg";

                            try {
                                String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
                                data = data + "<body style=\"background-color:black;\"><center>" +
                                        "<img width=\"80%\" height='100%' src=\"" + url + "\" " +
                                        "onerror=\"this.onerror=null; this.src='" + erurl + "'\"/>" +
                                        "" +
                                        "</center></body></html>";
                                dp.getSettings().setJavaScriptEnabled(true);
                                dp.loadData(data, "text/html", "UTF-8");
                            } catch (Exception e) {
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            txt_dhyaasid.setText(" TG-" + v.getId());
                            txt_name.setText(" " + v.getFullname());
                            txt_email.setText(Html.fromHtml(" <b>Email Id </b><br>" + v.getEmail()));
                            txt_mobile.setText(Html.fromHtml(" <b>Mobile Number </b><br>" + v.getMobile1() + "," + v.getMobile2()));
                            txt_address.setText(Html.fromHtml(" <b>Address </b><br>" + v.getPermanent_Address()));
//                            if (v.getUsercategory().equals("1"))
//                                txt_batch.setText(" Category :Groom");
//                            else
//                                txt_batch.setText(" Category :Bride");

                            et_name.setText(v.getFullname());
                            et_dob.setText(v.getDob());
                            et_age.setText(v.getAge());
                            et_email.setText(v.getEmail());
                            et_mobile1.setText(v.getMobile1());
                            et_mobile2.setText(v.getMobile2());
                            et_height.setText(v.getHeight());
                            et_color.setText(v.getColor());
                            et_gotra.setText(v.getGotra());
                            et_qualification.setText(v.getQualification());
                            et_jobdetails.setText(v.getJobdetails());
                            et_joblocation.setText(v.getJoblocation());
                            et_month_income.setText(v.getMonthlyIncome());
                            et_localadress.setText(v.getLocal_address());
                            et_premanent_address.setText(v.getPermanent_Address());
                            et_hobbies.setText(v.getHobbies());
                            et_parner_requirment.setText(v.getPartnerRequirment());
                            et_fathername.setText(v.getFathername());
                            et_father_ocupation.setText(v.getFatherocupation());
                            et_mothername.setText(v.getMothersname());
                            et_totalbrother.setText(v.getTotalbrothers());
                            et_brotherstatus.setText(v.getBothersmaritialStatas());
                            et_totalsister.setText(v.getTotalsister());
                            et_sisterstatus.setText(v.getSistermaritialStatus());
                            et_mamachekul.setText(v.getMamacheKul());


                            if (v.getUsercategory().trim().equals("1"))
                                rb_catagroom.setChecked(true);
                            else if (v.getUsercategory().trim().equals("0"))
                                rb_cata_bride.setChecked(true);


                        }

                        try {
                            // getMenuList();

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<UserModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (
                Exception e) {

        }

    }
    public void signup() {

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

            str_et_qualification = et_qualification.getText().toString().trim();
            str_et_jobdetails = et_jobdetails.getText().toString().trim();

            str_et_premanent_address = et_premanent_address.getText().toString().trim();


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
            }
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




            if (flag > 0) {
                Toast.makeText(context, "You have missing " + flag + " values to enter.", Toast.LENGTH_SHORT).show();

            } else {


                if (CheckConnection.checkInternet(context)) {
                    signingup();
                } else {
                    //  Toast.makeText(Flash.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    Dialog dialog = new Dialog(context);
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
      //  Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
String userid=Preferences.get(context,Preferences.USER_ID);

            Call<String> call = RetrofitClient.getInstance().getMyApi().updateUser(
                    userid,
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

                            Toast.makeText(context, ""+saravMenuModels, Toast.LENGTH_SHORT).show();

                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
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
}