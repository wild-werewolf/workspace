/*
 * ChangeLanguageListener.java
 *
 * Created on 19 ���� 2005 �., 12:36
 */

package ru.murzoid.tools.md5.listeners;

import java.util.ResourceBundle;

public interface ChangeLanguageListener {
    /**
     * ������, ����������� ������ ���������, ��� ������ ���� ������� ������
     * ��������� ������ �� mes, � �������������� ��������� ���� ����������.
     * @param mes ��������� ������, ������� ������������ ��� �������� ����������
     */
    public void changeLanguage(ResourceBundle mes);
}
