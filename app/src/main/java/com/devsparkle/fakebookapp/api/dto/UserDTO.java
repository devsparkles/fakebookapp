package com.devsparkle.fakebookapp.api.dto;

/**
 * Created by xmaximin on 8/21/17.
 */

public class UserDTO {

  String username;
  String apikey;
  String token;


  public UserDTO() {
  }

  public UserDTO(String username, String apikey) {
    this.username = username;
    this.apikey = apikey;
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


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
