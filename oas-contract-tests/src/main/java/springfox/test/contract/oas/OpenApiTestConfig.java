package springfox.test.contract.oas;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import springfox.documentation.core.builders.PathSelectors;
import springfox.documentation.core.schema.WildcardType;
import springfox.documentation.core.service.ApiKey;
import springfox.documentation.core.service.AuthorizationScope;
import springfox.documentation.core.service.HttpAuthenticationScheme;
import springfox.documentation.core.service.OAuth2Scheme;
import springfox.documentation.core.service.SecurityScheme;
import springfox.documentation.spi.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static springfox.documentation.core.schema.AlternateTypeRules.newRule;

@Configuration
public class OpenApiTestConfig {
  @Bean
  public Docket petStore(List<SecurityScheme> authorizationTypes) {
    return new Docket(DocumentationType.OAS_30)
        .groupName("petstore")
        .useDefaultResponseMessages(false)
        .securitySchemes(authorizationTypes)
        .produces(new HashSet<String>() {{
          add("application/xml");
          add("application/json");
        }})
        .select()
        .paths(PathSelectors.regex("/.*")
            .and(PathSelectors.regex(".*/error").negate())
            .and(PathSelectors.regex(".*/bugs/.*").negate())
            .and(PathSelectors.regex(".*/features/.*").negate())
            .and(PathSelectors.regex(".*/profile").negate()))
        .build()
        .enableUrlTemplating(false)
        .host("petstore.swagger.io")
        .protocols(new HashSet<>(Arrays.asList(
            "http",
            "https")))
        .securitySchemes(Arrays.asList(
            new ApiKey("api_key", "api_key", "header"),
            HttpAuthenticationScheme.BASIC_AUTH_BUILDER
                .name("basicScheme")
                .build(),
            OAuth2Scheme.OAUTH2_IMPLICIT_FLOW_BUILDER
                .name("petstore_auth")
                .authorizationUrl("https://petstore3.swagger.io/oauth/authorize")
                .scopes(Arrays.asList(
                    new AuthorizationScope("write:pets", "Write scope"),
                    new AuthorizationScope("read:pets", "Read scope")))
                .build()));
  }

  @Bean
  public Docket bugs(
      List<SecurityScheme> authorizationTypes,
      TypeResolver resolver) {
    return new Docket(DocumentationType.OAS_30)
        .groupName("bugs")
        .useDefaultResponseMessages(false)
        .securitySchemes(authorizationTypes)
        .produces(new HashSet<String>() {{
          add("application/xml");
          add("application/json");
        }})
        .select()
        .paths(PathSelectors.regex(".*/bugs/.*")
            .and(PathSelectors.regex(".*/error").negate())
            .and(PathSelectors.regex(".*/profile").negate()))
        .build()
        .enableUrlTemplating(false)
        .alternateTypeRules(
            newRule(resolver.resolve(Mono.class,
                resolver.resolve(ResponseEntity.class, InputStreamResource.class)),
                resolver.resolve(File.class)),
            newRule(resolver.resolve(Iterable.class, WildcardType.class),
                resolver.resolve(List.class, WildcardType.class)))
        .host("bugs.springfox.io")
        .protocols(new HashSet<>(Arrays.asList(
            "http",
            "https")))
        .securitySchemes(Arrays.asList(
            new ApiKey("api_key", "api_key", "header"),
            HttpAuthenticationScheme.BASIC_AUTH_BUILDER
                .name("basicScheme")
                .build(),
            OAuth2Scheme.OAUTH2_IMPLICIT_FLOW_BUILDER
                .name("petstore_auth")
                .authorizationUrl("https://petstore3.swagger.io/oauth/authorize")
                .scopes(Arrays.asList(
                    new AuthorizationScope("write:pets", "Write scope"),
                    new AuthorizationScope("read:pets", "Read scope")))
                .build()));
  }

  @Bean
  public Docket features(
      TypeResolver resolver) {
    return new Docket(DocumentationType.OAS_30)
        .groupName("features")
        .useDefaultResponseMessages(false)
        .produces(new HashSet<String>() {{
          add("application/xml");
          add("application/json");
        }})
        .select()
        .paths(PathSelectors.regex(".*/features/.*")
            .and(PathSelectors.regex(".*/error").negate())
            .and(PathSelectors.regex(".*/profile").negate()))
        .build()
        .enableUrlTemplating(false)
        .alternateTypeRules(
            newRule(resolver.resolve(Mono.class,
                resolver.resolve(ResponseEntity.class, InputStreamResource.class)),
                resolver.resolve(File.class)))
        .host("bugs.springfox.io")
        .protocols(new HashSet<>(Arrays.asList(
            "http",
            "https")));
  }
}
