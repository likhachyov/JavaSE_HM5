package WorkWithFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static final String fileSeparator = System.getProperty("file.separator");
    private static String fileName;
    private static String targetFilePath = new String();

    public static void main(String[] args) throws InterruptedException, IOException {
        setParser();
//        Имитация работы пользователя:
//        1) Он указывает нужный файл.
        fileName =  "data-20190425-structure-20150209";
        Thread.sleep(1000);
        targetFilePath = "src/WorkWithFile/" + fileName + ".csv";
//        2) Создаёт маркерный файл, после которого начнёт работу парсер.
        Thread.sleep(1000);
        String relativePath = "src" + fileSeparator + "WorkWithFile" + fileSeparator + fileName + ".dam";
        File damFile = new File(relativePath);
        damFile.createNewFile();
        Thread.sleep(1000);
//        3) Удаляем маркерный файл, чтобы его не было при новом запуске программы.
        damFile.delete();
    }

//      Метод, настраивающий поток, в котором разбирается данный файл.
    private static void setParser() {
        Runnable parser = () -> {
            File f;
            String damFile;
//            Пока не появится маркерный файл, парсер ждёт.
            do {
                damFile = targetFilePath.replaceAll("\\..+$", ".dam");
                f = new File(damFile);
            } while (!f.exists());

            int [] top = parse(targetFilePath);
            System.out.println("ТОП регионов по кол-ву приставов: ");
            for (int i = 0; i < top.length; i++) {
                System.out.printf("%d место: регион №%d", i + 1, top[i]);
                System.out.println();
            }
        };
        Thread parserThread = new Thread(parser);
        parserThread.start();
    }

    private static int[] parse(String filePath) {
//        Создаём директорию "temp".
        File tempDir = new File("src" + fileSeparator + "WorkWithFile" + fileSeparator +"temp");
        createDir(tempDir);
//        Помещаем туда копию файла для разбора.
        File copyFile = new File("src/WorkWithFile/temp/" + fileName + ".csv");
        boolean res = copyFile(filePath, copyFile);

        List<FileEntry> list = parseFile(copyFile.getPath());
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        list.forEach((e)-> {
            if (hashMap.keySet().contains(e.getRegion())){
                hashMap.put(e.getRegion(), hashMap.get(e.getRegion()) + 1);
            }
            else
                hashMap.put(e.getRegion(), 1);
        });
//        Если разбор файла прошёл успешно, удаляем папку temp
        if (res)
            delDir(tempDir);
        return defineTop(hashMap);
    }

    private static int[] defineTop(HashMap<Integer, Integer> hashMap) {
        int[] top = {0,0,0,0,0};
        hashMap.forEach( (k,v) -> {
            for (int i = 0; i < top.length; i++) {
                if (top[i] != 0 && hashMap.get(top[i]) <= v){
                    top[i] = k;
                    break;
                }
                else if (top[i] == 0){
                    top[i] = k;
                }
            }
        });
        return top;
    }

    private static ArrayList<FileEntry> parseFile(String pathFile) {
        ArrayList<FileEntry> list = new ArrayList<>();
        Scanner scan = null;
        try{
            scan = new Scanner(new File(pathFile));
        }
        catch (Exception e) {
            System.out.println(e.getCause());
        }
        if (scan != null){
            String entryString = new String();
            int count = 0;
//            Пропускаем строку с именами столбцов
            scan.nextLine();
            while (scan.hasNext()){
                count++;
                entryString = scan.nextLine();
                String[] entryFields = entryString.split(",");
//                На случай неверной записи в файле
                try {
                    list.add(new FileEntry(Integer.valueOf(entryFields[0]), entryFields[1], entryFields[2], entryFields[3], entryFields[4], entryFields[4]));
                }
                catch (Exception e){
                    System.out.println(String.format("Wrong in the %d string: ",count) + entryString);
                    scan.close();
                }
            }
            scan.close();
        }
        return list;
    }

    private static boolean copyFile(String filePath, File copyFile) {
        try {
            Files.copy(new File(filePath).toPath(), copyFile.toPath());
            return true;
        }
        catch (IOException e) {
            System.out.println("File already exists.");
        }
        return false;
    }

    private static void createDir(File tempDir){
        if (!tempDir.exists()){
            tempDir.mkdir();
        }
        else{
            delDir(tempDir);
            tempDir.mkdir();
        }

    }

    private static void delDir(File tempDir) {
        File[] oldFiles = tempDir.listFiles();
        if (oldFiles == null){
            tempDir.delete();
            return;
        }
        for (File file: oldFiles) {
            file.delete();
        }
        tempDir.delete();
    }
}
