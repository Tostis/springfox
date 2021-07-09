module springfox.schema {
    exports springfox.documentation.schema;
    exports springfox.documentation.schema.plugins;
    exports springfox.documentation.schema.property.bean;
    exports springfox.documentation.schema.property.field;
    exports springfox.documentation.schema.property;
    exports springfox.documentation.schema.configuration;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.classmate;
    requires org.slf4j;
    requires spring.beans;
    requires spring.context;
    requires springfox.spi;
    requires springfox.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires spring.core;
    requires java.xml.bind;
    requires java.sql;
    requires spring.web;
}