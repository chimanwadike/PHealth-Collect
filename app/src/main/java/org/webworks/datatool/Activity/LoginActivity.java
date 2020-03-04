package org.webworks.datatool.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.webworks.datatool.Model.User;
import org.webworks.datatool.R;
import org.webworks.datatool.Repository.UserRepository;
import org.webworks.datatool.Utility.ApiGetConn;
import org.webworks.datatool.Web.Connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    Context context;
    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private CheckBox chkRemember;
    private View loginProgressView;
    private UserLoginTask mAuthTask = null;
    private String PREFS_NAME;
    SharedPreferences sharedPreferences;
    UserRepository userRepository;
    String user_email;

    private String PREF_VERSION_CODE_KEY;
    final int DOESNT_EXIST = -1, REQUEST_CODE = 0;
    private static int DONE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        PREFS_NAME = this.getResources().getString(R.string.pref_name);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        userRepository = new UserRepository(context);
        initComponent();

    }

    @Override
    public void onResume() {
        super.onResume();
        txtPassword.setText("");
    }

    private void initComponent() {
        // Set up the login form.
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        chkRemember = findViewById(R.id.remember_me);
        loginProgressView = findViewById(R.id.login_progress);
        // Set remembered user
        user_email = sharedPreferences.getString("USER_EMAIL", "");
        if (!user_email.equals("")) {
            txtEmail.setText(user_email);
            txtPassword.requestFocus();
            chkRemember.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        txtEmail.requestFocus();
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        txtEmail.setError(null);
        txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is required");
            focusView = txtPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            txtPassword.setError("Password is invalid");
            focusView = txtPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is required");
            focusView = txtEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            txtEmail.setError("Email is invalid");
            focusView = txtEmail;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);
            if (userRepository.userExists(email) == 1) {
                if(!chkRemember.isChecked())
                    sharedPreferences.edit().putString("USER_EMAIL", email).apply();
                else
                    sharedPreferences.edit().putString("USER_EMAIL", email).apply();
                userRepository.updateUserLogin(user_email);

                Intent intent = new Intent(context,TestingActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //finish();
                showProgress(false);
            }else {
                if (Connectivity.isInternetAvailable()) {
                    mAuthTask = new UserLoginTask(email, password);
                    mAuthTask.execute((Void) null);
                }
                else {
                    Toast.makeText(context, "Make sure that you have access to the internet, then try again", Toast.LENGTH_LONG).show();
                    showProgress(false);
                }
            }
        }
    }

    private String PostLoginForm(String email, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  json.toString();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        BufferedReader reader=null;
        String data;
        String text = "";

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            data = PostLoginForm(mEmail, mPassword);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                String login_url = getResources().getString(R.string.api_url) + getResources().getString(R.string.api_login) + "?email=" + mEmail + "" +
                        "&password=" + mPassword;
                //URL url = new URL(getResources().getString(R.string.base_url) + getResources().getString(R.string.api_login));
                URL url = new URL(login_url);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                //conn.setConnectTimeout(90000);
                /*OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();*/

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
                text = sb.toString();
                conn.disconnect();
            } catch (ProtocolException e) {
                e.printStackTrace();
                return false;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            if (text.equals("error"))
                return false;

            try {
                User user = new User();
                user.setEmail(mEmail);
                //user.setId(Integer.parseInt(text));
                userRepository.LogOutUsers();
                userRepository.addUser(user);
                sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                Gson gson = new Gson();
                String userObject = gson.toJson(user);
                //if(chkRemember.isChecked())
                sharedPreferences.edit().putString("USER_EMAIL", user.getEmail()).commit();
                sharedPreferences.edit().putString("USER_OBJECT", userObject).apply();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                ApiGetConn apiGetConn = new ApiGetConn(context);
                apiGetConn.execute();

                showProgress(false);
                //Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,TestingActivity.class);

                startActivity(intent);
                //finish();
            } else {
                /*mPasswordView.setError("Incorrect password");
                mPasswordView.requestFocus();*/
                Toast.makeText(context, "Login not successful", Toast.LENGTH_LONG).show();

                //bypass login for now
                Intent intent = new Intent(context,TestingActivity.class);

                startActivity(intent);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        //return email.matches("^[_A-Za-z0-9]+(\\.[_A-Za-z0-9]+)*@[_A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    private void showProgress(final boolean show) {
        loginProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
