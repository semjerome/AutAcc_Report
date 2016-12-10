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
//

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

//Team name Galaxy Noise

//public class MainActivity extends AppCompatActivity {
//
//    String url;
//    EditText etPW;
//    EditText etUserName;
//    public String username;
//    public String password;
//
//    String uid;
//    String j_username;
//    String j_password;
//    public boolean valid;
//    boolean alreadyDownloaded;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button b1 = (Button) findViewById(R.id.btnLogin);
//
//        valid = false;
//        alreadyDownloaded = false;
//
//
//        etUserName = (EditText)findViewById(R.id.etUserName);
//        etPW = (EditText)findViewById(R.id.etPW);
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View v)
//            {
//                if(alreadyDownloaded == false)
//                {
//                    url = "http://semjerome.com/app/login.php";
//                    new VerifyAccount().execute();
//                }
//
//                if(valid == true && username.equals(j_username) && password.equals(j_password))  {
//                    Toast.makeText(MainActivity.this, "Valid Login!", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), ReportList.class);
//                    startActivity(intent);
//                }
//                else
//                {
//                    Toast.makeText(MainActivity.this, "Invalid Login or Password!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException{
//        byte[] buffer = new byte[1024];
//        int length;
//        while((length = inputStream.read(buffer))>0){
//            outputStream.write(buffer, 0 , length);
//
//        }
//        inputStream.close();
//        outputStream.close();
//
//    }
//
//    public class HttpHandler {
//
//        private final String TAG = HttpHandler.class.getSimpleName();
//
//        public HttpHandler() {
//        }
//
//        public String makeServiceCall(String reqUrl) {
//            String response = null;
//            try {
//                URL url = new URL(reqUrl);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setRequestMethod("GET");
//                // read the response
//                InputStream in = new BufferedInputStream(conn.getInputStream());
//                response = convertStreamToString(in);
//            } catch (MalformedURLException e) {
//                Log.e(TAG, "MalformedURLException: " + e.getMessage());
//            } catch (ProtocolException e) {
//                Log.e(TAG, "ProtocolException: " + e.getMessage());
//            } catch (IOException e) {
//                Log.e(TAG, "IOException: " + e.getMessage());
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//            return response;
//        }
//
//        private String convertStreamToString(InputStream is) {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//            StringBuilder sb = new StringBuilder();
//
//            String line;
//            try {
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line).append('\n');
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            return sb.toString();
//        }
//    }
//
//    private class VerifyAccount extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... arg0) {
//
//            HttpHandler sh = new HttpHandler();
//            // Making a request to url and getting response
//            String jsonStr= sh.makeServiceCall(url);
//            Log.d("Response from url: ", jsonStr);
//
//            if (jsonStr != null) {
//                try {
//                    HashMap<String, String> add_first_info = new HashMap<>();
//                    HashMap<String, String> add_second_info = new HashMap<>();
//                    HashMap<String, String> add_loc = new HashMap<>();
//
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray mainArray = jsonObj.getJSONArray("User");
//
//                    // looping through All Contacts
//                    for (int i = 0; i < mainArray.length(); i++) {
//                        JSONObject insideJsonObject = mainArray.getJSONObject(i);
//
//                        if (insideJsonObject != null) {
//                            uid = insideJsonObject.getString("uid");
//                            j_username = insideJsonObject.getString("username");
//                            j_password = insideJsonObject.getString("password");
//                            Log.d("Inside JSON uid: ", uid);
//                            Log.d("Inside JSON username: ", j_username);
//                            Log.d("Inside JSON password: ", j_password);
//                        }
//                    }
//                }catch(JSONException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            // Dismiss the progress dialog
//            //if (pDialog.isShowing())
//            //pDialog.dismiss();
//
//            //textView.setText(nameList1.get(0).toString());// +"\n" + nameList2.get(0).toString() +"\n"
//            //+ locList.get(0).toString());
//            username = etUserName.getText().toString();
//            password= etPW.getText().toString();
//            alreadyDownloaded = true;
//            if(username.equals(j_username) && password.equals(j_password))
//            {
//                valid = true;
//            }
//        }
//    }
//
//}

/*try{
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
        }*/

