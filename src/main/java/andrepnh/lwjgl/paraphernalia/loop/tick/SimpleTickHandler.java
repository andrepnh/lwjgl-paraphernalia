package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.GlobalState;
import andrepnh.lwjgl.paraphernalia.loop.input.DefaultInputHandler;
import andrepnh.lwjgl.paraphernalia.loop.render.DefaultRenderer;
import andrepnh.lwjgl.paraphernalia.loop.update.DefaultUpdater;
import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.function.Consumer;

public class SimpleTickHandler implements TickHandler {

    @Override
    public List<Consumer<TickState>> getSteps(GlobalState state) {
        return ImmutableList.<Consumer<TickState>>builder()
            .add(tstate -> new DefaultInputHandler().handleInput())
            .add(tstate -> new DefaultUpdater(state).update())
            .add(tstate -> new DefaultRenderer(state).render())
            .build();
    }
    
}
