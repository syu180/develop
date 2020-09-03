package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class ProcessHandler extends Thread {

    private Socket socket;
    private Map<String , HttpServlet> servletMap;

    public ProcessHandler(Socket socket, Map<String , HttpServlet> servletMap){
        this.socket = socket;
        this.servletMap = servletMap;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Request request = new Request(inputStream);
            Response response = new Response(outputStream);

            if ( !servletMap.containsKey(request.getUrl())){
                response.outputHtml(request.getUrl());
            } else {
                HttpServlet httpServlet = servletMap.get(request.getUrl());
                httpServlet.service(request,response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
