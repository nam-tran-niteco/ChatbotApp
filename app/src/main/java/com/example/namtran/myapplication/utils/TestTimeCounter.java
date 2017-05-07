package com.example.namtran.myapplication.utils;

/**
 * Created by Tran on 07-May-17.
 */

public class TestTimeCounter {

    private static long _timeLong = 0;

    public static void startTimeCounter() {
        _timeLong = System.currentTimeMillis();
    }

    public static long endTimeCounter() {
        return System.currentTimeMillis() - _timeLong;
    }

}
