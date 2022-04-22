import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class FileChooser extends JFileChooser {
    public DialogType dialogType;

    enum DialogType {
        LOAD, SAVE;
    }

    public FileChooser(DialogType dialog_type) {
        dialogType = dialog_type;
        setDialogTitle("Choose an image to load");
        FileFilter bmp = new FileNameExtensionFilter("bitmap images (*.bmp)", "bmp");
        addChoosableFileFilter(bmp);
        setFileFilter(bmp);
        File currentDir = new File(".");
        setCurrentDirectory(currentDir);
    }

    public void initListeners(Form form) {
        addActionListener(actionEvent -> {
            switch (actionEvent.getActionCommand()) {
                case "ApproveSelection":
                    switch (dialogType) {
                        case LOAD:
                            BufferedImage img;
                            try {
                                img = ImageIO.read(getSelectedFile().getAbsoluteFile());
                                form.imageMeta = ImageUtil.getFileMetadata(getSelectedFile().getAbsoluteFile(), img);
                            } catch (IOException e) {
                                e.printStackTrace();
                                return;
                            }
                            form.isDrawingToCanvas = false;
                            form.selectedImage = img;
                            form.fileMetadata.setText(Util.fileMetaAsString(form.imageMeta));
                            form.paintPanel.updateUI();
                            System.out.println(img.getType());
                            System.out.println("image updated");
                            break;
                        case SAVE:
                            System.out.println("saving to " + getSelectedFile().getAbsoluteFile() + "...");
                            if(form.isDrawingToCanvas)
                                form.selectedImage.getGraphics().drawImage(form.selectedImage, 0, 0, null);
                            try {
                                boolean w = ImageIO.write((RenderedImage) form.selectedImage, "bmp", getSelectedFile());
                                System.out.println("w?: " + w);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                default:
                    System.out.println("selection canceled");
                    break;
            }
        });
    }
}
