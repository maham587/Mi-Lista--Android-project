package togolam.fr.market;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity2 extends AppCompatActivity {
    RelativeLayout relativeLayout1 ;
    Menu optionMenu;
    ActionBar actionBar;
    Intent intent;
    LocalSQLiteOpenHelper lop;
    ArrayList<ArrayList<String>> listArrayList = new ArrayList<>();
    PurchaseListAdapter listAdapter;
    RecyclerView recyclerView;
    TextView textView;
    ImageView imageView;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.dark_blue));
        setContentView(R.layout.activity_main2);


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();

                Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                startActivity(intent);
            }
        });


        AdView mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        relativeLayout1 = (RelativeLayout)findViewById(R.id.empty_viwer) ;
        textView = (TextView)findViewById(R.id.addind_text);
        imageView = (ImageView)findViewById(R.id.folder);
        actionBar = getSupportActionBar();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        intent = new Intent(MainActivity2.this, MainActivity.class);
        getIntent();

        lop = new LocalSQLiteOpenHelper(getApplicationContext());
        //SQLiteDatabase db = lop.getReadableDatabase();
        //lop.dropTable(db);
        EmptyArticleCase();



        recyclerView = (RecyclerView)findViewById(R.id.list_recycler);
        listArrayList = lop.getRows(listArrayList);
        Collections.reverse(listArrayList);

        listAdapter = new PurchaseListAdapter( listArrayList, textView, imageView, MainActivity2.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(listAdapter);



    }




    public void helpMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);



        builder.setPositiveButton(getResources().getString(R.string.daccord), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });


        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.helptext));
        builder.create();
        builder.show();
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public void share(String subject, String Text){
        String to = "mescourses.developer@gmail.com";
        if(subject != null) {
            String mailTo = "mailto:" + to +
                    "?&subject=" + Uri.encode(subject) +
                    "&body=" + Uri.encode(Text);
            Intent emailIntent = new Intent(Intent.ACTION_VIEW);
            emailIntent.setData(Uri.parse(mailTo));
            startActivity(emailIntent);
        }else {
            Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        optionMenu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.help:
                helpMessage();
                return true;

            case R.id.developper:
                share(getDeviceName(), "");




        }
        return super.onOptionsItemSelected(item);

    }



    @Override
    protected void onStart() {

        super.onStart();
    }

    public void EmptyArticleCase(){
        LocalSQLiteOpenHelper lop = new LocalSQLiteOpenHelper(getApplicationContext());
        int n = lop.getProfilesCount();
        if(n != 0){
            imageView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else{
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }

    }



}