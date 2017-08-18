package com.devsparkle.fakebookapp.api;

import com.devsparkle.fakebookapp.models.User;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by xmaximin on 8/18/17.
 */

public interface AuthService {
  @POST("login") Call<User> getToken(@Header("Content-Type") String content_type, @Body User user);
}
