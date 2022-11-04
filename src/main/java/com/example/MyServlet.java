package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.codec.digest.DigestUtils;
import io.opentelemetry.api.*;
import io.opentelemetry.api.trace.*;
import io.opentelemetry.context.*;

public class MyServlet extends HttpServlet {
  Tracer tracer = GlobalOpenTelemetry.getTracer("instrumentation-library-name", "1.0.0");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(true);
    System.out.println("session id: " + session.getId());

    String hexString = DigestUtils.md5Hex(session.getId());
    System.out.println(hexString);

    Span span = null;
    if(session.isNew()) {
      System.out.println("new session");
      span = tracer.spanBuilder("MyServlet").startSpan();
      session.setAttribute("rootSpan", span);
    } else {
      System.out.println("ongoing session");
      Span rootSpan = (Span) session.getAttribute("rootSpan");
      span = tracer.spanBuilder("MyServlet2").setParent(Context.current().with(rootSpan)).startSpan();
    }

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

