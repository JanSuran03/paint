import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class FileChooser extends JFileChooser {
    public boolean isLoadDialog;

    public FileChooser(boolean isLoadDialogSaveOtherwise) {
        isLoadDialog = isLoadDialogSaveOtherwise;
        setDialogTitle("Choose an image to load");
        FileFilter bmp = new FileNameExtensionFilter("bitmap images (*.bmp)", "bmp");
        addChoosableFileFilter(bmp);
        setFileFilter(bmp);
        File currentDir = new File(".");
        setCurrentDirectory(currentDir);
    }

    public void initListeners(Form form) {
        addActionListener(actionEvent -> {
            if (isLoadDialog) { // load image
                Image img;
                try {
                    img = ImageIO.read(getSelectedFile().getAbsoluteFile());
                    System.out.println("pepega - file metadata:: ");
                    form.imageMeta = ImageUtil.getFileMetadata(getSelectedFile().getAbsoluteFile(), img);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                form.isDrawingToCanvas = false;
                form.selectedImage = img;
                form.fileMetadata.setText(Util.fileMetaAsString(form.imageMeta));
                form.paintPanel.updateUI();
                System.out.println("image updated");
            } else { // save image
                try {
                    ImageIO.write((RenderedImage) form.selectedImage, "bmp", getSelectedFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
