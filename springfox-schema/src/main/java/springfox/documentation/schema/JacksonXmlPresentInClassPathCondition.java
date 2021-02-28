package springfox.documentation.schema;

import springfox.documentation.core.common.ClassPresentInClassPathCondition;

public class JacksonXmlPresentInClassPathCondition extends ClassPresentInClassPathCondition {
  @Override
  protected String getClassName() {
    return "com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty";
  }
}
