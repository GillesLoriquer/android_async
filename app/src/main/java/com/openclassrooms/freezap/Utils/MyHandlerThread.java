package com.openclassrooms.freezap.Utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;

public class MyHandlerThread extends HandlerThread {

    private WeakReference<ProgressBar> progressBarWeakReference;

    // 1 - Constructor
    public MyHandlerThread(String name, ProgressBar progressBar) {
        super(name);
        progressBarWeakReference = new WeakReference<>(progressBar);
    }

    // 2 - Public method that will start handler
    public void startHandler() {

        // 2.1 - Checking if progressbar is accessible, and setting it visible
        if (progressBarWeakReference.get() != null)
            progressBarWeakReference.get().setVisibility(View.VISIBLE);

        // 2.2 - Checking if handlerThread is already alive, else we start it.
        if (!this.isAlive()) this.start();

        // 2.3 - Creating a new Handler and setting it the looper of handlerThread
        Handler handler = new Handler(this.getLooper());

        // 2.4 - Executing a new Runnable
        handler.post(new Runnable() {
            @Override
            public void run() {
                // 2.5 - Execute our long task during 7 seconds
                Utils.executeLongActionDuring7seconds();

                // 2.6 - Update UI after task finished (In Main Thread -> MainLooper)
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (progressBarWeakReference.get() != null)
                            progressBarWeakReference.get().setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}