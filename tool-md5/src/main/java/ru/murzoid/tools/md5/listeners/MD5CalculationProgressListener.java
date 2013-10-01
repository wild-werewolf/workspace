/*
 * MD5CalculationProcessListener.java
 *
 * Created on 2 ћарт 2005 г., 17:09
 */

package ru.murzoid.tools.md5.listeners;


public interface MD5CalculationProgressListener {
    /**
     * Ётот метод устанавливает новое значение процента выполнени€
     * рассчета.
     * @param f процент выполнени€ рассчета
     */
    public void setNewMD5ProgressValue(float f);
}
