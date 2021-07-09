module springfox.spi {
    exports springfox.documentation.spi.spi;
    exports springfox.documentation.spi.spi.service;
    exports springfox.documentation.spi.spi.service.contexts;
    exports springfox.documentation.spi.service;
    exports springfox.documentation.spi.spi.schema.contexts;
    exports springfox.documentation.spi.spi.schema;
    requires spring.plugin.core;
    requires springfox.core;
    requires com.fasterxml.classmate;
    requires spring.web;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires spring.context;
}