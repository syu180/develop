package process;

import adapter.Adapter;
import server.Request;
import server.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Processor implements Runnable {
    private final Socket socket;
    private final Adapter adapter;

    public Processor(Socket socket,Adapter adapter){
        this.socket = socket;
        this.adapter = adapter;
        System.out.println(this.getClass().getName()+" recv a request " + socket.getLocalAddress().toString() +" port:"+socket.getPort());
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Request request = new Request(inputStream);
            Response response = new Response(outputStream);

            adapter.service(request,response);

//            if ( !servletMap.containsKey(request.getUrl())){
//                response.outputHtml(request.getUrl());
//            } else {
//                HttpServlet httpServlet = servletMap.get(request.getUrl());
//                httpServlet.service(request,response);
//            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
