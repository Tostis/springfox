module springfox.spring.webflux {
    exports springfox.documentation.spring.webflux;
    requires springfox.core;
    requires com.fasterxml.classmate;
    requires spring.core;
    requires spring.web;
    requires reactor.core;
    requires springfox.spi;
    requires spring.webflux;
    requires springfox.spring.web;
    requires com.fasterxml.jackson.databind;
    requires spring.beans;
    requires spring.context;
    requires springfox.schema;
}