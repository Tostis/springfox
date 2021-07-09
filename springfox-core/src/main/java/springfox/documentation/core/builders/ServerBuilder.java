package springfox.documentation.core.builders;

import springfox.documentation.core.service.Server;
import springfox.documentation.core.service.ServerVariable;
import springfox.documentation.core.service.VendorExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerBuilder {
  private String name;
  private String url;
  private String description;
  private final List<ServerVariable> variables = new ArrayList<>();
  private final List<VendorExtension> extensions = new ArrayList<>();

  public ServerBuilder name(String name) {
    this.name = name;
    return this;
  }

  public ServerBuilder url(String url) {
    this.url = url;
    return this;
  }

  public ServerBuilder description(String description) {
    this.description = description;
    return this;
  }

  public ServerBuilder variables(Collection<ServerVariable> variables) {
    this.variables.addAll(BuilderDefaults.nullToEmptyList(variables));
    return this;
  }

  public ServerBuilder extensions(Collection<VendorExtension> extensions) {
    this.extensions.addAll(BuilderDefaults.nullToEmptyList(extensions));
    return this;
  }

  public Server build() {
    return new Server(name, url, description, variables, extensions);
  }
}