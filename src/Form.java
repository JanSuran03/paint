import javax.swing.*;
import java.awt.*;


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
    static final int MIN_WIDTH = 1, MAX_WIDTH = 150;
    public final ColorPicker color_picker = new ColorPicker(changeColorButton);
    public final FileChooser fileChooser = new FileChooser();
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

        fileChooser.initListeners(form);
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
        paintPanel = new PaintPanel();
    }
}
