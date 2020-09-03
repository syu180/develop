package container.impl;

import container.Engine;
import container.Server;
import container.Service;
import life.LifecycleBase;

public class StandService extends LifecycleBase implements Service {
    private Server server;
    private Engine []engines = new Engine[0];

    @Override
    public void setServer(Server server) {
        this.server = server;
    }

    @Override
    public void addEngine(Engine engine){
        Engine[] result = new Engine[engines.length+1];
        System.arraycopy(engines,0,result,0,engines.length);
        result[engines.length] = engine;
        engines = result;
    }

    @Override
    public Engine[] getEngines() {
        return engines;
    }

    @Override
    protected void initInternal() {
        for (Engine engine : engines) {
            engine.init();
        }
    }

    @Override
    protected void startInternal() {
        for (Engine engine : engines) {
            engine.start();
        }
    }
}
