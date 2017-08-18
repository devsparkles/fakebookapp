package com.devsparkle.fakebookapp.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.devsparkle.fakebookapp.R;

public class RecipientsActivity extends AppCompatActivity {


  // Current resources
  Button mButtonLogout;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipients);

    // Set current resources
    setResources();





  }



  // private methods
  private void setResources() {
    mButtonLogout = (Button) findViewById(R.id.button_logout);

  }
}
