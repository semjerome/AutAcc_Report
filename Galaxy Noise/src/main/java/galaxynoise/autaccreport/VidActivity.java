package galaxynoise.autaccreport;

//Team name Galaxy Noise

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class VidActivity extends AppCompatActivity implements View.OnClickListener {
    int drawables[] = {
            R.drawable.ic_car,
            R.drawable.ic_driver,
            R.drawable.ic_location,
            R.drawable.ic_video
    };

    String reportid;
    String incidentdate;
    String longitude;
    String latitude;
    String videoName;
    String[] makes;
    String[] models;
    ArrayList<HashMap<String, String>> makeInfo = new ArrayList<>();
    ArrayList<HashMap<String, String>> modelInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fragment_car);

        //get DATA could include car

        Intent i =  getIntent();

        reportid = i.getStringExtra("reportid");
        incidentdate =i.getStringExtra("incidentdate");
        longitude= i.getStringExtra("longi");
        latitude= i.getStringExtra("lati");
        videoName =i.getStringExtra("videoName");

        parseJson();
        Set<HashMap<String, String>> setMakes= new HashSet<>();
        setMakes.addAll(makeInfo);
        makeInfo.clear();
        makeInfo.addAll(setMakes);
        makes = new String[makeInfo.size()];
        models = new String[modelInfo.size()];

        for(int x = 0; x< makeInfo.size(); x++)
        {
            makes[x] = makeInfo.get(x).get("make").toString();
        }
        for(int x = 0; x< modelInfo.size();x++)
        {
            models[x]= modelInfo.get(x).get("model").toString();
        }

        //getDataFromReportList();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),
                VidActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(drawables[0]);
        tabLayout.getTabAt(1).setIcon(drawables[1]);
        tabLayout.getTabAt(2).setIcon(drawables[2]);
        tabLayout.getTabAt(3).setIcon(drawables[3]);


    }

    public void parseJson()
    {
        try {
            JSONObject json = new JSONObject(loadCarJsonLocal());
            JSONArray m_jArry = json.getJSONArray("carmakes");
            HashMap<String, String> make_li;
            HashMap<String, String> model_li;
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                String make_value = jo_inside.getString("make");
                String model_value = jo_inside.getString("model");

                //Add your values in your `ArrayList` as below:
                make_li = new HashMap<String, String>();
                model_li = new HashMap<String, String>();
                make_li.put("make", make_value);
                model_li.put("model", model_value);
                //Log.d("loading make ", make_value);
                //Log.d("loading model ", model_value);
                makeInfo.add(make_li);
                modelInfo.add(model_li);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String loadCarJsonLocal()
    {
        String json = null;
        try {
            InputStream is = getAssets().open("car_makemodel.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public String [] getFromReport()
    {
        String fromReport [] = {reportid, incidentdate, longitude, latitude, videoName};
        return fromReport;
    }
    public String [] getMakes()
    {
        return makes;
    }

    public String [] getModels()
    {
        return models;
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 4;
        private Context context;
        private String tabTitles[] = new String[] { "Car Info","Driver", "Location","Video" };
        Drawable myDrawable;
        String title;

        public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {

            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position) {
                case 0:
                    return tabTitles[0];
                //return tabTitles[0];
                case 1:
                    return tabTitles[1];
                //return tabTitles[1];
                case 2:
                    return tabTitles[2];
                //return tabTitles[2];
                case 3:
                    return tabTitles[3];
            }
            return "";
        }

        @Override
        public Fragment getItem(int position) {

            return PageFragmentVid.create(position + 1);
        }
    }
    @Override
    // Handle the Back Key
    public void onBackPressed() {
        finish();
    }
    @Override
    public void onClick(View v) {

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Intent intent = null;

        switch (menu.getItemId())
        {
            //Product website
            case R.id.about:
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://autaccreport.com/wordpresstest"));
                startActivity(intent);
                break;

            //Insurance auto claims phone number
            case R.id.CAA:
                try {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1-800-387-2656"));
                    startActivity(intent);
                } catch (Exception e) {
                }
                break;
            case R.id.TD:
                try {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1-800-293-4941"));
                    startActivity(intent);
                } catch (Exception e) {

                }
                break;
            case R.id.RBC:
                try {
                    intent= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1-877-748-7224"));
                    startActivity(intent);
                } catch (Exception e) {

                }
                break;
            case R.id.Desj:
                try {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "1-888-776-8343"));
                    startActivity(intent);
                } catch (Exception e) {

                }
                break;
            case R.id.Farm:
                try {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "855-209-9549"));
                    startActivity(intent);
                } catch (Exception e) {

                }
                break;

        }
        return super.onOptionsItemSelected(menu);
    }


}
