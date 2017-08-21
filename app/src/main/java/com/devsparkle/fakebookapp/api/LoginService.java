package com.devsparkle.fakebookapp.api;

import com.devsparkle.fakebookapp.api.dto.UserDTO;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by xmaximin on 8/18/17.
 */

public interface LoginService {
  @POST("login") Call<UserDTO> getToken(@Header("Content-Type") String contentType, @Body UserDTO user);
}
