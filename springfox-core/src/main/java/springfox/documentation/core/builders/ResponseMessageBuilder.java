/*
 *
 *  Copyright 2017 the original author or authors.
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

package springfox.documentation.core.builders;

import springfox.documentation.core.schema.Example;
import springfox.documentation.core.schema.ModelReference;
import springfox.documentation.core.service.ResponseMessage;
import springfox.documentation.core.service.Header;
import springfox.documentation.core.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Use {@link ResponseBuilder} instead
 * @deprecated @since 3.0.0
 */
@Deprecated
public class ResponseMessageBuilder {
  private int code;
  private String message;
  private ModelReference responseModel;
  private final List<Example> examples = new ArrayList<>();
  private final Map<String, Header> headers = new TreeMap<>();
  private final List<VendorExtension> vendorExtensions = new ArrayList<>();

  /**
   * Updates the http response code
   *
   * @param code - response code
   * @return this
   */
  public ResponseMessageBuilder code(int code) {
    this.code = code;
    return this;
  }

  /**
   * Updates the response message
   *
   * @param message - message
   * @return this
   */
  public ResponseMessageBuilder message(String message) {
    this.message = BuilderDefaults.defaultIfAbsent(message, this.message);
    return this;
  }

  /**
   * Updates the model the response represents
   *
   * @param responseModel - model reference
   * @return this
   */
  public ResponseMessageBuilder responseModel(ModelReference responseModel) {
    this.responseModel = BuilderDefaults.defaultIfAbsent(responseModel, this.responseModel);
    return this;
  }

  /**
   * Updates the response examples
   *
   * @param examples response examples
   * @return this
   * @since 3.0.0
   */
  public ResponseMessageBuilder examples(List<Example> examples) {
    this.examples.addAll(BuilderDefaults.nullToEmptyList(examples));
    return this;
  }

  /**
   * Updates the response headers
   *
   * @param headers headers with description
   * @return this
   * @since 2.5.0
   */
  public ResponseMessageBuilder headersWithDescription(Map<String, Header> headers) {
    this.headers.putAll(BuilderDefaults.nullToEmptyMap(headers));
    return this;
  }

  /**
   * Updates the response message extensions
   *
   * @param extensions - response message extensions
   * @return this
   * @since 2.5.0
   */
  public ResponseMessageBuilder vendorExtensions(List<VendorExtension> extensions) {
    this.vendorExtensions.addAll(BuilderDefaults.nullToEmptyList(extensions));
    return this;
  }

  public ResponseMessage build() {
    return new ResponseMessage(
        code,
        message,
        responseModel,
        examples,
        headers,
        vendorExtensions);
  }
}