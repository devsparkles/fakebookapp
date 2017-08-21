package com.devsparkle.fakebookapp.models;

/**
 * Created by xmaximin on 8/20/17.
 */

public class Recipient {

  public static final String ID = "RECIPIENT_ID_KEY";
  public static final String NAME = "RECIPIENT_NAME_KEY";

  String id;
  String name;




  public Recipient(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public Recipient(String name) {
    this.name = name;
  }

  public Recipient() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
