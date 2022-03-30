import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

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
            try {
                img = ImageIO.read(getSelectedFile().getAbsoluteFile());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            form.selectedImage = img;
            ((PaintPanel)form.paintPanel).img = img;
            form.paintPanel.updateUI();
            System.out.println("image updated");
        });
    }
}
