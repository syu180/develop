package container.impl;

import container.Engine;
import container.Host;
import container.Service;
import life.LifecycleBase;

public class StandEngine extends LifecycleBase implements Engine {
    private Service service;
    private Host[] hosts = new Host[0];

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public void setHosts(Host[] hosts) {
        this.hosts = hosts;
    }

    @Override
    public Host[] getHosts() {
        return this.hosts;
    }

    @Override
    protected void initInternal() {
        for (int i = 0; i < hosts.length; i++) {
            hosts[i].init();
        }
    }

    @Override
    protected void startInternal() {
        for (int i = 0; i < hosts.length; i++) {
            hosts[i].start();
        }
    }
}
