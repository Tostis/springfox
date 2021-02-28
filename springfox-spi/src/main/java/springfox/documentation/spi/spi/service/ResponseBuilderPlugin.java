package springfox.documentation.spi.spi.service;

import org.springframework.plugin.core.Plugin;
import springfox.documentation.core.builders.ResponseBuilder;
import springfox.documentation.core.service.Response;
import springfox.documentation.spi.spi.DocumentationType;
import springfox.documentation.spi.spi.service.contexts.ResponseContext;

public interface ResponseBuilderPlugin extends Plugin<DocumentationType> {
  /**
   * Implement this method to enrich return values
   *
   * @param responseContext - context that can be used to override the parameter attributes
   * @see Response
   * @see ResponseBuilder
   */
  void apply(ResponseContext responseContext);
}
