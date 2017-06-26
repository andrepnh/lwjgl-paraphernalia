package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.input.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.DefaultRenderer;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;

public class FluidTimeStep implements TickHandler {
    
    private long previousFrameStart;

    @Override
    public List<Consumer<TickState>> getSteps(GlobalState state) {
        return ImmutableList.<Consumer<TickState>>builder()
            .add(tstate -> tstate.put(Property.FRAME_START, System.currentTimeMillis()))
            .add(tstate -> new DefaultInputHandler().handleInput())
            .add(tstate -> simulateSlowUpdate(state.updateDelay))
            .add(tstate -> new DefaultUpdater(state).update())
            .add(tstate -> simulateSlowRender(state.renderDelay))
            .add(tstate -> new DefaultRenderer(state).render())
            .build();
    }

    @Override
    public void runSteps(GlobalState state) {
        TickState tstate = new TickState();
        previousFrameStart = System.currentTimeMillis();
        tstate.put(Property.PREVIOUS_FRAME_START, previousFrameStart);
        getSteps(state).forEach(step -> step.accept(tstate));
    }
    
    private enum Property implements TickProperty {
        FRAME_START, PREVIOUS_FRAME_START
    }
    
}
