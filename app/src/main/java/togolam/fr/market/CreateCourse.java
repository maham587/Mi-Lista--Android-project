package togolam.fr.market;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

public class CreateCourse {

    Context context;
    Activity activity;

    public CreateCourse(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void createOnearticle(){
        AlertDialog.Builder builder = new  AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.creating_article, null);
        builder.setView(view);
        //EditText editText = (EditText) dialogView.findViewById(R.id.label_field);
        //editText.setText("test label");
        builder.setPositiveButton("ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        builder.setNegativeButton("annnuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });

        builder.setCancelable(false);
        builder.setTitle("Ajout d'un article");
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }


}
