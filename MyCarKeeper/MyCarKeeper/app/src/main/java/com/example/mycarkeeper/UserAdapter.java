package com.example.mycarkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;


class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    List<Task> tasks;
  Context context;
    public UserAdapter(List<Task>tasks, Context context){
        this.tasks=tasks;
        this.context=context;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.user_task, parent,false);
    return  new ViewHolder(view);
    }

    //Για τα αντικείμενα στο RecyclerView έχουμε τον holder και θέτουμε τις τιμές στα στοιχεία του user_row που περιέχει
    //τις πληροφορίες εγγραφής για το κάθε εργασία που εισάγει ο χρήστης
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, final int position) {
        holder.firstTask.setText(tasks.get(position).getTaskMessage());
        holder.textViewDate.setText(tasks.get(position).getTaskDate());
        holder.editTextCarModel.setText(tasks.get(position).getCarsName());
        holder.editTextTaskPlate.setText(tasks.get(position).getPlateName());
        holder.editTextTaskResponsible.setText(tasks.get(position).getRespon());

        // Όταν ο χρήστης πατήσει διαγραφή  σε ένα συγκεκριμένο view, αρχικά καλείται η alertdialog για να ελέγξει
        // αν όντως επιθυμεί ο χρήστης τη διαγραφή
       holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showDialog(position);
                switch (v.getId()){
                    case R.id.buttonDelete:
                        alertDialog();
                        break;
                }
            }

           // Αν ο χρήστης επιλέξει Ναι για την διαγραφή τότε το στοιχείο διαγράφεται απο τη βάση και εμφανίζεται
           // ένα μήνυμα toast για να ενημερώσει τον χρήστη, αν όχι του εμφανίζεται toast οτι ακυρώθηκε.
           private void alertDialog() {
               AlertDialog.Builder dialog=new AlertDialog.Builder(context);
               dialog.setMessage(R.string.choose);
               dialog.setTitle(R.string.deleteCheck);
               dialog.setPositiveButton(R.string.yes,
                       new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog,
                                               int which) {
                               AppDatabase db = Room.databaseBuilder(context, AppDatabase.class,"production")
                                       .allowMainThreadQueries().build();
                               db.taskDao().deleteTask(tasks.get(position));
                               //διαγραφή της συγκεκριμένης εργασίας που έχει πατηθεί
                               tasks.remove(position);
                               notifyDataSetChanged();
                               Toast.makeText(context.getApplicationContext(),R.string.deleteToast,Toast.LENGTH_SHORT).show();
                           }
                       });
               dialog.setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(context.getApplicationContext(),R.string.cancelToast,Toast.LENGTH_SHORT).show();
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

        return tasks.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        public  TextView firstTask;
        public TextView textViewDate;
        public TextView  editTextCarModel;
        public TextView editTextTaskPlate;
        public TextView editTextTaskResponsible;
        public ImageButton deleteButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstTask = itemView.findViewById(R.id.firstTask);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            editTextCarModel=itemView.findViewById(R.id.editTextCarModel);
            editTextTaskPlate=itemView.findViewById(R.id.editTextTaskPlate);
            editTextTaskResponsible=itemView.findViewById(R.id.editTextTaskResponsible);
            deleteButton =itemView.findViewById(R.id.buttonDelete);

        }

    }

}
