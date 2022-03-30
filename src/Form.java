import javax.swing.*;
import java.awt.*;


public class Form extends JFrame {
    private JPanel mainPanel;
    private JSpinner numberChooser;
    private JPanel paintPanel;
    private JPanel numberChooserWrapper;
    private JLabel widthLabel;
    private JButton changeColorButton;
    private JButton saveImageButton;
    private JPanel wrapperForButtons;
    static final int MIN_WIDTH = 1, MAX_WIDTH = 150;
    private final colorPicker color_picker = new colorPicker(changeColorButton);

    void initListeners() {
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
    }

    public void init() {
        numberChooser.setValue(8);
        mainPanel.setBackground(new Color(170, 170, 170));
        //paintPanel.setSize(600, 400);
    }

    Form() {
        super("Paint");
        setContentPane(mainPanel);
        setBounds(300, 100, 1100, 700);
        init();
        initListeners();
    }

    public void main() {
        setVisible(true);
    }

    public static void main(String[] args) {
        Form form = new Form();
        form.main();
    }
}
