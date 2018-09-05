package ru.innopolis.stc;
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

import java.io.*;
import java.net.URL;

public class getOccurencies {
    private static int currentItemSources = 0;
    private static int countItemSources = 0;
    private String[] sources;
    private String[] words;
    private String res;
    private String[] arrayForFind = new String[100];
    private int count = 0;

    public getOccurencies(String[] sources, String[] words, String res) {
        this.sources = sources;
        this.words = words;
        this.res = res;
    }

    public synchronized void readString() throws InterruptedException, IOException {
        String tempString;
        int count = 100;
        byte[] buffer = new byte[1024];

        if ((sources.length - currentItemSources + count) % count > 1) {

        } else {
            count = sources.length - currentItemSources;
        }
        for (countItemSources = 0; countItemSources < count; countItemSources++) {
            if (!sources[countItemSources].isEmpty()) {
                InputStream inputStream;
                if ((sources[countItemSources].regionMatches(true, 0, "ftp", 0, 3)) ||
                        (sources[countItemSources].regionMatches(true, 0, "www", 0, 3)) ||
                        (sources[countItemSources].regionMatches(true, 0, "http", 0, 4))) {
                    URL url = new URL(sources[countItemSources]);
                    inputStream = url.openStream();
                } else {
                    File inputFile = new File(sources[countItemSources]);
                    FileInputStream fis = new FileInputStream(inputFile);
                    inputStream = fis;
                }
                try (BufferedInputStream bis = new BufferedInputStream(inputStream)) {
                    int i = 0;
                    while ((i = bis.read(buffer, 0, 1024)) != -1) {
                        System.out.println(buffer);
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                arrayForFind[countItemSources] = sources[currentItemSources];
                currentItemSources++;
            }
        }

        System.out.println(arrayForFind);
        notifyAll();
    }

    public synchronized void findWord() throws InterruptedException {
        boolean free = false;
        wait();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < words.length; j++) {
                if (arrayForFind[i].contains(words[j])) {
                    System.out.println("Finding word is" + words[j]);
                    System.out.println("Пишем слово в файл");
                }
            }
        }
    }
}