package tender.guru.suvidha.api_response;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.R;
import tender.guru.suvidha.adapter.NoValuesPresentAdapter;
import tender.guru.suvidha.adapter.TenderAdapter;
import tender.guru.suvidha.model.TenderModel;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.ResultOutput;
import tender.guru.suvidha.util.RetrofitClient;

public class MasterScreenAPI  {
    Context context;
    String result = "";
    ProgressDialog progressDialog;
    ResultOutput resultOutput;

    public MasterScreenAPI(Context context, ResultOutput resultOutput) {
        this.context = context;
        this.resultOutput = resultOutput;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait..");
    }

    public void getNotificationCount(String mobile, String userid) {
        String data = "Default";
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<String> call = RetrofitClient.getInstance().getMyApi().getNotificatioCount(
                    mobile, userid);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        String saravMenuModels = response.body();
                        try {
                            /*     textView.setText(""+saravMenuModels);*/
                            //  Toast.makeText(context, ""+saravMenuModels, Toast.LENGTH_SHORT).show();
                            result = saravMenuModels;
                            resultOutput.onResult(result);

                        } catch (NullPointerException e) {
                            result = "" + e.getMessage();
                            resultOutput.onResult("");
                            //Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            result = "" + e.getMessage();
                            resultOutput.onResult("");
                            // Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        result = "NO Data Found";
                        resultOutput.onResult("");
                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    result = "On Failue";
                    resultOutput.onResult("");
                    Log.e("Error is", t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.i("Error is ", e.getMessage());
            result = "Final Error";
            resultOutput.onResult("");
        }

    }

    public void getTender(String mobile, String userid, String deviceid, String srcString) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

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
                            resultOutput.onListResponce(videos);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
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
