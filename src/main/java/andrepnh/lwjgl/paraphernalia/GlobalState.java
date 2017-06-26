package andrepnh.lwjgl.paraphernalia;

import andrepnh.lwjgl.paraphernalia.loop.LoopKind;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class GlobalState {
    public static final int DEFAULT_MILLISECONDS_PER_FRAME = 16; // 60fps
    
    public static final float SQUARE_SPEED_UNITS_PER_MILLISECOND = (float)1 / 16;
    
    private static final long MIN_DELAY = 0;
    
    private static final long MAX_DELAY = 1000;
    
    public final long window;
    
    public int x;
    
    public int y;
    
    public final List<float[]> squares;
    
    public long updateDelay;
    
    public long renderDelay;
    
    private final Iterator<LoopKind> cyclicLoopKinds;
    
    public LoopKind currentLoopKind;

    public GlobalState(long window) {
        this.window = window;
        squares = IntStream.range(0, 10)
            .mapToObj(i -> new float[] {0, i})
            .collect(Collectors.toList());
        cyclicLoopKinds = Iterators.cycle(getLoopKinds());
        nextLoopKind();
    }

    private static Iterable<LoopKind> getLoopKinds() {
        List<LoopKind> loopKinds = new ArrayList<>(Arrays.asList(LoopKind.values()));
        loopKinds.remove(LoopKind.FLUID_TIME_STEP); // Not important and has a tendency to break
        return loopKinds;
    }
    
    public long incrementUpdateDelay() {
        if (updateDelay + 1 <= MAX_DELAY) {
            updateDelay++;
        }
        return updateDelay;
    }
    
    public long decrementUpdateDelay() {
        if (updateDelay - 1 >= MIN_DELAY) {
            updateDelay--;
        }
        return updateDelay;
    }
    
    public long incrementRenderDelay() {
        if (renderDelay + 1 <= MAX_DELAY) {
            renderDelay++;
        }
        return renderDelay;
    }
    
    public long decrementRenderDelay() {
        if (renderDelay - 1 >= MIN_DELAY) {
            renderDelay--;
        }
        return renderDelay;
    }
    
    public void nextLoopKind() {
        currentLoopKind = cyclicLoopKinds.next();
    }
    
}
