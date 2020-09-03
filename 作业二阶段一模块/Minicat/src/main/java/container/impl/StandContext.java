package container.impl;

import container.Context;
import container.Wapper;
import life.LifecycleBase;
import server.Servlet;

import java.util.Map;
import java.util.Set;

public class StandContext extends LifecycleBase implements Context {
    private Map<String,Wapper> wapperMap = new java.util.concurrent.ConcurrentHashMap<>();
    private String contextName;
    private ClassLoader classLoader;

    @Override
    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    @Override
    public String getContextName() {
        return this.contextName;
    }

    @Override
    public void addWapper(Wapper wapper) {
        wapperMap.put(wapper.getUrlPattern(),wapper);
    }

    @Override
    public Wapper findWapper(String urlPattern) {
        return wapperMap.get(urlPattern);
    }

    @Override
    public void setClassloader(ClassLoader classloader) {
        this.classLoader = classloader;
    }

    @Override
    protected void initInternal() {
        Set<Map.Entry<String, Wapper>> entries = wapperMap.entrySet();
        for (Map.Entry<String,Wapper> entry : entries) {
            Wapper value = entry.getValue();
            try {
                Class<?> aClass = this.classLoader.loadClass(value.getClassName());
                Servlet servlet = (Servlet) aClass.newInstance();
                value.setServlet(servlet);
                servlet.init();
                System.out.println(servlet.getClass().getName()+" init");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void startInternal() {
        System.out.println(this.getClass().getName()+" startContext");
    }
}
