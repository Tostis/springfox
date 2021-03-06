package springfox.documentation.swagger.web
import spock.lang.Specification
import springfox.documentation.common.web.SwaggerApiListingReader
import springfox.documentation.core.builders.ApiListingBuilder
import springfox.documentation.core.service.ResourceGroup
import springfox.documentation.spi.spi.DocumentationType
import springfox.documentation.spi.spi.service.contexts.ApiListingContext
import springfox.documentation.spi.spi.service.contexts.Orderings
import springfox.documentation.spring.web.dummy.DummyController
import springfox.documentation.spring.web.dummy.DummyControllerWithTags

class SwaggerApiListingReaderSpec extends Specification {
  def "ApiListingTagReaderSpec supports all documentation types" () {
    given:
    SwaggerApiListingReader sut = new SwaggerApiListingReader()
    expect:
      sut.supports(DocumentationType.SWAGGER_12)
      sut.supports(DocumentationType.SWAGGER_2)
  }

  def "ApiListingTagReaderSpec extracts tags" () {
    given:
      SwaggerApiListingReader sut = new SwaggerApiListingReader()
    and:
      ApiListingContext context = Mock(ApiListingContext)
    when:
      context.resourceGroup >> new ResourceGroup("test", clazz)
    and:
      context.apiListingBuilder() >> new ApiListingBuilder(Orderings.apiPathCompatator())
    then:
      sut.apply(context)
    where:
      clazz                         | tags
      String                        | ["String"]
      DummyController               | ["Dummy Controller"]
      DummyControllerWithTags       | ["Tag1", "Tag2"]


  }
}
