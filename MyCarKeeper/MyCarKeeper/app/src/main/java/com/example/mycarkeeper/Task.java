package com.example.mycarkeeper;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {

    // H δομή της βάσης, έχουμε έναν constructor
    public Task(String taskMessage , String taskDate, String carsName, String plateName, String respon) {
        this.taskMessage = taskMessage;
        this.taskDate=taskDate;
        this.carsName=carsName;
        this.plateName=plateName;
        this.respon=respon;
    }
    // βάζουμε σαν κύριο κλειδί το id και έπειτα 5 στήλες στη βάση που θα αποθηκεύονται οι πληροφορίες
    //που θα εισάγει ο χρήστης και θα αποθηκεύονται στη βάση
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="tasks_name")
    private String taskMessage;

    @ColumnInfo(name="tasks_date")
    private String taskDate;

    @ColumnInfo(name="cars_name")
    private String carsName;

    @ColumnInfo(name="plate_name")
    private String plateName;

    @ColumnInfo(name="responsibles_name")
    private String respon;

    // getters and setters για να παίρνουμε και να θέτουμε τις τιμές της βάσης
    public String getCarsName() {
        return carsName;
    }

    public void setCarsName(String carsName) {
        this.carsName = carsName;
    }

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getRespon() {
        return respon;
    }

    public void setRespon(String respon) {
        this.respon = respon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskMessage() {
        return taskMessage;
    }

    public void setTaskMessage(String taskMessage) {
        this.taskMessage = taskMessage;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskMessage) {
        this.taskDate = taskDate;
    }
}
