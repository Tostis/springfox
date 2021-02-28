package springfox.documentation.core.builders;

import org.slf4j.Logger;
import org.springframework.http.MediaType;
import springfox.documentation.core.schema.ScalarType;
import springfox.documentation.core.service.ContentSpecification;
import springfox.documentation.core.service.Representation;
import springfox.documentation.core.service.ParameterSpecification;
import springfox.documentation.core.service.SimpleParameterSpecification;

import java.util.Optional;

import static org.slf4j.LoggerFactory.*;

public class MultipartFormDataParameterSpecificationProvider implements ParameterSpecificationProvider {
  private static final Logger LOGGER = getLogger(MultipartFormDataParameterSpecificationProvider.class);

  @Override
  public ParameterSpecification create(ParameterSpecificationContext context) {
    SimpleParameterSpecification simpleParameter = context.getSimpleParameter();
    ContentSpecification contentParameter = context.getContentParameter();

    ContentSpecificationBuilder contentSpecificationBuilder = context.getContentSpecificationBuilder();
    if (context.getAccepts().stream()
        .noneMatch(mediaType -> mediaType.equalsTypeAndSubtype(MediaType.APPLICATION_FORM_URLENCODED))) {
      if (simpleParameter != null && simpleParameter.getModel() != null) {
        contentSpecificationBuilder
            .copyOf(contentParameter)
            .requestBody(true)
            .representation(MediaType.MULTIPART_FORM_DATA)
            .apply(r -> r.model(m -> m.copyOf(simpleParameter.getModel()))
                .encoding(context.getName())
                .apply(e -> e.propertyRef(context.getName())
                    .contentType(MediaType.TEXT_PLAIN_VALUE)
                    .build()));
      } else if (contentParameter != null) {
        for (Representation each : contentParameter.getRepresentations()) {
          Optional<Representation> mediaType = contentParameter.representationFor(each.getMediaType());
          contentSpecificationBuilder
              .copyOf(contentParameter)
              .requestBody(true)
              .representation(each.getMediaType())
              .apply(r -> {
                r.model(m -> m.copyOf(
                    mediaType.map(Representation::getModel)
                        .orElse(new ModelSpecificationBuilder()
                            .name(context.getName())
                            .scalarModel(ScalarType.STRING)
                            .build())));
                contentParameter.getRepresentations().stream()
                    .flatMap(rep -> rep.getEncodings().stream())
                    .forEach(encoding -> r.encoding(encoding.getPropertyRef())
                        .apply(e -> e.copyOf(encoding)));
              });
        }
      } else {
        LOGGER.warn("Parameter should either be a simple or a content type");
        contentSpecificationBuilder
            .requestBody(true)
            .representation(MediaType.TEXT_PLAIN)
            .apply(r -> r.model(m -> m.copyOf(new ModelSpecificationBuilder()
                .name(context.getName())
                .scalarModel(ScalarType.STRING)
                .build()))
                .clearEncodings());
      }
    }

    return new ParameterSpecification(
        null,
        contentSpecificationBuilder.build());
  }
}
