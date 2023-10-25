package togolam.fr.market;

 
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PurchaseListAdapter extends RecyclerView.Adapter<PurchaseListAdapter.ViewHolder> {

    ArrayList<ArrayList<String>> PurchaseList= new ArrayList<>();

    Context context;
    TextView textView;
    ImageView imageView;





    public PurchaseListAdapter(ArrayList<ArrayList<String>> PurchaseList, TextView textView, ImageView imageView, Context context) {
        this.PurchaseList = PurchaseList;
        this.context = context;
        this.imageView = imageView;

        this.textView = textView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public final TextView market_name;
        public CardView mCardView;
        public TextView date;
        public ImageButton editButton;
        public ImageButton deleteButton;
        SwitchCompat switchCompat;
        ArrayList<ArrayList<String>> PurchaseList= new ArrayList<>();
        Context context;
        PurchaseListAdapter purchaseListAdapter;




        public ViewHolder(View view, Context context, ArrayList<ArrayList<String>> PurchaseList, PurchaseListAdapter purchaseListAdapter )
        {


            super(view);
            market_name =(TextView)view.findViewById(R.id.market_name);
            mCardView = (CardView) view.findViewById(R.id.card_view);
            date = (TextView) view.findViewById(R.id.date);
            editButton = (ImageButton)view.findViewById(R.id.editing) ;
            deleteButton = (ImageButton) view.findViewById(R.id.deleting) ;
            switchCompat = (SwitchCompat)view.findViewById(R.id.switch64) ;
            this.context = context;
            this.PurchaseList = PurchaseList;
            this.purchaseListAdapter = purchaseListAdapter;




        }


        }



    @NonNull
    @Override
    public PurchaseListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {




        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitems, parent, false);



        PurchaseListAdapter.ViewHolder vh = new PurchaseListAdapter.ViewHolder(v, context, PurchaseList, this);
        return vh;
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    @Override
    public void onBindViewHolder(PurchaseListAdapter.ViewHolder holder, int position) {
        LocalSQLiteOpenHelper lop = new LocalSQLiteOpenHelper(context);
        holder.market_name.setText( context.getResources().getString(R.string.str1) + PurchaseList.get(position).get(2));
        holder.date.setText(context.getResources().getString(R.string.str2) + PurchaseList.get(position).get(0) + context.getResources().getString(R.string.str3) + PurchaseList.get(position).get(1));
        //if(position == 0){
        //    holder.mCardView.setBackground( new ColorDrawable(Color.parseColor("#ecf1f8")));
        //}
        int id = getItemCount() - position;

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               confiramtion(id, position);







            }
        });


        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("RawNumber", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.putInt("raw", id );
                editor.apply();
                Intent intent = new Intent(context, MainActivity4.class);


                context.startActivity(intent);

            }
        });

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences("RawNumber", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.putInt("raw", id );
                editor.apply();
                Intent intent = new Intent(context, MainActivity5.class);
                context.startActivity(intent);
            }
        });

        holder.switchCompat.setChecked(lop.done_state(id));
        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){lop.change_done_value(id,"1");}
                else {
                    lop.change_done_value(id, "0");
                }
            }
        });



    }
    @Override
    public int getItemCount() {

        return PurchaseList.size();

    }

    public void confiramtion(int id, int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogCustom);



        builder.setPositiveButton(context.getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                LocalSQLiteOpenHelper lop = new LocalSQLiteOpenHelper(context);
                lop.delete(id );
                PurchaseList.remove(position);
                notifyDataSetChanged();
                if(getItemCount() == 0){
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                }

            }
        });
        builder.setNegativeButton(context.getString(R.string.non), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });

        builder.setCancelable(false);
        builder.setMessage(context.getResources().getString(R.string.deleteinfo));
        builder.create();
        builder.show();

    }


}
