package togolam.fr.market;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Random;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;


public class MainActivity5 extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String>  strings = new ArrayList<>();
    ArrayList<String> arrayList;
    ArrayList<ArrayList<String>> completedArticleList = new ArrayList<>();
    FinalAdapter finalAdapter;
    String subject;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        AdView mAdView = findViewById(R.id.banner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

       Window window = this.getWindow();
       window.setStatusBarColor(ContextCompat.getColor(this, R.color.dark_blue));
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        final InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8964829217767208/4810278905");
        interstitialAd.setAdListener(new AdListener()
        {

            @Override
            public void onAdLoaded()
            {
                if(getRandom() == 0) {
                    interstitialAd.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
            }
        });

        interstitialAd.loadAd(new AdRequest.Builder().build());




        recyclerView = (RecyclerView)findViewById(R.id.finalrecycler);
        TextView textView = (TextView)findViewById(R.id.description_txt) ;
        LocalSQLiteOpenHelper lsop = new LocalSQLiteOpenHelper(getApplicationContext());
            SharedPreferences sp = getApplicationContext().getSharedPreferences("RawNumber", Context.MODE_PRIVATE);
            int mraw = sp.getInt("raw", 1);
            SharedPreferences.Editor editor = sp.edit();
            editor.clear();
            arrayList = lsop.getRaw(mraw);

            getSupportActionBar().setTitle(getResources().getString(R.string.str1) + arrayList.get(2));
            subject = getResources().getString(R.string.str1) + arrayList.get(2);
            textView.setText(getResources().getString(R.string.str2) + arrayList.get(0) + getResources().getString(R.string.str3) + arrayList.get(1) + "\n" + arrayList.get(3));

            try {
            ManagingArticleData manage = new ManagingArticleData();
            if(arrayList.get(4).length() != 0) {

                completedArticleList = manage.getArralistFromBuider(arrayList.get(4), completedArticleList);
                strings = getStringArray(completedArticleList);




                finalAdapter = new FinalAdapter(strings, MainActivity5.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

                recyclerView.setAdapter(finalAdapter);
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }



        Intent intent = new Intent(MainActivity5.this, MainActivity2.class);
        getIntent();

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenuend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case  R.id.sharing:
                share(subject, getText());
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    public String getText() {
        String s = getResources().getString(R.string.no_article);
        try {
            if(strings.size() != 0) {
                s = getResources().getString(R.string.str2) + arrayList.get(0) + getResources().getString(R.string.str3) + arrayList.get(1);//+ "\n" + arrayList.get(3);
                s = s + "\n\n" + getResources().getString(R.string.str4) + "\n";
                for (int i = 0; i < strings.size(); i++) {
                    s = s + "\n" + strings.get(i);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return s;

    }


    public void share(String subject, String Text){
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_SUBJECT,subject);
        intentShare.putExtra(Intent.EXTRA_TEXT,Text);
        startActivity(Intent.createChooser(intentShare, "Shared the text ..."));
    }

    public static int getRandom() {
        int[] array = {0, 1,3};
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

}