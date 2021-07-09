module springfox.spring.webmvc {
    exports springfox.documentation.spring.webmvc;
    requires springfox.core;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.web;
    requires spring.webmvc;
    requires springfox.spi;
    requires springfox.spring.web;
    requires com.fasterxml.jackson.databind;
    requires springfox.schema;
    requires com.fasterxml.classmate;
}