package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.loop.LoopKind;

public final class TickHandlerFactory {
    
    public TickHandler getInstance(LoopKind loopKind) {
        switch (loopKind) {
            case SIMPLE: return new SimpleTickHandler();
            case FIXED_FPS: return new FixedFpsTickHandler();
            default: throw new IllegalArgumentException("Unknown LoopKind: " + loopKind);
        }
    }
}
