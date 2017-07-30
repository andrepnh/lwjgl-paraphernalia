package andrepnh.lwjgl.paraphernalia.loop;

import andrepnh.lwjgl.paraphernalia.LoopStrategy;
import andrepnh.lwjgl.paraphernalia.Context;

public class FluidTimeStepLoopStrategy implements LoopStrategy {
    private static final long NANOSECONDS_PER_FRAME = 1000000000 / 60; // Targeting 60fps
    
    private long previousFrameTime;
    
    @Override
    public void loop(Context context) {
        previousFrameTime = context.currentFrameStart - context.previousFrameStart;
        moveSquares(context);
        sleepMilli(context.updateDelayFactor);
        render(context.squares);
        sleepMilli(context.renderDelayFactor);
        swapBuffers(context);
        pollEvents();
    }

    @Override
    public void moveSquares(Context context) {
        float framesToAdvance = (float)previousFrameTime / NANOSECONDS_PER_FRAME;
        context.autoSquares.forEach(square -> square.moveRight(framesToAdvance));
    }
}
