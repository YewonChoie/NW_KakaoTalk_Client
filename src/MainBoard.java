
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainBoard extends JFrame {

    private static ArrayList<String> friend_list = new ArrayList<String>();
    private JPanel main;
    private JPanel top;
    private JPanel left;
    private JPanel pane;
    private JScrollPane scroll_pane;
    private JPanel scroll;
    private JButton chat;

    private String user_id;

    public MainBoard() {


        setContentPane(main);
        setSize(800, 1000);
        //left.setSize(150, 500);
        setBounds(0, 0, 850, 1000);
        setVisible(true);

//        scroll_pane.getVerticalScrollBar().setUnitIncrement(15); //속도
//
//        GridBagLayout Gbag = new GridBagLayout();
//        scroll.setLayout(Gbag);
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        for (int i = 0; i < friend_list.size(); i++) {
//            if (friend_list.get(i).equals(user_id)) {
//                continue;
//            }
//            friend pane = new friend(friend_list.get(i));
//            gbc.fill = GridBagConstraints.BOTH;
//            gbc.ipadx = 600;
//            gbc.ipady = 100;
//            gbc.gridx = 0;
//            gbc.gridy = i;
//            Gbag.setConstraints(pane, gbc);
//            scroll.add(pane);
//            scroll.updateUI();
//        }
//
//        scroll_pane.setBackground(new Color(255, 255, 255));
//        scroll_pane.setViewportView(scroll);
//        scroll_pane.setVisible(true);
//        scroll.setVisible(true);
//
//        //setSize(400, 500);
//        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        setTitle("Kakaotalk");
//        //setVisible(true);
//        chat.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                chat chatting = new chat();
//            }
//        });
//    }
//
//    public class friend extends JPanel {
//
//        private JButton user;
//        private String friend_id;
//
//        public friend(String friend_id) {
//            this.friend_id = friend_id;
//
//            setLayout(new GridLayout(1, 1));
//            setSize(600, 100);
//            user = new JButton(friend_id);
//            user.setBackground(new Color(255, 255, 255));
//            user.setSize(600, 100);
//            add(user);
//        }
   }
}

