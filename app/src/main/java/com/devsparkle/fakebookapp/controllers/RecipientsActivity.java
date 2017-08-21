package com.devsparkle.fakebookapp.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.devsparkle.fakebookapp.R;
import com.devsparkle.fakebookapp.adapter.RecipientAdapter;
import com.devsparkle.fakebookapp.api.RecipientsService;
import com.devsparkle.fakebookapp.api.dto.RecipientDTO;
import com.devsparkle.fakebookapp.config.ApiConfig;
import com.devsparkle.fakebookapp.models.Recipient;
import com.devsparkle.fakebookapp.models.User;
import com.squareup.okhttp.ResponseBody;
import java.util.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class RecipientsActivity extends AppCompatActivity {

  private static final String TAG = "RecipientsActivity";

  public static final String PREFS_NAME = "FakebookAppPrefsFile";

  // Current resources
  Button mButtonLogout;
  ListView mListViewRecipients;
  Button mButtonAddRecipient;
  EditText mEditTextRecipient;

  SharedPreferences mSettings;

  Retrofit mRetrofit;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipients);

    // Set current resources
    setResources();

    mSettings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    // retrieve the recipients
    mRetrofit = new Retrofit.Builder().baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    mButtonLogout.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        logOut();
      }
    });

    refreshRecipientList();

    mButtonAddRecipient.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        String recipientName = mEditTextRecipient.getText().toString();

        // retrieve the token
        String token = mSettings.getString(User.TOKEN, "");

        RecipientsService service = mRetrofit.create(RecipientsService.class);

        RecipientDTO recipientDTO = new RecipientDTO();
        Recipient recipientNew = new Recipient(recipientName);

        recipientDTO.setRecipient(recipientNew);

        Call<RecipientDTO> recipientSrv =
            service.addRecipient("application/json", "Bearer " + token, recipientDTO);

        recipientSrv.enqueue(new Callback<RecipientDTO>() {
          @Override public void onResponse(Response<RecipientDTO> response, Retrofit retrofit) {
            // response.isSuccess() is true if the response code is 2xx
            if (response.isSuccess()) {
              RecipientDTO recipient = response.body();
              Log.d(TAG, recipient.toString());
              refreshRecipientList();
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

    // when clicking on listview pop details and allow the adding of a payments

    mListViewRecipients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Recipient r = (Recipient) mListViewRecipients.getItemAtPosition(position);
        Intent myIntent = new Intent(view.getContext(), PaymentActivity.class);
        myIntent.putExtra(Recipient.ID, r.getId());
        myIntent.putExtra(Recipient.NAME, r.getName());
        startActivity(myIntent);
      }
    });

  }

  private void refreshRecipientList() {

    // retrieve the token
    String token = mSettings.getString(User.TOKEN, "");

    RecipientsService service = mRetrofit.create(RecipientsService.class);

    Call<RecipientDTO> user = service.listRecipients("application/json", "Bearer " + token);

    user.enqueue(new Callback<RecipientDTO>() {
      @Override public void onResponse(Response<RecipientDTO> response, Retrofit retrofit) {
        // response.isSuccess() is true if the response code is 2xx
        if (response.isSuccess()) {
          RecipientDTO recipient = response.body();
          Log.d(TAG, recipient.toString());

          List<Recipient> recipientList = recipient.getRecipients();
          // 2
          //String[] listItems = new String[recipientList.size()];
          // 3
         // for (int i = 0; i < recipientList.size(); i++) {
         //   Recipient recipe = recipientList.get(i);
         //   listItems[i] = recipe.getName();
          //}
          // 4
          RecipientAdapter recipientAdapter = new RecipientAdapter(RecipientsActivity.this, recipientList);
          //ArrayAdapter adapter =
          //    new ArrayAdapter(RecipientsActivity.this, android.R.layout.simple_list_item_1,
           //       listItems);
          mListViewRecipients.setAdapter(recipientAdapter);
          // mListViewRecipients.setAdapter();
        } else {
          int statusCode = response.code();
          // handle request errors yourself
          ResponseBody errorBody = response.errorBody();
          Log.d(TAG, "Authentification request -> status code : " + Integer.toString(statusCode));
        }
      }

      @Override public void onFailure(Throwable t) {
        // handle execution failures like no internet connectivity
        Log.d(TAG, "Authentfication request -> NO CONNECTIVITY");
      }
    });
  }

  // private methods
  private void setResources() {
    mButtonLogout = (Button) findViewById(R.id.button_logout);
    mListViewRecipients = (ListViewCompat) findViewById(R.id.listviewRecipients);
    mButtonAddRecipient = (Button) findViewById(R.id.button_add_recipient);
    mEditTextRecipient = (EditText) findViewById(R.id.editText_recipient);
  }

  private void goToLoginActivityAndFinish() {
    Intent intent = new Intent(RecipientsActivity.this, LoginActivity.class);
    startActivity(intent);
    finish();
  }

  private void logOut() {
    SharedPreferences.Editor editor = mSettings.edit();
    editor.putString(User.USERNAME, "");
    editor.putString(User.PASSWORD, "");
    editor.putString(User.TOKEN, "");
    editor.putBoolean(User.IS_ALREADY_LOGGED, false);
    editor.commit();

    goToLoginActivityAndFinish();
  }
}
