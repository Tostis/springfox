package springfox.documentation.core.schema;

import springfox.documentation.core.builders.ElementFacetBuilder;

public interface ElementFacet {
  Class<? extends ElementFacetBuilder> facetBuilder();
}
