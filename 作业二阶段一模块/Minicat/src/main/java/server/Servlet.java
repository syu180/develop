package server;

public interface Servlet {

    /**
     * 初始化
     */
    void init();

    /**
     * 销毁
     */
    void destroy();

    /**
     * 服务
     * @param request
     * @param response
     */
    void service(Request request, Response response);
}
