package org.nobel.highriseandroid.activities;

import org.nobel.highriseandroid.AppConstants;
import org.nobel.highriseandroid.R;
import org.nobel.highriseandroid.data.HighriseApiService;
import org.nobel.highriseapi.InvalidUserCredentialsException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

    class LoginCredentials {

        public String password;

        public String username;

        public LoginCredentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    class LoginTask extends AsyncTask<LoginCredentials, Void, Void> {

        private ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(LoginCredentials... loginCredentials) {
            try {
                HighriseApiService.createInstance(loginCredentials[0].username, loginCredentials[0].password,
                        getSharedPreferences(AppConstants.PREFERENCES, MODE_PRIVATE));
            }
            catch (InvalidUserCredentialsException e) {
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(
                                getApplicationContext(),
                                "Login failed. We didn't recognize the username or password you entered. Please try again.",
                                Toast.LENGTH_LONG).show();

                    }
                });
                this.cancel(false);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            progressDialog.dismiss();
            startHomeActivity();
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(LoginActivity.this, "Log in", "Requesting API token...");
        }

    }

    @Override
    public void onClick(View arg0) {
        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        new LoginTask().execute(new LoginCredentials(username, password));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);
        findViewById(R.id.loginButton).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences(AppConstants.PREFERENCES, MODE_PRIVATE);
        if (HighriseApiService.tokenIsPresent(preferences)) {
            startHomeActivity();
        }
    }

    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
