package pro.comhelps.readdatajson.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class RegisterActivity extends AppCompatActivity {

    EditText txt_name, txt_email, txt_pass, txt_cpass;
    Button btn_reg;
    String req_url = "";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_name = findViewById(R.id.en_reg_name);
        txt_email = findViewById(R.id.en_reg_email);
        txt_pass = findViewById(R.id.en_reg_pass);
        txt_cpass = findViewById(R.id.en_reg_cpass);
        btn_reg = findViewById(R.id.btn_reg_register);
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPassValidate(
            String pass){return pass.length() < 6;
    }

    public void pushBtn(View v) {

        String name = txt_name.getText().toString().trim();
        String email = txt_email.getText().toString().trim();
        String pass = txt_pass.getText().toString().trim();
        String cpass = txt_cpass.getText().toString().trim();


        // Валидация введённых данных на клиенте
        builder = new AlertDialog.Builder(RegisterActivity.this);

        if (!(pass.equals(cpass))) {
            builder.setMessage("Пароли не совпадают!");
            displayAlert("input_error");
        }else if(!isEmailValid(email)){
            builder.setMessage("Не корректный E-mail!");
            displayAlert("incorrect_email");
        }else if(isPassValidate(pass)){
            builder.setMessage("Пароль должен быть не менее 6 символов!");
            displayAlert("incorrect_pass");
        }else {
            new BackgroundTask().execute(name, email, pass);
        }
    }

    class BackgroundTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {

            String m_name = params[0];
            String m_email = params[1];
            String m_pass = params[2];
            String m_cpass = txt_cpass.getText().toString();
            BufferedReader reader = null;

            try {


                URL url = new URL(req_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String my_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(m_name, "UTF-8")
                        + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(m_email, "UTF-8")
                        + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(m_pass, "UTF-8");

                bw.write(my_data);
                bw.flush();
                bw.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = br.readLine()) != null){
                    buffer.append(line);
                }
                // System.out.println(buffer);


                String finalJson = buffer.toString();
                JSONObject jsonObject = new JSONObject(finalJson);
                JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray("response");

                JSONObject finalObject = jsonArray.getJSONObject(0);
                String out_mess = finalObject.getString("message");
                String out_code= finalObject.getString("code");

                if(out_code.equals("code_success")){

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);

                }

                inputStream.close();
                httpURLConnection.disconnect();

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
            //req_url = "https://business-tomsk.pro/site/sign";
            req_url = "http://tomsk-life.pro/site/a-signup";
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
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
                if (code.equals("input_error")) {
                    txt_pass.setText("");
                    txt_cpass.setText("");
                }
                else if(code.equals("incorrect_email")){
                    txt_email.setText("");

                }else if(code.equals("incorrect_pass")){
                    txt_pass.setText("");
                    txt_cpass.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
