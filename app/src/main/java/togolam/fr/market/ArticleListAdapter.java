package togolam.fr.market;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ViewHolder>
{
    ArrayList<ArrayList<String>> completedArticleList;
    ArrayList<String> articleList;
    Context context;
    Activity activity;


    public ArticleListAdapter(ArrayList<ArrayList<String>> completedArticleList, ArrayList<String> articleList, Context context, Activity activity) {
        this.completedArticleList = completedArticleList;
        this.articleList = articleList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ArticleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adding_article_item, parent, false);

        ArticleListAdapter.ViewHolder vh = new ArticleListAdapter.ViewHolder( v, context );
        return vh;
    }

    @Override
    public void onBindViewHolder(ArticleListAdapter.ViewHolder holder, int position) {

        holder.textView.setText(articleList.get(position));
        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                articleList.remove(position);
                completedArticleList.remove(position);
                notifyDataSetChanged();
            }
        });


        holder.imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editingArticleAddded(position);
            }
        });





    }
    @Override
    public int getItemCount() {

        return articleList.size();


    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {


        Context context;
        TextView textView;
        ImageButton imageButton1;
        ImageButton imageButton2;
        public CardView mCardView;



        public ViewHolder(View view, Context context  )
        {
            super(view);
            textView = (TextView) view.findViewById(R.id.article_details) ;
            imageButton1 = (ImageButton) view.findViewById(R.id.editing_article);
            imageButton2 = (ImageButton) view.findViewById(R.id.deleting_article);
            mCardView = (CardView) view.findViewById(R.id.card_view2) ;
            this.context = context;

        }
    }

    public void editingArticleAddded(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.creating_article, null);
        builder.setView(view);
        EditText edit1 = (EditText) view.findViewById(R.id.article_name_edit);
        EditText edit2 = (EditText) view.findViewById(R.id.article_quantity_edit);
        Spinner dialogSpiner = (Spinner) view.findViewById(R.id.dialogSpiner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.quantity, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpiner.setAdapter(adapter);

        edit1.setText(completedArticleList.get(position).get(0));
        edit2.setText(completedArticleList.get(position).get(1));
        setSpinText(dialogSpiner, completedArticleList.get(position).get(2) );


        dialogSpiner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        //editText.setText("test label");
        builder.setPositiveButton(context.getResources().getString(R.string.Modifier), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String str1 = edit1.getText().toString();
                String str2 = edit2.getText().toString();
                String str3 = dialogSpiner.getSelectedItem().toString();
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(str1);
                arrayList.add(str2);
                arrayList.add(str3);
                if (str1.length() != 0 & str2.length() != 0) {
                    // stringArray.add(0, str1 + " x " + str2 + " " + str3);
                    articleList.remove(position);
                    articleList.add(0, str1 + " x " + str2 + " " + str3);
                    notifyDataSetChanged();
                    completedArticleList.remove(position);
                    completedArticleList.add(0, arrayList);

                    builder.setCancelable(true);
                }
            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.annuler), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);

            }
        });

        builder.setCancelable(false);
        builder.setTitle(context.getResources().getString(R.string.modif_art));
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }



    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }

    public void addArticle(int index, ArrayList<String> strings){
         completedArticleList.add(index, strings);
    }





}
