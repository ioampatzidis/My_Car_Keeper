package com.example.mycarkeeper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class addNewCar extends AppCompatActivity {


    //Αρχικοποιούμε τα στοιχεία που έχει το xml της συγκεκριμένης κλάσης ώστε να τα πάρουμε μετά με το
    // id τους και να χρησιμοποιήσουμε τις τιμές που θα εισάγει ο χρήστης ή να κάνουμε τις ενέργειες που πρέπει
    // όταν ο χρήστης πατήσει το κουμπί buttonSaveNewCar
    EditText editTextTaskNewCar;
    EditText editTextTaskNewCarBrand;
    EditText editTextTaskNewCarPlate;
    Button buttonSaveNewCar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_car);

        //παίρνουμε τα στοιχεία του xml με βάση το id τους
        editTextTaskNewCar = findViewById(R.id.editTextNewCar);
        editTextTaskNewCarBrand = findViewById(R.id.editTextNewCarBrand);
        editTextTaskNewCarPlate = findViewById(R.id.editTextNewCarPlate);
        buttonSaveNewCar = findViewById(R.id.buttonSaveNewCar);

        // Με την Glide φορτώνουμε την εικόνα στο ImageView ώστε
        // σε συσκευές με μικρή μνήμη, να μην κρασάρει η εφαρμογή λόγω του μεγάλου μεγέθους της εικόνας.
        ImageView image = findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.addcar).into(image);

        final CarDatabase db1 = Room.databaseBuilder(getApplicationContext(), CarDatabase.class, "production2")
                .allowMainThreadQueries()
                .build();

        // Κάθε φορά που ο χρήστης πατάει το κουμπί για την αποθήκευση κάνουμε έλεγχο μέσα στον click listener ώστε
        // αν κάποιο απο τα EditText είναι άδειο να μη προχωρά σε αποθήκευση της βάσης αλλά να του βγάζει ένα εικονίδιο και ένα μήνυμα λάθους
        // ώστε ο χρήστης να ξέρει τι έχει παραλείψει
        buttonSaveNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editTextTaskNewCar.getText().toString())) {
                    editTextTaskNewCar.setError(getText(R.string.car_error));
                    return;
                }

                if (TextUtils.isEmpty(editTextTaskNewCarBrand.getText().toString())) {
                    editTextTaskNewCarBrand.setError(getText(R.string.brand_error));
                    return;
                }
                if (TextUtils.isEmpty(editTextTaskNewCarPlate.getText().toString())) {
                    editTextTaskNewCarPlate.setError(getText(R.string.plate_error));
                    return;
                }
                // Αν έχει βάλει όλα τα πεδία τότε το δημιουργούμε ένα αντικείμενο της κλάσης Car με τις τιμές των
                // editTexts και έπειτα τα αποθηκεύουμε  μέσω του αντικειμένου car στη βάση db1 καλώντας τη μέθοδο insertAll
                Car car = new Car(editTextTaskNewCar.getText().toString(), editTextTaskNewCarBrand.getText().toString(), editTextTaskNewCarPlate.getText().toString());
                db1.carDao().insertAll(car);
                // Αφού γίνει η αποθήκευση η εφαρμογή πάει τον χρήστη στην αρχική οθόνη
                startActivity(new Intent(addNewCar.this,MainActivity.class));


            }
        });


    }
    public void onBackPressed() {

        startActivity(new Intent(addNewCar.this,MainActivity.class));

    }

}
