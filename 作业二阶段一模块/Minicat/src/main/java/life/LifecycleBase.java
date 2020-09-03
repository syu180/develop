package life;

public abstract class LifecycleBase implements Lifecycle {

    protected abstract void initInternal();

    @Override
    public void init() {
        System.out.println(this.getClass().getName()+"  init begin");
        initInternal();
        System.out.println(this.getClass().getName()+"  init finished");
    }

    @Override
    public void start() {
        System.out.println(this.getClass().getName()+"  start begin");
        startInternal();
        System.out.println(this.getClass().getName()+"  start finished");
    }

    protected abstract void startInternal();
}
