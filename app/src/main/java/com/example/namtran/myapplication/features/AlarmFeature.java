package com.example.namtran.myapplication.features;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Tran on 03-Apr-17.
 */

public class AlarmFeature extends Feature {

    private Context _context;

    private HashMap<String, String> params;

    public AlarmFeature(Context context, HashMap<String, String> params) {
        _context = context;
        this.params = params;
    }

    @Override
    public String doAction() {
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, 5);
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, 30);
        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, false);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(alarmIntent);
        return "Alarm Set";
    }



}
