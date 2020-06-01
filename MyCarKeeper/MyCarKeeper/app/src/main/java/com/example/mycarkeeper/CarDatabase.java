package com.example.mycarkeeper;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Ορισμός Βάσης δεδομένων CarDatabase, έχει το CarDao που περιέχει τα queries για την βάση δεδομένων

@Database(entities = {Car.class}, version=1)
public abstract class CarDatabase extends RoomDatabase {
    public  abstract CarDao carDao();
}
