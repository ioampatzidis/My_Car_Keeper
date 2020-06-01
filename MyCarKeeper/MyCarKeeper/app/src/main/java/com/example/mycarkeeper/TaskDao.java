package com.example.mycarkeeper;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    //με την getAllTasks παίρνουμε όλες τις αποθηκευμένες εργασίες απο τη βάση δεδομένων
    @Query("SELECT * FROM task")
    List<Task> getAllTasks();

    //με την insertAll εισάγουμε εργασίες στη βάση δεδομένων
    @Insert
    void  insertAll(Task... tasks);

    //Διαγράφουμε μία εργασία-task απο τη βάση δεδομένων
    @Delete
    public void deleteTask(Task task);


}
