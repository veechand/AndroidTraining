package com.veechand.broadcastexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"Message recieved, see u r inbox",Toast.LENGTH_LONG).show();
        //Showing Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentText("Arey Blah Blah");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("My title");

        Intent intent1 = new Intent(Intent.ACTION_CALL);
        intent1.setData(Uri.parse("tel://89898"));

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,intent1,PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(100,builder.build());


    }
}
