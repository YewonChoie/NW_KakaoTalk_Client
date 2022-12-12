import ImageResizer.ImageResizer;

import javax.swing.*;

public class FriendBoard extends JFrame {
    private String user;

    private JPanel FriendPanel;
    private JPanel fieldPanel;
    private JLabel lblField;
    private JButton btnSearch;
    private JButton btnAdd;
    private JScrollPane scrFriend;
    private JPanel container;

    public JPanel getFriendPanel() {
        return this.FriendPanel;
    }

    FriendBoard(String user) {
        this.user = user;



        JButton[] btnGroup = new JButton[2];
        btnGroup[0] = btnSearch;
        btnGroup[1] = btnAdd;

        ImageResizer.FriendBoardImage(btnGroup);
    }
}
