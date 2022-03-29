public class Util {
    public static int between(int val, int min_val, int max_val) {
        return Math.min(Math.max(val, min_val), max_val);
    }
}
