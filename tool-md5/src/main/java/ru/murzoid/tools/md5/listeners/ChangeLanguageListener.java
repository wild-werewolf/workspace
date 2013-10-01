/*
 * ChangeLanguageListener.java
 *
 * Created on 19 Март 2005 г., 12:36
 */

package ru.murzoid.tools.md5.listeners;

import java.util.ResourceBundle;

public interface ChangeLanguageListener {
    /**
     * Объект, реализующий данный интерфейс, при вызове этой функции должен
     * прочитать строки из mes, и соответственно настроить язык интерфейса.
     * @param mes текстовые строки, которые используются при создании интерфейса
     */
    public void changeLanguage(ResourceBundle mes);
}
