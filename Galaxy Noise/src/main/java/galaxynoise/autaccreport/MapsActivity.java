package galaxynoise.autaccreport;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(43.7305, -79.6015);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
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
}
