package za.co.tut4life;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;

import java.util.Calendar;
import java.util.Date;

import za.co.tut4life.model.Priority;
import za.co.tut4life.model.SharedViewModel;
import za.co.tut4life.model.TaskModel;
import za.co.tut4life.model.TaskViewModel;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class BottomSheetFragment  extends BottomSheetDialogFragment implements View.OnClickListener{
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private ImageButton saveButton;
    private CalendarView calenderview;
    private Group calendarGroup;
    private Date dueDate;
    Calendar calendar=Calendar.getInstance();
    private SharedViewModel sharedviewModel;


    public  BottomSheetFragment(){}
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.bottom_sheet, container, false);

       calendarGroup=view.findViewById(R.id.calendar_group);
        calenderview=view.findViewById(R.id.calendar_view);
        calendarButton=view.findViewById(R.id.today_calendar_button);
        enterTodo=view.findViewById(R.id.enter_todo_et);
        saveButton=view.findViewById(R.id.save_todo_button);
        priorityButton=view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup=view.findViewById(R.id.radioGroup_priority);

        Chip todayChip=view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip=view.findViewById(R.id.tomorrow_chip);
       tomorrowChip.setOnClickListener(this);
        Chip nextweekChip=view.findViewById(R.id.next_week_chip);
        nextweekChip.setOnClickListener(this);
        return  view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if(sharedviewModel.getSelectedItem().getValue() != null){
            TaskModel task=sharedviewModel.getSelectedItem().getValue();
            Log.d("my","onViewCreate"+ task.getTask());
        }

    }

    public void onViewCreated(@NonNull View view,
                              Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);

       sharedviewModel=new ViewModelProvider(requireActivity())
               .get(SharedViewModel.class);

        // set the date
        calendarButton.setOnClickListener(view2->{
            calendarGroup.setVisibility(
                    calendarGroup.getVisibility()==View.GONE ? View.VISIBLE : View.GONE
            );
        });

        calenderview.setOnDateChangeListener((calendarview,year,month,dayOfMonth) ->{
      calendar.clear();
      calendar.set(year,month,dayOfMonth);
      dueDate=calendar.getTime();
            // Log.d("Cal","onViewCreated: ====>month" +(month +1)+", dayOfMonth "+ dayOfMonth);

        });

//Save task to data base
   saveButton.setOnClickListener(view1->{
    String task =enterTodo.getText().toString().trim();
    if(!TextUtils.isEmpty(task) && dueDate !=null){
    TaskModel mytask=new TaskModel(task, Priority.High,dueDate,Calendar.getInstance().getTime(),false);
    TaskViewModel.insert(mytask);
    
}

   });

    }

    @Override
    public void onClick(View v) {
    int id= v.getId();
    if (id==R.id.today_chip){
        calendar.add(Calendar.DAY_OF_YEAR,0);
    dueDate=calendar.getTime();
    }else if(id==R.id.tomorrow_chip){
        calendar.add(Calendar.DAY_OF_YEAR,1);
        dueDate=calendar.getTime();
    }else if(id==R.id.next_week_chip){
        calendar.add(Calendar.DAY_OF_YEAR,7);
        dueDate=calendar.getTime();
    }
    }
}