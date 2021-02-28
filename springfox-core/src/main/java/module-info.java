module springfox.core {
    exports springfox.documentation.core.annotations;
    exports springfox.documentation.core;
    exports springfox.documentation.core.schema;
    exports springfox.documentation.core.service;
    exports springfox.documentation.core.builders;
    exports springfox.documentation.core.spring.wrapper;
    requires net.bytebuddy;
    requires spring.core;
    requires spring.web;
    requires com.fasterxml.classmate;
    requires com.fasterxml.jackson.annotation;
    requires java.sql;
    requires org.slf4j;
}