package com.example.mycarkeeper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Αρχικοποιούμε τα κουμπιά της αρχικής και τους δυο adapter για να δείχνει κάθε
    // φορά το recyclerView την κατάλληλη λίστα
    Button newCar;
    Button newTask;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter2;
    RecyclerView.Adapter adapter;
    Boolean showListTask;
    Button ButtonChangeView;
    List<Car>cars;
    TextView listTitle;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showListTask= Boolean.TRUE;
        recyclerView =findViewById(R.id.recyclerView);

        //Παίρνουμε απο την βάση δεδομένων CarDatabase όλα τα αυτοκίνητα που έχουν εισαχθεί σε αυτή και τα αποθηκεύουμε
        // στην λίστα cars
        CarDatabase db1= Room.databaseBuilder(getApplicationContext(),CarDatabase.class,"production2")
                .allowMainThreadQueries()
                .build();
        List<Car> cars=db1.carDao().getAllCars();

         //Παίρνουμε απο την βάση δεδομένων AppDatabase όλες τις εργασίες που έχουν εισαχθεί σε αυτή και τις αποθηκεύουμε
        // στην λίστα tasks
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"production")
                .allowMainThreadQueries().build();

        List<Task> tasks = db.taskDao().getAllTasks();


        // Με την Glide φορτώνουμε την εικόνα στο background ώστε
        // σε συσκευές με μικρή μνήμη, να μην κρασάρει η εφαρμογή.
        final ConstraintLayout constraintLayout = findViewById(R.id.backgroundLayout);

        Glide.with(this).load(R.drawable.carimage).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                constraintLayout.setBackground(resource);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new UserAdapter(tasks,this); //we pass data
        recyclerView.setAdapter(adapter);

        adapter2= new CarAdapter(cars,this);

        listTitle = findViewById(R.id.myList);
        ButtonChangeView =findViewById(R.id.ButtonChangeView);
        ButtonChangeView.setTransformationMethod(null);
        ButtonChangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Αλλάζουμε το χρώμα του κουμπιού ώστε να καταλαβαίνει ο χρήστης ότι το πάτησε
                ButtonChangeView.setBackgroundColor(Color.rgb(241,14,14));

                // change to original after 5 secs.
                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        ButtonChangeView.setBackgroundColor(Color.rgb(128,3,3));
                    }
                }, 100);

                //Ανάλογα με την τιμή της boolean μεταβλητής που αλλάζει με το πάτημα του κουμπιού, αλλάζει ο adapter στo recycleView
                // ώστε σε αυτή να δείχνει είτε τα αυτοκίνητα της μίας βάσης είτε τις εργασίες που είναι αποθηκευμένες στην άλλη
                if(showListTask==true){
                    ButtonChangeView.setText(R.string.change_list2);
                    listTitle.setText(R.string.mycars);
                    ButtonChangeView.animate().alpha(1f).setDuration(1000);
                    showListTask=false;
                    recyclerView.setAdapter(adapter2);
                }
                else if(showListTask==false){
                    ButtonChangeView.setText(R.string.change_list);
                    listTitle.setText(R.string.mytasks);
                    ButtonChangeView.animate().alpha(1f).setDuration(1000);
                    showListTask=true;
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        // Αν ο χρήστης πατήσει το κουμπί <Νεα εργασία/newTask> ανοίγει το νέο activity στην οθόνη του χρήστη, addNewTask με το intent
        newTask = findViewById(R.id.newTask);
        newTask.setTransformationMethod(null);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,addNewTask.class));

            }
        });

        // Αν ο χρήστης πατήσει το κουμπί <Νεο αυτοκίνητο/newCar> ανοίγει το νέο activity στην οθόνη του χρήστη, addNewCar με το intent
        newCar = findViewById(R.id.newCar);
       newCar.setTransformationMethod(null);
       newCar.setOnClickListener(new View.OnClickListener() {
      @Override
          public void onClick(View v) {

          startActivity(new Intent(MainActivity.this,addNewCar.class));

      }
     });

    }
    // στην παρακάτω μέθοδο, όταν ο χρήστης πατάει το πίσω, τον ενημερώνει η εφαρμογή αν θέλει
    // να ξαναπατήσει για να βγει από την εφαρμογή
    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            finishAffinity();
            moveTaskToBack(true);
        } else {
            backToast = Toast.makeText(getBaseContext(), R.string.pressBack, Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}
