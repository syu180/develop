package connector;


import container.Server;
import container.Service;
import life.LifecycleBase;

import java.io.IOException;

/**
 * 连接器
 */
public class Connector extends LifecycleBase {
    //端口
    private Integer port;
    private Service service;
    private Server server;
    private ProtocolHandler protocolHandler;

    public Connector(ProtocolHandler protocolHandler){
        this.protocolHandler = protocolHandler;
    }

    public Integer getPort(){
        return port;
    }
    public void setPort(Integer port){
        this.port = port;
    }

    public void setServer(Server server){
        this.server = server;
    }

    public Server getServer(){
        return this.server;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    protected void initInternal() {
        try {
            protocolHandler.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void startInternal() {
        try {
            protocolHandler.setExecutor(server.getExecutor());
            protocolHandler.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
