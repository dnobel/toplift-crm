package org.nobel.topliftcrm.data;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

public abstract class LoadDataTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private final Context context;

    public LoadDataTask(Context context) {
        this.context = context;
    }

    @Override
    protected Result doInBackground(Params... params) {
        Result result = null;
        try {
            result = doLoad(params);
        } catch(Exception ex) {
            cancel(true);
        }
        return result;
    }

    protected abstract Result doLoad(Params... params);

    protected abstract void hideProgessbar();

    @Override
    protected void onCancelled() {
        hideProgessbar();
        Toast.makeText(context, "Loading data failed. Please try again.", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(Result result) {
        hideProgessbar();
    }

    @Override
    protected void onPreExecute() {
        showProgessbar();
        if (!checkNetworkState()) {
            cancel(true);
        }
    }

    protected abstract void showProgessbar();

    private final boolean checkNetworkState() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        NetworkInfo[] allNetworkInfo = manager.getAllNetworkInfo();

        if (allNetworkInfo != null) {
            for (NetworkInfo networkInfo : allNetworkInfo) {
                if (networkInfo.isAvailable() && networkInfo.isConnected()) {
                    return true;
                }
            }
        }

        return false;
    }
}
