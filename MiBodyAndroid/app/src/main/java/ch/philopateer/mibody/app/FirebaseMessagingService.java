package ch.philopateer.mibody.app;

/**
 * Created by Nakeeb on 7/19/2016.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.Landing;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getData().get("promotion"));
    }

    private void showNotification(String message) {

        Intent i = new Intent(this, Landing.class);
        i.putExtra(message, "message");
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("MiBody Promotions")
                .setContentText(message)
                .setSmallIcon(R.drawable.mibody_logo)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0, builder.build());
    }


}