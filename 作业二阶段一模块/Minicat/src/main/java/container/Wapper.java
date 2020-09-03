package container;


import server.Servlet;

/**
 * servlet容器
 */
public interface Wapper {
    /**
     * 获取servlet
     * @return
     */
    Servlet getServlet();

    /**
     * 设置servlet
     * @param servlet
     */
    void setServlet(Servlet servlet);

    /**
     * 设置URLPattern
     * @param urlPattern
     */
    void setUrlPattern(String urlPattern);

    /**
     * 获取urlPattern
     * @return
     */
    String getUrlPattern();

    /**
     * 设置ClassName
     * @param className
     */
    void setClassName(String className);

    String getClassName();

    void setServletName(String servletName);

    String getServletName();

    void setContext(Context context);
}
