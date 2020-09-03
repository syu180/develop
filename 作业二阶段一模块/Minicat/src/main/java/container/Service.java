package container;

/**
 *
 */
public interface Service extends Container{
    void setServer(Server server);

    /**
     * 获取引擎列表
     * @return
     */
    Engine[] getEngines();

    void addEngine(Engine engine);
}
