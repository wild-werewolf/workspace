/*
 * MD5MessageReader.java
 *
 * Created on 12 ���� 2005 �., 19:45
 */

package ru.murzoid.tools.md5.logic;

import java.io.*;


public class MD5MessageReader {
    
    private byte[] message = null;
    private int[] decodedMessage = null;
    private File dataFile = null;
    private BufferedInputStream is = null;
    private int currentPos = 0;
    private boolean appended = false;
    
    /**
     * ������� ����� ���������� ������ <CODE>MD5MessageReader</CODE>
     */
    public MD5MessageReader() {
    }
    
    /**
     * ������ ���� ��� �������� ���������� ���������� MD5 �����.
     * @param f ��� �����
     * @throws java.io.IOException ��� ������������� ������ ������ � ������
     */
    public void readMessage(File f) throws IOException {
        dataFile = f;
        currentPos = 0;
        appended = false;
        message = null;
        if(is != null) {
            is.close();
            is = null;
        }
    }
    
    /**
     * ���������� ������ ���� <CODE>int</CODE> ������ 16,
     * ���������� ������ ��� �������� MD5 �����.
     * ������ ����������� ����� ����� ������ ���������� ����� ����
     * ������. ���� ��������� ����� ��������� ��������� ����������
     * <CODE>null</CODE>.
     * ��� ����, ����� �������� ������ ��������� ��� (������� �
     * ������ ���������) ����� ������� ����� <CODE>readMessage(File f)</CODE>.
     * @throws java.io.IOException ���� �������� ������ ��� ������ � ������ �������� ������
     * @return ����� ���� ������ � ���� ������� ���� <CODE>int</CODE>,
     * ������ 16 (16 32-������� ����), ���� ��������� �����
     * ��������� - <CODE>null</CODE>
     */
    public int[] getNextMessageBlock() throws IOException {
        if(dataFile == null || (message == null && appended == true))
            return null;
        int[] res = new int[16];
        if(message == null)
            message = getMessageFromFile();
        if(currentPos >= message.length / 4) {
            currentPos = 0;
            message = getMessageFromFile();
            if(message == null)
                return null;
        }
        decodedMessage = decode(message);
        System.arraycopy(decodedMessage, currentPos, res, 0, 16);
        currentPos += 16;
        return res;
    }
    
    /*
     ���� ����� ������ ��������� ���� ������ �� ����� � �����������
     ��� � ������ byte[] message. � ���������� ����� ������ ������������
     ���� � ������������ � ����� 1 � 2 � RFC1321.
     ���������� ��������� �� ������ ����������� ������ (���� ��� ���� ���������),
     null - ���� ��������� ����� �����.
    */
    private byte[] getMessageFromFile() throws IOException {
        if(is == null)
            is = new BufferedInputStream(new FileInputStream(dataFile), 1024*1024);
        final int bufLength = 1024;
        byte[] buf = new byte[bufLength];
        int bytesNumb = is.read(buf);
        if(bytesNumb == -1 && appended) {
            is.close();
            is = null;
            return null;
        }
        if(bytesNumb == -1)
            bytesNumb = 0;
        if(bytesNumb != bufLength) {
            byte[] appBlock = getAppendBlock(bytesNumb);
            byte[] messageLength = encode(dataFile.length());
            byte[] res = new byte[bytesNumb + appBlock.length
                    + messageLength.length];
            System.arraycopy(buf, 0, res, 0, bytesNumb);
            System.arraycopy(appBlock, 0, res, bytesNumb, appBlock.length);
            System.arraycopy(messageLength, 0, res,
                    bytesNumb + appBlock.length, messageLength.length);
            appended = true;
            return res;
        }
        return buf;
    }
    
    /* ����������� �������� ����� ��������� (mesLength) � ������ �� 8 ����.
       � ������������ � RFC1321 ����� ������������ � �������� �������.
     */
    private byte[] encode(long mesLength) {
        long mesLengthInBits = mesLength * 8;
        byte[] res = new byte[8];
        for(int i = 0; i < 8; i++)
            res[i] = (byte)((mesLengthInBits >>> (i*8)) & 0xFF);
        return res;
    }
    
    /* ����������� ������ ���� � ������ int (�� 4 �����). � ������������
       � RFC1321 ������ ���� � ������ 4-� ������� ������ ������������
       ���������.
     */
    private static int[] decode(byte[] mes) {
        int[] res = new int[mes.length / 4];
        for(int i = 0; i < res.length; i++)
            res[i] = (((int)mes[i*4]) & 0xFF) |
                     (((((int)mes[i*4 + 1]) << 8) & 0xFF00)) |
                     (((((int)mes[i*4 + 2]) << 16) & 0xFF0000)) |
                     (((((int)mes[i*4 + 3]) << 24) & 0xFF000000));
        return res;
    }
    
    /* ��������� ��������� ����� �������, ����� ������� �� ������� ���
       ����� �� 512 ��� (64 ����) ��� ����� 448 ��� (56 ����).
       ��� 1 � RFC1321.
     */
    private byte[] getAppendBlock(int length) {
        int blockLength;
        int p = length % 64;
        boolean firstByte = true;
        if(p < 56)
            blockLength = 56 - p;
        else
            blockLength = 120 - p;
        byte[] res = new byte[blockLength];
        for(int i = 0; i < res.length; i++) {
            if(firstByte) {
                firstByte = false;
                res[i] = (byte)(res[i] | 0x80);
            }
            else
                res[i] = 0;
        }
        return res;
    }
    
    /**
     * ������������ ����� � ����� ���� <CODE>int</CODE> (4 �����)
     * � �������� �������, �.�. ������ ���� ������ ���������.
     * @param val �����, ������� ����� �������������
     * @return ��������������� �����
     */
    public static int convertInt(int val) {
        byte[] t = new byte[4];
        t[3] = (byte)(val & 0xFF);
        t[2] = (byte)((val >>> 8) & 0xFF);
        t[1] = (byte)((val >>> 16) & 0xFF);
        t[0] = (byte)((val >>> 24) & 0xFF);
        int[] res = decode(t);
        return res[0];
    }
}    
