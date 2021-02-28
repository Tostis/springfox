package springfox.documentation.spring.web.plugins;

import org.springframework.stereotype.Component;
import springfox.documentation.spi.spi.DocumentationType;
import springfox.documentation.spi.spi.service.ResponseBuilderPlugin;
import springfox.documentation.spi.spi.service.contexts.ResponseContext;

@Component
public class DefaultResponseTypeReader implements ResponseBuilderPlugin {
  @Override
  public void apply(ResponseContext responseContext) {
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return true;
  }
}
