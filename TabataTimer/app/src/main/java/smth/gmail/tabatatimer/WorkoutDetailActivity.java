package smth.gmail.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import smth.gmail.tabatatimer.models.Sequence;
import smth.gmail.tabatatimer.models.WorkoutTimer;

public class WorkoutDetailActivity extends AppCompatActivity {

    private static final String EXTRA_WORKOUT = "SequenceDetailActivity.EXTRA_WORKOUT";
    private WorkoutTimer workoutTimer;
    private EditText workout_name;
    private EditText time;
    private Button add;
    private Button minus;
    private Button del;
    private Button save;

    public static void start(Activity caller, WorkoutTimer workoutTimer){
        Intent intent = new Intent(caller, WorkoutDetailActivity.class);
        if (workoutTimer != null){ intent.putExtra(EXTRA_WORKOUT, workoutTimer); }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsActivity.SetConfigurations(this);
        setContentView(R.layout.activity_workout_detail_activity);

        workout_name = findViewById(R.id.workout_title);
        time = findViewById(R.id.timer);
        add = findViewById(R.id.add_time);
        minus = findViewById(R.id.minus_time);

        if(getIntent().hasExtra(EXTRA_WORKOUT)){
            workoutTimer = getIntent().getParcelableExtra(EXTRA_WORKOUT);
            workout_name.setText(workoutTimer.title);
            time.setText(String.valueOf(workoutTimer.timer));
        }else{
            workoutTimer = new WorkoutTimer();
        }

        del = findViewById(R.id.delete_btn);
        save = findViewById(R.id.save_btn);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App.getInstance().getWorkoutDao().delete(workoutTimer);
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutTimer.title = workout_name.getText().toString();
                workoutTimer.timer = Integer.parseInt(time.getText().toString());
                if (getIntent().hasExtra(EXTRA_WORKOUT)){
                    App.getInstance().getWorkoutDao().update(workoutTimer);
                }else{
                    App.getInstance().getWorkoutDao().insert(workoutTimer);
                }

                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(time.getText().toString());
                num++;
                time.setText(String.valueOf(num));
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(time.getText().toString());
                if(num>0){
                    num--;
                    time.setText(String.valueOf(num));
                }
            }
        });
    }
}