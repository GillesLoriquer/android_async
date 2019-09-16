package com.openclassrooms.freezap.Utils;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class SyncJobService extends JobService implements MyAsyncTask.Listeners {

    // 1 - Declare an AsyncTask and the parameters of the job
    private MyAsyncTask jobTask;
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.e(this.getClass().getSimpleName(), "SyncJob is started.");
        // 2 - When job starts, we execute our AsyncTask
        this.jobParameters = jobParameters;
        this.jobTask = new MyAsyncTask(this);
        this.jobTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters params) {
        Log.e(this.getClass().getSimpleName(), "SyncJob is stopped !");
        // 4 - We cancel the AsyncTask when Job stopped, mainly to avoid Memory Leaks
        if (this.jobTask != null) this.jobTask.cancel(true);
        return false;
    }

    @Override
    public void onPreExecute() {  }

    @Override
    public void doInBackground() { }

    @Override
    public void onPostExecute(Long taskEnd) {
        // 3 - When background task ended, we set the job as terminated
        Log.e("TAG", "Task ended at : "+taskEnd);
        jobFinished(jobParameters, false);
    }
}
