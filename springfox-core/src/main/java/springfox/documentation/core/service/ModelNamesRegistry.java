package springfox.documentation.core.service;

import springfox.documentation.core.schema.ModelKey;
import springfox.documentation.core.schema.ModelSpecification;

import java.util.Map;
import java.util.Optional;

public interface ModelNamesRegistry {
  Map<String, ModelSpecification> modelsByName();

  Optional<String> nameByKey(ModelKey key);
}
