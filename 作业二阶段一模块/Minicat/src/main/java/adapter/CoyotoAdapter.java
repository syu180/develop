package adapter;

import connector.Connector;
import container.Context;
import container.Engine;
import container.Host;
import container.Wapper;
import server.Request;
import server.Response;

import java.io.IOException;

public class CoyotoAdapter implements Adapter {
    private Connector connector;

    public CoyotoAdapter(){
    }

    @Override
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    @Override
    public Connector getConnector() {
        return this.connector;
    }

    @Override
    public void service(Request request, Response response) {
        String[] split = request.getUrl().split("/");
        //查找Context
        Engine[] engines = this.connector.getService().getEngines();
        boolean isInHost = false;
        for (Engine engine : engines){
            Host[] hosts = engine.getHosts();
            for(Host host : hosts){
                if (!host.getName().equalsIgnoreCase(request.getHost())){
                    continue;
                }
                Context[] contextes = host.getContextes();
                for (Context context : contextes){
                    String[] split1 = request.getUrl().split("/");
                    if (context.getContextName().equalsIgnoreCase(split1[1])){
                        //查找上下文
                        request.setContext(context);
                        response.setContext(context);
                        isInHost = true;
                        response.setHost(host);
                        break;
                    }
                }

                if (isInHost){
                    break;
                }
            }
            if (isInHost){
                break;
            }
        }

        // 查找servlet并且执行。
        if (request.getContext()!=null){
            String contextName = request.getContext().getContextName();
            String urlPattern = request.getUrl().replaceFirst("/" + contextName, "");
            Wapper wapper = request.getContext().findWapper(urlPattern);
            if (wapper == null){
                try {
                    response.outputHtml1(request.getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                wapper.getServlet().service(request,response);
            }
        }
    }
}
