package com.devsparkle.fakebookapp.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.devsparkle.fakebookapp.R;
import com.devsparkle.fakebookapp.api.AuthService;
import com.devsparkle.fakebookapp.config.ApiConfig;
import com.devsparkle.fakebookapp.models.User;
import com.squareup.okhttp.ResponseBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {

  private static final String TAG = "LoginActivity";

  public static final String PREFS_NAME = "FakebookAppPrefsFile";

  // Current resources
  EditText editTestUsername;
  EditText editTextPassword;
  Button btnLogin;

  ImageView imageViewLogo;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    // Set current resources
    setResources();

    // if the user was already logged in go to request screen directly
    SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    Boolean isAlreadyLogged = settings.getBoolean(User.IS_ALREADY_LOGGED, false);
    if (isAlreadyLogged) {
      // then go to RequestsActivity
      goToRecipientsActivityAndFinish();
    }

    // if you tap on the fakebook logo your field get prefilled with the
    // Username: <xavierm>
    // API Key: <EF3B477DAE97B123>
    imageViewLogo.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        editTestUsername.setText("xavierm");
        editTextPassword.setText("EF3B477DAE97B123");
      }
    });

    btnLogin.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // retrieve login access info
        String username = editTestUsername.getText().toString();
        String apikey = editTextPassword.getText().toString();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        AuthService service = retrofit.create(AuthService.class);

        Call<User> user = service.getToken("application/json", new User(username, apikey));

        user.enqueue(new Callback<User>() {
          @Override public void onResponse(Response<User> response, Retrofit retrofit) {
            // response.isSuccess() is true if the response code is 2xx
            if (response.isSuccess()) {
              User user = response.body();

              // store token in android preference
              SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
              SharedPreferences.Editor editor = settings.edit();
              editor.putString(User.USERNAME, editTestUsername.getText().toString());
              editor.putString(User.PASSWORD, editTextPassword.getText().toString());
              editor.putString(User.TOKEN, user.getToken());
              editor.putBoolean(User.IS_ALREADY_LOGGED, true);
              editor.commit();

              // then go to RequestsActivity
              goToRecipientsActivityAndFinish();
            } else {
              int statusCode = response.code();
              // handle request errors yourself
              ResponseBody errorBody = response.errorBody();
              Log.d(TAG,
                  "Authentification request -> status code : " + Integer.toString(statusCode));
            }
          }

          @Override public void onFailure(Throwable t) {
            // handle execution failures like no internet connectivity
            Log.d(TAG, "Authentfication request -> NO CONNECTIVITY");
          }
        });
      }
    });
  }

  // private methods
  private void setResources() {
    editTestUsername = (EditText) findViewById(R.id.editText_username);
    editTextPassword = (EditText) findViewById(R.id.editText_password);
    imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);
    btnLogin = (Button) findViewById(R.id.button_login);
  }

  private Typeface getTypeFaceFont(String fontFile) {
    Typeface tf = Typeface.createFromAsset(getAssets(), fontFile);
    return tf;
  }

  private void goToRecipientsActivityAndFinish() {
    Intent intent = new Intent(LoginActivity.this, RecipientsActivity.class);
    startActivity(intent);
    finish();
  }
}
