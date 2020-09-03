package adapter;

import connector.Connector;
import server.Request;
import server.Response;

public interface Adapter {
    Connector getConnector();

    void setConnector(Connector connector);

    void service(Request request, Response response);
}
