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


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;


public class Main {

    static final Logger myLogger = Logger.getLogger(Main.class);

    public static String[] getFIleNames() {
        File folder = new File("E:\\temp\\testSet2\\");
        File[] listOfFiles = folder.listFiles();
        List<String> results = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                results.add("E:\\temp\\testSet2\\" + listOfFiles[i].getName());
            }
        }
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        PropertyConfigurator.configure("src/main/resources/log4j.properties");
        final int QTY_THREADS = 20;
        String[] words = new String[7];
        Queue<ArrayList<String>> queue = new ConcurrentLinkedQueue<>();
        FindingResource findingResource = new FindingResource(queue);

        String[] source = getFIleNames();
        words[0] = "computer";
        words[1] = "programmer";
        words[2] = "construction";
        words[3] = "getter";
        words[4] = "setter";
        words[5] = "javacources";
        words[6] = "test";
        myLogger.info("Start finding words");
        long startTime = System.currentTimeMillis();
        final ExecutorService taskExecutor = Executors.newFixedThreadPool(QTY_THREADS);
        for (int i = 0; i < source.length; i++) {
            Future<InputStream> is = taskExecutor.submit(new GetStream(source, i));
            Future<StringBuilder> sb = taskExecutor.submit(new ReadStream(is.get()));
            taskExecutor.submit(new SearchWord(findingResource, sb.get(), words));
        }
        taskExecutor.shutdown();
        try {
            taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        WriteToFile writeToFile = new WriteToFile("result.txt", findingResource);
        writeToFile.run();
        long endTime = System.currentTimeMillis() - startTime;
        myLogger.info("\r\nЗатрачено времени: " + endTime / 1000 + "секунд" + "\r\n");
        myLogger.info("The End");
    }
}
