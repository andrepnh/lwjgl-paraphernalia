package andrepnh.lwjgl.paraphernalia.loop;

import andrepnh.lwjgl.paraphernalia.LoopStrategy;
import andrepnh.lwjgl.paraphernalia.Context;

public class MultipleUpdatesLoopStrategy implements LoopStrategy {
    private long accumulatedLag = 0;
    private long previousFrameTime;
    
    @Override
    public void loop(Context context) {
        long nanosecondsPerFrame = 1000000000 / context.targetFps;
        previousFrameTime = context.currentFrameStart - context.previousFrameStart;
        accumulatedLag += previousFrameTime;
        
        while (accumulatedLag >= nanosecondsPerFrame) {
            moveSquares(context);
            accumulatedLag -= nanosecondsPerFrame;
        }
        
        moveSquares(context);
        sleepMilli(context.updateDelayFactor);
        render(context.squares);
        sleepMilli(context.renderDelayFactor);
        swapBuffers(context);
        pollEvents();
    }
}
