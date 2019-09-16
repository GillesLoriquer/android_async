package com.openclassrooms.freezap.Utils;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

public class MyAsyncTask extends AsyncTask<Void, Void, Long> {

    // 1 - Implement listeners methods (Callback)
    public interface Listeners {
        void onPreExecute();
        void doInBackground();
        void onPostExecute(Long success);
    }

    // 2 - Declare callback
    private final WeakReference<Listeners> callback;

    // 3 - Constructor
    public MyAsyncTask(Listeners callback){
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.callback.get().onPreExecute(); // 4 - Call the related callback method
        Log.e("TAG", "AsyncTask is started.");
    }

    @Override
    protected void onPostExecute(Long success) {
        super.onPostExecute(success);
        this.callback.get().onPostExecute(success); // 4 - Call the related callback method
        Log.e("TAG", "AsyncTask is finished.");
    }

    @Override
    protected Long doInBackground(Void... voids) {
        this.callback.get().doInBackground(); // 4 - Call the related callback method
        Log.e("TAG", "AsyncTask doing some big work...");
        return Utils.executeLongActionDuring7seconds(); // 5 - Execute our task
    }
}
