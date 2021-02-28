package springfox.documentation.swagger2.mappers;

import io.swagger.models.properties.ObjectProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;
import springfox.documentation.core.schema.QualifiedModelName;
import springfox.documentation.core.schema.ReferenceModelSpecification;
import springfox.documentation.core.service.ModelNamesRegistry;

import java.util.Optional;

import static org.slf4j.LoggerFactory.*;
import static springfox.documentation.core.builders.BuilderDefaults.emptyToNull;

public class ReferenceModelSpecificationToPropertyConverter
    implements Converter<ReferenceModelSpecification, Property> {
  private static final Logger LOGGER = getLogger(ReferenceModelSpecificationToPropertyConverter.class);
  private final ModelNamesRegistry modelNamesRegistry;

  public ReferenceModelSpecificationToPropertyConverter(ModelNamesRegistry modelNamesRegistry) {
    this.modelNamesRegistry = modelNamesRegistry;
  }

  @Override
  public Property convert(ReferenceModelSpecification source) {
    ObjectProperty objectProperty = new ObjectProperty();

    QualifiedModelName qualifiedModelName = source.getKey().getQualifiedModelName();
    if ("java.lang".equals(qualifiedModelName.getNamespace())
        && "object".equals(qualifiedModelName.getName())) {
      return objectProperty;
    }
    if (emptyToNull(source.getKey().getQualifiedModelName().getName()) != null) {
      Optional<String> name = modelNamesRegistry.nameByKey(source.getKey());
      if (!name.isPresent()) {
        LOGGER.error("Unable to find a model that matches key {}", source.getKey());
      }
      return new RefProperty(
          name.orElse("Error-" + source.getKey().getQualifiedModelName()));
    }
    return null;
  }
}
