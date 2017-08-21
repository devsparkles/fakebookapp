package com.devsparkle.fakebookapp.api;

import com.devsparkle.fakebookapp.api.dto.RecipientDTO;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by xmaximin on 8/18/17.
 */

public interface RecipientsService {

  @GET("recipients") Call<RecipientDTO> listRecipients(@Header("Content-Type") String contentType,
      @Header("Authorization") String token);

  @POST("recipients") Call<RecipientDTO> addRecipient(@Header("Content-Type") String contentType,
      @Header("Authorization") String token, @Body RecipientDTO recipient);



}
