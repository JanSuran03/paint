import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileChooser extends JFileChooser {
    public FileChooser() {
        setDialogTitle("Choose an image to load");
        FileFilter bmp = new FileNameExtensionFilter("bitmap images (*.bmp)", "bmp");
        addChoosableFileFilter(bmp);
        setFileFilter(bmp);
        File currentDir = new File(".");
        setCurrentDirectory(currentDir);
    }

    public void initListeners(Form form) {
        addActionListener(actionEvent -> {
            Image img;
            HashMap<String, String> meta;
            try {
                img = ImageIO.read(getSelectedFile().getAbsoluteFile());
                System.out.println("pepega - file metadata:: ");
                form.rotateQuad = 1;
                meta = ImageUtil.getFileMetadata(getSelectedFile().getAbsoluteFile(), img);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            form.selectedImage = img;
            StringBuilder metaText = new StringBuilder();
            meta.forEach((k, v) -> {
                metaText.append(k).append(":  ").append(v).append('\n');
            });
            form.fileMetadata.setText(metaText.toString());
            ((PaintPanel) form.paintPanel).img = img;
            form.paintPanel.updateUI();
            System.out.println("image updated");
        });
    }
}
