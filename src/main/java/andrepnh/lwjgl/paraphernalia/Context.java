package andrepnh.lwjgl.paraphernalia;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import java.util.List;

public final class Context {

    public static final float UNIT_SIZE = 0.005f;

    public static final int MAX_UNITS = 200;

    public final long window;

    public Square player = new Square(0, 0, 1, new float[]{1, 1, 1});

    public final List<Square> autoSquares;

    public final List<Square> squares;

    public int updateDelayFactor = 0;

    public int renderDelayFactor = 0;

    public Iterator<LoopType> cyclicLoopType = Iterators.cycle(
        LoopType.SIMPLE,
        LoopType.FIXED_FPS,
        LoopType.FLUID_TIME_STEP,
        LoopType.MULTI_UPDATE,
        LoopType.EXTRAPOLATING_MULTI_UPDATE
    );

    public LoopType loopType;

    public long previousFrameStart;

    public long currentFrameStart;
    
    public long targetFps = 60;

    public Context(long window) {
        this.window = window;
        ImmutableList.Builder<Square> builder = ImmutableList.<Square>builder()
            .add(new Square(0f, Square.SIZE, 1, new float[]{1, 0.3f, 0.8f}))
            .add(new Square(-Square.SIZE, Square.SIZE * 2, 2, new float[]{1, 0.2f, 0.53f}))
            .add(new Square(-Square.SIZE * 2, Square.SIZE * 3, 3, new float[]{1, 0.1f, 0.26f}))
            .add(new Square(0f, -Square.SIZE, 1, new float[]{0, 0.8f, 1}))
            .add(new Square(-Square.SIZE, -Square.SIZE * 2, 2, new float[]{0, 0.6f, 0.8f}))
            .add(new Square(-Square.SIZE * 2, -Square.SIZE * 3, 3, new float[]{0, 0.4f, 0.6f}));
        autoSquares = builder.build();
        squares = builder.add(player).build();
        nextLoopKind();
        previousFrameStart = currentFrameStart = System.nanoTime();
    }

    public void nextLoopKind() {
        loopType = cyclicLoopType.next();
    }

    public void increaseRenderDelayFactor() {
        renderDelayFactor++;
    }

    public void decreaseRenderDelayFactor() {
        renderDelayFactor--;
        if (renderDelayFactor < 0) {
            renderDelayFactor = 0;
        }
    }

    public void increaseUpdateDelayFactor() {
        updateDelayFactor++;
    }

    public void decreaseUpdateDelayFactor() {
        updateDelayFactor--;
        if (updateDelayFactor < 0) {
            updateDelayFactor = 0;
        }
    }
    
    public void increaseTargetFps() {
        targetFps += 10;
    }

    public void decreaseTargetFps() {
        targetFps -= 10;
        if (targetFps < 15) {
            updateDelayFactor = 15;
        }
    }

    public void setLoopType(LoopType targetLoopType) {
        int i = 0;
        while (loopType != targetLoopType) {
            nextLoopKind();
            if (i++ > 20) {
                throw new IllegalStateException(
                    String.format("Infinite loop detected, loop type %s might not be available in context", 
                        targetLoopType));
            }
        }
    }
}
