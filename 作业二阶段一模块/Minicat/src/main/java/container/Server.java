package container;


import connector.Connector;
import life.Lifecycle;

import java.util.concurrent.Executor;

public interface Server extends Lifecycle {

    void setServices(Service[] services);

    Service[] findServices();

    void addConnector(Connector connector);

    Executor getExecutor();

}
