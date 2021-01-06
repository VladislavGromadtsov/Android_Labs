package smth.gmail.converterapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import smth.gmail.converterapp.ConverterViewModel;
import smth.gmail.converterapp.R;

public class DataFragmentPro extends DataFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        setBtns(view, converterViewModel);
        return view;
    }

    public void setBtns(View view, ConverterViewModel converterViewModel){
        Button cop1 = view.findViewById(R.id.copy1);
        Button cop2 = view.findViewById(R.id.copy2);
        Button change = view.findViewById(R.id.change);

        change.setOnClickListener(view1 -> {converterViewModel.setInputData(converterViewModel.getOutputData().getValue());});

        cop1.setOnClickListener(view1 -> {
            ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", converterViewModel.getInputData().getValue());
            clipboard.setPrimaryClip(clip);
            Toast toast = Toast.makeText(requireContext().getApplicationContext(), "Field1 Copied", Toast.LENGTH_LONG);
            toast.show();
        });

        cop2.setOnClickListener(view1 -> {
            ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", converterViewModel.getOutputData().getValue());
            clipboard.setPrimaryClip(clip);
            Toast toast = Toast.makeText(requireContext().getApplicationContext(), "Field2 Copied", Toast.LENGTH_LONG);
            toast.show();
        });
    }
}