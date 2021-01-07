package smth.gmail.tabatatimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import smth.gmail.tabatatimer.models.WorkoutTimer;

public class SequenceDetailViewModel extends ViewModel {
    private LiveData<List<WorkoutTimer>> worktimerLiveData = App.getInstance().getWorkoutDao().getAllWorkActivities();

    public  LiveData<List<WorkoutTimer>> getWorktimerLiveData (){
        return worktimerLiveData;
    }

    public List<WorkoutTimer> getWorkActivities (int id){
        return App.getInstance().getWorkoutDao().getSequenceActivities(id);
    }
}
