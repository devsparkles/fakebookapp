package com.devsparkle.fakebookapp.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.devsparkle.fakebookapp.R;
import com.devsparkle.fakebookapp.adapter.PaymentAdapter;
import com.devsparkle.fakebookapp.api.PaymentsService;
import com.devsparkle.fakebookapp.api.RecipientsService;
import com.devsparkle.fakebookapp.api.dto.PaymentDTO;
import com.devsparkle.fakebookapp.api.dto.RecipientDTO;
import com.devsparkle.fakebookapp.config.ApiConfig;
import com.devsparkle.fakebookapp.models.Payment;
import com.devsparkle.fakebookapp.models.Recipient;
import com.devsparkle.fakebookapp.models.User;
import com.squareup.okhttp.ResponseBody;
import java.util.List;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PaymentActivity extends AppCompatActivity {

  private static final String TAG = "PaymentActivity";

  public static final String PREFS_NAME = "FakebookAppPrefsFile";

  Button mButtonBack;
  TextView mTextViewRecipientNameCreatePayment;
  Button mButtonCreatePayment;
  EditText mEditTextAmount;
  ListViewCompat mListviewPayments;

  SharedPreferences mSettings;
  Retrofit mRetrofit;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);

    setResources();

    mSettings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    // retrieve the recipients
    mRetrofit = new Retrofit.Builder().baseUrl(ApiConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    // refresh payment list
    refreshPaymentList();

    mButtonBack.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });

    Intent intent = getIntent();
    final String recipientId = intent.getStringExtra(Recipient.ID);
    final String recipientName = intent.getStringExtra(Recipient.NAME);
    // just display the recipient name on the screen
    mTextViewRecipientNameCreatePayment.setText("to: " + recipientName);

    // create payment
    mButtonCreatePayment.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        // retrieve the token
        String token = mSettings.getString(User.TOKEN, "");

        PaymentsService service = mRetrofit.create(PaymentsService.class);

        PaymentDTO paymentDTO = new PaymentDTO();
        Payment paymentNew = new Payment();

        paymentNew.setAmount(mEditTextAmount.getText().toString());
        paymentNew.setCurrency("GBP");
        paymentNew.setRecipient_id(recipientId);

        paymentDTO.setPayment(paymentNew);

        Call<PaymentDTO> recipientSrv =
            service.createPayment("application/json", "Bearer " + token, paymentDTO);

        recipientSrv.enqueue(new Callback<PaymentDTO>() {
          @Override public void onResponse(Response<PaymentDTO> response, Retrofit retrofit) {
            // response.isSuccess() is true if the response code is 2xx
            if (response.isSuccess()) {
              PaymentDTO payment = response.body();
              refreshPaymentList();
              Log.d(TAG, payment.toString());

              // refresh the list

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
    mButtonBack = (Button) findViewById(R.id.button_back);
    mTextViewRecipientNameCreatePayment =
        (TextView) findViewById(R.id.textViewRecipientNameCreatePayment);
    mButtonCreatePayment = (Button) findViewById(R.id.button_create_payment);
    mEditTextAmount = (EditText) findViewById(R.id.editTextAmount);
    mListviewPayments = (ListViewCompat) findViewById(R.id.listviewPayments);
  }

  private void refreshPaymentList() {

    // retrieve the token
    String token = mSettings.getString(User.TOKEN, "");

    PaymentsService service = mRetrofit.create(PaymentsService.class);

    Call<PaymentDTO> paymentService = service.listPayments("application/json", "Bearer " + token);

    paymentService.enqueue(new Callback<PaymentDTO>() {
      @Override public void onResponse(Response<PaymentDTO> response, Retrofit retrofit) {
        // response.isSuccess() is true if the response code is 2xx
        if (response.isSuccess()) {
          PaymentDTO payments = response.body();

          List<Payment> paymentList = payments.getPayments();

          populateRecipientName(paymentList);

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

  private void populateRecipientName(final List<Payment> list) {

    String token = mSettings.getString(User.TOKEN, "");
    RecipientsService service = mRetrofit.create(RecipientsService.class);
    Call<RecipientDTO> user = service.listRecipients("application/json", "Bearer " + token);
    user.enqueue(new Callback<RecipientDTO>() {
      @Override public void onResponse(Response<RecipientDTO> response, Retrofit retrofit) {
        // response.isSuccess() is true if the response code is 2xx
        if (response.isSuccess()) {
          RecipientDTO recipient = response.body();
          Log.d(TAG, recipient.toString());
          recipient.getRecipients();
          for (Payment p : list) {
            p.setRecipient_name(searchRecipientNameById(recipient.getRecipients(), p.getRecipient_id()));
          }


          PaymentAdapter paymentAdapter = new PaymentAdapter(PaymentActivity.this, list);
          mListviewPayments.setAdapter(paymentAdapter);
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

  private String searchRecipientNameById(List<Recipient> list, String id) {
    String result = "";
    for (Recipient r : list) {
      if (id.equals(r.getId())) {
        return r.getName();
      }
    }
    return result;
  }


}
