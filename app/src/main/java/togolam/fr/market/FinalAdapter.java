package togolam.fr.market;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FinalAdapter extends RecyclerView.Adapter<FinalAdapter.ViewHolder>
{

    ArrayList<String> articleList;
    Context context;



    public FinalAdapter( ArrayList<String> articleList, Context context) {

        this.articleList = articleList;
        this.context = context;

    }

    @NonNull
    @Override
    public FinalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articleslist, parent, false);

        FinalAdapter.ViewHolder vh = new FinalAdapter.ViewHolder( v, context );
        return vh;
    }

    @Override
    public void onBindViewHolder(FinalAdapter.ViewHolder holder, int position) {

        holder.textView.setText(articleList.get(position));







    }
    @Override
    public int getItemCount() {

        return articleList.size();


    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {


        Context context;
        TextView textView;

        public CardView mCardView;



        public ViewHolder(View view, Context context  )
        {
            super(view);
            textView = (TextView) view.findViewById(R.id.final_article_details) ;

            mCardView = (CardView) view.findViewById(R.id.fianl_card_view) ;
            this.context = context;

        }
    }













}