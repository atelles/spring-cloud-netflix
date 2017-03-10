package org.springframework.cloud.netflix.feign.annotation;

import feign.MethodMetadata;
import org.springframework.cloud.netflix.feign.AnnotatedParameterProcessor;
import org.springframework.web.bind.annotation.RequestPart;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;

/**
 * {@link RequestPart} parameter processor.
 *
 * @author Lukáš Vasek
 * @see AnnotatedParameterProcessor
 */
public class RequestPartParameterProcessor implements AnnotatedParameterProcessor {

    private static final Class<RequestPart> ANNOTATION = RequestPart.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context, Annotation annotation) {
        int parameterIndex = context.getParameterIndex();
        MethodMetadata data = context.getMethodMetadata();

        RequestPart requestPart = ANNOTATION.cast(annotation);
        String name = requestPart.name();
        checkState(emptyToNull(name) != null, "RequestPart.value() was empty on parameter %s", parameterIndex);
        context.setParameterName(name);
        if (!data.formParams().contains(name))
            data.formParams().add(name);

        return true;
    }

}