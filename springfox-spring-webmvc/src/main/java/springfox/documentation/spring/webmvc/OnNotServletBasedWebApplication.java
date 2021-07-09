package springfox.documentation.spring.webmvc;

import springfox.documentation.core.common.ClassNotPresentInClassPathCondition;

public class OnNotServletBasedWebApplication extends ClassNotPresentInClassPathCondition {
  private static final String SERVLET_WEB_APPLICATION_CLASS
      = "org.springframework.web.context.support.GenericWebApplicationContext";

  @Override
  protected String getClassName() {
    return SERVLET_WEB_APPLICATION_CLASS;
  }
}
