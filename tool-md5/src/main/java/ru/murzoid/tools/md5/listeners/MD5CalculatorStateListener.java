/*
 * Created on 27.07.2005
 *
 */
package ru.murzoid.tools.md5.listeners;


public interface MD5CalculatorStateListener {
    
    /**
     * ���� ����� ����������, ���� ����� ������� ������� ���������
     * ����� � ������ � ��� (����������� ������ ������, �����������
     * �� ������ ������� � �.�.)
     */
    public void folderStructureAnalyseBegin();
    
    /**
     * ���� ����� ���������� ����� ���������� ������� ��������� �����.
     * � �������� ���������� �������� ���������� �������.
     * 
     * @param folderCount ���������� �����
     * @param filesCount ���������� ������
     * @param filesSize ����� ������ ������
     */
    public void folderStructureAnalyseEnd(int folderCount,
            int filesCount, long filesSize);
    
    /**
     * ���� ����� ���������� ����� ������� �������� MD5 �����
     * ���������� �����
     * @param fileName ��� �����
     * @param fileLength ����� �����
     */
    public void MD5SumCalculationBegin(String fileName, long fileLength);
    
    /**
     * ���� ����� ���������� ����� ��������� �������� MD5 �����
     * ���������� �����
     * @param fileName ��� �����
     */
    public void MD5SumCalculationEnd(String fileName);
}
