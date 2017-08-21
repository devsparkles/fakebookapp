package com.devsparkle.fakebookapp.api.dto;

import com.devsparkle.fakebookapp.models.Recipient;
import java.util.List;

/**
 * Created by xmaximin on 8/21/17.
 */

public class RecipientDTO {

  Recipient recipient;
  List<Recipient> recipients;

  public List<Recipient> getRecipients() {
    return recipients;
  }

  public void setRecipients(List<Recipient> recipients) {
    this.recipients = recipients;
  }

  public Recipient getRecipient() {
    return recipient;
  }

  public void setRecipient(Recipient recipient) {
    this.recipient = recipient;
  }

  public RecipientDTO() {
  }

  public RecipientDTO(Recipient recipient) {
    this.recipient = recipient;
  }
}
