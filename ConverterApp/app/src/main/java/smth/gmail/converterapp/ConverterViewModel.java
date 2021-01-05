package smth.gmail.converterapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ConverterViewModel extends ViewModel {
    private MutableLiveData<String> cCategory = new MutableLiveData<String>("");
    private MutableLiveData<String> InputData = new MutableLiveData<String>("");
    private MutableLiveData<String> OutputData = new MutableLiveData<String>();
    private MutableLiveData<String> FromCat = new MutableLiveData<String>();
    private MutableLiveData<String> ToCat = new MutableLiveData<String>();
    private Measures measures;

    public ConverterViewModel(){
        measures = new Measures();
    }

    public void SetCurrentCategory(String category){
        cCategory.setValue(category);
    }

    public LiveData<String> GetCurrentCategory(){
        return cCategory;
    }

    public ArrayList<String> getMesNames(String category){
        return measures.getMeasuresNames(category);
    }

    public LiveData<String> getInputData(){
        return InputData;
    }

    public LiveData<String> getOutputData(){
        return OutputData;
    }

    public void setOutputData(String data){
        OutputData.setValue(data);
    }

    public void setInputData(String data){
        if (data.charAt(data.length()-1) == 'C'){ data = "";}
        InputData.setValue(data);
        setOutputData(Converting(data));
    }

    public void setFromCat(String cat) {
        FromCat.setValue(cat);
    }

    public LiveData<String> getFromCat(){
        return FromCat;
    }

    public void setToCat(String cat) {
        ToCat.setValue(cat);
    }

    public LiveData<String> getToCat(){
        return ToCat;
    }

    private String Converting(String data){
        double Cf1, Cf2;
        Cf1 = measures.getCf(cCategory.getValue(), getFromCat().getValue());
        Cf2 = measures.getCf(cCategory.getValue(), getToCat().getValue());
        return measures.Convert(data, Cf1, Cf2);
    }
}
