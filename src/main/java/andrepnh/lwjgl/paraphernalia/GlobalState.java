package andrepnh.lwjgl.paraphernalia;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GlobalState {
    public final long window;
    
    public int x;
    
    public int y;
    
    public final List<int[]> squares;

    public GlobalState(long window) {
        this.window = window;
        squares = IntStream.range(0, 10)
            .mapToObj(i -> new int[] {0, i})
            .collect(Collectors.toList());
    }
    
}
