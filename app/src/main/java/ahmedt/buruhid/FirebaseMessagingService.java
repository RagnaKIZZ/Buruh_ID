package ahmedt.buruhid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import ahmedt.buruhid.notification.NotificationActivity;
import ahmedt.buruhid.ui.home.HomeFragment;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    public static final String INFO_UPDATE = "info_update";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Context c = this;
        showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        Intent intent = new Intent(INFO_UPDATE);
        intent.putExtra(INFO_UPDATE, "1");
        LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
    }

    private void showNotification(String title, String message){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";
        Random rand = new Random();
        final int notification_ID = rand.nextInt();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("My Channel");
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            manager.createNotificationChannel(notificationChannel);
        }

        Intent i = new Intent(this, NotificationActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent intent = PendingIntent.getActivity(this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent intent = TaskStackBuilder.create(this).addNextIntentWithParentStack(i).getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(intent);

        manager.notify(notification_ID,builder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("Service", "onNewToken: "+s);
    }
}
