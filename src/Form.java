import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;


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
    private JButton switchToPencilButton;
    static final int MIN_WIDTH = 1, MAX_WIDTH = 150;
    public final ColorPicker color_picker = new ColorPicker(changeColorButton);
    public final FileChooser fileChooser = new FileChooser(true);
    public final FileChooser fileSaver = new FileChooser(false);
    public Image selectedImage;
    public boolean isDrawingToCanvas = false;
    public HashMap<String, String> imageMeta;

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
                selectedImage = ImageUtil.rotateImage(form, selectedImage, 90);
                form.paintPanel.updateUI();
            }
        });

        switchToPencilButton.addActionListener(actionEvent -> {
            if (!isDrawingToCanvas) {
                isDrawingToCanvas = true;
                selectedImage = new BufferedImage(paintPanel.getWidth(),
                        paintPanel.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
                imageMeta = null;
                fileMetadata.setText("");
                paintPanel.updateUI();
            }
        });
    }

    public void initButtonIcon() {
        ImageIcon imageIcon = new ImageIcon("images/pencil.png");
        Image img = imageIcon.getImage();
        Image newImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        switchToPencilButton.setIcon(new ImageIcon(newImg));
    }

    public void init() {
        numberChooser.setValue(8);
        mainPanel.setBackground(new Color(170, 170, 170));
        initButtonIcon();
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
        ((PaintPanel) paintPanel).initListeners();
    }
}
