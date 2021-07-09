/*
 *
 *  Copyright 2018 the original author or authors.
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
package springfox.documentation.common.web;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperationsSorter {
  ALPHA("alpha"),
  METHOD("method");

  private final String value;

  OperationsSorter(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  public static OperationsSorter of(String name) {
    for (OperationsSorter operationsSorter : OperationsSorter.values()) {
      if (operationsSorter.value.equals(name)) {
        return operationsSorter;
      }
    }
    return null;
  }
}