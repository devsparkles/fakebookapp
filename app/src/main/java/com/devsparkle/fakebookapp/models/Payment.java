package com.devsparkle.fakebookapp.models;

/**
 * Created by xmaximin on 8/21/17.
 */

public class Payment {

  String id;
  String recipient_id;
  String recipient_name;


  String currency;
  String amount;
  String status;

  public Payment() {
  }

  public Payment(String recipient_id, String currency, String amount) {
    this.recipient_id = recipient_id;
    this.currency = currency;
    this.amount = amount;
  }

  public String getRecipient_id() {
    return recipient_id;
  }

  public void setRecipient_id(String recipient_id) {
    this.recipient_id = recipient_id;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getStatus() {

    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getRecipient_name() {
    return recipient_name;
  }

  public void setRecipient_name(String recipient_name) {
    this.recipient_name = recipient_name;
  }

}
