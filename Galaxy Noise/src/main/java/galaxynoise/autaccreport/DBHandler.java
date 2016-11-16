package galaxynoise.autaccreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semjeromers on 11/7/2016.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Report_db";

    private static final String TABLE_NAME ="User";

    public static final String COLUMN_ID ="uid";
    public static final String COLUMN_USER ="username";
    public static final String COLUMN_PASSWORD ="password";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " TEXT PRIMARY KEY,"
                + COLUMN_USER + " TEXT,"
                + COLUMN_PASSWORD +" TEXT," + ")";
        db.execSQL(CREATE_USERS_TABLE_QUERY);
        Log.d("Create","db has been created......");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }
    User getUser(String id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_ID,
                        COLUMN_USER, COLUMN_PASSWORD}, COLUMN_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex(DBHandler.COLUMN_ID);
        int userIndex = cursor.getColumnIndex(DBHandler.COLUMN_USER);
        int passIndex = cursor.getColumnIndex(DBHandler.COLUMN_PASSWORD);



        User user=new User(cursor.getInt(idIndex),cursor.getString(userIndex),cursor.getString(passIndex));

        return user;
    }

    public Cursor selectQuery(String query) {
        Cursor c1 = null;

        SQLiteDatabase db = this.getReadableDatabase();
        c1 = db.rawQuery(query, null);

        return c1;

    }


/*
    public void delectUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getUser_id())});
        db.close();

    }
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getUser_name());
        values.put(COLUMN_BDATE,user.getUser_bDate());
        values.put(COLUMN_GENDER, user.getUser_gender());
        values.put(COLUMN_MNUMBER,user.getUser_mNumber());


        return db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[] { String.valueOf(user.getUser_id()) });
    }

*/
    /*
    public List<User> selectAll() {
        ArrayList<User> contactList = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);



        if (cursor.moveToFirst()) {
            do {
                User contact = new User();
                contact.setUser_id(cursor.getString(0));
                contact.setUser_name(cursor.getString(1));
                contact.setUser_gender(cursor.getString(2));
                contact.setUser_bDate(cursor.getString(3));
                contact.setUser_mNumber(cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();
        // return contact list
        return contactList;
    }

    public List<User> searchUser(String query) {
        ArrayList<User> contactList = new ArrayList<User>();

        String selectQuery = query;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User contact = new User();
                contact.setUser_id(cursor.getString(0));
                contact.setUser_name(cursor.getString(1));
                contact.setUser_gender(cursor.getString(2));
                contact.setUser_bDate(cursor.getString(3));
                contact.setUser_mNumber(cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return contactList;
    }
*/

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
