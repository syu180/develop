package container;

public interface Host extends Container{
    void setEngine(Engine engine);
    Engine getEngine();
    void setName(String name);
    String getName();
    void setAppBase(String appbase);
    String getAppBase();
    void addContext(Context context);
    Context[] getContextes();
}
