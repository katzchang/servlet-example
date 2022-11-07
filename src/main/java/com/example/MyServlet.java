package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

import io.opentelemetry.api.*;
import io.opentelemetry.api.trace.*;
import io.opentelemetry.context.*;

public class MyServlet extends HttpServlet {
  Tracer tracer = GlobalOpenTelemetry.getTracer("instrumentation-servlet-session", "0.1.0");

  private Span spanFromSession(HttpSession session, String spanName) {
    Span span = (Span) session.getAttribute("rootSpan");
    if (span != null) {
      Span childSpan = tracer.spanBuilder(spanName).setParent(Context.current().with(span)).startSpan();
      return childSpan;
    } else {
      Span rootSpan = tracer.spanBuilder(spanName).startSpan();
      session.setAttribute("rootSpan", rootSpan);
      return rootSpan;
    }
  }
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(true);
    System.out.println("session id: " + session.getId());
    
    Span span = spanFromSession(session, "MyServlet");
    span.makeCurrent();
    System.out.println("span: " + span);
 
    try {
      resp.setContentType("text/html; charset=utf-8");
      PrintWriter out = resp.getWriter();
      out.println("<html><body>Hello, Servlet World!</body></html>");
      out.flush();
      out.close();
    } finally {
      span.end();
    }
  }
}
