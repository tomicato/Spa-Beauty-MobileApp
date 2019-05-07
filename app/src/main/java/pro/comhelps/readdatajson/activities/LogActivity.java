package pro.comhelps.readdatajson.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import pro.comhelps.readdatajson.R;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LogActivity extends AppCompatActivity {

    String req_url = "";
    EditText et_email, et_pass;
    TextView register_link;
    SharedPreferences sPref;
    AlertDialog.Builder builder;

    final String SAVED_TEXT = "saved_text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        et_email = findViewById(R.id.txt_email);
        et_pass = findViewById(R.id.txt_pass);
        register_link = findViewById(R.id.link_reg);

    }


    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPassValidate(
            String pass){return pass.length() < 6;
    }

    public void pushBtn(View v){
        String email = et_email.getText().toString().trim();
        String password = et_pass.getText().toString().trim();

        // Валидация введённых данных на клиенте
        builder = new AlertDialog.Builder(LogActivity.this);
        if(!isEmailValid(email)){
            builder.setMessage("Не корректный E-mail!");
            displayAlert("incorrect_email");
        }else if(isPassValidate(password)){
            builder.setMessage("Пароль должен быть не менее 6 символов!");
            displayAlert("incorrect_pass");
        }
        new Background().execute(email, password);
    }

    public void regLink(View view){
        Intent intent = new Intent(LogActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    class Background extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String m_email = params[0];
            String m_pass = params[1];
            BufferedReader reader = null;
            try {
                URL url = new URL(req_url);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("POST");
                huc.setDoOutput(true);

                OutputStream os = huc.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                String my_data = URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(m_email, "UTF-8")
                         + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(m_pass, "UTF-8");

                bw.write(my_data);
                bw.flush();
                bw.close();
                os.close();



                InputStream inputStream = huc.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = br.readLine()) != null){
                    buffer.append(line);
                }




                String finalJson = buffer.toString();
                JSONObject jsonObject = new JSONObject(finalJson);
                JSONArray jsonArray = jsonObject.getJSONArray("success");

                JSONObject finalObject = jsonArray.getJSONObject(0);

                String out_code = finalObject.getString("code");
                String out_id = finalObject.getString("id");
                String out_mess = finalObject.getString("message");

                System.out.println(finalObject);

               if(out_code.equals("1")){
                    Intent intent = new Intent(LogActivity.this, AccountActivity.class);
                    intent.putExtra("id", out_id);
                    startActivity(intent);


                    return out_id;
                }else if(out_code.equals("0")){

                      return out_mess;
                }

                inputStream.close();
                huc.disconnect();

                return  out_mess;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {
            req_url = "http://tomsk-life.pro/site/a-login";
        }

        @Override
        protected void onPostExecute(String s) {
            sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_TEXT, s);
            ed.apply();


           // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public void displayAlert ( final String code){

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(code.equals("incorrect_email")){
                    et_email.setText("");

                }else if(code.equals("incorrect_pass")){
                    et_pass.setText("");

                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
