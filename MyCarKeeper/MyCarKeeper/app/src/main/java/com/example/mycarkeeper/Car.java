package com.example.mycarkeeper;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Car {

    // H δομή της βάσης, έχουμε έναν constructor
    public Car(String newcarname, String newcarbrand, String newcarplate) {
        this.newcarname = newcarname;
        this.newcarbrand = newcarbrand;
        this.newcarplate = newcarplate;
    }

    // βάζουμε σαν κύριο κλειδί το id και έπειτα τρεις στήλες για το newcarsname, newcarsbrand, newcarplate
    //που θα εισάγει ο χρήστης και θα αποθηκεύονται στη βάση
    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "newcarname")
    private String newcarname;

    @ColumnInfo(name = "newcarbrand")
    private String newcarbrand;

    @ColumnInfo(name = "newcarplate")
    private String newcarplate;



// getters and setters για να παίρνουμε και να θέτουμε τις τιμές της βάσης
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewcarname() {
        return newcarname;
    }

    public void setNewcarname(String newcarname) {
        this.newcarname = newcarname;
    }

    public String getNewcarbrand() {
        return newcarbrand;
    }

    public void setNewcarbrand(String newcarbrand) {
        this.newcarbrand = newcarbrand;
    }

    public String getNewcarplate() {
        return newcarplate;
    }

    public void setNewcarplate(String newcarplate) {
        this.newcarplate = newcarplate;
    }





}
