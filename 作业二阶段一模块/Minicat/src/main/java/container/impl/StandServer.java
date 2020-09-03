package container.impl;

import connector.Connector;
import container.Server;
import container.Service;
import life.LifecycleBase;

import java.util.concurrent.*;

public final class StandServer extends LifecycleBase implements Server {
    private Service[] services = new Service[0];
    private Connector[] connectors = new Connector[0];

    private Executor executor;

    @Override
    protected void initInternal() {
        for (Service service : services){
            service.init();
        }
        for (Connector connector : connectors) {
            connector.init();
        }

        //定义线程池
        int corePoolSize = 10; //线程池大小
        int maximumPoolSize = 50; //线程池最大大小
        long keepAliveTime = 100L; //保持活动时间
        TimeUnit unit = TimeUnit.SECONDS; //保持活动单位
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(50);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        this.executor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                workQueue,
                threadFactory,
                handler);
    }

    @Override
    protected void startInternal() {
        for (Service service : services){
            service.start();
        }
        for (Connector connector : connectors) {
            connector.start();
        }
    }

    @Override
    public void setServices(Service[] services) {
        this.services = services;
    }

    @Override
    public Service[] findServices() {
        return this.services;
    }

    @Override
    public void addConnector(Connector connector) {
        Connector[] result = new Connector[connectors.length+1];
        System.arraycopy(connectors,0,result,0,connectors.length);
        result[connectors.length] = connector;
        connectors = result;
    }

    @Override
    public Executor getExecutor() {
        return this.executor;
    }

}
