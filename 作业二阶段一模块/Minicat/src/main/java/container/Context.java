package container;

import life.Lifecycle;

/**
 * 应用上下文
 */
public interface Context extends Lifecycle {

    /**
     * 设置应用上下文名称
     * @param contextName
     */
    void setContextName(String contextName);

    /**
     * 获取应用上下文名称
     * @return
     */
    String getContextName();

    /**
     * 添加servlet容器
     * @param wapper
     */
    void addWapper(Wapper wapper);

    /**
     * 查找Servlet
     * @param servletName
     * @return
     */
    Wapper findWapper(String servletName);

    /**
     * 设置web的classload
     * @param classloader
     */
    void setClassloader(ClassLoader classloader);

}
