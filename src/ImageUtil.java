import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ImageUtil {

    static DateFormat df = new SimpleDateFormat("dd.MM.yyyy, hh:mm a");

    public static String str(Object... objs) {
        StringBuilder sb = new StringBuilder();
        for (Object o : objs)
            sb.append(o);
        return sb.toString();
    }

    public static double toFixed(double x, int n) {
        return Math.round(x * Math.pow(10, n)) / Math.pow(10, n);
    }

    public static HashMap<String, String> getFileMetadata(File f, Image img) throws IOException {
        HashMap<String, String> ret = new HashMap<>();
        long size = Files.size(Path.of(f.getAbsolutePath()));
        ret.put("file size", str(toFixed(((double) size) / 1024, 3), " kB"));
        ret.put("image width", str(img.getWidth(null)));
        ret.put("image height", str(img.getHeight(null)));
        ret.put("last modified", str(df.format(f.lastModified())));
        return ret;
    }
}
