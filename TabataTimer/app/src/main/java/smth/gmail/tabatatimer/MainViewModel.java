package smth.gmail.tabatatimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import smth.gmail.tabatatimer.models.Sequence;

public class MainViewModel extends ViewModel {
    private LiveData<List<Sequence>>  sequenceLiveData = App.getInstance().getSequenceDao().getAllLiveData();

    public LiveData<List<Sequence>> getSequenceLiveData() {
        return sequenceLiveData;
    }
}
