package Reflection;

import Reflection.Examples.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();
        list.add(new Man("John", 39, 50, 150, "Муж"));
        list.add(new Stone("Green", 200, 2,"Oval"));
        list.add(new Tree("Willow", 30, 6));

        StringBuilder res = new StringBuilder();
        list.forEach((o -> res.append(getWritables(o))));
        try (
            FileWriter fileWriter = new FileWriter(new File("src/Reflection/output.txt"))) {
            fileWriter.append(res.toString());
            System.out.println("См. файл output.txt");
        } catch (IOException e) {
            System.out.println(e.getCause());
        }
    }

    private static String getWritables(Object o) {
        Class obj = o.getClass();
        StringBuilder res = new StringBuilder();
        res.append(obj.getName() + ": ");
        Field[] fields = obj.getDeclaredFields();
        for (Field field : fields) {
            List<Annotation> annotations = Arrays.asList(field.getAnnotations());
            if(annotations.size() > 0 && annotations.get(0).toString().equals("@Reflection.Writable()")) {
                field.setAccessible(true);
                try {
                    res.append(field.get(o) + " ");

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if(field.getType().toString().equals("int"))
                    res.append("years");
            }
        }
        res.append(System.lineSeparator());
        return res.toString();
    }


}
