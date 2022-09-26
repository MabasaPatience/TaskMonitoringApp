package za.co.tut4life.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;

import java.util.List;

import za.co.tut4life.R;
import za.co.tut4life.adapter.util.Utils;
import za.co.tut4life.model.TaskModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
   private final List<TaskModel> taskModelList;
   private final OnTodoClickListener todoClickListener;
   public RecyclerViewAdapter(List<TaskModel> taskModelList,OnTodoClickListener onTodoClickListener){
       this.taskModelList=taskModelList;
       this.todoClickListener = onTodoClickListener;
   }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.fragment_todo_row_activity,parent,false);
       return  new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

       TaskModel task =taskModelList.get(position);
       // String formated = Utils.formatDate(task.getDueDate());
        holder.task.setText(task.getTask());
    //  holder.todayChip.setText(formated);

    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }



    ///////////////////////////////////////////////////////////////
    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
       public AppCompatRadioButton radioButton;
       public AppCompatTextView task;
       public Chip todayChip;
        OnTodoClickListener onTodoClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton=itemView.findViewById(R.id.todo_radio_button);
            task=itemView.findViewById(R.id.todo_row_todo);
            todayChip=itemView.findViewById(R.id.todo_row_chip);
          this.onTodoClickListener=todoClickListener;

          itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
TaskModel currTask ;
int id=v.getId();
if(id==R.id.todo_row_layout){
     currTask = taskModelList.get(getAbsoluteAdapterPosition());
    onTodoClickListener.onTodoClick(currTask);
}else if(id==R.id.todo_radio_button){
     currTask = taskModelList.get(getAbsoluteAdapterPosition());
    onTodoClickListener.onTodoRadioButton(currTask);
}
        }
    }
}
