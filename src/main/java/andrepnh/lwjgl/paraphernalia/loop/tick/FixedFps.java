package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.input.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import andrepnh.lwjgl.paraphernalia.loop.render.DefaultRenderer;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;

public class FixedFps implements TickHandler {
    private static final int MILLISECONDS_PER_FRAME = 32; // 30fps

    @Override
    public List<Consumer<TickState>> getSteps(GlobalState state) {
        return ImmutableList.<Consumer<TickState>>builder()
            .add(tstate -> tstate.put(Property.FRAME_START, System.currentTimeMillis()))
            .add(tstate -> new DefaultInputHandler().handleInput())
            .add(tstate -> simulateSlowUpdate(state.updateDelay))
            .add(tstate -> new DefaultUpdater(state).update(GlobalState.DEFAULT_MILLISECONDS_PER_FRAME))
            .add(tstate -> simulateSlowRender(state.renderDelay))
            .add(tstate -> new DefaultRenderer(state).render(0))
            .add(tstate -> sleep(tstate.<Long>get(Property.FRAME_START) 
                + MILLISECONDS_PER_FRAME - System.currentTimeMillis()))
            .build();
    }

    private enum Property implements TickProperty {
        FRAME_START
    }
    
}
