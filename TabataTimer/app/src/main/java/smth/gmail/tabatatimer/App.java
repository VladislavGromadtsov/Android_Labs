package smth.gmail.tabatatimer;

import android.app.Application;

import androidx.room.Room;

import smth.gmail.tabatatimer.data.Database;
import smth.gmail.tabatatimer.data.SequenceDao;
import smth.gmail.tabatatimer.data.WorkoutDao;

public class App extends Application {

    private Database database;
    private SequenceDao sequenceDao;
    private WorkoutDao workoutDao;
    private static App instance;

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        database = Room.databaseBuilder(getApplicationContext(), Database.class, "TT-db").allowMainThreadQueries().build();
        sequenceDao = database.sequenceDao();
        workoutDao = database.workoutDao();
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public SequenceDao getSequenceDao() {
        return sequenceDao;
    }

    public void setSequenceDao(SequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }

    public WorkoutDao getWorkoutDao() {
        return workoutDao;
    }

    public void setWorkoutDao(WorkoutDao workoutDao) {
        this.workoutDao = workoutDao;
    }
}

