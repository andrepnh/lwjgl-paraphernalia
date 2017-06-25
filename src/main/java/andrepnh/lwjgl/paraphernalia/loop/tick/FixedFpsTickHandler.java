package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.input.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import andrepnh.lwjgl.paraphernalia.loop.render.DefaultRenderer;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class FixedFpsTickHandler implements TickHandler {
    private static final int MILLISECONDS_PER_FRAME = 32; // 30fps

    @Override
    public List<Consumer<TickState>> getSteps(GlobalState state) {
        return ImmutableList.<Consumer<TickState>>builder()
            .add(tstate -> tstate.put(Property.FRAME_START, System.currentTimeMillis()))
            .add(tstate -> new DefaultInputHandler().handleInput())
            .add(tstate -> new DefaultUpdater(state).update())
            .add(tstate -> new DefaultRenderer(state).render())
            .add(tstate -> {
                try {
                    long frameStart = tstate.<Long>get(Property.FRAME_START);
                    long sleep = frameStart + MILLISECONDS_PER_FRAME - System.currentTimeMillis();
                    System.out.println(sleep);
                    TimeUnit.MILLISECONDS.sleep(sleep);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }).build();
    }

    enum Property implements TickProperty {
        FRAME_START
    }
    
}
