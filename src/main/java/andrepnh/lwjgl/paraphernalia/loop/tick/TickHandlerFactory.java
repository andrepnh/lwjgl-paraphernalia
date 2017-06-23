package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.loop.tick.TickHandler;
import andrepnh.lwjgl.paraphernalia.GlobalState;

public final class TickHandlerFactory {
    public TickHandler getInstance(GlobalState state) {
        switch (state.currentLoopKind) {
            case SIMPLE: return new SimpleTickHandler(state);
            case FIXED_FPS: return new FixedFpsTickHandler(state);
            default: throw new IllegalArgumentException("Unknown LoopKind: " + state.currentLoopKind);
        }
    }
}
