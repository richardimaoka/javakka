package com.richard.client;

import akka.actor.typed.ActorSystem;

public class Main {
  public static void main(String[] args) {
    APIProxy proxy = new APIProxy();
    ActorSystem<APIClientActor.TriggerMessage> system = ActorSystem.create(APIClientActor.initial(), "gya");

    system.tell(new APIClientActor.InvokeAPICall("oath", proxy));
    system.tell(new APIClientActor.InvokeAPICall("oath", proxy));

    try {
      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (Throwable ignored) {
    } finally {
      system.terminate();
    }
  }
}
