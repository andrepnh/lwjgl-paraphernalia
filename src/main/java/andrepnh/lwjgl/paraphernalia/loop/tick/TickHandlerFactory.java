package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.loop.LoopKind;

public final class TickHandlerFactory {
    
    public TickHandler getInstance(LoopKind loopKind) {
        switch (loopKind) {
            case SIMPLE: return new DefaultTickHandler();
            case FIXED_FPS: return new FixedFps();
            default: throw new IllegalArgumentException("Unknown LoopKind: " + loopKind);
        }
    }
}
