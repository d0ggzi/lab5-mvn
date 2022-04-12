import commands.CollectionManager;

import java.io.*;
import java.util.Comparator;


public class Main {

    public static void main(String[] args){
        System.out.println("Привет! Напиши help, чтобы узнать функционал");
        String filepath = "";
        try{
            filepath = args[0];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Неправильный аргумент командной строки");
        }
        MainApp mainapp = new MainApp(new CollectionManager(filepath));
        // MainApp mainapp = new MainApp(new CollectionManager("test.xml")); //"C:\\Users\\maks-\\GoogleDrive\\programming\\java\\lab5_all\\lab5.1\\src\\test.xml"
        mainapp.start();
    }
}


