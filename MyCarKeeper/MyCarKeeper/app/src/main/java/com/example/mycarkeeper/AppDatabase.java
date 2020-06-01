package com.example.mycarkeeper;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Ορισμός Βάσης δεδομένων AppDatabase, έχει το taskDao που περιέχει τα queries για την βάση δεδομένων
@Database(entities = {Task.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
