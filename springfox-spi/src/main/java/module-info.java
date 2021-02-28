module springfox.spi {
    exports springfox.documentation.spi.spi;
    exports springfox.documentation.spi.spi.service;
    requires spring.plugin.core;
    requires springfox.core;
    requires com.fasterxml.classmate;
    requires spring.web;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires spring.context;
}