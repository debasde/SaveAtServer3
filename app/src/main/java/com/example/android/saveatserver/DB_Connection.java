package com.example.android.saveatserver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;


/**
 * Created by deba on 17/6/15.
 */
public class DB_Connection {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "std_name";
    public static final String KEY_AGE = "std_age";
    public static final String KEY_QUALIFICATION = "std_qualification";
    public static final String KEY_EMPORG = "emp_org";
    public static final String KEY_EMPSKILL = "emp_skill";
    public static final String KEY_COLLEGE = "std_colg";
    public static final String KEY_SUBJECT = "std_sub";


    private static final String DATABASE_NAME = "DB_saveatserver";
    private static final String DATABASE_TABLE = "registration";
    private static final int DATABASE_VERSION = 1;

    private  DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public long createEntry(String std_name, String std_age, String std_quali, String std_org, String std_eskill, String std_colg, String std_sub) {

    ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, std_name);
        cv.put(KEY_AGE, std_age);
        cv.put(KEY_QUALIFICATION, std_quali);
        cv.put(KEY_EMPORG, std_org);
        cv.put(KEY_EMPSKILL,std_eskill);
        cv.put(KEY_COLLEGE, std_colg);
        cv.put(KEY_SUBJECT, std_sub);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);

    }




    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                            KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
            KEY_AGE + " INTEGER NOT NULL, " +
            KEY_QUALIFICATION + " TEXT NOT NULL, " +
            KEY_EMPORG + " TEXT NOT NULL, " +
            KEY_EMPSKILL + " TEXT NOT NULL, " +
            KEY_COLLEGE + " TEXT NOT NULL, " +
            KEY_SUBJECT + " TEXT NOT NULL);"

            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }
    }

    public DB_Connection(Context c){

        ourContext= c;
    }

    public DB_Connection open() throws SQLException{

        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public String getData() {
        String[] columns = new String[]{ KEY_ROWID, KEY_NAME, KEY_AGE, KEY_QUALIFICATION, KEY_EMPORG, KEY_EMPSKILL, KEY_COLLEGE, KEY_SUBJECT };
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iAge = c.getColumnIndex(KEY_AGE);
        int iQuali = c.getColumnIndex(KEY_QUALIFICATION);
        int iOrg = c.getColumnIndex(KEY_EMPORG);
        int iSkill = c.getColumnIndex(KEY_EMPSKILL);
        int iColg = c.getColumnIndex(KEY_COLLEGE);
        int iSub = c.getColumnIndex(KEY_SUBJECT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result + c.getString(iRow) + " " + c.getString(iName) + " " + c.getString(iAge) + " " + c.getString(iQuali) + " " + c.getString(iOrg) + " " + c.getString(iSkill) + " " + c.getString(iColg) + " " + c.getString(iSub) + "\n";
        }
        return result;
    }

    public void close(){
        ourHelper.close();
    }
}
