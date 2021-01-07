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

public class SequenceAdapter extends RecyclerView.Adapter<SequenceAdapter.SequenceViewHolder> {

    private SortedList<Sequence> sortedList;

    public SequenceAdapter(){
        sortedList = new SortedList<>(Sequence.class, new SortedList.Callback<Sequence>() {
            @Override
            public int compare(Sequence o1, Sequence o2) {
                return 0;
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Sequence oldItem, Sequence newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Sequence item1, Sequence item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public SequenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SequenceViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sequence_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SequenceViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Sequence> sequences){
        sortedList.replaceAll(sequences);
    }

    static class SequenceViewHolder extends RecyclerView.ViewHolder{

        TextView sequence_name;
        Button delete;
        Sequence sequence;

        public SequenceViewHolder(@NonNull final View itemView) {
            super(itemView);

            sequence_name = itemView.findViewById(R.id.sequence_name);
            delete = itemView.findViewById(R.id.delete_seq);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SequenceDetailActivity.start((Activity)itemView.getContext(), sequence);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getSequenceDao().delete(sequence);
                }

            });

        }

        public void bind(Sequence sequence){
            this.sequence = sequence;

            sequence_name.setText(sequence.title);
            itemView.setBackgroundColor(sequence.color);
        }
    }
}
