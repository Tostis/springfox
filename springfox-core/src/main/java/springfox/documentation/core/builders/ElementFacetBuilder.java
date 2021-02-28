package springfox.documentation.core.builders;

import springfox.documentation.core.schema.ElementFacet;

public interface ElementFacetBuilder {
  ElementFacet build();

  ElementFacetBuilder copyOf(ElementFacet facet);
}
