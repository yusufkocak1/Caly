package com.yube.caly;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(String.valueOf(remoteMessage.getNotification().getBody())); // Mesaj içeriği alınıp bildirim gösteren metod çağırılıyor


    }

    private void showNotification(String message) {

        Intent i = new Intent(this,MainActivity.class); // Bildirime basıldığında hangi aktiviteye gidilecekse
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra("mesaj",message);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setAutoCancel(true) // Kullanıcı bildirime girdiğinde otomatik olarak silinsin. False derseniz bildirim kalıcı olur.
                .setContentTitle("Mesajınız var") // Bildirim başlığı
                .setContentText(message) // Bildirim mesajı
                .setSmallIcon(R.mipmap.logo) // Bildirim simgesi
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }
}