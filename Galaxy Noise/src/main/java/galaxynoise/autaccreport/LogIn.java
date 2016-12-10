package galaxynoise.autaccreport;

//Team name Galaxy Noise
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

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
