package com.devsparkle.fakebookapp.api;

import com.devsparkle.fakebookapp.api.dto.PaymentDTO;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by xmaximin on 8/18/17.
 */

public interface PaymentsService {

  @POST("payments") Call<PaymentDTO> createPayment(@Header("Content-Type") String contentType,
      @Header("Authorization") String token, @Body PaymentDTO paymentDTO);

  @GET("payments") Call<PaymentDTO> listPayments(@Header("Content-Type") String contentType,
      @Header("Authorization") String token);

}
