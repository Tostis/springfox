package springfox.documentation.schema.property;

import com.fasterxml.classmate.ResolvedType;
import org.springframework.stereotype.Service;
import springfox.documentation.core.builders.ModelSpecificationBuilder;
import springfox.documentation.core.builders.ReferenceModelSpecificationBuilder;
import springfox.documentation.core.schema.CollectionSpecification;
import springfox.documentation.core.schema.EnumerationFacet;
import springfox.documentation.core.schema.Enums;
import springfox.documentation.core.schema.MapSpecification;
import springfox.documentation.core.schema.ModelSpecification;
import springfox.documentation.core.schema.ReferenceModelSpecification;
import springfox.documentation.core.schema.ScalarType;
import springfox.documentation.core.schema.ScalarTypes;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.core.service.AllowableListValues;
import springfox.documentation.core.service.AllowableValues;
import springfox.documentation.spi.spi.schema.EnumTypeDeterminer;
import springfox.documentation.spi.spi.schema.contexts.ModelContext;

import java.util.Optional;

import static springfox.documentation.schema.property.PackageNames.*;

@Service
public class ModelSpecificationFactory {

  private final TypeNameExtractor typeNameExtractor;
  private final EnumTypeDeterminer enumTypeDeterminer;

  public ModelSpecificationFactory(
      TypeNameExtractor typeNameExtractor,
      EnumTypeDeterminer enumTypeDeterminer) {
    this.typeNameExtractor = typeNameExtractor;
    this.enumTypeDeterminer = enumTypeDeterminer;
  }

  public ModelSpecification create(
      ModelContext modelContext,
      ResolvedType resolvedType) {
    ReferenceModelSpecification reference = null;
    CollectionSpecification collectionSpecification =
        new CollectionSpecificationProvider(this)
            .create(
                modelContext,
                resolvedType)
            .orElse(null);

    MapSpecification mapSpecification =
        new MapSpecificationProvider(this)
            .create(
                modelContext,
                resolvedType)
            .orElse(null);

    Optional<ScalarType> scalar = ScalarTypes.builtInScalarType(resolvedType);
    EnumerationFacet enumerationFacet = null;
    if (!scalar.isPresent()
        && collectionSpecification == null
        && mapSpecification == null) {
      if (resolvedType != null
          && enumTypeDeterminer.isEnum(resolvedType.getErasedType())) {
        scalar = Optional.of(ScalarType.STRING);
        AllowableValues allowableValues = Enums.allowableValues(resolvedType.getErasedType());
        if (allowableValues instanceof AllowableListValues) {
          enumerationFacet = new EnumerationFacet(((AllowableListValues) allowableValues).getValues());
        }
      } else {
        reference = new ReferenceModelSpecificationBuilder()
            .key(k -> k.qualifiedModelName(q ->
                q.namespace(safeGetPackageName(resolvedType))
                    .name(
                        typeNameExtractor.typeName(
                            ModelContext.fromParent(
                                modelContext,
                                resolvedType))).build())
                .viewDiscriminator(modelContext.getView().orElse(null))
                .validationGroupDiscriminators(modelContext.getValidationGroups())
                .isResponse(modelContext.isReturnType()))
            .build();
      }
    }
    EnumerationFacet finalEnumerationFacet = enumerationFacet;
    ReferenceModelSpecification finalReference = reference;
    return new ModelSpecificationBuilder()
        .scalarModel(scalar.orElse(null))
        .referenceModel(r -> r.copyOf(finalReference))
        .collectionModel(c -> c.copyOf(collectionSpecification))
        .mapModel(m -> m.copyOf(mapSpecification))
        .facets(f -> f.enumerationFacet(e -> e.copyOf(finalEnumerationFacet)))
        .build();
  }

  public TypeNameExtractor getTypeNameExtractor() {
    return typeNameExtractor;
  }

  public EnumTypeDeterminer getEnumTypeDeterminer() {
    return enumTypeDeterminer;
  }
}
