package tender.guru.suvidha.api_response;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.model.PriceModel;

import tender.guru.suvidha.util.RetrofitClient;

public class PricingAPI {



    Context context;
    List<PriceModel> result ;
    ProgressDialog progressDialog;
    ResultOutput resultOutput;

    public PricingAPI(Context context, ResultOutput resultOutput) {
        this.context = context;
        this.resultOutput = resultOutput;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait..");
    }
    public interface ResultOutput{
        public void onResultPrizing(List<PriceModel> priceModels);
    }

    public void getPrizing(String mobile, String userid) {
        String data = "Default";
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<PriceModel>> call = RetrofitClient.getInstance().getMyApi().getPrizingDetails(
                    mobile, userid);
            call.enqueue(new Callback<List<PriceModel>>() {
                @Override
                public void onResponse(Call<List<PriceModel>> call, Response<List<PriceModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<PriceModel> saravMenuModels = response.body();
                        try {
                            /*     textView.setText(""+saravMenuModels);*/
                            //  Toast.makeText(context, ""+saravMenuModels, Toast.LENGTH_SHORT).show();
                            result = saravMenuModels;
                            resultOutput.onResultPrizing(result);

                        } catch (NullPointerException e) {

                            //Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {

                            // Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<PriceModel>> call, Throwable t) {
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
