package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;

public class Suggestions extends AppCompatActivity {
     EditText et_message;
     Button btn_send;
     String message="";
    Context context;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        context = Suggestions.this;
        btn_send=findViewById(R.id.btn_send);
        et_message=findViewById(R.id.et_message);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }
    public void sendMessage()
    {
        try{
            message=et_message.getText().toString().trim();
             if(message.trim().equals("") )
             {
                 et_message.setError("Please enter the message.");
             }
             else if(message.length()<5)
             {
                 et_message.setError("Message must be 5 characters long.");
             }
             else
             {
                 sendChat();
             }


        }catch(Exception e)
        {

        }
    }

    private void sendChat() {
        //  Toast.makeText(context, "Calling", Toast.LENGTH_SHORT).show();
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
            String userid = Preferences.get(context, Preferences.USER_ID).toString().trim();

            String message = et_message.getText().toString().trim();

            Call<String> call = RetrofitClient.getInstance().getMyApi().addSuggestion(
                    userid, message);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        String saravMenuModels = response.body();
                        try {
                            et_message.setText("");
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