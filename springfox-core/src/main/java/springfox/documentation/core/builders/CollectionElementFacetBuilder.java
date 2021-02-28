package springfox.documentation.core.builders;

import springfox.documentation.core.schema.CollectionElementFacet;
import springfox.documentation.core.schema.ElementFacet;
import springfox.documentation.core.service.CollectionFormat;

public class CollectionElementFacetBuilder implements ElementFacetBuilder {
  private Integer maxItems;
  private Integer minItems;
  private Boolean uniqueItems;
  private CollectionFormat collectionFormat;


  public CollectionElementFacetBuilder collectionFormat(CollectionFormat collectionFormat) {
    this.collectionFormat = BuilderDefaults.defaultIfAbsent(collectionFormat, this.collectionFormat);
    return this;
  }

  public CollectionElementFacetBuilder maxItems(Integer maxItems) {
    this.maxItems = BuilderDefaults.defaultIfAbsent(maxItems, this.maxItems);
    return this;
  }

  public CollectionElementFacetBuilder minItems(Integer minItems) {
    this.minItems = BuilderDefaults.defaultIfAbsent(minItems, this.minItems);
    return this;
  }

  public CollectionElementFacetBuilder uniqueItems(Boolean uniqueItems) {
    this.uniqueItems = BuilderDefaults.defaultIfAbsent(uniqueItems, this.uniqueItems);
    return this;
  }

  @Override
  public ElementFacet build() {
    if (maxItems == null && minItems == null && uniqueItems == null) {
      return null;
    }
    return new CollectionElementFacet(maxItems, minItems, uniqueItems);
  }

  @Override
  public CollectionElementFacetBuilder copyOf(ElementFacet facet) {
    if (!(facet instanceof CollectionElementFacet)) {
      return this;
    }
    CollectionElementFacet other = (CollectionElementFacet) facet;
    return maxItems(other.getMaxItems())
        .minItems(other.getMinItems())
        .uniqueItems(other.getUniqueItems());
  }
}