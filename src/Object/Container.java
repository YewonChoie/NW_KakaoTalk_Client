package Object;

import javax.swing.*;
import java.awt.*;

public class Container {
    private JPanel panel;

    public JPanel getContainer() {
        return panel;
    }

    public Container(String user, String message) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));

        JLabel userName = new JLabel(user);
        JLabel userMessage = new JLabel(message);

        panel.add(userName);
        panel.add(userMessage);
    }
}
