package connector.impl;

import adapter.Adapter;
import connector.Endpoint;
import connector.ProtocolHandler;
import process.Processor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioEndpoint implements Endpoint {
    private ProtocolHandler protocolHandler;
    private Adapter adapter;
    private ServerSocket serverSocket;

    public BioEndpoint(Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    public void setProtocolHandler(ProtocolHandler protocolHandler) {
        this.protocolHandler = protocolHandler;
    }

    @Override
    public void init() throws IOException {
        bind();
    }

    @Override
    public void start() throws IOException {
        System.out.println(this.getClass().getName()+" start recv.");
        while (true){
            // 开始接收请求
            Socket accept = this.serverSocket.accept();
            // 创建Processer处理请求。
            Processor processor = createProcessor(accept, adapter);
            protocolHandler.getExecutor().execute(processor);
        }
    }

    private Processor createProcessor(Socket accept, Adapter adapter) {
        return new Processor(accept,adapter);
    }

    @Override
    public void bind() throws IOException {
        serverSocket = new ServerSocket(protocolHandler.getPort());
        System.out.println(this.getClass().getName()+" init port "+ protocolHandler.getPort());
    }


}
