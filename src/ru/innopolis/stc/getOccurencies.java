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
    private final int BUFFER_SIZE = 65536;

    public getOccurencies(String[] sources, String[] words, String res) {
        this.sources = sources;
        this.words = words;
        this.res = res;
    }

    public synchronized void readParceString() throws IOException {
        String tempString;
        int count = 10;
        if ((sources.length - currentItemSources + count) / count > 1) {

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

                    inputStream = new FileInputStream(new File(sources[countItemSources]));
                }
                String lineString = null;
                String spliter = null;
                String[] StrArray = null;

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                try {
                    byte[] byteBuffer = new byte[BUFFER_SIZE];
                    int numberOfBytes;
                    do {
                        numberOfBytes = bufferedInputStream.read(byteBuffer, 0, BUFFER_SIZE);
                    } while (numberOfBytes >= 0);
                } finally {
                    bufferedInputStream.close();
                }
                System.out.println(bufferedInputStream);
                /*while ((lineString = bufferedReaderreader.readLine()) != null) {
                    spliter += lineString;
                    System.out.println(lineString);
                }
                bufferedReaderreader.close();*/

                /*StrArray = spliter.split("[\\.!?]");

                for (String s : StrArray) {

                    if (s.contains("мама мыла раму"))
                        System.out.println(s);
                }*/

                arrayForFind[countItemSources] = sources[currentItemSources];
                currentItemSources++;
            }
        }

        //System.out.println(arrayForFind);
        notifyAll();
    }

    public synchronized void writeToFile() throws InterruptedException {
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
