package galaxynoise.autaccreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by semjeromers on 11/7/2016.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "reportdb";

    private static final String TABLE_NAME ="User";
    private static final String TABLE_NAME1 ="Incident";
    private static final String TABLE_NAME2 ="Car";
    private static final String TABLE_NAME3 ="Driver";

    //User
    public static final String COLUMN_ID ="uid";
    public static final String COLUMN_USER ="username";
    public static final String COLUMN_PASSWORD ="password";

    //Incident
    public static final String COLUMN_REPORTID ="reportid";
    public static final String COLUMN_INCIDENT_DATE ="incidentdate";
    public static final String COLUMN_LONGI ="longi";
    public static final String COLUMN_LATI ="lati";

    //Car
    public static final String COLUMN_PLATENUMBER ="platenumber";
    public static final String COLUMN_CAR_MAKE ="carmake";
    public static final String COLUMN_CAR_MODEL ="carmodel";
    public static final String COLUMN_CAR_YEAR ="caryear";

    //Driver
    public static final String COLUMN_DRIVER_LINCENSE ="driverlicense";
    public static final String COLUMN_FNAME ="fname";
    public static final String COLUMN_LNAME ="lname";
    public static final String COLUMN_GENDER ="gender";
    public static final String COLUMN_INSURANCE ="insurancenumber";



    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_USER + " TEXT,"
                + COLUMN_PASSWORD +" TEXT," + ")";
        db.execSQL(CREATE_USERS_TABLE_QUERY);

        String CREATE_INCIDENT_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME1 + "("
                + COLUMN_REPORTID + " INTEGER PRIMARY KEY,"
                + COLUMN_INCIDENT_DATE + " DATE,"
                + COLUMN_LONGI + " REAL,"
                + COLUMN_LATI +" REAL," + ")";
        db.execSQL(CREATE_INCIDENT_TABLE_QUERY);

        String CREATE_CAR_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME2 + "("
                + COLUMN_PLATENUMBER + " TEXT PRIMARY KEY,"
                + COLUMN_CAR_MAKE + " TEXT,"
                + COLUMN_CAR_MODEL + " TEXT,"
                + COLUMN_CAR_YEAR +" INTEGER," + ")";
        db.execSQL(CREATE_CAR_TABLE_QUERY);

        String CREATE_DRIVER_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME3 + "("
                + COLUMN_DRIVER_LINCENSE + " TEXT PRIMARY KEY,"
                + COLUMN_FNAME+ " TEXT,"
                + COLUMN_LNAME + " TEXT,"
                + COLUMN_GENDER+" TEXT,"
                + COLUMN_INSURANCE + " TEXT,"+ ")";
        db.execSQL(CREATE_DRIVER_TABLE_QUERY);

        Log.d("Create","db has been created......");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    //GetUser
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

    //GetIncident
    Incident getIncident(String id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME1, new String[] { COLUMN_REPORTID,
                        COLUMN_INCIDENT_DATE, COLUMN_LONGI, COLUMN_LATI}, COLUMN_REPORTID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex(DBHandler.COLUMN_REPORTID);
        int incdateIndex = cursor.getColumnIndex(DBHandler.COLUMN_INCIDENT_DATE);
        int longiIndex = cursor.getColumnIndex(DBHandler.COLUMN_LONGI);
        int latiIndex = cursor.getColumnIndex(DBHandler.COLUMN_LATI);



        Incident incident=new Incident(cursor.getInt(idIndex),cursor.getString(incdateIndex),cursor.getDouble(longiIndex),cursor.getDouble(latiIndex));

        return incident;
    }
    //Get Car
    Car getCar(String id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME2, new String[] { COLUMN_PLATENUMBER,
                        COLUMN_CAR_MAKE, COLUMN_CAR_MODEL, COLUMN_CAR_YEAR}, COLUMN_PLATENUMBER + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        int plateIndex = cursor.getColumnIndex(DBHandler.COLUMN_PLATENUMBER);
        int makeIndex = cursor.getColumnIndex(DBHandler.COLUMN_CAR_MAKE);
        int modelIndex = cursor.getColumnIndex(DBHandler.COLUMN_CAR_MODEL);
        int yearIndex = cursor.getColumnIndex(DBHandler.COLUMN_CAR_YEAR);



        Car car=new Car(cursor.getString(plateIndex),cursor.getString(makeIndex),cursor.getString(modelIndex),cursor.getInt(yearIndex));

        return car;
    }
    //Get Driver
    Driver getDriver(String id) {
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.query(TABLE_NAME3, new String[] { COLUMN_DRIVER_LINCENSE,
                        COLUMN_FNAME, COLUMN_LNAME, COLUMN_GENDER, COLUMN_INSURANCE}, COLUMN_DRIVER_LINCENSE + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        int licIndex = cursor.getColumnIndex(DBHandler.COLUMN_DRIVER_LINCENSE);
        int fnameIndex = cursor.getColumnIndex(DBHandler.COLUMN_FNAME);
        int lnameIndex = cursor.getColumnIndex(DBHandler.COLUMN_LNAME);
        int genderIndex = cursor.getColumnIndex(DBHandler.COLUMN_GENDER);
        int insIndex = cursor.getColumnIndex(DBHandler.COLUMN_INSURANCE);



        Driver driver=new Driver(cursor.getString(licIndex),cursor.getString(fnameIndex),cursor.getString(lnameIndex),cursor.getString(genderIndex),cursor.getString(insIndex));

        return driver;
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
