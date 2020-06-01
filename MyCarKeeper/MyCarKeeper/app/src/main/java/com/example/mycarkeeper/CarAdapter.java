package com.example.mycarkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;

class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    List<Car> cars;
    Context context1;
    public CarAdapter(List<Car> cars, Context context1) {
        this.cars = cars;
        this.context1=context1;
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_row,parent,false);
       return  new ViewHolder(view);
    }

    //Για τα αντικείμενα στο RecyclerView έχουμε τον holder και θέτουμε τις τιμές στα στοιχεία του car_row που περιέχει
    //τις πληροφορίες εγγραφής για το κάθε αυτοκίνητο που εισάγει ο χρήστης
    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, final int position) {
        holder.firstCar.setText(cars.get(position).getNewcarname());
        holder.firstCarModel1.setText(cars.get(position).getNewcarbrand());
        holder.firstTaskPlate1.setText(cars.get(position).getNewcarplate());

        // Όταν ο χρήστης πατήσει διαγραφή  σε ένα συγκεκριμένο view, αρχικά καλείται η alertdialog για να ελέγξει
        // αν όντως επιθυμεί ο χρήστης τη διαγραφή
        holder.deleteButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.buttonDelete1:
                        alertDialog();
                        break;
                }
            }

            // Αν ο χρήστης επιλέξει Ναι για την διαγραφή τότε το στοιχείο διαγράφεται απο τη βάση και εμφανίζεται
            // ένα μήνυμα toast για να ενημερώσει τον χρήστη, αν όχι του εμφανίζεται toast οτι ακυρώθηκε.
            private void alertDialog() {
                AlertDialog.Builder dialog=new AlertDialog.Builder(context1);
                dialog.setMessage(R.string.choose);
                dialog.setTitle(R.string.deleteCheck);
                dialog.setPositiveButton(R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                CarDatabase db1 = Room.databaseBuilder(context1, CarDatabase.class,"production2")
                                        .allowMainThreadQueries().build();
                                db1.carDao().deleteCar(cars.get(position));
                                //διαγραφή της συγκεκριμένης εγγραφής αυτοκινήτου που έχει επιλεγεί
                                cars.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context1,R.string.deleteToast,Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog.setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context1,R.string.cancelToast,Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
                //notifyAll();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstCar;
        public TextView firstCarModel1;
        public TextView firstTaskPlate1;
        public ImageButton deleteButton1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstCar=itemView.findViewById(R.id.firstTask1);
            firstCarModel1=itemView.findViewById(R.id.firstTaskModel);
            firstTaskPlate1=itemView.findViewById(R.id.firstTaskPlate);
            deleteButton1 =itemView.findViewById(R.id.buttonDelete1);
        }
    }
}
