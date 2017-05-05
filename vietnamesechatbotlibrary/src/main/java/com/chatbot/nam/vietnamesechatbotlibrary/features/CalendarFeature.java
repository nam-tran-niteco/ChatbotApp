package com.example.namtran.myapplication.features;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.CalendarContract.*;
import android.util.Log;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Tran on 25-Feb-17.
 */

public class CalendarFeature extends Feature {

    private Context _context;

    private static final String MY_ACCOUNT_NAME = "namth";

    public CalendarFeature(Context context) {
        _context = context;
    }

    @Override
    public String doAction() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return "Hôm nay là ngày " + day + "/" + month + "/" + year;
    }

    public void getCalendar() {
        String[] projection =
                new String[]{
                        Calendars._ID,
                        Calendars.NAME,
                        Calendars.ACCOUNT_NAME,
                        Calendars.ACCOUNT_TYPE};
        Cursor calCursor =
                _context.getContentResolver().
                        query(Calendars.CONTENT_URI,
                                projection,
                                Calendars.VISIBLE + " = 1",
                                null,
                                Calendars._ID + " ASC");
        if (calCursor.moveToFirst()) {
            do {
                long id = calCursor.getLong(0);
                String displayName = calCursor.getString(1);
//                if(displayName != null) Log.d("chatbot", displayName);
            } while (calCursor.moveToNext());
        }

//        createLocalCalendar();
        addEvent();
    }

    private void createLocalCalendar () {
        ContentValues values = new ContentValues();
        values.put(
                Calendars.ACCOUNT_NAME,
                MY_ACCOUNT_NAME);
        values.put(
                Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        values.put(
                Calendars.NAME,
                "GrokkingAndroid Calendar");
        values.put(
                Calendars.CALENDAR_DISPLAY_NAME,
                "GrokkingAndroid Calendar");
        values.put(
                Calendars.CALENDAR_COLOR,
                0xffff0000);
        values.put(
                Calendars.CALENDAR_ACCESS_LEVEL,
                Calendars.CAL_ACCESS_OWNER);
        values.put(
                Calendars.OWNER_ACCOUNT,
                "namth27494@gmail.com");
        values.put(
                Calendars.CALENDAR_TIME_ZONE,
                "Europe/Berlin");
        values.put(
                Calendars.SYNC_EVENTS,
                1);
        Uri.Builder builder =
                CalendarContract.Calendars.CONTENT_URI.buildUpon();
        builder.appendQueryParameter(
                Calendars.ACCOUNT_NAME,
                "com.grokkingandroid");
        builder.appendQueryParameter(
                Calendars.ACCOUNT_TYPE,
                CalendarContract.ACCOUNT_TYPE_LOCAL);
        builder.appendQueryParameter(
                CalendarContract.CALLER_IS_SYNCADAPTER,
                "true");
        Uri uri =
                _context.getContentResolver().insert(builder.build(), values);
        if (uri != null) Log.d("chatbot", uri.getPath());
    }

    private long getCalendarId() {
        String[] projection = new String[]{Calendars._ID};
        String selection =
                Calendars.ACCOUNT_NAME +
                        " = ? AND " +
                        Calendars.ACCOUNT_TYPE +
                        " = ? ";
        // use the same values as above:
        String[] selArgs =
                new String[]{
                        MY_ACCOUNT_NAME,
                        CalendarContract.ACCOUNT_TYPE_LOCAL};
        Cursor cursor =
                _context.getContentResolver().
                        query(
                                Calendars.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1;
    }

    private void addEvent() {
        long calId = getCalendarId();
        if (calId == -1) {
            // no calendar account; react meaningfully
            return;
        }
        Calendar cal = new GregorianCalendar(2017, 4, 5);
        cal.setTimeZone(TimeZone.getTimeZone("UTC+7"));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        ContentValues values = new ContentValues();
        values.put(Events.DTSTART, start);
        values.put(Events.DTEND, start);
        values.put(Events.RRULE,
                "FREQ=DAILY;COUNT=20;BYDAY=MO,TU,WE,TH,FR;WKST=MO");
        values.put(Events.TITLE, "Some title");
        values.put(Events.EVENT_LOCATION, "Münster");
        values.put(Events.CALENDAR_ID, calId);
        values.put(Events.EVENT_TIMEZONE, "Europe/Berlin");
        values.put(Events.DESCRIPTION,
                "The agenda or some description of the event");
// reasonable defaults exist:
        values.put(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
        values.put(Events.SELF_ATTENDEE_STATUS,
                Events.STATUS_CONFIRMED);
        values.put(Events.ALL_DAY, 1);
        values.put(Events.ORGANIZER, "nameth27494@gmail.com");
        values.put(Events.GUESTS_CAN_INVITE_OTHERS, 1);
        values.put(Events.GUESTS_CAN_MODIFY, 1);
        values.put(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
        Uri uri =
                _context.getContentResolver().
                        insert(Events.CONTENT_URI, values);
        long eventId = new Long(uri.getLastPathSegment());
        Log.d("chatbot", eventId + "");
    }
}
