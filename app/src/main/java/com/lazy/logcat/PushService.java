package com.lazy.logcat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.lazy.library.logging.Logcat;

public class PushService extends Service {
    public static final String TAG = "PushService_Event";

    public PushService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logcat.i(TAG, "PushService is created");
    }
}
