package smth.gmail.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import smth.gmail.tabatatimer.models.Sequence;
import smth.gmail.tabatatimer.models.WorkoutTimer;

import static android.Manifest.permission.FOREGROUND_SERVICE;

public class TrainingActivity extends AppCompatActivity implements SoundPool.OnLoadCompleteListener {

    private Sequence sequence;
    public static final String EXTRA_SEQ = "Sequence";
    public static final String EXTRA_TIMEVALUE ="TimeValue";
    private TextView current_ex;
    private TextView current_time;
    public RecyclerView recyclerView;
    public List<WorkoutTimer> workoutTimers;
    private Button start_btn;
    private Button next_btn;
    private Button pause_btn;
    private boolean timerInProcess;
    private boolean timerPaused;
    private int timeRemain;
    private BroadcastReceiver broadcastReceiver;
    final int MAX_STREAMS = 5;
    SoundPool sp;
    private int soundIdAlarm;


    public static void start(Activity caller, Sequence sequence){
        Intent intent = new Intent(caller, TrainingActivity.class);
        if (sequence != null){ intent.putExtra(EXTRA_SEQ, sequence); }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsActivity.SetConfigurations(this);
        setContentView(R.layout.activity_training);

        current_ex = findViewById(R.id.current_ex_name);
        current_time = findViewById(R.id.current_ex_time);
        recyclerView = findViewById(R.id.training_list);
        start_btn = findViewById(R.id.start_btn);
        next_btn = findViewById(R.id.next_btn);
        pause_btn = findViewById(R.id.pause_btn);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final TrainigsAdapter trainigsAdapter = new TrainigsAdapter();
        recyclerView.setAdapter(trainigsAdapter);

        this.sequence = getIntent().getParcelableExtra(EXTRA_SEQ);
        workoutTimers = App.getInstance().getWorkoutDao().getSequenceActivities(sequence.id);
        trainigsAdapter.setItems(workoutTimers);

        ActivityCompat.requestPermissions(this, new String[]{FOREGROUND_SERVICE}, PackageManager.PERMISSION_GRANTED);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("Counter");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Long longtime = intent.getLongExtra("TimeRemaining", 0);
                current_time.setText(longtime.toString());

                if(intent.hasExtra("TimerFinished")){
                    sp.play(soundIdAlarm, 1,1,0,0,1);
                    workoutTimers.remove(0);
                    trainigsAdapter.setItems(workoutTimers);
                    startNewExercise();
                }
            }
        };

        registerReceiver(broadcastReceiver, intentFilter);
        sp = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        soundIdAlarm = sp.load(this, R.raw.alarm, 1);
    }


    public void start_btn_clicked(View view) {
        if(timerInProcess == false && timerPaused == false){
            timerInProcess = true;
            startNewExercise();
        }else{
            if(timerPaused == true){
                timerPaused = false;
                Intent intentService = new Intent(this, TimerService.class);
                intentService.putExtra(EXTRA_TIMEVALUE, timeRemain);
                startService(intentService);
            }
        }
    }

    public void startNewExercise(){
        if(workoutTimers.size() != 0) {
            Intent intentService = new Intent(this, TimerService.class);
            current_ex.setText(workoutTimers.get(0).title);
            intentService.putExtra(EXTRA_TIMEVALUE, workoutTimers.get(0).timer);
            startService(intentService);
        }else{
            timerInProcess = false;
            unregisterReceiver(broadcastReceiver);
            finish();
        }
    }

    public void pause_btn_clicked(View view) {
        if (timerInProcess == true && timerPaused == false){
            timerPaused = true;
            timeRemain = Integer.valueOf(current_time.getText().toString());
            Intent localIntent = new Intent();
            localIntent.setAction("TimerPaused");
            sendBroadcast(localIntent);
        }
    }

    public void next_btn_clicked(View view) {
        Intent localIntent = new Intent();
        localIntent.setAction("TimerPaused");
        sendBroadcast(localIntent);
        localIntent.setAction("Counter");
        localIntent.putExtra("TimerFinished", true);
        sendBroadcast(localIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        unregisterReceiver(broadcastReceiver);
        workoutTimers.clear();
        finish();
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {

    }
}