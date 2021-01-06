package smth.gmail.converterapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NumpadFragment extends Fragment  implements View.OnClickListener{
    ConverterViewModel converterViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        converterViewModel = ViewModelProviders.of(getActivity()).get(ConverterViewModel.class);
        View view =  inflater.inflate(R.layout.fragment_numpad, container, false);

        Button btn0 = (Button) view.findViewById(R.id.btnNumpad0);
        Button btn1 = (Button) view.findViewById(R.id.btnNumpad1);
        Button btn2 = (Button) view.findViewById(R.id.btnNumpad2);
        Button btn3 = (Button) view.findViewById(R.id.btnNumpad3);
        Button btn4 = (Button) view.findViewById(R.id.btnNumpad4);
        Button btn5 = (Button) view.findViewById(R.id.btnNumpad5);
        Button btn6 = (Button) view.findViewById(R.id.btnNumpad6);
        Button btn7 = (Button) view.findViewById(R.id.btnNumpad7);
        Button btn8 = (Button) view.findViewById(R.id.btnNumpad8);
        Button btn9 = (Button) view.findViewById(R.id.btnNumpad9);
        Button btnDel = (Button) view.findViewById(R.id.btnNumpadDelete);
        Button btnDot = (Button) view.findViewById(R.id.btnNumpadDot);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnDot.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        converterViewModel.setInputData(converterViewModel.getInputData().getValue() + btn.getText());
    }
}