/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaporware.GeneradorComplejidad;

/**
 *
 * @author Angel
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

//@WebServlet(name = "servGenerador", urlPatterns = "/generador1/*")
public class servGenerador extends HttpServlet {

    Generador g;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        String complex = request.getParameter("complex");
        String metodos = request.getParameter("metodos");
        String var = request.getParameter("var");
        String varA = request.getParameter("varA");
        String bucles = request.getParameter("bucles");
        g = new Generador(Integer.parseInt(metodos), Integer.parseInt(var), Integer.parseInt(varA), Integer.parseInt(bucles), complex);
        g.constructor(complex);
        for(int i=0;i<Integer.parseInt(metodos);i++){
            
        }
        out.print(g.mostrarCodigo());

        response.setContentType("text/html");
        out.println("<html>");
        out.println("<head><title>Table Example</title></head>");
        out.println("<body>");
        String form = "<form method='post' action='" + request.getRequestURL() + "?" + request.getQueryString() + "'>";
        out.println(form);
        out.println("<p><input type='submit' value='comprobar'></p>");
        out.println("<p>Click <a th:href=\'@{/home}\'>here</a> to go home .</p>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        PrintWriter out = response.getWriter();
        out.println(g.imprimir());
    }

}
