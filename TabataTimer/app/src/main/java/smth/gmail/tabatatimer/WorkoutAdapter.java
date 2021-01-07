package smth.gmail.tabatatimer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import java.util.List;

import smth.gmail.tabatatimer.models.Sequence;
import smth.gmail.tabatatimer.models.WorkoutTimer;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private SortedList<WorkoutTimer> workoutTimerSortedList;

    public WorkoutAdapter(){
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

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkoutAdapter.WorkoutViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        holder.bind(workoutTimerSortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return workoutTimerSortedList.size();
    }

    public void setItems(List<WorkoutTimer> workoutTimers){
        workoutTimerSortedList.replaceAll(workoutTimers);
    }

    static class WorkoutViewHolder extends RecyclerView.ViewHolder{
        WorkoutTimer workoutTimer;
        TextView workout_name;
        TextView workout_time;

        public WorkoutViewHolder(@NonNull final View itemView) {
            super(itemView);
            workout_name = itemView.findViewById(R.id.workout_name);
            workout_time = itemView.findViewById(R.id.workout_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WorkoutDetailActivity.start((Activity)itemView.getContext(), workoutTimer);
                }
            });
        }


        public void bind(WorkoutTimer workoutTimer){
            this.workoutTimer = workoutTimer;
            workout_name.setText(workoutTimer.title);
            workout_time.setText(String.valueOf(workoutTimer.timer));
        }
    }
}
