package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import java.util.List;
import java.util.function.Consumer;

public interface TickHandler {
    
    List<Consumer<TickState>> getSteps(GlobalState state);
    
    default void runSteps(GlobalState state) {
        TickState tstate = new TickState();
        getSteps(state)
            .stream()
            .forEach(step -> step.accept(tstate));
    }
    
}
