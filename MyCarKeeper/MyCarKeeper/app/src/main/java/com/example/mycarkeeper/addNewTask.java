package com.example.mycarkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class addNewTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

     EditText editTextTask;
     TextView textViewDate;
     AutoCompleteTextView editTextCarModel;
    AutoCompleteTextView editTextTaskPlate;
     EditText editTextTaskResponsible;
     Button saveTask;
     private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        //Αρχικοποιούμε τα στοιχεία που έχει το xml της συγκεκριμένης κλάσης ώστε να τα πάρουμε μετά με το
        // id τους και να χρησιμοποιήσουμε τις τιμές που θα εισάγει ο χρήστης ή να κάνουμε τις ενέργειες που πρέπει
        textViewDate = findViewById(R.id.textViewDate);
        saveTask=findViewById(R.id.saveTask);
        editTextTask=findViewById(R.id.editTextTask);
        editTextCarModel= findViewById(R.id.editTextCarModel);
        editTextTaskPlate = findViewById(R.id.editTextTaskPlate);
        editTextTaskResponsible = findViewById(R.id.editTextTaskResponsible);
        ImageView image = findViewById(R.id.imageView2);
        Button dateTextButton = findViewById(R.id.dateTextButton);
        dateTextButton.setTransformationMethod(null);


        //
        //Εδώ Αρχικοποιούμε και παίρνουμε από την βάση που περιέχει τα αυτοκίνητα του χρήστη όλα τα ονόματα μοντέλων αυτοκινήτων που έχει εισάγει ο χρήστης
        // Έπειτα τα αποθηκεύουμε στην λιστα Brands.
        final CarDatabase db1= Room.databaseBuilder(getApplicationContext(),CarDatabase.class,"production2")
                .allowMainThreadQueries()
                .build();

        List<String> Brands = db1.carDao().getAllBrands();

        // Με την Glide φορτώνουμε την εικόνα στο ImageView ώστε
        // σε συσκευές με μικρή μνήμη, να μην κρασάρει η εφαρμογή λόγω του μεγάλου μεγέθους της εικόνας.
        Glide.with(this).load(R.drawable.pen).into( image);


        // Δημιουργούμε έναν Adapter ώστε όταν ο χρήστης πάει να συμπληρώσει το editTextCarModel
        // και γράφωντας τα δύο πρώτα γράμματα του μοντέλου να φορτώνει σαν πρόταση αντιστοιχίες μοντέλων αυτοκινήτων
        //που υπάρχουν ήδη στη βάση και έχουν αποθηκευτεί λίγο πιο πάνω στη λίστα Brands.
        ArrayAdapter<String> adapterBrand = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Brands);
        editTextCarModel.setAdapter(adapterBrand);
        context=this;
        // Με τον TextWatcher παρακάτω, όταν ο χρήστης έχει συμπληρώσει το μοντέλου του αυτοκινήτου στο editTextCarModel
        // παίρνει το μοντέλο σαν κλειδι 's' για την afterTextChanged(Editable s) έτσι ώστε να αναζητήσει
        //στην βάση εγγραφές με το συγκεκριμένο όνομα μοντέλου και να του εμφανίσει τις πινακίδες που
        // αντιστοιχούν στο συγκεκριμένο/να μοντέλα αυτοκινήτων που έχει συμπληρώσει παραπάνω
        editTextCarModel.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                List<String> CarPlates=db1.carDao().getAllPlates(s.toString());
                ArrayAdapter<String> adapterPlate = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1,CarPlates);
                editTextTaskPlate.setAdapter(adapterPlate);

                // TODO Auto-generated method stub
            }
        });


        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,"production")
                .allowMainThreadQueries().build();

        // Αν ο χρήστης πατήσει το κουμπί για την επιλογή ημερομηνίας, καλείται η μέθοδος showDatePickerDialog
        // για να εμφανιστεί το ημερολόγιο και να επιλέξει
        findViewById(R.id.dateTextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        saveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save to db
                // Κάθε φορά που ο χρήστης πατάει το κουμπί για την αποθήκευση κάνουμε έλεγχο μέσα στον click listener ώστε
                // αν κάποιο απο τα πεδία που πρέπει να συμπληρώσει είναι άδειο να μη προχωρά σε αποθήκευση της βάσης αλλά να του βγάζει ένα εικονίδιο και ένα μήνυμα λάθους
                // ώστε ο χρήστης να ξέρει τι έχει παραλείψει
                if(TextUtils.isEmpty(editTextTask.getText().toString())) {
                    editTextTask.setError(getText(R.string.nameTask_error));
                    return;
                }

                if(TextUtils.isEmpty(editTextCarModel.getText().toString())) {
                    editTextCarModel.setError(getText(R.string.carModelTask_error));
                    return;
                }
                if(TextUtils.isEmpty(editTextTaskPlate.getText().toString())) {
                    editTextTaskPlate.setError(getText(R.string.newTask_plate_error));
                    return;
                }
                if(TextUtils.isEmpty(editTextTaskResponsible.getText().toString())) {
                    editTextTaskResponsible.setError(getText(R.string.newTaskYp_error));
                    return;
                }
                if(TextUtils.isEmpty(textViewDate.getText().toString())) {
                    textViewDate.setError(getText(R.string.new_date_error));
                    return;
                }
                // Αν έχει βάλει όλα τα πεδία τότε δημιουργούμε ένα αντικείμενο της κλάσης Task με τις τιμές των
                // πεδίων και έπειτα τα αποθηκεύουμε μέσω του αντικειμένου task στη βάση db καλώντας τη μέθοδο insertAll
                Task task = new Task(editTextTask.getText().toString(),textViewDate.getText().toString(),editTextCarModel.getText().toString(),editTextTaskPlate.getText().toString(),editTextTaskResponsible.getText().toString());
                db.taskDao().insertAll(task);
                // Αφού γίνει η αποθήκευση η εφαρμογή πάει τον χρήστη στην αρχική οθόνη
                startActivity(new Intent(addNewTask.this,MainActivity.class));

            }
        });

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int montha = month+1;
        String date= dayOfMonth + "/" + montha + "/" + year;
        textViewDate.setText(date);
    }

    public void onBackPressed() {

        startActivity(new Intent(addNewTask.this,MainActivity.class));

    }

}
