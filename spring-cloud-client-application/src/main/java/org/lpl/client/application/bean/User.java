package org.lpl.client.application.bean;

/**
 * @author penglei.liu
 * @version 1.0
 * @date 2019-09-03 10:31
 **/
public class User {

  private Integer id;
  private String userName;
  private Integer age;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }
}
