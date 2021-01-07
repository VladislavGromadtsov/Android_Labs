package smth.gmail.tabatatimer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.List;

import smth.gmail.tabatatimer.models.WorkoutTimer;

public class TrainigsAdapter extends RecyclerView.Adapter<TrainigsAdapter.TrainingViewHolder> {
    private SortedList<WorkoutTimer> workoutTimerSortedList;

    public TrainigsAdapter(){
        workoutTimerSortedList = new SortedList<>(WorkoutTimer.class, new SortedList.Callback<WorkoutTimer>() {
            @Override
            public int compare(WorkoutTimer o1, WorkoutTimer o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemChanged(position,count);
            }

            @Override
            public boolean areContentsTheSame(WorkoutTimer oldItem, WorkoutTimer newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(WorkoutTimer item1, WorkoutTimer item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position,count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position,count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition,toPosition);
            }
        });
    }

    public void setItems(List<WorkoutTimer> workoutTimers){
        workoutTimerSortedList.replaceAll(workoutTimers);
    }

    @NonNull
    @Override
    public TrainigsAdapter.TrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrainigsAdapter.TrainingViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trainig_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull TrainigsAdapter.TrainingViewHolder holder, int position) {
        holder.bind(workoutTimerSortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return workoutTimerSortedList.size();
    }


    static class TrainingViewHolder extends RecyclerView.ViewHolder{

        TextView workout_name;
        TextView workout_time;

        public TrainingViewHolder(@NonNull View itemView) {
            super(itemView);
            workout_name = itemView.findViewById(R.id.workout_nm);
            workout_time = itemView.findViewById(R.id.workout_time);
        }

        public void bind(WorkoutTimer workoutTimer){
            workout_name.setText(workoutTimer.title);
            workout_time.setText(String.valueOf(workoutTimer.timer));
        }
    }
}
