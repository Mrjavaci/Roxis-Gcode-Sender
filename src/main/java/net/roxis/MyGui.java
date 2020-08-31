package net.roxis;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyGui {
    private JPanel MyPanel;
    private JButton YenileButton;
    private JButton BaglanButton;
    private JTextArea textArea;
    private MyDropdownMenu myPortList;
    private JLabel TagLabel;
    private JTextField komutField;
    private JButton gonderButton;
    private JButton dosyaSecButton;
    private JButton baslaButton;
    private JProgressBar progressBar1;
    private JButton hareketButton;
    Boolean isConnected = false;
    MyConnection myConnection;
    String filePath = null;
    GetSerialThread getSerialThread = null;

    public MyGui() {
        myPortList.refreshMenu();

        YenileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myPortList.refreshMenu();
            }
        });

        BaglanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isConnected) {
                    myConnection = new MyConnection(myPortList.getSelectedItem().toString(), 9600);


                    if (myConnection.openConnection()) {
                        isConnected = true;
                        BaglanButton.setText("Bağlantıyı Kes");
                        myPortList.setEnabled(false);
                        YenileButton.setEnabled(false);
                        //myConnection.serialWrite("");
                        getSerialThread = new GetSerialThread(myConnection, textArea);
                        getSerialThread.setRun(true);
                        getSerialThread.start();
                        //     myConnection.serialWrite("X10");

                    }
                } else {
                    getSerialThread.setRun(false);
                    isConnected = false;
                    myConnection.closeConnection();
                    BaglanButton.setText("Bağlan");
                    myPortList.setEnabled(true);
                    YenileButton.setEnabled(true);
                }
            }
        });
        gonderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myConnection.serialWrite(komutField.getText() + "\n");

            }
        });
        dosyaSecButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Gcode Veya NC file", "nc", "gcode", "g", "gc");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("Açılan dosya yolu: " +
                            chooser.getSelectedFile().getAbsolutePath());
                    filePath = chooser.getSelectedFile().getAbsolutePath();

                }
            }
        });
        baslaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    if (filePath != null) {

                        MyGcodeFileSender myGcodeFileSender = new MyGcodeFileSender(filePath, myConnection, textArea, progressBar1);
                        myGcodeFileSender.setRunning(true);
                        myGcodeFileSender.start();

                    } else {
                        JOptionPane.showMessageDialog(MyPanel,
                                "Dosya Seçilmedi",
                                "Dosya Seçmediniz!",
                                JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NullPointerException ee) {
                    JOptionPane.showMessageDialog(MyPanel,
                            "Dosya Seçilmedi",
                            "Dosya Seçmediniz!",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        hareketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HareketUi hareketUi = new HareketUi("Hareket ");
                hareketUi.setMyConnection(myConnection);

                hareketUi.setVisible(true);

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MyGui");
        frame.setContentPane(new MyGui().MyPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        MyPanel = new JPanel();
        MyPanel.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        MyPanel.setBackground(new Color(-15000032));
        MyPanel.setForeground(new Color(-15261119));
        MyPanel.setMinimumSize(new Dimension(800, 228));
        YenileButton = new JButton();
        YenileButton.setBackground(new Color(-11775918));
        YenileButton.setBorderPainted(true);
        YenileButton.setContentAreaFilled(true);
        YenileButton.setEnabled(true);
        YenileButton.setFocusPainted(false);
        YenileButton.setText("Yenile");
        MyPanel.add(YenileButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textArea = new JTextArea();
        textArea.setAutoscrolls(false);
        textArea.setCaretColor(new Color(-9145228));
        textArea.setDragEnabled(true);
        textArea.setEditable(true);
        textArea.setFocusable(true);
        Font textAreaFont = this.$$$getFont$$$(null, -1, 18, textArea.getFont());
        if (textAreaFont != null) textArea.setFont(textAreaFont);
        textArea.setForeground(new Color(-15000032));
        MyPanel.add(textArea, new GridConstraints(7, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(800, 150), new Dimension(800, 150), new Dimension(800, 150), 0, false));
        myPortList = new MyDropdownMenu();
        myPortList.setBackground(new Color(-11841711));
        MyPanel.add(myPortList, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        TagLabel = new JLabel();
        Font TagLabelFont = this.$$$getFont$$$(null, -1, 18, TagLabel.getFont());
        if (TagLabelFont != null) TagLabel.setFont(TagLabelFont);
        TagLabel.setForeground(new Color(-1));
        TagLabel.setText("Bağlantı durumu: Bağlı Değil");
        MyPanel.add(TagLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        BaglanButton = new JButton();
        BaglanButton.setBackground(new Color(-11841711));
        BaglanButton.setBorderPainted(true);
        BaglanButton.setContentAreaFilled(true);
        BaglanButton.setFocusPainted(false);
        BaglanButton.setOpaque(true);
        BaglanButton.setText("Bağlan");
        MyPanel.add(BaglanButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setText("Komut Gönder");
        MyPanel.add(label1, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        komutField = new JTextField();
        komutField.setOpaque(true);
        MyPanel.add(komutField, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        gonderButton = new JButton();
        gonderButton.setText("Gönder");
        MyPanel.add(gonderButton, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$(null, -1, 18, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setForeground(new Color(-1));
        label2.setText("Gcode/nc Dosyası Gönder");
        MyPanel.add(label2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dosyaSecButton = new JButton();
        dosyaSecButton.setText("Dosya Seç");
        MyPanel.add(dosyaSecButton, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        baslaButton = new JButton();
        baslaButton.setText("Başla");
        MyPanel.add(baslaButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        progressBar1 = new JProgressBar();
        progressBar1.setValue(2);
        MyPanel.add(progressBar1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        hareketButton = new JButton();
        hareketButton.setText("Hareket Butonları");
        MyPanel.add(hareketButton, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return MyPanel;
    }

}
