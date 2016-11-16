package galaxynoise.autaccreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

        try{
            String destPath = "/data/data/" + getPackageName() +
                    "/databases";
            File f = new File(destPath);

            if(!f.exists()) {
                f.mkdirs();
                f.createNewFile();

                CopyDB(getBaseContext().getAssets().open("incident_db"),
                        new FileOutputStream(destPath + "/Incident_DB"));
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(getApplicationContext(), ReportList.class);
                startActivity(intent);
            }
        });
    }

    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException{
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer))>0){
            outputStream.write(buffer, 0 , length);

        }
        inputStream.close();
        outputStream.close();

    }

}
