package com.devsparkle.fakebookapp.config;

/**
 * Created by xmaximin on 8/18/17.
 */

public class ApiConfig {

  final public static String BASE_URL = "https://coolpay.herokuapp.com/api/";

  private static final ApiConfig ourInstance = new ApiConfig();

  public static ApiConfig getInstance() {
    return ourInstance;
  }

  private ApiConfig() {
  }
}
