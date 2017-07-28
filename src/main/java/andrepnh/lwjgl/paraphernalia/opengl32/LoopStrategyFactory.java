package andrepnh.lwjgl.paraphernalia.opengl32;

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
