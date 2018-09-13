/*
* Рализовать следующий интерфейс:

void getOccurencies(String[] sources, String[] words, String res) throws …;

Многоточие означает необходимые для реализации исключения

Функциональные требования: метод получает на вход массив адресов ресурсов (файлы, FTP, web-ссылки) и слов.
Необходимо в многопоточном режиме найти вхождения всех слов второго массива в ресурсы.
Если слово входит в предложение, это предложение добавляется в файл по адресу res.
При начале исполнения метода файл очищается (чтобы исключить наличие старой информации).

Все ресурсы текстовые. Необходимо определить оптимальную производительность.
Возможны ситуации как с большим числом ресурсов (>2000 текстовых ресурсов каждый <2кб), так и с очень большими ресурсами (ресурс>1ГБ).
Максимальный размер массива ресурсов 2000 элементов. Максимальный размер массива слов 2000 элементов.
Предложение это последовательность слов, начинающаяся с заглавной буквы и заканчивающаяся точкой,
вопросительным знаком, восклицательным знаком, или многоточием. Внутри предложения могут находиться знаки препинания.
Слово это последовательность символов кириллических, либо латинских.
* */


package ru.innopolis.stc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class Main {

    public static String[] getFIleNames() {
        File folder = new File("D:\\temp\\testSet1\\");
        File[] listOfFiles = folder.listFiles();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add("D:\\temp\\testSet1\\" + listOfFiles[i].getName());
            }
        }
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        final int QTY_THREADS = 20;
        String[] words = new String[7];
        ConcurrentHashMap<String, ArrayList<String>> findingHashMap = new ConcurrentHashMap<>();
        CopyOnWriteArrayList<InputStream> inputStreamsList = new CopyOnWriteArrayList<>();
        FindingResource findingResource = new FindingResource(inputStreamsList, findingHashMap);
        String[] source = getFIleNames();
        words[0] = "qptefypl";
        words[1] = "end";
        words[2] = "start";
        words[3] = "мастер";
        words[4] = "маргарита";
        words[5] = "бегемот";
        words[6] = "берлиоз";
        long startTime = System.currentTimeMillis();
        final ExecutorService taskExecutorRead = Executors.newFixedThreadPool(QTY_THREADS);
        final ExecutorService taskExecutorSearch = Executors.newFixedThreadPool(QTY_THREADS);
        final ExecutorService taskExecutorWrite = Executors.newFixedThreadPool(QTY_THREADS);
        for (int i = 0; i < source.length; i += QTY_THREADS) {
            if (i < source.length) {
                taskExecutorRead.submit(new ReadSource(i, source, findingResource));
            }
            if (i + 1 < source.length) {
                taskExecutorRead.submit(new ReadSource(i + 1, source, findingResource));//Почему-то так работает медленнее
            }
            if (i + 2 < source.length) {
                taskExecutorRead.submit(new ReadSource(i + 2, source, findingResource));
            }
            if (i + 3 < source.length) {
                taskExecutorRead.submit(new ReadSource(i + 3, source, findingResource));
            }
            if (i + 4 < source.length) {
                taskExecutorRead.submit(new ReadSource(i + 4, source, findingResource));
            }
            //taskExecutorRead.submit(new ReadSource(i, source, findingResource));

        }
        taskExecutorRead.shutdown();
        try {
            taskExecutorRead.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < source.length; i++) {
            taskExecutorSearch.submit(new SearchWord(words, findingResource));
        }
        taskExecutorSearch.shutdown();
        try {
            taskExecutorSearch.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        taskExecutorWrite.submit(new WriteToFile("result.txt", findingResource));
        taskExecutorWrite.shutdown();
        try {
            taskExecutorWrite.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("\r\nЗатрачено времени: " + endTime / 1000 + "секунд" + "\r\n");
        System.out.println("The End");
    }
}
