package server;

import java.io.IOException;

public class Demo1Servlet extends HttpServlet {
    @Override
    public void doGet(Request request, Response response) {
        String content = "<h1>Demo1Servlet get "+request.getContext().getContextName()+"</h1>";
        try {
            response.write(ProtocolHttpUtil.http200header(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {
        String content = "<h1>LagouServlet post "+request.getContext().getContextName()+"</h1>";
        try {
            response.write(ProtocolHttpUtil.http200header(content.getBytes().length) + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
