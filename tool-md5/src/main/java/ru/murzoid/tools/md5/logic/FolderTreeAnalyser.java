/*
 * Created on 27.07.2005
 *
 */
package ru.murzoid.tools.md5.logic;

import java.util.*;
import java.io.*;

import ru.murzoid.tools.md5.listeners.MD5CalculationProgressListener;
import ru.murzoid.tools.md5.listeners.MD5CalculatorStateListener;

public class FolderTreeAnalyser {

    private List fileList = null; //������ ������
    private List results = null; //������ ����� � ������������
    private List listeners = null; //������ ���������� �� ������, ������� �����
    							   //���������� � ���� �������
    private MD5Calculator calculator = null; //�����������, ��������� �������
    										 //MD5 ���� ��� ��������� ������
    private boolean stop = false; //��������� ����� �� ���������� �������
    private int filesCount = 0; //���������� ������
    private int folderCount = 0; //���������� �����
    private long filesSize = 0; //����� ������ ������
    
    /**
     * �����������. ������� ����� ���������� ������ <code>FolderTreeAnalyser</code>.
     */
    public FolderTreeAnalyser() {
        //�������� ��������
        fileList = new ArrayList();
        results = new ArrayList();
        listeners = new ArrayList();
        calculator = new MD5Calculator();
        stop = false;
    }
    
    /**
     * ������������ �����, ������� ����� �������� ����������� � ������� �������
     * �����.
     * @param listener ��������� �� �����, �������� ����� ���������� � ��������
     * ������� (�� ������ ������������� ��������� MD5CalculatorStateListener).
     */
    public void addMD5CalculatorStateListener(MD5CalculatorStateListener listener) {
        if(listener != null && listeners != null) {
            listeners.add(listener);
        }
    }
    
    /**
     * ������������ �����, ������� ����� �������� ����������� �� �������� MD5 �����
     * ����������� �����.
     * @param listener ��������� �� �����, �������� ����� ���������� � ��������
     * �������� (�� ������ ������������� ��������� MD5CalculationProgressListener).
     */
    public void addMD5CalculationProgressListener(
            MD5CalculationProgressListener listener) {
        if(listener != null && calculator != null) {
            calculator.setMD5CalculationProgressListener(listener);
        }
    }
    
    /**
     * ��������� ������� MD5 ���� ��� ���� ������ � �������� �����.
     * @param folderName ��� �����
     * @return List, ���������� ������ � ������� ������ � �� MD5 �������, ����
     * ������ �������� �������, null - � ��������� ������
     * @throws IOException ���� �������� ������ �����-������
     */
    public List analyse(String folderName) throws IOException {
        //��������� ��������� ��������
        stop = false;
        fileList.clear();
        results.clear();
        filesCount = 0;
        folderCount = 0;
        filesSize = 0;
        File startingFolder = new File(folderName);
        //�������� ������������� �����
        if(startingFolder.exists() == false) {
            throw new IOException();
        }
        //���������� ������������������ ������, � ������ ������� ����� (����� ������)
        for(int i = 0; i < listeners.size(); i++) {
            ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseBegin();
        }
        //���� �������� ����� �������� ������
        if(startingFolder.isFile()) {
            //���������� ������������������ ������, � ���������� �������
            //��������� �����
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseEnd(
                        0, 1, startingFolder.length());
            }
            //���������� ������������������ ������, � ������ �������� MD5 ����
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).MD5SumCalculationBegin(
                        startingFolder.getName(), startingFolder.length());
            }
            //������������ MD5 �����
            calculator.readMessage(startingFolder);
            String md5sum = calculator.calculate();
            //��������� ���������, � ��������� ��� � ������
            String res = new String(md5sum + " *" + startingFolder.getName());
            results.add(res);
            //���������� ������������������ ������, � ���������� �������� MD5 �����
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).MD5SumCalculationEnd(
                        startingFolder.getName());
            }
        }
        else {
            //���������� ������������������ ������, � ������ ������� ��������� �����
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseBegin();
            }
            //�������� ������ ����� (����������� ������ ������)
            analyseFolderTree(startingFolder);
            //���������� ������������������ ������, � ���������� �������
            //��������� �����
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseEnd(
                        folderCount, filesCount, filesSize);
            }
            //���� ������ ������ �� ������
            if(fileList == null) {
                return null;
            }
            //���� ����� �� �������� ������, ���������� ������������������ ������
            //� ���������� �������
            if(fileList.size() == 0) {
                for(int k = 0; k < listeners.size(); k++) {
                    ((MD5CalculatorStateListener)(
                            listeners.get(k))).MD5SumCalculationEnd("");
                }
                //��������� ������
                return results;
            }
            //�������� ������� MD5 ���� ��� ������ �� ������ fileList
            for(int i = 0; i < fileList.size(); i++) {
                //���������, ����� �� ���������� �������
                if(stop == true) {
                    //��������� ������
                    return null;
                }
                //���������� ������������������ ������, � ������ ��������
                //MD5 ����� ���������� �����
                for(int j = 0; j < listeners.size(); j++) {
                    ((MD5CalculatorStateListener)(listeners.get(j))).MD5SumCalculationBegin(
                            ((File)(fileList.get(i))).getName(), ((File)(fileList.get(i))).length());
                }
                //������������ MD5 ����� ���������� �����, � ��������� ���������
                File curFile = (File)fileList.get(i);
                calculator.readMessage(curFile);
                String md5sum = calculator.calculate();
                String relPath = getRelativePath(startingFolder.getAbsolutePath(),
                        curFile.getAbsolutePath());
                String res = new String(md5sum + " *" + relPath);
                results.add(res);
                //���������� ������������������ ������, � ���������� ��������
                //MD5 ����� ���������� �����
                for(int k = 0; k < listeners.size(); k++) {
                    ((MD5CalculatorStateListener)(listeners.get(k))).MD5SumCalculationEnd(
                            ((File)(fileList.get(i))).getName());
                }
            }
        }
        //���������� ���������
        return results;
    }
    
    /**
     * ������������� ������� �������. ����� ����� ������������ ����
     * ����� analyse() ������� � ��������� ������.
     */
    public void stopAnalyse() {
        calculator.stopCalculation();
        stop = true;
    }
    
    /**
     * ���������� ���������� ������ � �����, ������ ������� ����������.
     * ����� ����� ������������ ����� ������ ������ analyse().
     * @return ���������� ������
     */
    public int getFilesCount() {
        return filesCount;
    }
    
    /**
     * ���������� ���������� ����� � �����, ������ ������� ����������.
     * ����� ����� ������������ ����� ������ ������ analyse().
     * @return ���������� �����
     */
    public int getFolderCount() {
        return folderCount;
    }
    
    /**
     * ���������� ����� ������ ������ � �����, ������ ������� ����������.
     * ����� ����� ������������ ����� ������ ������ analyse().
     * @return ����� ������ ������
     */
    public long getFilesSize() {
        return filesSize;
    }
    
    /**
     * ���� ����� ���������� ���� � �����, ������������ �������� �����.
     * @param basePath ���� � �������� �����
     * @param fullPath ������ ���� � �����
     * @return ���� � ����� ������������ �������� �����
     */
    public static String getRelativePath(String basePath, String fullPath) {
        StringBuffer res = new StringBuffer(fullPath);
        res.delete(0, basePath.length() + 1);
        return res.toString();
    }
    
    /*
     * ��������� ����������� ������ ������ � �������� ����� � �� ���������,
     * ���������� �� ������, ������������ �� ����� ������ � �������
     * ���������� ��������� �����.
     */
    private void analyseFolderTree(File folderName) {
        //��������� ����� �� ���������� ������
        if(stop == true) {
            return;
        }
        //���� ������� ����� �������� ������ - �������
        if(folderName.isFile()) {
            return;
        }
        //������� �����
        folderCount++;
        //�������� ������ �� ������, ������� ��������� � ������� �����
        File[] files = folderName.listFiles();
        //���� ��� ��������� ������� �������� ������ - �������
        if(files == null) {
            return;
        }
        //���� ����� �� �������� ������ - �������
        if(files.length == 0) {
            return;
        }
        //������������ ������ ������
        for(int i = 0; i < files.length; i++) {
            //���� ���� �������� ������, �� �������� ���� �� ����� � ������
            //������ � �������� ���������
            if(files[i].isDirectory()) {
                analyseFolderTree(files[i]);
            }
            //���� ���� �������� ������
            else {
                //����������� �������� �������� ������
                filesCount++;
                //��������� ������ ����� � ������ �������
                filesSize += files[i].length();
                //��������� ���� � ������
                fileList.add(files[i]);
            }
        }
    }
}
