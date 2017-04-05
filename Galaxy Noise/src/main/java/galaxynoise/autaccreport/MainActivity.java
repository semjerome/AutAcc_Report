package galaxynoise.autaccreport;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    String Name, Password;
    Context ctx=this;
    String NAME=null, PASSWORD=null, EMAIL=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.etUserName);
        password = (EditText) findViewById(R.id.etPW);
        Button b1 = (Button) findViewById(R.id.btnLogin);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick (View v)
        {
            main_login();
        }
    });
    }

    public void main_register(View v){
        startActivity(new Intent(this,ReportList.class));
    }

    public void main_login(){
        Name=null;
        Password=null;
        Name = username.getText().toString();
        Password = password.getText().toString();
        BackGround b = new BackGround();
        b.execute(Name, Password);
    }

    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://semjerome.com/app/login.php");
                String urlParams = "name="+name+"&password="+password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err=null;

            try {
                JSONObject root = new JSONObject(s);
                //JSONObject user_data = root.getJSONObject("User");
                JSONArray mainArray = root.getJSONArray("User");

                // looping through All Contacts
                for (int i = 0; i < mainArray.length(); i++) {
                    JSONObject insideJsonObject = mainArray.getJSONObject(i);
                    EMAIL=null;
                    if (insideJsonObject != null) {
                        NAME = insideJsonObject.getString("username");
                        PASSWORD = insideJsonObject.getString("password");
                        EMAIL = insideJsonObject.getString("uid");
                        Log.d("Inside JSON uid: ", EMAIL);
                        Log.d("Inside JSON username: ", NAME);
                        Log.d("Inside JSON password: ", PASSWORD);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }
            if(EMAIL!=null) {
                Toast.makeText(MainActivity.this, "Valid Login!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ctx, ReportList.class);
                i.putExtra("name", NAME);
                i.putExtra("password", PASSWORD);
                i.putExtra("uid", EMAIL);
                i.putExtra("err", err);
                startActivity(i);
            }
            else{
                Toast.makeText(MainActivity.this, "Invalid Login or Password!", Toast.LENGTH_SHORT).show();

            }
        }
    }
}



