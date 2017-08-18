package com.devsparkle.fakebookapp.models;

/**
 * Created by xmaximin on 8/18/17.
 */

public class User {

  public static final String TOKEN = "TOKEN_KEY";
  public static final String IS_ALREADY_LOGGED = "IS_ALREADY_LOGGED";
  public static final String USERNAME = "USERNAME_KEY";
  public static final String PASSWORD = "PASSWORD_KEY";

  String username;
  String apikey;
  String token;

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
}
