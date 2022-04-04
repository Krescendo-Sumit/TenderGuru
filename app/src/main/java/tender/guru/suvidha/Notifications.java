package tender.guru.suvidha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tender.guru.suvidha.adapter.ChatAdapter;
import tender.guru.suvidha.adapter.NotificationAdapter;
import tender.guru.suvidha.model.ChatModel;
import tender.guru.suvidha.util.Preferences;
import tender.guru.suvidha.util.RetrofitClient;

public class Notifications extends AppCompatActivity {
    Context context;
    ProgressDialog progressDialog;

    NotificationAdapter adapter1;
    RecyclerView rc_message;
    LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        context = Notifications.this;
        setTitle("Notifications");
        progressDialog = new ProgressDialog(context);
        rc_message = (RecyclerView) findViewById(R.id.rc_message);
        mManager = new LinearLayoutManager(context);
        progressDialog = new ProgressDialog(context);
        //   mManager.setReverseLayout(true);
        rc_message.setLayoutManager(mManager);
        getChat();
    }
    private void getChat() {
        try {
            getChating();
        } catch (Exception e) {

        }
    }
    private void getChating() {
        try {
           if (!progressDialog.isShowing())
                progressDialog.show();

            String sender = Preferences.get(context, Preferences.USER_ID).toString().trim();

            Call<List<ChatModel>> call = RetrofitClient.getInstance().getMyApi().getNotifications(sender);
            call.enqueue(new Callback<List<ChatModel>>() {
                @Override
                public void onResponse(Call<List<ChatModel>> call, Response<List<ChatModel>> response) {
                    if (response != null) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                        List<ChatModel> videos = response.body();
                        try {
                            adapter1 = new NotificationAdapter((ArrayList) videos, context);
                            rc_message.setAdapter(adapter1);
                            rc_message.smoothScrollToPosition(videos.size());
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Record will be available soon.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Record will be available soon.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "No data available yet.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ChatModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

}