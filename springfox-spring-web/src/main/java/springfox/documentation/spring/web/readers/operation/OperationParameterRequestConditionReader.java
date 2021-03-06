/*
 *
 *  Copyright 2016 the original author or authors.
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

package springfox.documentation.spring.web.readers.operation;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.core.service.ParameterType;
import springfox.documentation.spi.spi.service.contexts.OperationContext;
import springfox.documentation.core.spring.wrapper.NameValueExpression;

import java.util.Set;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class OperationParameterRequestConditionReader extends AbstractOperationParameterRequestConditionReader {

  @Autowired
  public OperationParameterRequestConditionReader(TypeResolver resolver) {
    super(resolver);
  }

  @SuppressWarnings("deprecation")
  @Override
  public void apply(OperationContext context) {
    Set<NameValueExpression<String>> params = context.params();
    context.operationBuilder()
        .parameters(getParameters(
            params,
            "query"))
        .requestParameters(getRequestParameters(
            params,
            ParameterType.QUERY));
  }

}
