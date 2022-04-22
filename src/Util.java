import java.util.HashMap;

public class Util {
    public static int between(int val, int min_val, int max_val) {
        return Math.min(Math.max(val, min_val), max_val);
    }

    public static String fileMetaAsString(HashMap<String, String> meta){
        StringBuilder ret = new StringBuilder();
        meta.forEach((k, v) -> {
            ret.append(k).append(":  ").append(v).append('\n');
        });
        return ret.toString();
    }
}
