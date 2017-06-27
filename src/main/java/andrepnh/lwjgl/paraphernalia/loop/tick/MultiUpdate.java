package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.input.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.DefaultRenderer;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;

public class MultiUpdate implements TickHandler {
    private static final int MILLISECONDS_PER_UPDATE = 12; // ~80fps //10; // 100fps

    @Override
    public List<Consumer<TickState>> getSteps(GlobalState state) {
        return ImmutableList.<Consumer<TickState>>builder()
            .add(tstate -> {
                long tickStart = System.currentTimeMillis();
                long elapsedMilliseconds = tickStart - tstate.<Long>get(Property.PREVIOUS_TICK_START);
                long lag = tstate.<Long>get(Property.LAG) + elapsedMilliseconds;
                tstate.put(Property.LAG, lag);
                tstate.put(Property.TICK_START, tickStart);
            }).add(tstate -> new DefaultInputHandler().handleInput())
            .add(tstate -> {
                long lag = tstate.<Long>get(Property.LAG);
                DefaultUpdater updater = new DefaultUpdater(state);
                while (lag >= MILLISECONDS_PER_UPDATE) {
                    simulateSlowUpdate(state.updateDelay);
                    updater.update(GlobalState.DEFAULT_MILLISECONDS_PER_FRAME);
                    lag -= MILLISECONDS_PER_UPDATE;
                }
                tstate.put(Property.LAG, lag);
            }).add(tstate -> simulateSlowRender(state.renderDelay))
            .add(tstate -> new DefaultRenderer(state).render())
            .add(tstate -> tstate.put(Property.PREVIOUS_TICK_START, tstate.get(Property.TICK_START)))
            .build();
    }

    @Override
    public TickState runSteps(GlobalState state, TickState previousTickState) {
        TickState tstate = new TickState();
        long previousTickStart = getPreviousFrameStart(previousTickState);
        tstate.put(Property.PREVIOUS_TICK_START, previousTickStart);
        
        long lag = getAccumulatedLag(previousTickState);
        tstate.put(Property.LAG, lag);
        
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

    private long getAccumulatedLag(TickState previousTickState) {
        if (previousTickState == null) {
            return 0;
        } else {
            Long previousLag = previousTickState.get(Property.LAG);
            boolean previousTickHadLag = previousLag != null;
            if (previousTickHadLag) {
                return previousLag;
            } else {
                return 0;
            }
        }
    }
    
    private enum Property implements TickProperty {
        TICK_START, PREVIOUS_TICK_START, LAG
    }
    
}
