package server;

public abstract class HttpServlet implements Servlet {

    @Override
    public void init() {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
        System.out.println("test classloader");
    }

    public abstract void doGet(Request request , Response response);

    public abstract void doPost(Request request , Response response);

    @Override
    public void service(Request request, Response response) {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            doGet(request , response);
        }else {
            doPost(request , response);
        }
    }

}
