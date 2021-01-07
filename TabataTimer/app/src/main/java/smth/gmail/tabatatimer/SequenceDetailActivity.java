package smth.gmail.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Random;

import smth.gmail.tabatatimer.models.Sequence;
import smth.gmail.tabatatimer.models.WorkoutTimer;

public class SequenceDetailActivity extends AppCompatActivity {
    private static final String EXTRA_SEQUENCE = "SequenceDetailActivity.EXTRA_SEQUENCE";
    private Sequence sequence;
    private EditText editText;
    private RecyclerView recyclerView;
    private Button save;
    private Button start_workout;
    private Button add_new_ex;

    public static void start(Activity caller, Sequence sequence){
        Intent intent = new Intent(caller, SequenceDetailActivity.class);
        if (sequence != null){ intent.putExtra(EXTRA_SEQUENCE, sequence); }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsActivity.SetConfigurations(this);
        setContentView(R.layout.activity_sequence_detail);

        editText = findViewById(R.id.sequence_name);
        if(getIntent().hasExtra(EXTRA_SEQUENCE)){
            sequence = getIntent().getParcelableExtra(EXTRA_SEQUENCE);
            editText.setText(sequence.title);
        }else{
            sequence = new Sequence();
        }
        recyclerView = findViewById(R.id.workout_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final WorkoutAdapter workoutAdapter = new WorkoutAdapter();
        recyclerView.setAdapter(workoutAdapter);
        editText.setBackgroundColor(sequence.color);

        final SequenceDetailViewModel sequenceDetailViewModel = ViewModelProviders.of(this).get(SequenceDetailViewModel.class);
        sequenceDetailViewModel.getWorktimerLiveData().observe(this, new Observer<List<WorkoutTimer>>() {
            @Override
            public void onChanged(List<WorkoutTimer> workoutTimers) {
                List<WorkoutTimer> new_exer = sequenceDetailViewModel.getWorkActivities(sequence.id);
                workoutAdapter.setItems(new_exer);
            }
        });

        save = findViewById(R.id.save);
        add_new_ex = findViewById(R.id.add_new_ex);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sequence.title = editText.getText().toString();
                if (getIntent().hasExtra(EXTRA_SEQUENCE)){
                    App.getInstance().getSequenceDao().update(sequence);
                }else{
                    Random rnd = new Random();
                    sequence.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    App.getInstance().getSequenceDao().insert(sequence);
                }

                finish();
            }
        });

        add_new_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutTimer workoutTimer = new WorkoutTimer();
                workoutTimer.title = "New exercise";
                workoutTimer.timer = 5;
                workoutTimer.sequence_id = sequence.id;
                App.getInstance().getWorkoutDao().insert(workoutTimer);
            }
        });


//        Toolbar toolbar = findViewById(R.id.toolbar_seq);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled();
    }

    public void start_workout(View view) {
        TrainingActivity.start(this, sequence);
    }
}