package org.encuestame.mvc.webflow;

import org.springframework.webflow.engine.FlowExecutionExceptionHandler;
import org.springframework.webflow.engine.RequestControlContext;
import org.springframework.webflow.engine.TargetStateResolver;
import org.springframework.webflow.engine.Transition;
import org.springframework.webflow.engine.support.DefaultTargetStateResolver;
import org.springframework.webflow.execution.FlowExecutionException;

public class ExceptionHandler implements FlowExecutionExceptionHandler {

    @Override
    public boolean canHandle(FlowExecutionException ex) {
        return ex.getCause().getClass()
                .isAssignableFrom(IllegalStateException.class);
    }

    @Override
    public void handle(FlowExecutionException ex, RequestControlContext ctx) {
        TargetStateResolver resolver = new DefaultTargetStateResolver("onError");
        Transition targetTransition = new Transition(resolver);
        ctx.execute(targetTransition);
    };

}
