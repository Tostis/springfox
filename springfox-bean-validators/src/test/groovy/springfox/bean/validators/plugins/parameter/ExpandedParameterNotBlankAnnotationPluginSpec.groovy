package springfox.bean.validators.plugins.parameter

import com.fasterxml.classmate.TypeResolver
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import springfox.bean.validators.plugins.AnnotationsSupport
import springfox.bean.validators.plugins.ReflectionSupport
import springfox.documentation.core.builders.ParameterBuilder
import springfox.documentation.core.builders.RequestParameterBuilder
import springfox.documentation.spi.spi.DocumentationType
import springfox.documentation.spi.spi.service.contexts.ParameterExpansionContext
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterMetadataAccessor

import javax.validation.constraints.NotBlank

class ExpandedParameterNotBlankAnnotationPluginSpec
    extends Specification
    implements AnnotationsSupport, ReflectionSupport {
  @Shared
  def resolver = new TypeResolver()

  def "Always supported"() {
    expect:
    new ExpandedParameterNotBlankAnnotationPlugin().supports(types)

    where:
    types << [DocumentationType.SPRING_WEB, DocumentationType.SWAGGER_2, DocumentationType.SWAGGER_12]
  }

  @Unroll
  def "@NotBlank annotations are reflected in the model for #fieldName"() {
    given:
    def sut = new ExpandedParameterNotBlankAnnotationPlugin()
    ParameterExpansionContext context = new ParameterExpansionContext(
        "Test",
        "",
        "",
        new ModelAttributeParameterMetadataAccessor(
            [named(Subject, fieldName).rawMember],
            resolver.resolve(Subject),
            fieldName),
        DocumentationType.SWAGGER_12,
        new ParameterBuilder(),
        new RequestParameterBuilder())

    when:
    sut.apply(context)
    def property = context.parameterBuilder.build()

    then:
    property.required == required

    where:
    fieldName      | required
    "noAnnotation" | false
    "annotated"    | true
  }

  class Subject {
    String noAnnotation
    @NotBlank
    String annotated
  }
}
