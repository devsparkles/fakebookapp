package com.devsparkle.fakebookapp.api;

import com.devsparkle.fakebookapp.models.User;
import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by xmaximin on 8/18/17.
 */

public interface RecipientsService {

  @POST("login") Call<User> listRecipients(@Header("Content-Type") String content_type,  @Header("Authorization:Bearer") User user);


}
