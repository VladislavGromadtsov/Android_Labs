package smth.gmail.tabatatimer.data;

import androidx.room.RoomDatabase;

import smth.gmail.tabatatimer.models.Sequence;
import smth.gmail.tabatatimer.models.WorkoutTimer;

@androidx.room.Database(entities = {Sequence.class, WorkoutTimer.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract SequenceDao sequenceDao();
    public abstract WorkoutDao workoutDao();
}
