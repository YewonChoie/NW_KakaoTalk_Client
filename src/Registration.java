import Object.ServerInfo;
import Utilization.Util;
import com.mysql.cj.xdevapi.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends JFrame {
    private JTextField txtName;
    private JTextField txtId;
    private JButton btnId;
    private JPasswordField pfPwd;
    private JPasswordField pfRePwd;
    private JTextField txtNickname;
    private JTextField txtBirth;
    private JTextField txtPhone;
    private JRadioButton rdMale;
    private JRadioButton rdFemale;
    private JPanel mainPanel;
    private JCheckBox ckAgree;
    private JButton btnRegist;
    private JLabel lblId;
    private JLabel lblRe;
    private JButton btnNickname;
    private JLabel lblNickname;
    private JTextField txtEmail;
    private JButton btnEmail;
    private JLabel lblEmail;

    private Socket socket;
    private Scanner clientInput;
    private PrintWriter clientOutput;

    private boolean isIdPossible = false;
    private boolean isNicknamePossible = false;
    private boolean isEmailPossible = false;
    private boolean rightFormat = false;
    private boolean isRegistSuccess = false;

    private ServerInfo info;

    private void ClearForm() {
        txtName.setText("");
        rdMale.setSelected(false);
        rdFemale.setSelected(false);
        txtId.setText("");
        lblId.setText("");
        pfPwd.setText("");
        pfRePwd.setText("");
        txtNickname.setText("");
        txtBirth.setText("");
        txtPhone.setText("");
        ckAgree.setSelected(false);
    }

    Registration(ServerInfo serverInfo) {
        this.info = serverInfo;

        try {
            socket = new Socket(info.serverIP, info.serverPort);
            clientInput = new Scanner(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            clientOutput = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch(Exception e) {
            e.printStackTrace();
        }

        btnId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pattern idPattern = Pattern.compile("^[a-zA-Z0-9]{1,15}$");
                Matcher matcher = idPattern.matcher(txtId.getText());
                System.out.println("Check Duplicated Id...");

                if (matcher.find()) {
                    String idCheck = Util.createSingleJSON(1001, "id", txtId.getText());
                    clientOutput.println(idCheck);

                    if (clientInput.hasNextLine()) {
                        String line = new String();

                        try {
                            line = clientInput.nextLine();
                            if (line.isEmpty()) line = clientInput.nextLine();

                            JSONParser parser = new JSONParser();
                            JSONObject object = (JSONObject) parser.parse(line);

                            int response = Integer.parseInt(String.valueOf(object.get("code")));

                            if (response == 200) {
                                String check = String.valueOf(object.get("id"));
                                System.out.println(check);

                                if (check.equals("true")) {
                                    lblId.setForeground(Color.GREEN);
                                    lblId.setText("Possible");
                                    isIdPossible = true;
                                }
                                else {
                                    lblId.setForeground(Color.RED);
                                    lblId.setText("Impossible");
                                    isIdPossible = false;
                                }
                            }
                            else {
                                System.out.println("Error");
                            }
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            }
        });

        btnNickname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pattern nickNamePattern = Pattern.compile("^[a-zA-Z0-9]{1,15}$");
                Matcher matcher = nickNamePattern.matcher(txtNickname.getText());
                System.out.println("Check Duplicated Nickname...");

                if (matcher.find()) {
                    String nickNameCheck = Util.createSingleJSON(1002, "nickname", txtNickname.getText());
                    clientOutput.println(nickNameCheck);

                    if (clientInput.hasNextLine()) {
                        String line = new String();

                        try {
                            line = clientInput.nextLine();
                            if (line.isEmpty()) line = clientInput.nextLine();

                            JSONParser parser = new JSONParser();
                            JSONObject object = (JSONObject) parser.parse(line);

                            int response = Integer.parseInt(String.valueOf(object.get("code")));

                            if (response == 200) {
                                String check = String.valueOf(object.get("nickname"));
                                System.out.println(check);

                                if (check.equals("true")) {
                                    lblNickname.setForeground(Color.GREEN);
                                    lblNickname.setText("Possible");
                                    isNicknamePossible = true;
                                }
                                else {
                                    lblNickname.setForeground(Color.RED);
                                    lblNickname.setText("Impossible");
                                    isNicknamePossible = false;
                                }
                            }
                            else {
                                System.out.println("Error");
                            }
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            }
        });

        txtBirth.addFocusListener(new FocusListener() {
            String hint  = new String("ex) yyyy-mm-dd");
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBirth.getText().equals(hint)) txtBirth.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtBirth.getText().length() == 0) txtBirth.setText("ex) yyyy-mm-dd");
            }
        });

        txtPhone.addFocusListener(new FocusListener() {
            String hint = new String("ex) xxx-xxxx-xxxx");
            @Override
            public void focusGained(FocusEvent e) {
                if (txtPhone.getText().equals(hint)) txtPhone.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtPhone.getText().length() == 0) txtPhone.setText(hint);
            }
        });

        txtEmail.addFocusListener(new FocusListener() {
            String hint = new String("ex) xxx@xxx");
            @Override
            public void focusGained(FocusEvent e) {
                if (txtEmail.getText().equals(hint)) txtEmail.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtEmail.getText().length() == 0) txtEmail.setText("");
            }
        });

        btnEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Pattern emailPattern = Pattern.compile("^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$");
                Matcher matcher = emailPattern.matcher(txtEmail.getText());
                System.out.println("Check Duplicated Email...");

                if (matcher.find()) {
                    String emailCheck = Util.createSingleJSON(1003, "email", txtEmail.getText());
                    clientOutput.println(emailCheck);

                    if (clientInput.hasNextLine()) {
                        String line = new String();

                        try {
                            line = clientInput.nextLine();
                            if (line.isEmpty()) line = clientInput.nextLine();

                            JSONParser parser = new JSONParser();
                            JSONObject object = (JSONObject) parser.parse(line);

                            int response = Integer.parseInt(String.valueOf(object.get("code")));

                            if (response == 200) {
                                String check = String.valueOf(object.get("email"));
                                System.out.println(check);

                                if (check.equals("true")) {
                                    lblEmail.setForeground(Color.GREEN);
                                    lblEmail.setText("Possible");
                                    isEmailPossible = true;
                                }
                                else {
                                    lblEmail.setForeground(Color.RED);
                                    lblEmail.setText("Impossible");
                                    isEmailPossible = false;
                                }
                            }
                            else {
                                System.out.println("Error");
                            }
                        } catch (ParseException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
            }
        });

        btnRegist.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Registration...");

                if (isIdPossible == true && isNicknamePossible == true && isEmailPossible == true) {
                    if (ckAgree.isSelected() && String.valueOf(pfPwd.getPassword()).length() >= 8) {
                        if (rdMale.isSelected() && String.valueOf(pfPwd.getPassword()).equals(String.valueOf(pfRePwd.getPassword()))) {
                            HashMap<String, Object> registHashMap = new HashMap<String, Object>();
                            registHashMap.put("id", txtId.getText());
                            registHashMap.put("pwd", Util.encryptSHA512(String.valueOf(pfPwd.getPassword())));
                            registHashMap.put("name", txtName.getText());
                            registHashMap.put("gender", rdMale.getText());
                            registHashMap.put("nickname", txtNickname.getText());
                            registHashMap.put("birth", txtBirth.getText());
                            registHashMap.put("phone", txtPhone.getText());
                            registHashMap.put("email", txtEmail.getText());

                            String registJSON = Util.createJSON(1004, registHashMap);
                            clientOutput.println(registJSON);

                            if (clientInput.hasNextLine()) {
                                String line = new String();

                                try {
                                    line = clientInput.nextLine();
                                    if (line.isEmpty()) line = clientInput.nextLine();

                                    JSONParser parser = new JSONParser();
                                    JSONObject object = (JSONObject) parser.parse(line);

                                    int response = Integer.parseInt(String.valueOf(object.get("code")));

                                    if (response == 200) {
                                        String registResult = String.valueOf(object.get("registration"));
                                        System.out.println(registResult);

                                        if (registResult.equals("true")) {
                                            JOptionPane.showMessageDialog(mainPanel, "Registration Success.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                            Login login = new Login(serverInfo);

                                            dispose();
                                            socket.close();
                                        } else {
                                            JOptionPane.showMessageDialog(mainPanel, "Registration Failed.", "Warning", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {
                                        System.out.println("Error");
                                    }
                                } catch (ParseException ex) {
                                    throw new RuntimeException(ex);
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        }
                    } else if (rdFemale.isSelected() && String.valueOf(pfPwd.getPassword()).equals(String.valueOf(pfRePwd.getPassword()))) {
                        HashMap<String, Object> registHashMap = new HashMap<String, Object>();
                        registHashMap.put("id", txtId.getText());
                        registHashMap.put("pwd", Util.encryptSHA512(String.valueOf(pfPwd.getPassword())));
                        registHashMap.put("name", txtName.getText());
                        registHashMap.put("gender", rdFemale.getText());
                        registHashMap.put("nickname", txtNickname.getText());
                        registHashMap.put("birth", txtBirth.getText());
                        registHashMap.put("phone", txtPhone.getText());
                        registHashMap.put("email", txtEmail.getText());

                        String registJSON = Util.createJSON(1004, registHashMap);
                        clientOutput.println(registJSON);

                        if (clientInput.hasNextLine()) {
                            String line = new String();

                            try {
                                line = clientInput.nextLine();
                                if (line.isEmpty()) line = clientInput.nextLine();

                                JSONParser parser = new JSONParser();
                                JSONObject object = (JSONObject) parser.parse(line);

                                int response = Integer.parseInt(String.valueOf(object.get("code")));

                                if (response == 200) {
                                    String registResult = String.valueOf(object.get("registration"));
                                    System.out.println(registResult);

                                    if (registResult.equals("true")) {
                                        JOptionPane.showMessageDialog(mainPanel, "Registration Success.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                        Login login = new Login(serverInfo);

                                        dispose();
                                        socket.close();
                                    } else {
                                        JOptionPane.showMessageDialog(mainPanel, "Registration Failed.", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                } else {
                                    System.out.println("Registration Error");
                                }
                            } catch (ParseException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                } else if (!String.valueOf(pfPwd.getPassword()).equals(String.valueOf(pfRePwd.getPassword()))) {
                    System.out.println("Not Equal");
                }
            }
        });

        setContentPane(mainPanel);

        setSize(600, 500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Instagram Registration Form");
        setVisible(true);
    }
}
