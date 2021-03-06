/*
 *
 *  Copyright 2015 the original author or authors.
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

package springfox.documentation.schema;

import springfox.documentation.spi.spi.schema.GenericTypeNamingStrategy;

/**
 * Strategy that uses \u00ab, \u00bb, and comma in generic type names
 */
public class DefaultGenericTypeNamingStrategy implements GenericTypeNamingStrategy {
  private static final String OPEN = "«";
  private static final String CLOSE = "»";
  private static final String DELIM = ",";

  @Override
  public String getOpenGeneric() {
    return OPEN;
  }

  @Override
  public String getCloseGeneric() {
    return CLOSE;
  }

  @Override
  public String getTypeListDelimiter() {
    return DELIM;
  }
}
