import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Form extends JFrame {
    public JPanel mainPanel;
    public JSpinner numberChooser;
    public JPanel paintPanel;
    public JPanel numberChooserWrapper;
    public JLabel widthLabel;
    public JButton changeColorButton;
    public JButton saveImageButton;
    public JPanel wrapperForButtons;
    public JButton loadImageButton;
    public JTextPane fileMetadata;
    public JButton rotateImageButton;
    static final int MIN_WIDTH = 1, MAX_WIDTH = 150;
    public final ColorPicker color_picker = new ColorPicker(changeColorButton);
    public final FileChooser fileChooser = new FileChooser(true);
    public final FileChooser fileSaver = new FileChooser(false);
    public Image selectedImage;

    void initListeners(Form form) {
        numberChooser.addChangeListener(changeEvent -> {
            Object v = numberChooser.getValue();
            try {
                numberChooser.setValue(Util.between((int) v,
                        MIN_WIDTH, MAX_WIDTH)
                );
            } catch (Throwable t) {
                System.out.println("invalid number: " + v.toString());
            }
        });

        changeColorButton.addActionListener(actionEvent -> {
            color_picker.show();
        });

        loadImageButton.addActionListener(actionEvent -> {
            fileChooser.showOpenDialog(form);
        });

        saveImageButton.addActionListener(actionEvent -> {
            if (selectedImage != null)
                System.out.println("opening saving dialog");
                fileSaver.showOpenDialog(form);
//            if (selectedImage != null) {
//                try {
//                    ImageIO.write((RenderedImage) selectedImage, "bmp", new File("pepega.bmp"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        });

        fileChooser.initListeners(form);
        fileSaver.initListeners(form);

        rotateImageButton.addActionListener(actionEvent -> {
            if (selectedImage != null) {
                selectedImage = ImageUtil.rotateImage(selectedImage, 90);
                form.paintPanel.updateUI();
            }
        });
    }

    public void init() {
        numberChooser.setValue(8);
        mainPanel.setBackground(new Color(170, 170, 170));
    }

    Form() {
        super("Paint");
        setContentPane(mainPanel);
        setBounds(300, 100, 1100, 700);
        init();
        initListeners(this);
    }

    public void main() {
        setVisible(true);
        Form f = this;
    }

    public static void main(String[] args) {
        Form form = new Form();
        form.main();
    }

    private void createUIComponents() {
        paintPanel = new PaintPanel(this);
        paintPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (selectedImage != null) {
                    System.out.println(selectedImage.toString());
                    System.out.println("width: " + selectedImage.getWidth(null));
                    System.out.println("height: " + selectedImage.getHeight(null));
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
    }
}
