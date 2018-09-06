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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class getOccurencies {
    private static int currentItemSources = 0;
    private static int countItemSources = 0;
    private String[] sources;
    private String[] words;
    private String res;
    private String[] arrayForFind = new String[100];
    private int count = 0;
    private static int countOccurencies = 0;
    private static int result = 2;

    public getOccurencies(String[] sources, String[] words, String res) {
        this.sources = sources;
        this.words = words;
        this.res = res;
    }

    public synchronized void readParceString() throws IOException {
        if (countItemSources < sources.length) {

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
                Scanner scanner = new Scanner(inputStream);
                try {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] strArray = line.split("[\\.!?]");
                        for (String s : strArray) {
                            if (s.toLowerCase().contains(words[0].toLowerCase())) {
                                countOccurencies++;
                            }
                        }
                    }
                } finally {
                    scanner.close();
                }
                countItemSources++;
                notifyAll();
            }
        }
    }

    public synchronized void writeToFile() throws InterruptedException {
        wait();
        System.out.println("Write succesfull");
        System.out.println("Слово " + words[0] + " встречается в тексте " + sources[0] + countOccurencies + "раз\r\n");
    }
}
