/*
 *
 *  Copyright 2015-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */

package springfox.service.model

import spock.lang.Specification
import springfox.documentation.core.builders.DocumentationBuilder
import springfox.documentation.core.service.ApiKey
import springfox.documentation.core.service.ApiListingReference
import springfox.documentation.core.service.Documentation
import springfox.documentation.core.service.SecurityScheme

class DocumentationSpec extends Specification {
  def "Groups are built correctly"() {
    given:
    List<SecurityScheme> authorizations = [new ApiKey("api-key", "test", "header",)]
    Documentation built = new DocumentationBuilder()
        .resourceListing {
          it.securitySchemes(authorizations)
              .apis([Mock(ApiListingReference)])
        }
        .apiListingsByResourceGroupName(new HashMap<>())
        .build()
    expect:
    built.apiListings.size() == 0
    built.resourceListing.securitySchemes.size() == 1
  }
}
