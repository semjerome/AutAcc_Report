package galaxynoise.autaccreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.button);


        /*
        * In this part, we will add the function which can login sql and
        * check if it is available to connect to sql
        * If it can't, ask to enter ID & password again
        * Otherwise, it will go to searching activity
        * */

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(getApplicationContext(), ReportList.class);
                startActivity(intent);
            }
        });
    }
}
