/*
 *
 *  Copyright 2015-2016 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package springfox.documentation.spring.web.scanners;

import org.springframework.stereotype.Component;
import springfox.documentation.core.RequestHandler;
import springfox.documentation.core.RequestHandlerKey;
import springfox.documentation.core.annotations.Incubating;
import springfox.documentation.core.service.ApiDescription;

import java.util.HashMap;
import java.util.Map;

/**
 * Given a handler method this class serves to lookup the api description. The overall goal of this abstraction is to
 * serve the purpose of looking up a given description.
 */
@Component
@Incubating("2.2.0")
public class ApiDescriptionLookup {
  private Map<RequestHandlerKey, ApiDescription> cache = new HashMap<>();

  public void add(RequestHandlerKey key, ApiDescription value) {
    cache.put(key, value);
  }

  public ApiDescription description(RequestHandler requestHandler) {
    return cache.get(requestHandler.key());
  }
}
