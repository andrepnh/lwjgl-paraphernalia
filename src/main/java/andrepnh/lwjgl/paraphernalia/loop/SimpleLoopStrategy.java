package andrepnh.lwjgl.paraphernalia.loop;

import andrepnh.lwjgl.paraphernalia.LoopStrategy;
import andrepnh.lwjgl.paraphernalia.Context;

public class SimpleLoopStrategy implements LoopStrategy {

    @Override
    public void loop(Context context) {
        moveSquares(context);
        sleepMilli(context.updateDelayFactor);
        render(context.squares);
        sleepMilli(context.renderDelayFactor);
        swapBuffers(context);
        pollEvents();
    }
    
}
