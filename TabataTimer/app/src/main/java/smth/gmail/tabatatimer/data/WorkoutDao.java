package smth.gmail.tabatatimer.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import smth.gmail.tabatatimer.models.WorkoutTimer;

@Dao
public interface WorkoutDao {
    @Query("SELECT * FROM workouttimer")
    LiveData<List<WorkoutTimer>> getAllWorkActivities();

    @Query("SELECT * FROM WorkoutTimer WHERE :id == sequence_id")
    List<WorkoutTimer> getSequenceActivities(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WorkoutTimer workoutTimer);

    @Update
    void update(WorkoutTimer workoutTimer);

    @Delete
    void delete(WorkoutTimer workoutTimer);
}
