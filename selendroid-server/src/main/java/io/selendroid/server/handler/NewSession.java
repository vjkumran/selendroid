/*
 * Copyright 2012-2014 eBay Software Foundation and selendroid committers.
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
package io.selendroid.server.handler;


import org.json.JSONException;
import org.json.JSONObject;

import io.selendroid.server.common.Response;
import io.selendroid.server.common.SelendroidResponse;
import io.selendroid.server.common.StatusCode;
import io.selendroid.server.common.exceptions.SelendroidException;
import io.selendroid.server.common.http.HttpRequest;
import io.selendroid.server.util.SelendroidLogger;

public class NewSession extends SafeRequestHandler {

  public NewSession(String mappedUri) {
    super(mappedUri);
  }

  @Override
  public Response safeHandle(HttpRequest request) throws JSONException {
    SelendroidLogger.info("new session command");
    JSONObject payload = getPayload(request);

    JSONObject desiredCapabilities = payload.getJSONObject("desiredCapabilities");

    String sessionID;
    try {
      sessionID = getSelendroidDriver(request).initializeSession(desiredCapabilities);
    } catch (SelendroidException e) {
      SelendroidLogger.error("Error while creating new session: ", e);
      return new SelendroidResponse("", StatusCode.SESSION_NOT_CREATED_EXCEPTION, e);
    }
    return new SelendroidResponse(sessionID, desiredCapabilities);
  }
}
