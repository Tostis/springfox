/*
 *
 *  Copyright 2016-2019 the original author or authors.
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
import springfox.documentation.core.builders.ParameterBuilder;
import springfox.documentation.core.builders.RequestParameterBuilder;
import springfox.documentation.core.schema.ModelRef;
import springfox.documentation.core.service.Parameter;
import springfox.documentation.core.schema.ScalarType;
import springfox.documentation.core.service.AllowableListValues;
import springfox.documentation.core.service.ParameterStyle;
import springfox.documentation.core.service.ParameterType;
import springfox.documentation.core.service.RequestParameter;
import springfox.documentation.spi.spi.DocumentationType;
import springfox.documentation.spi.spi.service.OperationBuilderPlugin;
import springfox.documentation.core.spring.wrapper.NameValueExpression;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.*;
import static org.springframework.util.StringUtils.*;
import static springfox.documentation.core.service.RequestParameter.DEFAULT_PRECEDENCE;

@SuppressWarnings("deprecation")
public abstract class AbstractOperationParameterRequestConditionReader implements OperationBuilderPlugin {
  private final TypeResolver resolver;

  public AbstractOperationParameterRequestConditionReader(TypeResolver resolver) {
    this.resolver = resolver;
  }

  public List<Parameter> getParameters(
      Set<NameValueExpression<String>> expressions,
      String parameterType) {
    List<Parameter> parameters = new ArrayList<>();
    for (NameValueExpression<String> expression : expressions) {
      if (skipParameter(parameters, expression)) {
        continue;
      }

      String paramValue = expression.getValue();
      AllowableListValues allowableValues = null;
      if (!isEmpty(paramValue)) {
        allowableValues = new AllowableListValues(singletonList(paramValue), "string");
      }
      Parameter parameter = new ParameterBuilder()
          .name(expression.getName())
          .description(null)
          .defaultValue(paramValue)
          .required(true)
          .allowMultiple(false)
          .type(resolver.resolve(String.class))
          .modelRef(new ModelRef("string"))
          .allowableValues(allowableValues)
          .parameterType(parameterType)
          .order(DEFAULT_PRECEDENCE)
          .build();
      parameters.add(parameter);
    }

    return parameters;
  }

  protected Set<RequestParameter> getRequestParameters(
      Set<NameValueExpression<String>> expressions,
      ParameterType parameterType) {
    Set<RequestParameter> parameters = new HashSet<>();
    for (NameValueExpression<String> expression : expressions) {
      if (skipRequestParameter(parameters, expression)) {
        continue;
      }

      String paramValue = expression.getValue();
      AllowableListValues allowableValues = null;
      if (!isEmpty(paramValue)) {
        allowableValues = new AllowableListValues(singletonList(paramValue), "string");
      }
      AllowableListValues finalAllowableValues = allowableValues;
      RequestParameter parameter = new RequestParameterBuilder()
          .name(expression.getName())
          .description(null)
          .required(true)
          .query(q -> q.style(ParameterStyle.SIMPLE)
              .explode(false)
              .allowReserved(false)
              .defaultValue(paramValue)
              .enumerationFacet(e -> e.allowedValues(finalAllowableValues))
              .model(m -> m.name(expression.getName())
                  .scalarModel(ScalarType.STRING)))
          .in(parameterType)
          .precedence(DEFAULT_PRECEDENCE)
          .build();
      parameters.add(parameter);
    }

    return parameters;
  }

  private boolean skipParameter(
      List<Parameter> parameters,
      NameValueExpression<String> expression) {
    return expression.isNegated() || parameterHandled(parameters, expression);
  }

  private boolean skipRequestParameter(
      Set<RequestParameter> parameters,
      NameValueExpression<String> expression) {
    return expression.isNegated()
        || parameters.stream()
        .anyMatch(p -> Objects.equals(p.getName(), expression.getName()));
  }

  private boolean parameterHandled(
      List<Parameter> parameters,
      NameValueExpression<String> expression) {
    return parameters.stream().anyMatch(input -> expression.getName().equals(input.getName()));
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }
}
