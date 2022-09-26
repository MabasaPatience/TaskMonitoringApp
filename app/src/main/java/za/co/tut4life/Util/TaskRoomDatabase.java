package za.co.tut4life.Util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import za.co.tut4life.Data.TaskDao;
import za.co.tut4life.model.TaskModel;

@Database(entities = {TaskModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TaskRoomDatabase  extends RoomDatabase{
    public static final int Number_of_threads=4;
    private static volatile TaskRoomDatabase instance;
    public static final String database_name="task_manager";
    public static final ExecutorService databaseWriterExecutor= Executors.newFixedThreadPool(Number_of_threads);

    public static final RoomDatabase.Callback sRoomDatabaseCollBack=
            new RoomDatabase.Callback(){
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriterExecutor.execute(()->{
                        TaskDao taskdao= instance.taskDao();
                        taskdao.deleteAll();
                    });
                }

            };

    public static  TaskRoomDatabase getDatabase(final Context context){
        if(instance==null){
            synchronized (TaskRoomDatabase.class){
                if(instance==null){
                    instance= Room.databaseBuilder(context.getApplicationContext(),
                                    TaskRoomDatabase.class,database_name)
                            .addCallback(sRoomDatabaseCollBack)
                            .build();

                }
            }
        }
        return  instance;
    }
    public abstract TaskDao taskDao();


}
