package za.co.tut4life.adapter;

import za.co.tut4life.model.TaskModel;

public interface OnTodoClickListener {
    void onTodoClick( TaskModel taskModel);
    void  onTodoRadioButton(TaskModel task);
}
