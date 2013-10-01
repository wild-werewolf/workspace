/*
 * Created on 28.07.2005
 */
package ru.murzoid.tools.md5.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.JFrame;

import ru.murzoid.tools.md5.listeners.MD5CalculationProgressListener;
import ru.murzoid.tools.md5.listeners.MD5CalculatorStateListener;
import ru.murzoid.tools.md5.logic.FolderTreeAnalyser;

import java.io.*;

import ru.murzoid.tools.md5.listeners.ChangeLanguageListener;

public class FolderTreeAnalyserPanel extends JPanel
		implements ChangeLanguageListener, ActionListener, Runnable,
		MD5CalculationProgressListener, MD5CalculatorStateListener {
    
    //��������� �� �������� ���������� ���������
    private GridBagConstraints c = null;
    private ResourceBundle messages = null;
    private JPanel folderNamePanel = null;
    private JPanel resultsFileNamePanel = null;
    private JPanel progressPanel = null;
    private JTextField folderName = null;
    private JTextField resultsFileName = null;
    private JButton browseFolder = null;
    private JButton browseResFile = null;
    private JButton run = null;
    private JProgressBar totalProgress = null;
    private JProgressBar curFileProgress = null;
    private JLabel curFile = null;
    private JLabel total = null;
    private JLabel status = null;
    
    private int totalFiles = 0; //����� ���������� ������ � �������� �����
    private int processedFiles = 0; //���������� ������������ ������
    private long filesSize = 0; //����� ������ ������
    private long curFileSize = 0; //������ �����, ��������������� � ������ ������
    private double prevPercent = 0; //������� �� ������ ������ ������ �������
    					//��������� �� ������ ������ (��� ����� ����� �������
    					//�������������� � ������ ������)
    private double curPercent = 0; //������� ������������� ������ �������� �����
    
    private FolderTreeAnalyser analyser = null; //��������� �� �����, �������
    					//��������� ��������� �������� �����
    private List calculationResults = null; //������ � ������������ ��������
    private Thread worker = null; //�����, � ������� ����������� ��������� �����
    private File srcDir = null; //�������� �����
    private File resFile = null; //����, � ������� ����� ��������� ����������
    
    private JFileChooser fc = null; //���������� ���� ������ �����
    
    private JFrame mainFrame = null; //��������� �� ������� ����� ���������
    
    /**
     * ������� ����� ���������� ������ <code>FolderTreeAnalyserPanel</code>
     * @param messages ��������� ������, ������� ������������ ��� �������� ����������
     * @param mainFrame ��������� �� ������� ����� ���������
     */
    public FolderTreeAnalyserPanel(ResourceBundle messages, JFrame mainFrame) {
        //����������� ��������� �������� ���������� � ��������� ����������
        super(new GridBagLayout());
        this.messages = messages;
        this.mainFrame = mainFrame;
        fc = new JFileChooser();
        analyser = new FolderTreeAnalyser();
        analyser.addMD5CalculationProgressListener(this);
        analyser.addMD5CalculatorStateListener(this);
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        folderName = new JTextField(30);
        browseFolder = new JButton(messages.getString(
                "FolderTreeAnalyserPanel.browse"));
        browseFolder.setActionCommand("browseFolder");
        browseFolder.addActionListener(this);
        folderNamePanel = new JPanel(new GridBagLayout());
        folderNamePanel.setBorder(BorderFactory.createTitledBorder(
                messages.getString("FolderTreeAnalyserPanel.folderName")));
        GridBagConstraints c1 = new GridBagConstraints();
        c1.weightx = 0.7;
        c1.weighty = 1;
        c1.fill = GridBagConstraints.BOTH;
        folderNamePanel.add(folderName, c1);
        c1.weightx = 0.3;
        folderNamePanel.add(browseFolder, c1);
        
        resultsFileName = new JTextField(30);
        browseResFile = new JButton(messages.getString(
                "FolderTreeAnalyserPanel.browse"));
        browseResFile.setActionCommand("browseResFile");
        browseResFile.addActionListener(this);
        resultsFileNamePanel = new JPanel(new GridBagLayout());
        resultsFileNamePanel.setBorder(BorderFactory.createTitledBorder(
                messages.getString("FolderTreeAnalyserPanel.saveRes")));
        c1.weightx = 0.7;
        resultsFileNamePanel.add(resultsFileName, c1);
        c1.weightx = 0.3;
        resultsFileNamePanel.add(browseResFile, c1);
        
        curFile = new JLabel(messages.getString(
                "FolderTreeAnalyserPanel.processingFile"));
        curFileProgress = new JProgressBar(0, 100);
        curFileProgress.setValue(0);
        total = new JLabel(messages.getString(
                "FolderTreeAnalyserPanel.totalProgress"));
        totalProgress = new JProgressBar(0, 100);
        totalProgress.setValue(0);
        run = new JButton(messages.getString("FolderTreeAnalyserPanel.run"));
        run.setActionCommand("run");
        run.addActionListener(this);
        progressPanel = new JPanel(new GridBagLayout());
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weighty = 0.25;
        c1.weightx = 0.7;
        progressPanel.add(curFile, c1);
        c1.gridy = 1;
        progressPanel.add(curFileProgress, c1);
        c1.gridy = 2;
        progressPanel.add(total, c1);
        c1.gridy = 3;
        progressPanel.add(totalProgress, c1);
        c1.gridy = 0;
        c1.gridx = 1;
        c1.gridheight = 4;
        c1.weightx = 0.3;
        c1.weighty = 1.0;
        progressPanel.add(run, c1);
        
        status = new JLabel(messages.getString("FolderTreeAnalyserPanel.status")
                + "...");
        
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 0.2;
        add(folderNamePanel, c);
        c.gridy = 1;
        add(resultsFileNamePanel, c);
        c.gridy = 2;
        c.weighty = 0.4;
        add(progressPanel, c);
        c.gridy = 3;
        c.weighty = 0.2;
        add(status, c);
    }

    /**
     * ���� ����� ����������, ���� ���������� �������� ���� ����������
     * @param mes ��������� ������, ������� ������������ ��� �������� ����������
     */
    public void changeLanguage(ResourceBundle mes) {
        this.messages = mes;
        //�������� ��������� �������� ���� ����������
        if(mainFrame != null) {
            mainFrame.setTitle(messages.getString("title"));
        }
        //�������� ������� �� ���� �����������
        browseFolder.setText(messages.getString("FolderTreeAnalyserPanel.browse"));
        ((TitledBorder)folderNamePanel.getBorder()).setTitle(
                messages.getString("FolderTreeAnalyserPanel.folderName"));
        browseResFile.setText(messages.getString("FolderTreeAnalyserPanel.browse"));
        ((TitledBorder)resultsFileNamePanel.getBorder()).setTitle(
                messages.getString("FolderTreeAnalyserPanel.saveRes"));
        curFile.setText(messages.getString("FolderTreeAnalyserPanel.processingFile"));
        total.setText(messages.getString("FolderTreeAnalyserPanel.totalProgress"));
        run.setText(messages.getString("FolderTreeAnalyserPanel.run"));
        if(totalFiles == 0) {
            status.setText(messages.getString("FolderTreeAnalyserPanel.status")
                    + "...");
        }
        else {
            status.setText(messages.getString("FolderTreeAnalyserPanel.status")
                    + ": " + processedFiles + " "
                    + messages.getString("FolderTreeAnalyserPanel.of")
                    + " " + totalFiles);
            
        }
    }

    /*
     * ���������� ���� ������ �����-�� �� ������ ������
     */
    public void actionPerformed(ActionEvent arg0) {
        //���� ������ ������ "����"
        if(arg0.getActionCommand().equals("run")) {
            srcDir = new File(folderName.getText());
            //������� ��������� �� ������, ���� ��������� ����� �� ����������
            if(!srcDir.exists()) {
                JOptionPane.showMessageDialog(mainFrame,
                        messages.getString("FolderTreeAnalyserPanel.noFolder"),
                        messages.getString("FolderTreeAnalyserPanel.error"),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            //���� ����� ���� � ������� ����� ��������� ���������...
            if(!resultsFileName.getText().equals("")) {
                resFile = new File(resultsFileName.getText());
                Object[] options = {messages.getString("FolderTreeAnalyserPanel.yes"),
                        messages.getString("FolderTreeAnalyserPanel.no")};
                //...��������� ���������� �� �� �� ������ ������, �, ����
                //����������, ���������� � ������������ ����� �� ��� ������������
                if(resFile.exists()) {
                    int resVal = JOptionPane.showOptionDialog(mainFrame,
                            messages.getString("FolderTreeAnalyserPanel.file") + " "
                            + resultsFileName.getText() + " "
                            + messages.getString("FolderTreeAnalyserPanel.exists"),
                            messages.getString("FolderTreeAnalyserPanel.fileExists"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[1]);
                    if(resVal == 1) {
                        //�������, ���� �������������� ������
                        return;
                    }
                }
            }
            //������� � ��������� ����� � ������� ����� ����������� ����������
            worker = new Thread(this);
            worker.start();
        }
        //���� ������ ������ "�����", ����������� � ������ �����
        if(arg0.getActionCommand().equals("browseFolder")) {
            //������� ������ ������ �����
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int retVal = fc.showOpenDialog(mainFrame);
            //������� ��������� ������ � ��������������� ��������� ����
            if(retVal == JFileChooser.APPROVE_OPTION) {
                folderName.setText(fc.getSelectedFile().toString());
            }
        }
        //���� ������ ������ "�����", ����������� � ������ ����� �����������
        if(arg0.getActionCommand().equals("browseResFile")) {
            //������� ������ ������ �����
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int retVal = fc.showSaveDialog(mainFrame);
            //������� ��������� ������ � ��������������� ��������� ����
            if(retVal == JFileChooser.APPROVE_OPTION) {
                resultsFileName.setText(fc.getSelectedFile().toString());
            }
        }
        //���� ������ ������ "����"
        if(arg0.getActionCommand().equals("stop")) {
            //���������� ��� ��������
            totalFiles = 0;
            processedFiles = 0;
            filesSize = 0;
            prevPercent = 0;
            curPercent = 0;
            //������������� ������� ��������, �, ���������� ��������������� ���������
            if(analyser != null) {
                analyser.stopAnalyse();
                JOptionPane.showMessageDialog(mainFrame,
                        messages.getString("FolderTreeAnalyserPanel.interruptedByUser"),
                        messages.getString("FolderTreeAnalyserPanel.interrupted"),
                        JOptionPane.INFORMATION_MESSAGE);
            }
            //�������� ���������� ������ "����" �� "����"
            run.setActionCommand("run");
            run.setText(messages.getString("FolderTreeAnalyserPanel.run"));
        }
    }
    
    /*
     * ������� ���������� ���� � ������������ �������
     * @param results ������ � ������������ �������
     * @param fileName ��� �����, � ������� ���� ��������� ����������
     */
    private void showResultsDialog(List results, String fileName) {
        //���� ������ ����������� �� ������, ������������ �� ���� ������������
        if(results == null) {
            JOptionPane.showMessageDialog(mainFrame,
                    messages.getString("FolderTreeAnalyserPanel.resultsAreAbsent"),
                    messages.getString("FolderTreeAnalyserPanel.noResults"),
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            //������� � ���������� ���������� ����
            ResultsDialog r = new ResultsDialog(mainFrame, true, results, messages,
                    fileName);
            r.pack();
            r.setVisible(true);
        }
    }

    /**
     * ���������� ��� �������� ������. � ���� ������ ����������� ������ MD5 ����.
     */
    public void run() {
        try {
            //��������� ����������
            calculationResults = analyser.analyse(srcDir.toString());
            //���� ��������� �� �����-���� �������� �� ������� - �������
            if(calculationResults == null) {
                return;
            }
            //���� ����� ���� �����������
            if(resFile != null) {
                //���������� � ���� ����������
                PrintWriter pw = null;
                try {
                    pw = new PrintWriter(new FileOutputStream(resFile));
                    for(int i = 0; i < calculationResults.size(); i++) {
                        pw.println((String)calculationResults.get(i));
                    }
                }
                //���� �������� ������ ��� ������ � ����...
                catch(IOException err) {
                    //... ���������� ��������������� ���������
                    JOptionPane.showMessageDialog(mainFrame,
                            messages.getString("FolderTreeAnalyserPanel.fileWriteError"),
                            messages.getString("FolderTreeAnalyserPanel.error"),
                            JOptionPane.ERROR_MESSAGE);
                }
                finally {
                    //��������� �����
                    pw.close();
                }
            }
        }
        catch(IOException err) {
            //���� �������� ������ ��� �������� MD5 ���� ��� ������� ��������� �����
            //�������� � ��� ������������
            JOptionPane.showMessageDialog(mainFrame,
                    err.getMessage(),
                    messages.getString("FolderTreeAnalyserPanel.error"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * ���� ����� ���������� ����������� ������ MD5Calculator.
     * � �������� ��������� ���������� ������� ��������� �� ������ ������
     * ����� ����������.
     * @param f ��������� ������ �����, ������� ��������� �� ������ ������
     */
    public void setNewMD5ProgressValue(float f) {
        final int val = (int)f;
        //������������ ������� ��������� �� ������ ������ ����� ����������
        //�� ������ ������
        double calculatedBytes = curFileSize*f/100;
        curPercent = calculatedBytes*100/filesSize;
        final int cp = (int)Math.round(curPercent);
        final int pp = (int)Math.round(prevPercent);
        //���������� ���������� � ���� �������� �� ������
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                curFileProgress.setValue(val);
                totalProgress.setValue(pp + cp);
            }});
    }

    /**
     * ���� ����� ���������� ����������� ������ FolderTreeAnalyser �����
     * ������� ������� ��������� �����
     */
    public void folderStructureAnalyseBegin() {
        //���������� ��� ��������
        totalFiles = 0;
        processedFiles = 0;
        filesSize = 0;
        prevPercent = 0;
        curPercent = 0;
        //���������� ���������� � ���, ��� ����� ������� ������� �� ������
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                curFileProgress.setValue(0);
                totalProgress.setIndeterminate(true);
                //�������� ���������� ������ "����" �� "����"
                run.setActionCommand("stop");
                run.setText(messages.getString("FolderTreeAnalyserPanel.stop"));
                curFile.setText(messages.getString(
                        "FolderTreeAnalyserPanel.processingFile"));
                status.setText(messages.getString(
                        "FolderTreeAnalyserPanel.totalProgress") + "...");
            }});
    }

    /**
     * ���� ����� ���������� ����������� ������ FolderTreeAnalyser �����
     * ��������� ������� ��������� �����. � �������� ���������� ����������
     * ���������� �������.
     * @param folderCount ���������� �����
     * @param filesCount ���������� ������
     * @param filesSize ����� ������ ������
     */
    public void folderStructureAnalyseEnd(int folderCount, int filesCount,
            long filesSize) {
        //��������� ����������� ��� ���������
        totalFiles = filesCount;
        this.filesSize = filesSize;
        //���������� ���������� ������� �� ������
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                totalProgress.setIndeterminate(false);
                totalProgress.setValue(0);
                status.setText(messages.getString(
                        "FolderTreeAnalyserPanel.totalProgress") +
                        " " + processedFiles + " " +
                        messages.getString("FolderTreeAnalyserPanel.of") +
                        " " + totalFiles);
            }});
    }

    /**
     * ���� ����� ���������� ����������� ������ FolderTreeAnalyser �����
     * ������� �������� MD5 ����� ���������� �����. � �������� ����������
     * ���������� ���������� � �����.
     * @param fileName ��� �����
     * @param fileLength ����� �����
     */
    public void MD5SumCalculationBegin(String fileName, long fileLength) {
        //��������� ���������
        curFileSize = fileLength;
        final String fN = fileName;
        //���������� ��� ����� �� ������
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                curFile.setText(messages.getString(
                        "FolderTreeAnalyserPanel.processingFile") + " " + fN);
            }});
    }

    /**
     * ���� ����� ���������� ����������� ������ FolderTreeAnalyser �����
     * ���������� �������� MD5 ����� ���������� �����.
     * @param fileName ��� �����
     */
    public void MD5SumCalculationEnd(String fileName) {
        if(fileName.equals("")) {
            //���� ����� �� �������� ������
            curPercent = 100;
        }
        else {
            //�������� �������� ���������
            processedFiles++;
            prevPercent += curPercent;
            curPercent = 0;
        }
        //���������� ���������� � ���������� �������� �� ������
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                status.setText(messages.getString(
                "FolderTreeAnalyserPanel.totalProgress") +
                " " + processedFiles + " " +
                messages.getString("FolderTreeAnalyserPanel.of") +
                " " + totalFiles);
            }});
        //���� ������ ���� �������� ���������, �� ���������� ���������� ����
        //� ������������ ����������
        if(processedFiles == totalFiles) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //�������� ���������� ������ "����" �� "����"
                    run.setActionCommand("run");
                    run.setText(messages.getString("FolderTreeAnalyserPanel.run"));
                    //���� ��� ����� ����������� ������, �� ���������� ��� �
                    //���������� ���� � ������������
                    if(resFile != null) {
                        //���������� ���������� ���� � ������������
                        showResultsDialog(calculationResults,
                                resFile.getAbsolutePath());
                    }
                    else {
                        //���������� ���������� ���� � ������������
                        showResultsDialog(calculationResults,
                                messages.getString("FolderTreeAnalyserPanel.noResFile"));
                    }
                }});
        }
    }
}
