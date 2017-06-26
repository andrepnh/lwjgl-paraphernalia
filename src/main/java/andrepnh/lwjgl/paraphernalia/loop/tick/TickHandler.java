package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface TickHandler {
    
    List<Consumer<TickState>> getSteps(GlobalState state);
    
    default TickState runSteps(GlobalState state, TickState previousTickState) {
        TickState tstate = new TickState();
        getSteps(state).forEach(step -> step.accept(tstate));
        return tstate;
    }
    
    default void simulateSlowUpdate(long millisecondsDelay) {
        sleep(millisecondsDelay);
    }
    
    default void simulateSlowRender(long millisecondsDelay) {
        sleep(millisecondsDelay);
    }
    
    default void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    
}
