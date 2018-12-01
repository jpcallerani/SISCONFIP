/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tcc.Utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jp
 */
public class RestrictPageFilter implements Filter {

    /**
     * 
     * @param filterConfig
     * @throws ServletException 
     */
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException 
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        String pageRequest = req.getRequestURL().toString();
        
        if (session.getAttribute("usuario") == null && pageRequest.contains("Principal.xhtml")) {
            resp.sendRedirect("Login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    /**
     * 
     */
    public void destroy() {
    }
    
}
