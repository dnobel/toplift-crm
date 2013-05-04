package org.nobel.topliftcrm.activities;

import org.nobel.highriseapi.InvalidUserCredentialsException;
import org.nobel.topliftcrm.AppConstants;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.data.HighriseApiService;

import android.app.Activity;
import android.app.AlertDialog;
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

	private static final String HELP_TEXT = "The Highrise Company ID can be found as the first part of URL in the webbrowser after a successful login in your Highrise CRM application (e.g. for an URL https://example.highrisehq.com/ the company ID is 'example').";

	class LoginCredentials {

		public String companyId;

		public String password;

		public String username;

		public LoginCredentials(String companyId, String username, String password) {
			this.companyId = companyId;
			this.username = username;
			this.password = password;
		}
	}

	class LoginTask extends AsyncTask<LoginCredentials, Void, Void> {

		private ProgressDialog progressDialog;

		@Override
		protected Void doInBackground(LoginCredentials... loginCredentials) {
			try {
				HighriseApiService.createInstance(loginCredentials[0].companyId, loginCredentials[0].username,
						loginCredentials[0].password, getSharedPreferences(AppConstants.PREFERENCES, MODE_PRIVATE));
			} catch (InvalidUserCredentialsException e) {
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
	public void onClick(View view) {
		String companyId = ((EditText) findViewById(R.id.company)).getText().toString();
		String username = ((EditText) findViewById(R.id.username)).getText().toString();
		String password = ((EditText) findViewById(R.id.password)).getText().toString();

		new LoginTask().execute(new LoginCredentials(companyId, username, password));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);
		findViewById(R.id.loginButton).setOnClickListener(this);
		findViewById(R.id.helpButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).setMessage(HELP_TEXT).create();
				dialog.show();
			}
		});

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
