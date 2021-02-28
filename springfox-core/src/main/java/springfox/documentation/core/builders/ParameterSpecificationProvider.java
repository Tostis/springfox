package springfox.documentation.core.builders;

import springfox.documentation.core.service.ParameterSpecification;

@FunctionalInterface
public interface ParameterSpecificationProvider {
  ParameterSpecification create(ParameterSpecificationContext context);
}
