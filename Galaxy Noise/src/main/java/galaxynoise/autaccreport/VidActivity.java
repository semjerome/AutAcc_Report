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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vid);
        //getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.fragment_car);

        //get DATA could include car

        Intent i =  getIntent();
        /*String pn = intent.getStringExtra("plate_number");
        String brand = intent.getStringExtra("brand");
        String model =intent.getStringExtra("model");
        String year =intent.getStringExtra("year");

        String fname = intent.getStringExtra("first_name");
        String  lname =intent.getStringExtra("last_name");
        String  license = intent.getStringExtra("license");
        String gender= intent.getStringExtra("gender");
        String insurance = intent.getStringExtra("insurance");*/

        reportid = i.getStringExtra("reportid");
        incidentdate =i.getStringExtra("incidentdate");
        longitude= i.getStringExtra("longi");
        latitude= i.getStringExtra("lati");
        videoName =i.getStringExtra("videoName");

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

    public String [] getFromReport()
    {
        String fromReport [] = {reportid, incidentdate, longitude, latitude, videoName};
        return fromReport;
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
                        Uri.parse("http://semjerome.com"));
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
