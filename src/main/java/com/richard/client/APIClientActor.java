package com.richard.client;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;

public class APIClientActor  {

  //***************************************************
  // Message type definitions
  //***************************************************
  public interface TriggerMessage {}

  public static final class InvokeAPICall implements TriggerMessage {
    public final String path;
    public final APIProxy proxy;

    public InvokeAPICall(String path, APIProxy proxy) {
      this.path = path;
      this.proxy = proxy;
    }
  }

  public static final class APIResult implements TriggerMessage {
    public final boolean status; // true for success, false for failure

    public APIResult(boolean status) {
      this.status = status;
    }
  }

  //***************************************************
  public static Behavior<TriggerMessage> initial() {
    System.out.println("create() called!!");
    return Behaviors.setup(ctx ->
      Behaviors.receive(TriggerMessage.class)
        .onMessage(InvokeAPICall.class, message -> onInvokeAPICall(ctx, message))
        .onMessage(APIResult.class, message -> unhandled(message))
        .build()
    );
  }

  public static Behavior<TriggerMessage> unhandled(TriggerMessage message) {
    System.out.println(message + " is not handled");
    return Behaviors.same();
  }

  private static Behavior<TriggerMessage> onInvokeAPICall(ActorContext<TriggerMessage> context, InvokeAPICall message) {
    message.proxy.callAPIMethod().thenRunAsync(() -> {
      context.getSelf().tell(new APIResult(true));
    });

    return Behaviors.receive(TriggerMessage.class)
      .onMessage(APIResult.class, msg -> onAPIResult(msg))
      .onMessage(InvokeAPICall.class, msg -> unhandled(msg))
      .build();
  }

  private static Behavior<TriggerMessage> onAPIResult(APIResult message) {
    System.out.println("API result received!!!");

    return Behaviors.empty();
  }
}
