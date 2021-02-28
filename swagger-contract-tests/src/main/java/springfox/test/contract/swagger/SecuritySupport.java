package springfox.test.contract.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.core.builders.ImplicitGrantBuilder;
import springfox.documentation.core.builders.OAuthBuilder;
import springfox.documentation.core.service.ApiKey;
import springfox.documentation.core.service.AuthorizationScope;
import springfox.documentation.core.service.GrantType;
import springfox.documentation.core.service.LoginEndpoint;
import springfox.documentation.core.service.SecurityScheme;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class SecuritySupport {
  @Bean
  public SecurityScheme oauth() {
    return new OAuthBuilder().name("petstore_auth").grantTypes(grantTypes()).scopes(scopes()).build();
  }

  @Bean
  public SecurityScheme apiKey() {
    return new ApiKey(
        "api_key",
        "api_key",
        "header");
  }

  public List<AuthorizationScope> scopes() {
    return Stream.of(
        new AuthorizationScope(
            "write:pets",
            "modify pets in your account"),
        new AuthorizationScope(
            "read:pets",
            "read your pets"))
        .collect(Collectors.toList());
  }

  public List<GrantType> grantTypes() {
    return Collections.singletonList(
        new ImplicitGrantBuilder()
            .loginEndpoint(new LoginEndpoint(
                "http://petstore.swagger.io/api/oauth/dialog"))
            .build());
  }

}
