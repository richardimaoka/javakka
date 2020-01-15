package com.richard.client;

import java.util.concurrent.CompletableFuture;

public class APIProxy {
  public APIProxy() {}

  public CompletableFuture<Void> callAPIMethod() {
    //Executed on the background ForkJoinPool
    return CompletableFuture.runAsync(() -> {
      try {
        Thread.sleep(2000);
        System.out.println("Calling API on Thread[" + Thread.currentThread() + "]: !!!!");
      } catch (InterruptedException exception) {
        System.out.println(exception);
      }
    });
  }
}


