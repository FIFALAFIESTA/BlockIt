package com.example.akylbektokonuulu.blockit;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.akylbektokonuulu.blockit.history.history;
import com.example.akylbektokonuulu.blockit.history.history_entry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class NLService extends NotificationListenerService {
    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String foreground = "";
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND ) {
                foreground = appProcess.processName;
                break;
            }
        }
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        ArrayList<String> background = new ArrayList<>();
        for (ApplicationInfo packageInfo : packages) {
            if (!isSTOPPED(packageInfo) && !isSYSTEM(packageInfo)) {
                background.add(getApplicationLabel(this, packageInfo.packageName));                 // + "\n" + packageInfo.packageName  + "\n-----------------------\n";
            }
        }

        Intent i = new  Intent("NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event_list","onNotificationPosted :" + sbn.getPackageName() + "\n");
        i.putExtra("notification_event",sbn.getPackageName() + " " +
                "time" + " " +
                "keyword" + " " +
                sbn.getNotification().category + " " +
                "1" + " " +
                "null" + " " +
                "accept");
        i.putExtra("key", sbn.getKey());

        sendBroadcast(i);
        /* BLOCK FACEBOOK */
        /*if (sbn.getPackageName().equals("com.facebook.orca"))
            cancelNotification(sbn.getKey());*/
        /* POSTPONE WHATSAPP */
        /*if (sbn.getPackageName().equals("com.whatsapp") && sbn.getNotification().tickerText.length() > 0) {
            cancelNotification(sbn.getKey());

            final NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            final Notification noti = sbn.getNotification();

            final NotificationCompat.Builder ncomp = new NotificationCompat.Builder(this);
            ncomp.setContentTitle("BlockIt: WhatsApp");
            ncomp.setContentText(noti.tickerText);
            ncomp.setTicker(noti.tickerText);
            ncomp.setSmallIcon(R.mipmap.ic_launcher);
            ncomp.setAutoCancel(true);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            ncomp.setSound(alarmSound);
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nManager.notify((int)System.currentTimeMillis(),ncomp.build());

        }*/

        Log.v(TAG, foreground);
        //Log.i(TAG,"**********  onNotificationPosted");
        //Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());



    }

    private static boolean isSTOPPED(ApplicationInfo pkgInfo) {

        return ((pkgInfo.flags & ApplicationInfo.FLAG_STOPPED) != 0);
    }

    private static boolean isSYSTEM(ApplicationInfo pkgInfo) {

        return ((pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public static String getApplicationLabel(Context context, String packageName) {

        PackageManager        packageManager = context.getPackageManager();
        List<ApplicationInfo> packages       = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String                label          = null;

        for (int i = 0; i < packages.size(); i++) {

            ApplicationInfo temp = packages.get(i);

            if (temp.packageName.equals(packageName))
                label = packageManager.getApplicationLabel(temp).toString();
        }

        return label;
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        //Log.i(TAG,"********** onNOtificationRemoved");
        //Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());

        Intent i = new  Intent("NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event_list","onNotificationRemoved :" + sbn.getPackageName() + "\n");
        i.putExtra("notification_event",sbn.getPackageName() + " " +
                "time" + " " +
                "keyword" + " " +
                sbn.getNotification().category + " " +
                "1" + " " +
                "null" + " " +
                "decline");

        sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("clearall")){
                NLService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event_list","=====================");
                sendBroadcast(i1);
                int i=1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event_list",i +" " + sbn.getPackageName() + "\n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event_list","===== Notification List ====");
                sendBroadcast(i3);

            }
            else if (intent.getStringExtra("command").equals("decline")) {
                NLService.this.cancelNotification(intent.getStringExtra("key"));
            }
        }
    }

}
