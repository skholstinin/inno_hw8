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
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class getOccurencies {
    //private static int cntIteration=0;
    private static int currentItemWords = 0;
    private int currentItemSources = 0;
    private String[] sources;
    private String[] words;
    private String res;
    private Map<String, ArrayList<String>> findingHashMap = new ConcurrentHashMap<>();
    private int count = 0;
    private static int countOccurencies = 0;
    private static int result = 2;

    public getOccurencies(String[] sources, String[] words, String res) {
        this.sources = sources;
        this.words = words;
        this.res = res;
    }

    public void setCurrentItemSources(int currentItemSources) {
        this.currentItemSources = currentItemSources;
    }


    public synchronized boolean readParceString() throws IOException {
        if (currentItemSources < sources.length) {

            if (!sources[currentItemSources].isEmpty()) {

                InputStream inputStream;
                if ((sources[currentItemSources].regionMatches(true, 0, "ftp", 0, 3)) ||
                        (sources[currentItemSources].regionMatches(true, 0, "www", 0, 3)) ||
                        (sources[currentItemSources].regionMatches(true, 0, "http", 0, 4))) {
                    URL url = new URL(sources[currentItemSources]);
                    inputStream = url.openStream();
                } else {
                    inputStream = new FileInputStream(new File(sources[currentItemSources]));
                }

                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String lineString = null;
                    String spliter = null;
                    String[] StrArray = null;

                    String newLine = System.getProperty("line.separator");
                    StringBuilder result = new StringBuilder();
                    String line;
                    boolean flag = false;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(flag ? newLine : "").append(line);
                        flag = true;
                    }
                    bufferedReader.close();
                    spliter += result;
                    StrArray = spliter.split("[\\.!?]");
                    for (String w : words) {
                        ArrayList<String> findingString = new ArrayList<>();
                        for (String s : StrArray) {
                            if (s.toLowerCase().contains(w.toLowerCase()))
                                findingString.add(s);
                        }
                        findingHashMap.put(w, findingString);
                    }
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }
                currentItemSources += currentItemSources;
            }
            System.out.println("Source is done");
            notifyAll();
            return true;
        } else {
            return false;
        }
    }

    public synchronized void writeToFile() throws InterruptedException {
        wait();

        File newFile = new File(res);
        if (newFile.isFile() && newFile.exists()) {
            newFile.delete();
        }
        try (BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(newFile, true))) {
            System.out.println("File is created");
            for (Map.Entry<String, ArrayList<String>> element : findingHashMap.entrySet()) {

                bufferWriter.write("\r\nСлово " + element.getKey() + " содержится в предложениях: \r\n");
                for (String s : element.getValue()) {
                    bufferWriter.write(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
