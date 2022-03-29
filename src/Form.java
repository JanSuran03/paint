import javax.swing.*;


public class Form extends JFrame {
    private JPanel mainPanel;
    private JSpinner numberChooser;
    static final int MIN_WIDTH = 1, MAX_WIDTH = 150;

    void initListeners(Form form) {
        numberChooser.addChangeListener(changeEvent -> {
            numberChooser.setValue(Util.between((int) numberChooser.getValue(),
                    MIN_WIDTH, MAX_WIDTH)
            );
        });
    }

    public void init() {
        numberChooser.setValue(1);
    }

    Form() {
        super("Paint");
        setContentPane(mainPanel);
        setBounds(300, 100, 1100, 700);
        init();
        initListeners(this);
    }

    public static void main(String[] args) {
        Form form = new Form();
        form.setVisible(true);
    }
}
