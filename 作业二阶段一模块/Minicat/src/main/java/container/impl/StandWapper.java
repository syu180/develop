package container.impl;

import container.Context;
import container.Wapper;
import server.Servlet;

public class StandWapper implements Wapper {
    private String className;
    private String servletName;
    private String urlPattern;
    private Servlet servlet;
    private Context context;

    @Override
    public Servlet getServlet() {
        return servlet;
    }

    @Override
    public void setServlet(Servlet servlet) {
        this.servlet = servlet;
    }

    @Override
    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    @Override
    public String getUrlPattern() {
        return this.urlPattern;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String getClassName() {
        return this.className;
    }

    @Override
    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    @Override
    public String getServletName() {
        return this.servletName;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

}
