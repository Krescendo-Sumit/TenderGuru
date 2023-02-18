package tender.guru.suvidha;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import tender.guru.suvidha.util.Preferences;


/**
 * Created by Akash Namdev.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    // private static final Intent REQUEST_ACCEPT =;

    Bitmap bitmap;
    Context mcontext;
    LocalBroadcastManager broadcaster;
    public static int NOTIFICATION_ID_COMMON = 5;
    String msg;


    public static String NOTIFICATIONCHANNELID_FCM = "channel_id_fcm";
    public static String NOTIFICATION_FCM = "notify_fcm";
    //private static final String TAG="MyFirebaseMsgService";

    @Override
    public void onCreate() {
        super.onCreate();


    }



    /**
     * Called when message is received.
     */
    // [START receive_message]
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d("NEW_TOKEN", s);
        Preferences.save(this, Preferences.KEY_FCM_TOKEN, s);
        Preferences.saveBool(this, Preferences.KEY_FCM_TOKEN_SYNCED, false);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mcontext = this;
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        // Log.d(TAG, "From: " + remoteMessage);


        // Check if message contains a data payload.
        if (remoteMessage.getData() != null && remoteMessage.getData().size() > 0) {
            System.out.println("remoteMessage. Data():" + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            msg = data.get("body");
        } else if (remoteMessage.getNotification() != null) {
            System.out.println("remoteMessage.Display():" + remoteMessage.getData());
            msg = remoteMessage.getNotification().getBody();

        }




        String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
//        Log.d("currentDatecurrentTime", currentDate + "Time" + currentTime);
        Log.d(TAG, "onMessageReceived: " + msg);
        if (msg != null) {

            sendNotification(msg, bitmap);

//            notificationModel.setTime(currentTime);
            Date date1=null;
            try {
                 date1=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(currentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }





        }


    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, Bitmap image) {
        int count = Preferences.getInt(mcontext, "HELLO1");
        count = count + 1;
        Preferences.saveInt(mcontext, "BADGECOUNT", count);

        broadcaster = LocalBroadcastManager.getInstance(getBaseContext());

        //Intent intent1 = new Intent(this, MainActivity.class);
        Intent intent1 = new Intent("local_broadcast");

        intent1.putExtra("Key", count);
        broadcaster.sendBroadcast(intent1);

        Intent intent = new Intent(this, Flash.class);
        //intent.putExtra("isFromNotification",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri default_msg_tone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bitmap = BitmapFactory.decodeResource(mcontext.getResources(), R.drawable.god);
        int color = mcontext.getResources().getColor(R.color.design_default_color_background);

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            importance = NotificationManager.IMPORTANCE_HIGH;
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATIONCHANNELID_FCM, NOTIFICATION_FCM, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mcontext, NOTIFICATIONCHANNELID_FCM)
                .setColor(color)
                .setSmallIcon(R.drawable.god)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentTitle(getResources().getString(R.string.app_name))
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setContentText(msg)

                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{500, 500})
                .setSound(default_msg_tone);


        // NotificationManager notificationmanager = (NotificationManager) mcontext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIFICATION_ID_COMMON++, mBuilder.build());

    }


    private int getNotificationIcon() {
        // boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return R.drawable.god;
        //return useWhiteIcon ? R.mipmap.appp_icon : R.mipmap.appp_icon;
    }

    /*
     *To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }
}
