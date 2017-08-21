package com.devsparkle.fakebookapp.models;

import java.util.List;

/**
 * Created by xmaximin on 8/18/17.
 */

public class User {

  public static final String TOKEN = "TOKEN_KEY";
  public static final String IS_ALREADY_LOGGED = "IS_ALREADY_LOGGED";
  public static final String USERNAME = "USER_USERNAME_KEY";
  public static final String PASSWORD = "USER_PASSWORD_KEY";

  String username;
  String apikey;
  String token;
  List<Recipient> recipients;


  public User(String username, String apikey) {
    this.username = username;
    this.apikey = apikey;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getApikey() {
    return apikey;
  }

  public void setApikey(String apikey) {
    this.apikey = apikey;
  }

  public List<Recipient> getRecipients() {
    return recipients;
  }

  public void setRecipients(List<Recipient> recipients) {
    this.recipients = recipients;
  }

}
