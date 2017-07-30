package andrepnh.lwjgl.paraphernalia.loop;

import andrepnh.lwjgl.paraphernalia.LoopStrategy;
import andrepnh.lwjgl.paraphernalia.Context;

public class FixedFpsLoopStrategy implements LoopStrategy {
    @Override
    public void loop(Context context) {
        long nanosecondsPerFrame = 1000000000 / context.targetFps;
        moveSquares(context);
        sleepMilli(context.updateDelayFactor);
        render(context.squares);
        sleepMilli(context.renderDelayFactor);
        swapBuffers(context);
        pollEvents();
        sleepNano(context.currentFrameStart + nanosecondsPerFrame - System.nanoTime());
    }
    
}
