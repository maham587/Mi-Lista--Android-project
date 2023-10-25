package togolam.fr.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity4 extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    LinearLayout article_layout;
    EditText editText1;
    EditText editText2;
    EditText editText3;

    Spinner spinner;
    RecyclerView recyclerView;
    ImageButton imageButton;



    ArticleListAdapter ArticleListAdapter;
    ArrayList<String> stringArray = new ArrayList<>();
    EditText edit1;
    EditText edit2;
    Spinner dialogSpiner;
    ArrayList<ArrayList<String>> completedArticleList = new ArrayList<>();

    int TABLE_ORDER;
    ArrayList<String> stringArrayList = new ArrayList<>();
    LocalSQLiteOpenHelper lsop;
    ManagingArticleData manage;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.dark_blue));
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#0892D0")));
        getSupportActionBar().setTitle(getResources().getString(R.string.modif));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
        getIntent();



        editText1 = (EditText) findViewById(R.id.date_txt);
        editText2 = (EditText) findViewById(R.id.hour);
        editText3 = (EditText) findViewById(R.id.market_txt);
        spinner = (Spinner) findViewById(R.id.description_txt);
        recyclerView = (RecyclerView) findViewById(R.id.myrecycler);
        ArrayList<String> arrayList = new ArrayList<>();
        lsop = new LocalSQLiteOpenHelper(this);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("RawNumber", Context.MODE_PRIVATE);
        int mraw = sp.getInt("raw",1);




        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.descriptions, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        arrayList = lsop.getRaw(mraw);
        editText1.setText(arrayList.get(0));
        editText2.setText(arrayList.get(1));
        editText3.setText(arrayList.get(2));
        setSpinText(spinner, arrayList.get(3));

        manage = new ManagingArticleData();
        if(arrayList.get(4).length() != 0){
            completedArticleList = manage.getArralistFromBuider(arrayList.get(4), completedArticleList);
            stringArray = getStringArray(completedArticleList);
//

        }


        ArticleListAdapter = new ArticleListAdapter(completedArticleList,  stringArray, this, this);
//
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(ArticleListAdapter);





        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(MainActivity4.this.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editText3.getWindowToken(), 0);
                return false;
            }
        });


        article_layout = (LinearLayout) findViewById(R.id.articles_layout);
        imageButton = (ImageButton) findViewById(R.id.add_shop);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOnearticle();
            }


        });


        final Calendar myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        editText1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity4.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //

        new TimeSetter(editText2);


        LocalSQLiteOpenHelper sqLiteOpenHelper = new LocalSQLiteOpenHelper(this);
        TABLE_ORDER = sqLiteOpenHelper.getProfilesCount();


    }


    public void CreateOnearticle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = MainActivity4.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.creating_article, null);
        builder.setView(view);
        edit1 = (EditText) view.findViewById(R.id.article_name_edit);
        edit2 = (EditText) view.findViewById(R.id.article_quantity_edit);
        dialogSpiner = (Spinner) view.findViewById(R.id.dialogSpiner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.quantity, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogSpiner.setAdapter(adapter);

        dialogSpiner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(MainActivity4.this.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(edit2.getWindowToken(), 0);
                return false;
            }
        });
        //editText.setText("test label");
        builder.setPositiveButton(getResources().getString(R.string.ajouter), new DialogInterface.OnClickListener() {
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
                    stringArray.add(0, str1 + " x " + str2 + " " + str3);
                    ArticleListAdapter.notifyDataSetChanged();

                    ArticleListAdapter.addArticle(0, arrayList);
                    builder.setCancelable(true);
                }
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.annuler), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);

            }
        });

        builder.setCancelable(false);
        builder.setTitle(getResources().getString(R.string.ajout));
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }


    private void updateLabel(final Calendar calendar) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText1.setText(sdf.format(calendar.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.article_menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();
                return true;

            case R.id.saving:
                getTextFromeditText();

                return true;


        }
        return super.onOptionsItemSelected(item);

    }

    public void getTextFromeditText() {


        String str1 = editText1.getText().toString();
        String str2 = editText2.getText().toString();
        String str3 = editText3.getText().toString();
        String str4 = String.valueOf(spinner.getSelectedItem());
        stringArrayList.add(str1);
        stringArrayList.add(str2);
        stringArrayList.add(str3);
        stringArrayList.add(str4);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("RawNumber", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int raw = sp.getInt("raw", 0) ;
        if (str1.length() != 0 && str2.length() != 0 && str3.length() != 0) {

            lsop.update_raw(raw,stringArrayList, completedArticleList);;
            goingTOWelcomeScreen();
        }
        editor.clear();
        editor.apply();


    }

    public void TextCompletedConfirmation() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.dialogTheme);


        builder.setPositiveButton(getResources().getString(R.string.daccord), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });


        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.alerte));
        builder.create();
        builder.show();

    }

    public void goingTOWelcomeScreen() {
        Intent intent = new Intent(MainActivity4.this, MainActivity2.class);

        startActivity(intent);

    }

    public ArrayList<String> getStringArray(ArrayList<ArrayList<String>> arrayLists){
        ArrayList<String> stringArray = new ArrayList<>();
        for(int i = 0; i < arrayLists.size(); i++){
            ArrayList<String> array = arrayLists.get(i);

            String string = array.get(0) + " x " + array.get(1) + " " + array.get(2);

            stringArray.add(string);
        }
        return stringArray;
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


}




















