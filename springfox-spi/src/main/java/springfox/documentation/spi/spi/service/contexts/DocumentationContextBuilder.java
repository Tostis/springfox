/*
 *
 *  Copyright 2015-2019 the original author or authors.
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

package springfox.documentation.spi.spi.service.contexts;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.core.OrderComparator;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.core.PathProvider;
import springfox.documentation.core.RequestHandler;
import springfox.documentation.core.schema.AlternateTypeRule;
import springfox.documentation.core.service.*;
import springfox.documentation.spi.spi.DocumentationType;
import springfox.documentation.spi.spi.schema.GenericTypeNamingStrategy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static springfox.documentation.core.builders.BuilderDefaults.*;

@SuppressWarnings("deprecation")
public class DocumentationContextBuilder {

  private final List<SecurityContext> securityContexts = new ArrayList<>();
  private final Set<Class> ignorableParameterTypes = new HashSet<>();
  private final Map<RequestMethod, List<ResponseMessage>> responseMessageOverrides
      = new TreeMap<>();
  private final Map<HttpMethod, List<Response>> responseOverrides = new TreeMap<>();
  private final List<Parameter> globalOperationParameters = new ArrayList<>();
  private final List<AlternateTypeRule> rules = new ArrayList<>();
  private final Map<RequestMethod, List<ResponseMessage>> defaultResponseMessages
      = new HashMap<>();
  private final Map<HttpMethod, List<Response>> defaultResponses = new HashMap<>();
  private final Set<String> protocols = new HashSet<>();
  private final Set<String> produces = new LinkedHashSet<>();
  private final Set<String> consumes = new LinkedHashSet<>();
  private final Set<ResolvedType> additionalModels = new HashSet<>();
  private final Set<Tag> tags = new TreeSet<>(Tags.tagComparator());
  private final List<SecurityScheme> securitySchemes = new ArrayList<>();
  private final List<VendorExtension> vendorExtensions = new ArrayList<>();

  private TypeResolver typeResolver;
  private List<RequestHandler> handlerMappings;
  private ApiInfo apiInfo;
  private String groupName;
  private PathProvider pathProvider;
  private Comparator<ApiListingReference> listingReferenceOrdering;
  private Comparator<ApiDescription> apiDescriptionOrdering;
  private DocumentationType documentationType;
  private Comparator<Operation> operationOrdering;
  private boolean applyDefaultResponseMessages;
  private ApiSelector apiSelector = ApiSelector.DEFAULT;
  private String host;
  private GenericTypeNamingStrategy genericsNamingStrategy;
  private Optional<String> pathMapping;
  private boolean isUrlTemplatesEnabled;
  private final List<RequestParameter> globalRequestParameters = new ArrayList<>();
  private final List<Server> servers = new ArrayList<>();

  public DocumentationContextBuilder(DocumentationType documentationType) {
    this.documentationType = documentationType;
  }

  public DocumentationContextBuilder requestHandlers(List<RequestHandler> handlerMappings) {
    this.handlerMappings = handlerMappings;
    return this;
  }

  public DocumentationContextBuilder apiInfo(ApiInfo apiInfo) {
    this.apiInfo = defaultIfAbsent(apiInfo, this.apiInfo);
    return this;
  }

  public DocumentationContextBuilder groupName(String groupName) {
    this.groupName = defaultIfAbsent(groupName, this.groupName);
    return this;
  }

  public DocumentationContextBuilder additionalIgnorableTypes(Set<Class> ignorableParameterTypes) {
    this.ignorableParameterTypes.addAll(ignorableParameterTypes);
    return this;
  }

  public DocumentationContextBuilder additionalResponseMessages(
      Map<RequestMethod, List<ResponseMessage>> additionalResponseMessages) {
    this.responseMessageOverrides.putAll(additionalResponseMessages);
    return this;
  }

  public DocumentationContextBuilder additionalResponses(
      Map<HttpMethod, List<Response>> additionalResponses) {
    this.responseOverrides.putAll(additionalResponses);
    return this;
  }

  public DocumentationContextBuilder additionalOperationParameters(
      List<Parameter> globalRequestParameters) {
    this.globalOperationParameters.addAll(nullToEmptyList(globalRequestParameters));
    return this;
  }

  public DocumentationContextBuilder pathProvider(PathProvider pathProvider) {
    this.pathProvider = defaultIfAbsent(pathProvider, this.pathProvider);
    return this;
  }

  public DocumentationContextBuilder securityContexts(List<SecurityContext> securityContext) {
    this.securityContexts.addAll(nullToEmptyList(securityContext));
    return this;
  }

  public DocumentationContextBuilder securitySchemes(List<SecurityScheme> securitySchemes) {
    this.securitySchemes.addAll(nullToEmptyList(securitySchemes));
    return this;
  }

  public DocumentationContextBuilder apiListingReferenceOrdering(
      Comparator<ApiListingReference> listingReferenceOrdering) {

    this.listingReferenceOrdering = defaultIfAbsent(listingReferenceOrdering, this.listingReferenceOrdering);
    return this;
  }

  public DocumentationContextBuilder apiDescriptionOrdering(Comparator<ApiDescription> apiDescriptionOrdering) {
    this.apiDescriptionOrdering = defaultIfAbsent(apiDescriptionOrdering, this.apiDescriptionOrdering);
    return this;
  }

  private Map<RequestMethod, List<ResponseMessage>> aggregateResponseMessages() {
    Map<RequestMethod, List<ResponseMessage>> responseMessages = new HashMap<>();
    if (applyDefaultResponseMessages) {
      responseMessages.putAll(defaultResponseMessages);
    }
    responseMessages.putAll(responseMessageOverrides);
    return responseMessages;
  }

  private Map<HttpMethod, List<Response>> aggregateResponses() {
    Map<HttpMethod, List<Response>> responseMessages = new HashMap<>();
    if (applyDefaultResponseMessages) {
      responseMessages.putAll(nullToEmptyMap(defaultResponses));
    }
    responseMessages.putAll(responseOverrides);
    return responseMessages;
  }

  public DocumentationContextBuilder applyDefaultResponseMessages(boolean applyDefaultResponseMessages) {
    this.applyDefaultResponseMessages = applyDefaultResponseMessages;
    return this;
  }

  public DocumentationContextBuilder ruleBuilders(List<Function<TypeResolver, AlternateTypeRule>> ruleBuilders) {
    rules.addAll(ruleBuilders.stream()
                             .map(evaluator(typeResolver))
                             .collect(toList()));
    return this;
  }

  public DocumentationContextBuilder typeResolver(TypeResolver typeResolver) {
    this.typeResolver = typeResolver;
    return this;
  }

  public DocumentationContextBuilder operationOrdering(Comparator<Operation> operationOrdering) {
    this.operationOrdering = defaultIfAbsent(operationOrdering, this.operationOrdering);
    return this;
  }

  public DocumentationContextBuilder rules(List<AlternateTypeRule> rules) {
    this.rules.addAll(rules);
    return this;
  }

  /**
   * Used to populate the defaults
   *
   * @param defaultResponseMessages - default response messages
   * @return DocumentationContextBuilder
   * @deprecated @since 3.1.0
   * Use {@link DocumentationContextBuilder#defaultResponses} instead
   */
  @Deprecated
  public DocumentationContextBuilder defaultResponseMessages(
      Map<RequestMethod, List<ResponseMessage>> defaultResponseMessages) {
    this.defaultResponseMessages.putAll(defaultResponseMessages);
    return this;
  }

  public DocumentationContextBuilder defaultResponses(
      Map<HttpMethod, List<Response>> defaultResponses) {
    this.defaultResponses.putAll(defaultResponses);
    return this;
  }

  public DocumentationContextBuilder produces(Set<String> produces) {
    this.produces.addAll(produces);
    return this;
  }

  public DocumentationContextBuilder consumes(Set<String> consumes) {
    this.consumes.addAll(consumes);
    return this;
  }

  public DocumentationContextBuilder genericsNaming(GenericTypeNamingStrategy genericsNamingStrategy) {
    this.genericsNamingStrategy = genericsNamingStrategy;
    return this;
  }

  public DocumentationContextBuilder host(String host) {
    this.host = defaultIfAbsent(host, this.host);
    return this;
  }

  public DocumentationContextBuilder protocols(Set<String> protocols) {
    this.protocols.addAll(protocols);
    return this;
  }

  public DocumentationContextBuilder selector(ApiSelector apiSelector) {
    this.apiSelector = apiSelector;
    return this;
  }

  public DocumentationContextBuilder pathMapping(Optional<String> pathMapping) {
    this.pathMapping = pathMapping;
    return this;
  }

  public DocumentationContextBuilder enableUrlTemplating(boolean isUrlTemplatesEnabled) {
    this.isUrlTemplatesEnabled = isUrlTemplatesEnabled;
    return this;
  }

  public DocumentationContextBuilder additionalModels(Set<ResolvedType> additionalModels) {
    this.additionalModels.addAll(additionalModels);
    return this;
  }

  public DocumentationContextBuilder tags(Set<Tag> tags) {
    this.tags.addAll(tags);
    return this;
  }

  public DocumentationContextBuilder vendorExtentions(List<VendorExtension> vendorExtensions) {
    this.vendorExtensions.addAll(vendorExtensions);
    return this;
  }


  public DocumentationContextBuilder additionalRequestParameters(List<RequestParameter> globalRequestParameters) {
    this.globalRequestParameters.addAll(globalRequestParameters);
    return this;
  }


  public DocumentationContext build() {
    Map<RequestMethod, List<ResponseMessage>> responseMessages
        = aggregateResponseMessages();
    Map<HttpMethod, List<Response>> responses = aggregateResponses();
    OrderComparator.sort(rules);
    return new DocumentationContext(
        documentationType,
        handlerMappings,
        apiInfo,
        groupName,
        apiSelector,
        ignorableParameterTypes,
        responseMessages,
        globalOperationParameters,
        globalRequestParameters,
        responses,
        pathProvider,
        securityContexts,
        securitySchemes,
        rules,
        listingReferenceOrdering,
        apiDescriptionOrdering,
        operationOrdering,
        produces,
        consumes,
        host,
        protocols,
        genericsNamingStrategy,
        pathMapping,
        isUrlTemplatesEnabled,
        additionalModels,
        tags,
        vendorExtensions,
        servers);
  }

  private Function<Function<TypeResolver, AlternateTypeRule>, AlternateTypeRule>
  evaluator(final TypeResolver typeResolver) {

    return input -> input.apply(typeResolver);
  }

  public DocumentationContextBuilder servers(List<Server> servers) {
    this.servers.addAll(nullToEmptyList(servers));
    return this;
  }
}
