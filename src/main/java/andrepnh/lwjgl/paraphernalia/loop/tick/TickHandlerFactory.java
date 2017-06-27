package andrepnh.lwjgl.paraphernalia.loop.tick;

import andrepnh.lwjgl.paraphernalia.loop.LoopKind;

public final class TickHandlerFactory {
    
    public TickHandler getInstance(LoopKind loopKind) {
        switch (loopKind) {
            case SIMPLE: return new DefaultTickHandler();
            case FIXED_FPS: return new FixedFps();
            case FLUID_TIME_STEP: return new FluidTimeStep();
            case MULTI_UPDATE: return new MultiUpdate();
            case EXTRAPOLATING_MULTI_UPDATE: return new ExtrapolatedMultiUpdate();
            default: throw new IllegalArgumentException("Unknown LoopKind: " + loopKind);
        }
    }
}
