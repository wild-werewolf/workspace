/*
 * MD5CalculationProcessListener.java
 *
 * Created on 2 ���� 2005 �., 17:09
 */

package ru.murzoid.tools.md5.listeners;


public interface MD5CalculationProgressListener {
    /**
     * ���� ����� ������������� ����� �������� �������� ����������
     * ��������.
     * @param f ������� ���������� ��������
     */
    public void setNewMD5ProgressValue(float f);
}
