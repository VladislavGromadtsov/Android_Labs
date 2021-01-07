package smth.gmail.tabatatimer.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Sequence.class, parentColumns = "id", childColumns = "sequence_id", onDelete = CASCADE))
public class WorkoutTimer implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "sequence_id")
    public int sequence_id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "timer")
    public int timer;

    public WorkoutTimer(){}

    protected WorkoutTimer(Parcel in) {
        id = in.readInt();
        sequence_id = in.readInt();
        title = in.readString();
        timer = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(sequence_id);
        dest.writeString(title);
        dest.writeInt(timer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WorkoutTimer> CREATOR = new Creator<WorkoutTimer>() {
        @Override
        public WorkoutTimer createFromParcel(Parcel in) {
            return new WorkoutTimer(in);
        }

        @Override
        public WorkoutTimer[] newArray(int size) {
            return new WorkoutTimer[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutTimer that = (WorkoutTimer) o;
        return id == that.id &&
                sequence_id == that.sequence_id &&
                timer == that.timer &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequence_id, title, timer);
    }
}
