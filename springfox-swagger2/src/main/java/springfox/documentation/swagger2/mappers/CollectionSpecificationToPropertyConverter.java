package springfox.documentation.swagger2.mappers;

import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.ByteArrayProperty;
import io.swagger.models.properties.Property;
import org.springframework.core.convert.converter.Converter;
import springfox.documentation.core.schema.CollectionSpecification;
import springfox.documentation.core.schema.CollectionType;
import springfox.documentation.core.schema.ScalarType;
import springfox.documentation.core.service.ModelNamesRegistry;

public class CollectionSpecificationToPropertyConverter implements Converter<CollectionSpecification, Property> {
  private final ModelNamesRegistry modelNamesRegistry;

  public CollectionSpecificationToPropertyConverter(ModelNamesRegistry modelNamesRegistry) {
    this.modelNamesRegistry = modelNamesRegistry;
  }

  @Override
  public Property convert(CollectionSpecification source) {
    ArrayProperty arrayProperty = new ArrayProperty();
    if (source.getModel().getScalar().isPresent()
        && source.getModel().getScalar().get().getType() == ScalarType.BYTE) {
      return new ByteArrayProperty();
    } else {
      arrayProperty.items(new PropertyMapper()
                              .fromModel(source.getModel(), modelNamesRegistry));
    }
    if (source.getCollectionType() == CollectionType.SET) {
      arrayProperty.uniqueItems();
    }
    return arrayProperty;
  }
}
