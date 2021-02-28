package springfox.documentation.core.builders;

import springfox.documentation.core.service.OpenIdConnectScheme;
import springfox.documentation.core.service.VendorExtension;

import java.util.ArrayList;
import java.util.List;

public class OpenIdConnectSchemeBuilder {
  private String name;
  private String description;
  private final List<VendorExtension> extensions = new ArrayList<>();
  private String openIdConnectUrl;

  public OpenIdConnectSchemeBuilder name(String name) {
    this.name = name;
    return this;
  }

  public OpenIdConnectSchemeBuilder description(String description) {
    this.description = description;
    return this;
  }

  public OpenIdConnectSchemeBuilder extensions(List<VendorExtension> extensions) {
    this.extensions.addAll(BuilderDefaults.nullToEmptyList(extensions));
    return this;
  }

  public OpenIdConnectSchemeBuilder openIdConnectUrl(String openIdConnectUrl) {
    this.openIdConnectUrl = openIdConnectUrl;
    return this;
  }

  public OpenIdConnectScheme build() {
    return new OpenIdConnectScheme(name, description, extensions, openIdConnectUrl);
  }
}