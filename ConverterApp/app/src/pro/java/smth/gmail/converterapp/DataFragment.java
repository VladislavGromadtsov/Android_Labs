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

public class DataFragment extends Fragment {
    ConverterViewModel converterViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        converterViewModel = new ViewModelProvider(requireActivity()).get(ConverterViewModel.class);
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        EditText edIn = view.findViewById(R.id.editText1);
        EditText edOut = view.findViewById(R.id.editText2);

        SetSpinners(view, converterViewModel);
        converterViewModel.getInputData().observe(getViewLifecycleOwner(), value -> {edIn.setText(value);});
        converterViewModel.getOutputData().observe(getViewLifecycleOwner(), value -> {edOut.setText(value);});
        setBtns(view, converterViewModel);
        return view;
    }

    public void SetSpinners(View view, ConverterViewModel converterViewModel){
        Spinner spinnerIn = view.findViewById(R.id.spinner1);
        Spinner spinnerTo = view.findViewById(R.id.spinner2);

        converterViewModel.GetCurrentCategory().observe(getViewLifecycleOwner(), value->{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, converterViewModel.getMesNames(value));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerIn.setAdapter(adapter);
            spinnerTo.setAdapter(adapter);
        });

        spinnerIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                converterViewModel.setFromCat(name);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                converterViewModel.setToCat(name);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        converterViewModel.getFromCat().observe(getViewLifecycleOwner(), value -> {spinnerIn.setSelection(converterViewModel.getMesNames(converterViewModel.GetCurrentCategory().getValue()).indexOf(value));});
        converterViewModel.getToCat().observe(getViewLifecycleOwner(), value -> {spinnerTo.setSelection(converterViewModel.getMesNames(converterViewModel.GetCurrentCategory().getValue()).indexOf(value));});
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