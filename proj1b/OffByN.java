public class OffByN implements CharacterComparator {
    private int off;

    public OffByN(int N) {
        off = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == off || x - y == -off;
    }
}
