package net.wvffle.android.pb.schedule.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver extends BroadcastReceiver {

    ConnectivityReceiverListener connectivityReceiverListener;

    public NetworkChangeReceiver(ConnectivityReceiverListener connectivityReceiverListener) {
        super();
        this.connectivityReceiverListener = connectivityReceiverListener;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected(context));
        }
    }

    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}

