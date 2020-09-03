package container.impl;

import container.Context;
import container.Engine;
import container.Host;
import life.LifecycleBase;

public class StandHost extends LifecycleBase implements Host {
    private Engine engine;
    private String name;
    private String appBase;
    private Context[] contexts = new Context[0];

    @Override
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public Engine getEngine() {
        return this.engine;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setAppBase(String appbase) {
        this.appBase = appbase;
    }

    @Override
    public String getAppBase() {
        return this.appBase;
    }

    @Override
    public void addContext(Context context) {
        Context result[] = new Context[contexts.length+1];
        System.arraycopy(this.contexts,0,result,0,this.contexts.length);
        result[contexts.length] = context;
        this.contexts = result;
    }

    @Override
    public Context[] getContextes() {
        return this.contexts;
    }

    @Override
    protected void initInternal() {
        for (int i = 0; i < contexts.length; i++) {
            contexts[i].init();
        }
    }

    @Override
    protected void startInternal() {
        for (int i = 0; i < contexts.length; i++) {
            contexts[i].start();
        }
    }
}
