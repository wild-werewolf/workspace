/*
 * Created on 29.07.2005
 *
 */
package ru.murzoid.tools.md5.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.util.List;
import java.util.ResourceBundle;

public class ResultsDialog extends JDialog implements ActionListener {

    private JTextArea res = null;
    private JLabel savedToFile = null;
    private JLabel fName = null;
    private JButton okButton = null;
    private JPanel bottomComponents = null;
    
    /**
     * ������� ��������� ������ <code>ResultsDialog</code>, �������
     * �������� ����������, ����������� ��� ����������� �����������
     * ������� MD5 ���������� ������.
     * @param frame ��������� �� �����, ������� ������� ������ ���������� ����
     * @param isModal ���������, �������� �� ������ ���������
     * @param results ������ � ������������ �������
     * @param messages ��������� ������, ������� ������������ ��� �������� ����������
     * @param fileName ��� �����, � ������� ��� �������� ���������
     */
    public ResultsDialog(JFrame frame, boolean isModal, List results,
            ResourceBundle messages, String fileName) {
        super(frame, isModal);
        
        //������� � ��������� ����������
        res = new JTextArea(20, 80);
        JScrollPane sp = new JScrollPane(res);
        for(int i = 0; i < results.size(); i++) {
            res.append((String)results.get(i));
            res.append("\n");
        }
        res.setCaretPosition(0);
        res.setFont(new Font("Monospaced", Font.PLAIN, 14));
        setTitle(messages.getString("ResultsDialog.title"));
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(sp, BorderLayout.CENTER);
        
        bottomComponents = new JPanel();
        bottomComponents.setLayout(new GridLayout(3, 1));
        savedToFile = new JLabel(messages.getString("ResultsDialog.savedToFile"));
        fName = new JLabel(fileName);
        okButton = new JButton(messages.getString("ResultsDialog.OK"));
        okButton.addActionListener(this);
        bottomComponents.add(savedToFile);
        bottomComponents.add(fName);
        bottomComponents.add(okButton);
        getContentPane().add(bottomComponents, BorderLayout.SOUTH);
    }

    /**
     * ���� ����� ����������, ���� ������������ ����� �� ������ "OK"
     */
    public void actionPerformed(ActionEvent arg0) {
        //������ ����
        hide();
    }
}
