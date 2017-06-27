package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.input.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.DefaultRenderer;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;

public class FluidTimeStep implements TickHandler {
    
    @Override
    public List<Consumer<TickState>> getSteps(GlobalState state) {
        return ImmutableList.<Consumer<TickState>>builder()
            .add(tstate -> tstate.put(Property.TICK_START, System.currentTimeMillis()))
            .add(tstate -> new DefaultInputHandler().handleInput())
            .add(tstate -> simulateSlowUpdate(state.updateDelay))
            .add(tstate -> {
                long elapsedMilliseconds = tstate.<Long>get(Property.TICK_START) 
                    - tstate.<Long>get(Property.PREVIOUS_TICK_START);
                new DefaultUpdater(state).update((int) elapsedMilliseconds);
            }).add(tstate -> simulateSlowRender(state.renderDelay))
            .add(tstate -> new DefaultRenderer(state).render(0))
            .add(tstate -> tstate.put(Property.PREVIOUS_TICK_START, tstate.get(Property.TICK_START)))
            .build();
    }

    @Override
    public TickState runSteps(GlobalState state, TickState previousTickState) {
        TickState tstate = new TickState();
        long previousTickStart = getPreviousFrameStart(previousTickState);
        tstate.put(Property.PREVIOUS_TICK_START, previousTickStart);
        getSteps(state).forEach(step -> step.accept(tstate));
        return tstate;
    }

    private long getPreviousFrameStart(TickState previousTickState) {
        if (previousTickState == null) {
            return System.currentTimeMillis();
        } else {
            Long previousTickStart = previousTickState.get(Property.TICK_START);
            boolean previousTickIsFluid = previousTickStart != null;
            if (previousTickIsFluid) {
                return previousTickStart;
            } else {
                return System.currentTimeMillis();
            }
        }
    }
    
    private enum Property implements TickProperty {
        TICK_START, PREVIOUS_TICK_START
    }
    
}
