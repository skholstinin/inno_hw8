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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    public static String[] getFIleNames() {
        File folder = new File("E:\\temp\\testSet\\");
        File[] listOfFiles = folder.listFiles();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add("E:\\temp\\testSet\\" + listOfFiles[i].getName());
            }
        }
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        String[] words = new String[3];
        Map<String, ArrayList<String>> findingHashMap = new ConcurrentHashMap<>();
        InputStream inputStream = null;
        String[] source = getFIleNames();
        words[0] = "njypdivud";
        words[1] = "ofo";
        words[2] = "ar";
        long startTime = System.currentTimeMillis();
        final ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < source.length; i++) {
            ReadSource readSource = new ReadSource(i, source);
            es.submit(readSource);
            es.submit(new SearchWord(readSource.getInputStream(), findingHashMap, words));
        }
        es.submit(new WriteToFile("result.txt", findingHashMap));
        long endTime = System.currentTimeMillis() - startTime;
        System.out.println("\r\nЗатрачено времени: " + endTime / 1000 + "секунд" + "\r\n");
        System.out.println("The End");
    }
}
