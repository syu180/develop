package container;

import life.Lifecycle;

/**
 * 引擎容器
 */
public interface Engine extends Lifecycle {
    /**
     * 获取服务容器
     * @return
     */
    Service getService();

    /**
     * 设置服务容器
     * @param service
     */
    void setService(Service service);

    /**
     * 设置Host
     * @param hosts
     */
    void setHosts(Host[] hosts);

    /**
     * 获取host列表
     * @return
     */
    Host[] getHosts();
}
