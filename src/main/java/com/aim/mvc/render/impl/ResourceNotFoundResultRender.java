package com.aim.mvc.render.impl;

import com.aim.mvc.RequestProcessorChain;
import com.aim.mvc.render.ResultRender;

import javax.servlet.http.HttpServletResponse;

public class ResourceNotFoundResultRender implements ResultRender {
    private final String requestMethod;
    private final String requestPath;

    public ResourceNotFoundResultRender(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    @Override
    public void render(RequestProcessorChain requestProcessorChain) throws Exception {
        requestProcessorChain.getResponse().sendError(HttpServletResponse.SC_NOT_FOUND,
                String.format("Resource not found with method %s url %s",requestMethod, requestPath));
    }
}
