package com.devsparkle.fakebookapp.api.dto;

import com.devsparkle.fakebookapp.models.Payment;

/**
 * Created by xmaximin on 8/21/17.
 */

public class PaymentDTO {

  Payment payment;

  public PaymentDTO() {
  }

  public PaymentDTO(Payment payment) {
    this.payment = payment;
  }

  public Payment getPayment() {
    return payment;
  }

  public void setPayment(Payment payment) {
    this.payment = payment;
  }
}
