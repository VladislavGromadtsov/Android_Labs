package smth.gmail.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.Random;

import smth.gmail.tabatatimer.models.Sequence;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SettingsActivity.SetConfigurations(this);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.List);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final SequenceAdapter sequenceAdapter = new SequenceAdapter();
        recyclerView.setAdapter(sequenceAdapter);

        Button add_new = findViewById(R.id.add_new_seq);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sequence sequence = new Sequence();
                sequence.title = "New workout";
                Random rnd = new Random();
                sequence.color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                App.getInstance().getSequenceDao().insert(sequence);
            }
        });

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getSequenceLiveData().observe(this, new Observer<List<Sequence>>() {
            @Override
            public void onChanged(List<Sequence> sequences) {
                sequenceAdapter.setItems(sequences);
            }
        });
    }

    public void settings_btn(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        this.startActivity(intent);
    }
}