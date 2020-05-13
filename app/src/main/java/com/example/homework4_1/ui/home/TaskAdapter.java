package com.example.homework4_1.ui.home;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework4_1.dialogAlert.FireMissilesDialogFragment;
import com.example.homework4_1.MainActivity;
import com.example.homework4_1.OnItemClickListener;
import com.example.homework4_1.R;
import com.example.homework4_1.models.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    public List<Task> list;
    private Resources res;
    private MainActivity mainActivity;
    private FireMissilesDialogFragment dialogFragment;
    public int position;
    public TaskAdapter(List<Task> list, Resources res, OnItemClickListener listener, FireMissilesDialogFragment dialogFragment, MainActivity mainActivity){
        this.list = list;
        this.res = res;
        onItemClickListener = listener;
        this.dialogFragment = dialogFragment;
        this.mainActivity = mainActivity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if ((position+1)%2 == 0) holder.linearLayout.setBackgroundColor(res.getColor(R.color.colorGrey));
        else holder.linearLayout.setBackgroundColor(res.getColor(R.color.colorWhite));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_title);
            linearLayout = itemView.findViewById(R.id.view_holder_homeFragment);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    position = getAdapterPosition();
                    dialogFragment.show(mainActivity.getSupportFragmentManager(), null);
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });
            
        }

        public void bind(Task task) {
            title.setText(task.getTitle());
        }
    }
}
