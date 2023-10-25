package togolam.fr.market;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.IOException;
import java.util.ArrayList;



public class LocalSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final String COURSE_INFO = "COURSE_INFO";
    public static final String DATE = "DATE";
    public static final String MARKET_NAME = "MARKET_NAME";
    public static final String COURSE_DESCRIPTION = "COURSE_DESCRIPTION1";
    public static final String ID = "id";
    public static final String DONE = "DONE";
    public static final String HOUR = "HOUR";
    public static final String ARTICLE_DATAS = "ARTICLE_DATAS";
    static String DB_NAME = "articles.db";
    static int DB_VERSION = 4;

    Context context;



    @Override
    public void onCreate(SQLiteDatabase DB) {

        try {
            String queryString1 = "CREATE TABLE " + COURSE_INFO+ " (" + ID + " INTEGER PRIMARY KEY, " + DATE + " TEXT, " + HOUR + " TEXT, " + MARKET_NAME + " TEXT, " + COURSE_DESCRIPTION + " TEXT, " + DONE + " TEXT, " + ARTICLE_DATAS + " TEXT)";
            DB.execSQL(queryString1);
        } catch (SQLiteException e) {
            try {
                throw new IOException(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        

    }
    public LocalSQLiteOpenHelper(Context context){

        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public void insert(ArrayList<String> strings, ArrayList<ArrayList<String>> _arrayLists ) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ManagingArticleData managing = new ManagingArticleData();
        ContentValues cv = new ContentValues();
        cv.put(DATE, strings.get(0));
        cv.put(HOUR, strings.get(1));
        cv.put(MARKET_NAME, strings.get(2));
        cv.put(COURSE_DESCRIPTION, strings.get(3));
        cv.put(DONE, "0");
        String articles = managing.getStringBuilder(_arrayLists);
        cv.put(ARTICLE_DATAS, articles);

        long my_long = myDB.insert(COURSE_INFO, null, cv);
       // Toast.makeText(context, "long2 = " + my_long, Toast.LENGTH_SHORT ).show();


    }

    public void delete(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSE_INFO,ID + "=?",new String[]{String.valueOf(id)});

        db.close();
        update_id();


    }

    public ArrayList<ArrayList<String>> getRows(ArrayList<ArrayList<String>> arrayListArrayList){

        int size = getProfilesCount();
        for(int i = 1; i < size + 1 ; i++) {
            ArrayList<String> stringArrayList = getRaw(i);
            arrayListArrayList.add(stringArrayList);
        }

        return arrayListArrayList;

    }

    public ArrayList<String> getRaw(int id){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, COURSE_INFO, new String[]{ID, DATE,HOUR, MARKET_NAME, COURSE_DESCRIPTION, ARTICLE_DATAS, DONE}, ID + " = " + String.valueOf(id), null, null, null, null, null);
        if(cursor.moveToFirst()){
            arrayList.add(cursor.getString(1));
            arrayList.add(cursor.getString(2));
            arrayList.add(cursor.getString(3));
            arrayList.add(cursor.getString(4));
            arrayList.add(cursor.getString(5));
            arrayList.add(cursor.getString(6));

        }cursor.close();
        db.close();
        return arrayList;
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + COURSE_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void dropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_INFO);

        onCreate(db);
    }


    public void update_id(){
        ContentValues cv = new ContentValues();
        ArrayList<String> array = get_id_list();
        SQLiteDatabase db = this.getWritableDatabase();

        for(int i = 0; i < array.size() ; i ++) {
            cv.put(ID, String.valueOf(i + 1));
            db.update(COURSE_INFO, cv, ID + "=?", new String[]{array.get(i)});
        }

        db.close();
    }

    public void update_raw(int raw, ArrayList<String> strings, ArrayList<ArrayList<String>> _arrayLists){
        ContentValues cv = new ContentValues();
        ManagingArticleData managing = new ManagingArticleData();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(ID, String.valueOf(raw));
        cv.put(DATE, strings.get(0));
        cv.put(HOUR, strings.get(1));
        cv.put(MARKET_NAME, strings.get(2));
        cv.put(COURSE_DESCRIPTION, strings.get(3));
        String articles = managing.getStringBuilder(_arrayLists);
        cv.put(ARTICLE_DATAS, articles);
        db.update(COURSE_INFO, cv, ID + "=?", new String[]{String.valueOf(raw)});
        db.close();
    }

    public ArrayList<String> get_id_list(){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(true, COURSE_INFO, new String[]{ID, DATE,HOUR, MARKET_NAME, COURSE_DESCRIPTION, ARTICLE_DATAS}, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            arrayList.add(cursor.getString(0));
        }cursor.close();
        db.close();
        return arrayList;
    }


    public void change_done_value(int id, String done){
        ContentValues cv = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();
        cv.put(DONE, done);
        db.update(COURSE_INFO, cv, ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }


    public boolean done_state(int id){
        String s = "3";

        s = getRaw(id).get(5);

        if(s == null) {
            s = "2";
        }
        return s.equals("1");



    }



}
