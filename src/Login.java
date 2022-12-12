
import ImageResizer.ImageResizer;
import Object.ServerInfo;
import Utilization.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;

public class Login extends JFrame {
    private JPanel mainPanel;
    private JTextField txtId;
    private JPasswordField pfPwd;
    private JLabel lblLogo;
    private JButton btnLogin;
    private JButton btnRegist;

    private ServerInfo info;
    private Socket socket;
    private Scanner clientInput;
    private PrintWriter clientOutput;


    private boolean isLogin = false;

    private void ClearForm() {
        txtId.setText("");
        pfPwd.setText("");
    }

    Login(ServerInfo serverInfo) {
        this.info = serverInfo;

        try {
            socket = new Socket(info.serverIP, info.serverPort);
            clientInput = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            clientOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch(Exception e) {
            e.printStackTrace();
        }

        txtId.addFocusListener(new FocusListener() {
            String hint = new String("kakao ID");
            @Override
            public void focusGained(FocusEvent e) {
                if (txtId.getText().equals(hint)) txtId.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtId.getText().length() == 0) txtId.setText("");
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, Object> loginHashMap = new HashMap<String, Object>();
                loginHashMap.put("id", txtId.getText());
                loginHashMap.put("pwd", Util.encryptSHA512(String.valueOf(pfPwd.getPassword())));

                String loginJSON = Util.createJSON(2001, loginHashMap);
                clientOutput.println(loginJSON);

                if (clientInput.hasNextLine()) {
                    String serverOutput = new String();

                    try {
                        serverOutput = clientInput.nextLine();
                        if (serverOutput.isEmpty()) serverOutput = clientInput.nextLine();

                        JSONParser parser = new JSONParser();
                        JSONObject object = (JSONObject) parser.parse(serverOutput);

                        int response = Integer.parseInt(String.valueOf(object.get("code")));

                        if (response == 200) {
                            String loginResult = String.valueOf(object.get("login"));
                            System.out.println(loginResult);

                            if (loginResult.equals("true")) {
                                JOptionPane.showMessageDialog(mainPanel, "Login Success.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                info.serverPort = 20815;

                                MainFrame mainFrame = new MainFrame(serverInfo, String.valueOf(object.get("nickname")));
                                dispose();
                            }
                            else {
                                JOptionPane.showMessageDialog(mainPanel, "Login Failed.", "Warning", JOptionPane.WARNING_MESSAGE);
                            }
                        } else {
                            System.out.println("Login Error");
                        }

                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        btnRegist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Registration loginToRegist = new Registration(serverInfo);
                dispose();
            }
        });

        setContentPane(mainPanel);

        setSize(350, 600);
        ImageResizer.LoginImage(lblLogo);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kakaotalk Login");
        setVisible(true);
    }
}
