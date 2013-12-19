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
package com.goodow.realtime.android;

import com.goodow.realtime.core.Net;
import com.goodow.realtime.core.Platform;
import com.goodow.realtime.core.WebSocket;
import com.goodow.realtime.java.JavaWebSocket;
import com.goodow.realtime.json.JsonObject;

class AndroidNet implements Net {

  private final Platform platform;

  public AndroidNet(Platform platform) {
    this.platform = platform;
  }

  @Override
  public WebSocket createWebSocket(String url, JsonObject options) {
    return new JavaWebSocket(platform, url);
  }
}