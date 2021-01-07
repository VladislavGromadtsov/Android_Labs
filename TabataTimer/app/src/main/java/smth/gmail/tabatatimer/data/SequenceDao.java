package smth.gmail.tabatatimer.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import smth.gmail.tabatatimer.models.Sequence;

@Dao
public interface SequenceDao {
    @Query("SELECT * FROM Sequence")
    List<Sequence> getAll();

    @Query("SELECT * FROM Sequence")
    LiveData<List<Sequence>> getAllLiveData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Sequence sequence);

    @Update
    void update(Sequence sequence);

    @Delete
    void delete(Sequence sequence);
}
