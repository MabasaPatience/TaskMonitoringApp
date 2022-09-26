package za.co.tut4life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

import za.co.tut4life.adapter.OnTodoClickListener;
import za.co.tut4life.adapter.RecyclerViewAdapter;
import za.co.tut4life.model.Priority;
import za.co.tut4life.model.SharedViewModel;
import za.co.tut4life.model.TaskModel;
import za.co.tut4life.model.TaskViewModel;


//public class MainActivity extends AppCompatActivity implements OnTodoClickListener {
    public class MainActivity extends AppCompatActivity implements OnTodoClickListener{
    private static final String TAG = "ITEM" ;
    private TaskViewModel taskViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private  BottomSheetFragment bottomsheetFragment;
    private SharedViewModel sharedViewModel;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
    recyclerView=findViewById(R.id.recycler_view);
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomsheetFragment=new BottomSheetFragment();
        ConstraintLayout constraintLayout=findViewById(R.id.bottomSheet);
       // BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior= BottomSheetBehavior.from(constraintLayout);
       // bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);





       taskViewModel=new ViewModelProvider.AndroidViewModelFactory(
        MainActivity.this.getApplication()).create(TaskViewModel.class);

       sharedViewModel =new ViewModelProvider(this)
               .get(SharedViewModel.class);

       taskViewModel.getAllTask().observe(this, task -> {
         recyclerViewAdapter=new RecyclerViewAdapter(task,this);
         recyclerView.setAdapter(recyclerViewAdapter);
      });
    taskViewModel=new ViewModelProvider.AndroidViewModelFactory(
            MainActivity.this.getApplication()).create(TaskViewModel.class);
        FloatingActionButton fab=findViewById(R.id.fade);
       fab.setOnClickListener(view -> {
          TaskModel taskmodel =new TaskModel("Test", Priority.High, Calendar.getInstance().getTime(),
                  Calendar.getInstance().getTime(),true);
          TaskViewModel.insert(taskmodel);
           showBottonSheet();
       });

    }
    private void showBottonSheet() {
        bottomsheetFragment.show(getSupportFragmentManager(), bottomsheetFragment.getTag());
    }

    @Override
    public void onTodoClick(TaskModel taskModel) {
       Log.d("Click","onTodoClick: "+ taskModel.getTask());
        sharedViewModel.selectItem(taskModel);
        showBottonSheet();
    }

    @Override
    public void onTodoRadioButton(TaskModel task) {
        Log.d("Click","onTodoRadioClick: "+ task.getTask());
        TaskViewModel.delete(task);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    public void DeleteButto(TaskModel task) {
        Log.d("Click","onTodoRadioClick: "+ task.getTask());
        TaskViewModel.delete(task);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}