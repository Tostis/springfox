module springfox.spring.web {
    exports springfox.documentation.spring.web;
    exports springfox.documentation.spring.web.readers.operation;
    exports springfox.documentation.spring.web.paths;
    exports springfox.documentation.spring.web.plugins;
    exports springfox.documentation.spring.web.json;
    requires org.slf4j;
    requires spring.core;
    requires spring.web;
    requires springfox.core;
    requires com.fasterxml.jackson.databind;
    requires springfox.spi;
    requires spring.context;
    requires com.fasterxml.classmate;
    requires spring.beans;
    requires spring.plugin.core;
    requires springfox.schema;
    requires io.github.classgraph;
    requires java.desktop;
}