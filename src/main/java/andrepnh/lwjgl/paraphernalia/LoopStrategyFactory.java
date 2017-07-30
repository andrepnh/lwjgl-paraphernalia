package andrepnh.lwjgl.paraphernalia;

import andrepnh.lwjgl.paraphernalia.loop.ExtrapolatedRenderingLoopStrategy;
import andrepnh.lwjgl.paraphernalia.loop.FixedFpsLoopStrategy;
import andrepnh.lwjgl.paraphernalia.loop.FluidTimeStepLoopStrategy;
import andrepnh.lwjgl.paraphernalia.loop.MultipleUpdatesLoopStrategy;
import andrepnh.lwjgl.paraphernalia.loop.SimpleLoopStrategy;

public class LoopStrategyFactory {
    public LoopStrategy getInstance(LoopType loopType) {
        switch (loopType) {
            case SIMPLE: return new SimpleLoopStrategy();
            case FIXED_FPS: return new FixedFpsLoopStrategy();
            case FLUID_TIME_STEP: return new FluidTimeStepLoopStrategy();
            
            // TODO: the game is not playing smoother, but faster when running multiple updates
            case MULTI_UPDATE: return new MultipleUpdatesLoopStrategy();
            case EXTRAPOLATING_MULTI_UPDATE: return new ExtrapolatedRenderingLoopStrategy();
            default: throw new IllegalArgumentException(loopType.toString());
        }
    }
}
