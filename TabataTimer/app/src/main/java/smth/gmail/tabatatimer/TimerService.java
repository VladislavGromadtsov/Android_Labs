package smth.gmail.tabatatimer;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class TimerService extends Service {
    private CountDownTimer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final long[] timeRemaining = {intent.getIntExtra(TrainingActivity.EXTRA_TIMEVALUE, 0)*1000};
        final Intent localIntent = new Intent();
        localIntent.setAction("Counter");
        timer = new CountDownTimer(timeRemaining[0], 1000) {
            @Override
            public void onTick(long l) {
                timeRemaining[0] = l;
                localIntent.putExtra("TimeRemaining", timeRemaining[0]/1000);
                sendBroadcast(localIntent);
            }

            @Override
            public void onFinish() {
                localIntent.putExtra("TimerFinished", 0);
                sendBroadcast(localIntent);
            }
        }.start();

        IntentFilter pausedIntentFilter = new IntentFilter();
        pausedIntentFilter.addAction("TimerPaused");
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                timer.cancel();
            }
        };

        registerReceiver(broadcastReceiver, pausedIntentFilter);

        return super.onStartCommand(intent, flags, startId);
    }

}
