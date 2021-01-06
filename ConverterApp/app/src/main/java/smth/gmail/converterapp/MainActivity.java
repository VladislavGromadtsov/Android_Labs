package smth.gmail.converterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void time_button_clicked(View view) {
        Intent intent = new Intent(this, ConverterActivity.class).putExtra("Category", "Time");
        startActivity(intent);
    }

    public void distance_button_clicked(View view) {
        Intent intent = new Intent(this, ConverterActivity.class).putExtra("Category", "Distance");
        startActivity(intent);
    }

    public void weight_button_clicked(View view) {
        Intent intent = new Intent(this, ConverterActivity.class).putExtra("Category", "Weight");
        startActivity(intent);
    }
}