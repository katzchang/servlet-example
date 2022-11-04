package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.setContentType("text/html; charset=utf-8");
    PrintWriter out = resp.getWriter();
    out.println("<html><body>Hello, Servlet World!</body></html>");
    out.flush();
    out.close();
  }
}

