package smth.gmail.converterapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

public class ConverterActivity extends AppCompatActivity {
    private String CategoryName;
    private ConverterViewModel converterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        Intent intent = getIntent();
        CategoryName = intent.getStringExtra("Category");
//        System.out.println(CategoryName);
        converterViewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        converterViewModel.SetCurrentCategory(CategoryName);
    }
}