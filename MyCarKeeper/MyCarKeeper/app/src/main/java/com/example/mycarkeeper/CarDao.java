package com.example.mycarkeeper;

import android.content.ClipData;
import android.text.Editable;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface CarDao {

    //με την getAllCars παίρνουμε όλα τα αυτοκίνητα απο τη βάση δεδομένων
    @Query("SELECT * FROM car")
    List<Car>getAllCars();

    //με την insertAll εισάγουμε  αυτοκίνητα στη βάση δεδομένων
    @Insert
    void  insertAll(Car... cars);

    //Διαγράφουμε ένα αυτοκίνητο απο τη βάση δεδομένων
    @Delete
    public void deleteCar(Car car);

    //Επιλέγουμε τη στήλη newcarbrand απο τη βάση δεδομένων
    @Query("SELECT newcarbrand  FROM car")
     List<String> getAllBrands();
    //Με αυτο το query δίνουμε σαν παράμετρο το μοντέλο του αυτοκινήτου
    //και επιστρέφει σε όλες τις πινακίδες οι οποίες υπάρχουν στη βάση και
    // αντιστοιχούν σε αυτό το μοντέλο-παράμετρο
    @Query("SELECT newcarplate FROM car WHERE newcarbrand= :newcarbrand")
    List<String> getAllPlates(String newcarbrand);

}
