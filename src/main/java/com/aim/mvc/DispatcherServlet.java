package com.aim.mvc;

import com.aim.core.context.BeanContainer;
import com.aim.mvc.processor.RequestProcessor;
import com.aim.mvc.processor.impl.ControllerRequestProcessor;
import com.aim.mvc.processor.impl.JSPRequestProcessor;
import com.aim.mvc.processor.impl.PreRequestProcessor;
import com.aim.mvc.processor.impl.StaticResourceRequestProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    private final List<RequestProcessor> PROCESSOR = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // container initialization
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.init("com.example.mvc");

        PROCESSOR.add(new PreRequestProcessor());
        PROCESSOR.add(new StaticResourceRequestProcessor(getServletContext()));
        PROCESSOR.add(new JSPRequestProcessor(getServletContext()));
        PROCESSOR.add(new ControllerRequestProcessor());
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        RequestProcessorChain processorChain = new RequestProcessorChain(PROCESSOR.iterator(),request, response);
        processorChain.doRequestProcessorChain();
        processorChain.doRender();
    }
}
