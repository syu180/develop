package life;

/**
 * 定义生命周期：
 * 抽象各个容器的生命周期
 * init();
 * start();
 * stop();//本期暂不提供
 * destory(); //本期暂不提供
 */
public interface Lifecycle {
    /**
     * 容器初始化接口
     */
    void init();

    /**
     * 容器启动接口
     */
    void start();
}
