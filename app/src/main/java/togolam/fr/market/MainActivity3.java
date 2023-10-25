package togolam.fr.market;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity3 extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    LinearLayout article_layout;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    Spinner spinner;
    RecyclerView recyclerView;
    ImageButton imageButton;
    CreateCourse createCourse;
    ArticleListAdapter ArticleListAdapter;
    ArrayList<String> stringArray = new ArrayList<>();
    EditText edit1;
    EditText edit2;
    Spinner dialogSpiner;
    ArrayList<ArrayList<String>> completedArticleList = new ArrayList<>();
    int TABLE_ORDER;
    ArrayList<String> stringArrayList = new ArrayList<>();
    LocalSQLiteOpenHelper lsop;




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.dark_blue));
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#0892D0")));
        getSupportActionBar().setTitle(getResources().getString(R.string.nouvelle_course));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        lsop = new LocalSQLiteOpenHelper(this);
        createCourse = new CreateCourse(this, this);


        editText1 = (EditText) findViewById(R.id.date_txt);
        editText2 = (EditText) findViewById(R.id.hour);
        editText3 = (EditText) findViewById(R.id.market_txt);
        spinner = (Spinner)  findViewById(R.id.description_txt);
        recyclerView = (RecyclerView) findViewById(R.id.myrecycler) ;


        ArticleListAdapter = new ArticleListAdapter(completedArticleList , stringArray, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(ArticleListAdapter);




        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.descriptions, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(MainActivity3.this.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(editText3.getWindowToken(), 0);
                return false;
            }
        });



        article_layout = (LinearLayout)findViewById(R.id.articles_layout) ;
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
                new DatePickerDialog(MainActivity3.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //

        new TimeSetter(editText2);


        LocalSQLiteOpenHelper sqLiteOpenHelper = new LocalSQLiteOpenHelper(this);
        TABLE_ORDER = sqLiteOpenHelper.getProfilesCount();



    }



    public void CreateOnearticle(){
        AlertDialog.Builder builder = new  AlertDialog.Builder(this);
        LayoutInflater inflater = MainActivity3.this.getLayoutInflater();
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
                InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(MainActivity3.this.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(edit2.getWindowToken(), 0);
                return false;
            }
        });
        //editText.setText("test label");
        builder.setPositiveButton(getResources().getText(R.string.ajouter), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String str1 = edit1.getText().toString();
                String str2 = edit2.getText().toString();
                String str3 = dialogSpiner.getSelectedItem().toString();
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.add(str1);
                arrayList.add(str2);
                arrayList.add(str3);
                if(str1.length() != 0 & str2.length() != 0){
                    stringArray.add(0, str1 + " x " + str2 + " " + str3);
                    ArticleListAdapter.notifyDataSetChanged();

                    ArticleListAdapter.addArticle(0, arrayList);
                    builder.setCancelable(true);
                }
            }
        });
        builder.setNegativeButton(getResources().getText(R.string.annuler), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);

            }
        });

        builder.setCancelable(false);
        builder.setTitle(getResources().getText(R.string.ajout));
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }




    private void updateLabel(final Calendar calendar) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

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

            case  R.id.saving:


                getTextFromeditText();


                return true;



        }
        return super.onOptionsItemSelected(item);

    }

    public void getTextFromeditText(){


        String str1 = editText1.getText().toString();
        String str2 = editText2.getText().toString();
        String str3 = editText3.getText().toString();
        String str4 =  String.valueOf(spinner.getSelectedItem());
        stringArrayList.add(str1);
        stringArrayList.add(str2);
        stringArrayList.add(str3);
        stringArrayList.add(str4);

        if ( str1.length() != 0 && str2.length() != 0 && str3.length() != 0) {

            lsop.insert(stringArrayList, completedArticleList);

            goingTOWelcomeScreen();
        }else{
            TextCompletedConfirmation();
            stringArrayList.clear();
        }




    }

    public void TextCompletedConfirmation(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);



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

    public void goingTOWelcomeScreen(){
        Intent intent = new Intent(MainActivity3.this, MainActivity2.class);

        startActivity(intent);

    }
    public String getStringPart(String s, int t){
        return String.valueOf(s.charAt(t) + s.charAt(t + 1));
    }
    public void confiramtion(int id, int position){



    }

    





}