package springfox.documentation.spi.spi.service;

import org.springframework.plugin.core.Plugin;
import springfox.documentation.core.service.ModelNamesRegistry;
import springfox.documentation.spi.spi.DocumentationType;
import springfox.documentation.spi.spi.service.contexts.ModelSpecificationRegistry;

public interface ModelNamesRegistryFactoryPlugin extends Plugin<DocumentationType> {
  ModelNamesRegistry modelNamesRegistry(ModelSpecificationRegistry registry);
}
