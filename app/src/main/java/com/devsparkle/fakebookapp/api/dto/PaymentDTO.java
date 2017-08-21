package com.devsparkle.fakebookapp.api.dto;

import com.devsparkle.fakebookapp.models.Payment;
import java.util.List;

/**
 * Created by xmaximin on 8/21/17.
 */

public class PaymentDTO {

  Payment payment;

  List<Payment> payments;



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

  public List<Payment> getPayments() {
    return payments;
  }

  public void setPayments(List<Payment> payments) {
    this.payments = payments;
  }
}
