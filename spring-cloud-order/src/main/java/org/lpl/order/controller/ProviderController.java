//package org.lpl.order.controller;
//
//import java.util.Random;
//import org.lpl.order.bean.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.client.serviceregistry.Registration;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author penglei.liu
// * @version 1.0
// * @date 2019-08-30 11:22
// **/
//@RestController
//public class ProviderController {
//
//  @Autowired
//  DiscoveryClient discoveryClient;
//
//  @Autowired
//  Registration registration;
//
//  @RequestMapping("/receiver")
//  public String receiver() throws InterruptedException {
//
//    printInfo();
//
//    return "hello";
//  }
//
//  @RequestMapping("/receiverUser")
//  private User receiverUser(@RequestBody User user) throws InterruptedException {
//
//    printInfo();
//
//    return user;
//  }
//
//  private void printInfo() throws InterruptedException {
//    String info = "host:" + registration.getHost() + ",service_id" + registration.getServiceId();
//    System.out.println(info);
//
//    int sleepTime = new Random().nextInt(5000);
//    System.out.println(sleepTime);
//    Thread.sleep(sleepTime);
//  }
//}
