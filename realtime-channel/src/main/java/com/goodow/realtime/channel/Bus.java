/*
 * Copyright 2013 Goodow.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.goodow.realtime.channel;

/**
 * A distributed lightweight event bus which can encompass multiple machines. The event bus
 * implements publish/subscribe, point to point messaging and request-response messaging.<p>
 * Messages sent over the event bus are represented by instances of the {@link Message} class.<p>
 * For publish/subscribe, messages can be published to a topic using one of the {@link #publish}
 * methods. A topic is a simple {@code String} instance.<p>
 * Handlers are registered against a topic. There can be multiple handlers registered against
 * each topic, and a particular handler can be registered against multiple topics. The event
 * bus will route a sent message to all handlers which are registered against that topic.<p>
 * For point to point messaging, messages can be sent to a topic using one of the {@link #send}
 * methods. The messages will be delivered to a single handler, if one is registered on that
 * topic. If more than one handler is registered on the same topic, the bus will choose one and
 * deliver the message to that. The bus will aim to fairly distribute messages in a round-robin way,
 * but does not guarantee strict round-robin under all circumstances.<p>
 * The order of messages received by any specific handler from a specific sender should match the
 * order of messages sent from that sender.<p>
 * When sending a message, a reply handler can be provided. If so, it will be called when the reply
 * from the receiver has been received. Reply messages can also be replied to, etc, ad infinitum<p>
 * Different event bus instances can be clustered together over a network, to give a single logical
 * event bus.<p>
 */
public interface Bus {

  /**
   * Publish a message
   *
   * @param topic The topic to publish it to
   * @param msg The message
   */
  Bus publish(String topic, Object msg);

  /**
   * Publish a local message
   *
   * @param topic The topic to publish it to
   * @param msg The message
   */
  Bus publishLocal(String topic, Object msg);

  /**
   * Send a message
   *
   * @param topic The topic to send it to
   * @param msg The message
   * @param replyHandler Reply handler will be called when any reply from the recipient is received
   */
  <T> Bus send(String topic, Object msg, Handler<AsyncResult<Message<T>>> replyHandler);

  /**
   * Send a local message
   *
   * @param topic The topic to send it to
   * @param msg The message
   * @param replyHandler Reply handler will be called when any reply from the recipient is received
   */
  <T> Bus sendLocal(String topic, Object msg, Handler<AsyncResult<Message<T>>> replyHandler);

  /**
   * Registers a handler against the specified topic
   *
   * @param topicFilter The topicFilter to register it at
   * @param handler The handler
   * @return the handler registration, can be stored in order to unregister the handler later
   */
  @SuppressWarnings("rawtypes")
  Registration subscribe(String topicFilter, Handler<? extends Message> handler);

  /**
   * Registers a local handler against the specified topic. The handler info won't be propagated
   * across the cluster
   *
   * @param topicFilter The topicFilter to register it at
   * @param handler The handler
   */
  @SuppressWarnings("rawtypes")
  Registration subscribeLocal(String topicFilter, Handler<? extends Message> handler);
}