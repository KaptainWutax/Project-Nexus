package kaptainwutax.nexus.task;

import kaptainwutax.nexus.utility.Time;

public abstract class Task {

    protected int tick = 0;

    public void tick() {
        if(tick == 0) {
            this.start();
        } else {
            this.update(Time.DELTA);
        }

        this.tick++;
    }

    protected abstract void start();
    protected abstract void update(long deltaTime);

}
