import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;


public class EndPopUp implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        int m = JOptionPane.showConfirmDialog(null, "Really Exit?", "Exit Program", JOptionPane.YES_NO_OPTION);
        if (m == JOptionPane.YES_OPTION)
            System.exit(0);
    }

}
