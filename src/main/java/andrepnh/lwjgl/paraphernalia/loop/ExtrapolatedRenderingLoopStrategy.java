package andrepnh.lwjgl.paraphernalia.loop;

import andrepnh.lwjgl.paraphernalia.LoopStrategy;
import andrepnh.lwjgl.paraphernalia.Square;
import andrepnh.lwjgl.paraphernalia.Context;
import java.util.ArrayList;
import java.util.List;

public class ExtrapolatedRenderingLoopStrategy implements LoopStrategy {
    private long accumulatedLag = 0;
    private long previousFrameTime;
    private long nanosecondsPerFrame;
    
    @Override
    public void loop(Context context) {
        nanosecondsPerFrame = 1000000000 / context.targetFps;
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

    @Override
    public void render(List<Square> squares) {
        // With our current entity implementation, all we can do is create new squares with 
        // extrapolated positions
        float frameProgress = (float)accumulatedLag / nanosecondsPerFrame;
        List<Square> extrapolatedSquares = new ArrayList<>(squares.size());
        for (Square square : squares) {
            Square extrapolated = new Square(square);
            extrapolated.moveRight(frameProgress);
            extrapolatedSquares.add(extrapolated);
        }
        LoopStrategy.super.render(extrapolatedSquares);
    }
    
    
}
