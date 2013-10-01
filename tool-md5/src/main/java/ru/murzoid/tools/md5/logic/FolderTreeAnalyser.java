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

    private List fileList = null; //список файлов
    private List results = null; //список строк с результатами
    private List listeners = null; //список указателей на классы, которым нужна
    							   //информация о ходе расчета
    private MD5Calculator calculator = null; //калькулятор, выполняет рассчет
    										 //MD5 сумм для отдельных файлов
    private boolean stop = false; //указывает можно ли продолжать рассчет
    private int filesCount = 0; //количество файлов
    private int folderCount = 0; //количество папок
    private long filesSize = 0; //общий размер файлов
    
    /**
     * Конструктор. Создает новые экземпляры класса <code>FolderTreeAnalyser</code>.
     */
    public FolderTreeAnalyser() {
        //создание объектов
        fileList = new ArrayList();
        results = new ArrayList();
        listeners = new ArrayList();
        calculator = new MD5Calculator();
        stop = false;
    }
    
    /**
     * Регистрирует класс, который будет получать уведомления о анализе текущей
     * папки.
     * @param listener указатель на класс, которому нужна информация о процессе
     * анализа (он должен реализовывать интерфейс MD5CalculatorStateListener).
     */
    public void addMD5CalculatorStateListener(MD5CalculatorStateListener listener) {
        if(listener != null && listeners != null) {
            listeners.add(listener);
        }
    }
    
    /**
     * Регистрирует класс, который будет получать уведомления об рассчете MD5 суммы
     * конкретного файла.
     * @param listener указатель на класс, которому нужна информация о процессе
     * рассчета (он должен реализовывать интерфейс MD5CalculationProgressListener).
     */
    public void addMD5CalculationProgressListener(
            MD5CalculationProgressListener listener) {
        if(listener != null && calculator != null) {
            calculator.setMD5CalculationProgressListener(listener);
        }
    }
    
    /**
     * Выполняет рассчет MD5 сумм для всех файлов в заданной папке.
     * @param folderName имя папки
     * @return List, содержащий строки с именами файлов и их MD5 суммами, если
     * расчет завершен успешно, null - в противном случае
     * @throws IOException если возникли ошибки ввода-вывода
     */
    public List analyse(String folderName) throws IOException {
        //установка начальных значений
        stop = false;
        fileList.clear();
        results.clear();
        filesCount = 0;
        folderCount = 0;
        filesSize = 0;
        File startingFolder = new File(folderName);
        //проверка существования папки
        if(startingFolder.exists() == false) {
            throw new IOException();
        }
        //уведомляем зарегистрированные классы, о начале анализа папки (поиск файлов)
        for(int i = 0; i < listeners.size(); i++) {
            ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseBegin();
        }
        //если заданная папка является файлом
        if(startingFolder.isFile()) {
            //уведомляем зарегистрированные классы, о завершении анализа
            //структуры папок
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseEnd(
                        0, 1, startingFolder.length());
            }
            //уведомляем зарегистрированные классы, о начале рассчета MD5 сумм
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).MD5SumCalculationBegin(
                        startingFolder.getName(), startingFolder.length());
            }
            //рассчитываем MD5 сумму
            calculator.readMessage(startingFolder);
            String md5sum = calculator.calculate();
            //формируем результат, и добавляем его в список
            String res = new String(md5sum + " *" + startingFolder.getName());
            results.add(res);
            //уведомляем зарегистрированные классы, о завершении рассчета MD5 суммы
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).MD5SumCalculationEnd(
                        startingFolder.getName());
            }
        }
        else {
            //уведомляем зарегистрированные классы, о начале анализа структуры папок
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseBegin();
            }
            //начинаем анализ папки (составление списка файлов)
            analyseFolderTree(startingFolder);
            //уведомляем зарегистрированные классы, о завершении анализа
            //структуры папок
            for(int i = 0; i < listeners.size(); i++) {
                ((MD5CalculatorStateListener)(listeners.get(i))).folderStructureAnalyseEnd(
                        folderCount, filesCount, filesSize);
            }
            //если список файлов не создан
            if(fileList == null) {
                return null;
            }
            //если папка не содержит файлов, уведомляем зарегистрированные классы
            //о завершении расчета
            if(fileList.size() == 0) {
                for(int k = 0; k < listeners.size(); k++) {
                    ((MD5CalculatorStateListener)(
                            listeners.get(k))).MD5SumCalculationEnd("");
                }
                //завершаем работу
                return results;
            }
            //начинаем рассчет MD5 сумм для файлов из списка fileList
            for(int i = 0; i < fileList.size(); i++) {
                //проверяем, можно ли продолжать рассчет
                if(stop == true) {
                    //завершаем работу
                    return null;
                }
                //уведомляем зарегистрированные классы, о начале рассчета
                //MD5 суммы очередного файла
                for(int j = 0; j < listeners.size(); j++) {
                    ((MD5CalculatorStateListener)(listeners.get(j))).MD5SumCalculationBegin(
                            ((File)(fileList.get(i))).getName(), ((File)(fileList.get(i))).length());
                }
                //рассчитываем MD5 сумму очередного файла, и формируем результат
                File curFile = (File)fileList.get(i);
                calculator.readMessage(curFile);
                String md5sum = calculator.calculate();
                String relPath = getRelativePath(startingFolder.getAbsolutePath(),
                        curFile.getAbsolutePath());
                String res = new String(md5sum + " *" + relPath);
                results.add(res);
                //уведомляем зарегистрированные классы, о завершении рассчета
                //MD5 суммы очередного файла
                for(int k = 0; k < listeners.size(); k++) {
                    ((MD5CalculatorStateListener)(listeners.get(k))).MD5SumCalculationEnd(
                            ((File)(fileList.get(i))).getName());
                }
            }
        }
        //возвращаем результат
        return results;
    }
    
    /**
     * Останавливает процесс расчета. Имеет смысл использовать если
     * метод analyse() запущен в отдельном потоке.
     */
    public void stopAnalyse() {
        calculator.stopCalculation();
        stop = true;
    }
    
    /**
     * Возвращает количество файлов в папке, анализ которой выполнялся.
     * Имеет смысл использовать после вызова метода analyse().
     * @return количество файлов
     */
    public int getFilesCount() {
        return filesCount;
    }
    
    /**
     * Возвращает количество папок в папке, анализ которой выполнялся.
     * Имеет смысл использовать после вызова метода analyse().
     * @return количество папок
     */
    public int getFolderCount() {
        return folderCount;
    }
    
    /**
     * Возвращает общий размер файлов в папке, анализ которой выполнялся.
     * Имеет смысл использовать после вызова метода analyse().
     * @return общий размер файлов
     */
    public long getFilesSize() {
        return filesSize;
    }
    
    /**
     * Этот метод возвращает путь к файлу, относительно заданной папки.
     * @param basePath путь к заданной папке
     * @param fullPath полный путь к файлу
     * @return путь к файлу относительно заданной папки
     */
    public static String getRelativePath(String basePath, String fullPath) {
        StringBuffer res = new StringBuffer(fullPath);
        res.delete(0, basePath.length() + 1);
        return res.toString();
    }
    
    /*
     * Выполняет составление списка файлов в заданной папке и ее подпапках,
     * составляет их список, рассчитывает их общий размер и считает
     * количество вложенных папок.
     */
    private void analyseFolderTree(File folderName) {
        //проверяем можно ли продолжать анализ
        if(stop == true) {
            return;
        }
        //если текущая папка является файлом - выходим
        if(folderName.isFile()) {
            return;
        }
        //считаем папки
        folderCount++;
        //получаем массив из файлов, которые находятся в текущей папке
        File[] files = folderName.listFiles();
        //если при получении массива возникли ошибки - выходим
        if(files == null) {
            return;
        }
        //если папка не содержит файлов - выходим
        if(files.length == 0) {
            return;
        }
        //обрабатываем массив файлов
        for(int i = 0; i < files.length; i++) {
            //если файл является папкой, то вызываем этот же метод с данной
            //папкой в качестве параметра
            if(files[i].isDirectory()) {
                analyseFolderTree(files[i]);
            }
            //если файл является файлом
            else {
                //увеличиваем значение счетчика файлов
                filesCount++;
                //добавляем размер файла к общему размеру
                filesSize += files[i].length();
                //добавляем файл в список
                fileList.add(files[i]);
            }
        }
    }
}
