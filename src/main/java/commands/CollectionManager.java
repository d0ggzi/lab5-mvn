package commands;

import organizations.*;

import java.util.*;
import java.io.*;

/**
 * Класс выполнения команд, работы с коллекцией
 *
 * @author Max Laptev
 * @version 1.0
 */

public class CollectionManager {
    private LinkedHashSet<Organization> organizations;
    protected static HashMap<String, String> commands;
    private final Date initiazitionDate;
    final Script script = new Script();
    File workFile = null;


    {
        commands = new HashMap<>();
        this.initiazitionDate = new Date();
        commands.put("help", "Вывести справку по доступным командам.");
        commands.put("info", "Вывести в стандартный поток вывода информацию о коллекции.");
        commands.put("show", "Вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        commands.put("add {element}", "Добавить новый элемент в коллекцию.");
        commands.put("update id {element}", "Обновить значение элемента коллекции, id которого равен заданному.");
        commands.put("remove_by_id id", "Удалить элемент из коллекции по его id.");
        commands.put("clear", "Очистить коллекцию.");
        commands.put("save", "Сохранить коллекцию в файл.");
        commands.put("execute_script file_name", "Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же.");
        commands.put("exit", "Завершить программу (без сохранения в файл).");
        commands.put("add_if_min {element}", "Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции.");
        commands.put("remove_lower {element}", "Удалить из коллекции все элементы, превышающие заданный.");
        commands.put("history", "Вывести последние 5 команд.");
        commands.put("filter_contains_name name", "Вывести элементы, значение поля name которых содержит заданную подстроку.");
        commands.put("filter_greater_than_type type", "Вывести элементы, значение поля type которых больше заданного.");
        commands.put("print_field_descending_type", "Вывести значения поля type всех элементов в порядке убывания.");
    }

    public CollectionManager(String filepath){
        while (true){
            File file = new File(filepath);
            try {
                if (file.isFile()){
                    Read collection = new Read();
                    organizations = collection.reading(file);
                    workFile = file;
                    break;
                }
                throw new FileNotFoundException();
            }
            catch (FileNotFoundException e){
                if (!file.exists()){
                    System.out.println("Файл по указанному пути не существует");
                }else if (!file.canRead()){
                    System.out.println("У файла по указанному пути нет прав на чтение");
                }else if (file.isDirectory()){
                    System.out.println("Указанный путь не ведет к файлу");
                }
                System.out.println("Введите путь к файлу:");
                filepath = read();
            }
        }

    }

    public String read(){
        try{
            Scanner scan = new Scanner(System.in);
            return scan.nextLine();
        }catch (NoSuchElementException e){
            System.exit(1);
        }
        return null;
    }

    public void help(){
        System.out.println("Команды: ");
        for (String s: commands.keySet()){
            System.out.println(s + " - " + commands.get(s));
        }
    }

    public void info(){
        System.out.println("Тип коллекции - " + organizations.getClass());
        System.out.println("Дата инициализации - " + initiazitionDate);
        System.out.println("Количество элементов - " + organizations.size());
    }

    public void show(){
        if (organizations.isEmpty()){
            System.out.println("Коллекция пуста.");
        }
        else{
            for (Organization org: organizations){
                System.out.println(org.toString());
            }
        }
    }

    public Organization getNewOrg(){
        long id = (long) (Math.random() * 1000000);
        String addName;
        int addCoordX;
        int addCoordY;
        int addAnnualTurnover;
        long addEmployeesCount;
        String addZipCode;
        OrganizationType addType;
        long addLocationX;
        long addLocationY;
        int addLocationZ;

        while (true){
            System.out.println("Введите имя организации: ");
            addName = read();
            if (addName.equals("")){
                System.out.println("Вы ввели пустую строку, попробуйте снова.");
                continue;
            }
            break;
        }

        while (true){
            System.out.println("Введите координату x (>-900): ");
            try{
                addCoordX = Integer.parseInt(read());
                if (addCoordX <= -900){
                    System.out.println("Значение должно быть больше -900");
                    continue;
                }
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Вы ввели не число, попробуйте снова.");
            }
        }

        while (true){
            System.out.println("Введите координату y: ");
            try{
                addCoordY = Integer.parseInt(read());
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Вы ввели не число, попробуйте снова.");
            }
        }

        while (true){
            System.out.println("Введите ежегодный оборот ( в миллионах $ ): ");
            try{
                addAnnualTurnover = Integer.parseInt(read());
                if (addAnnualTurnover <= 0){
                    System.out.println("Ежегодный оборот должен быть больше 0");
                    continue;
                }
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Вы ввели не число, попробуйте снова.");
            }
        }


        while (true){
            System.out.println("Введите количество работников: ");
            try{
                addEmployeesCount = Long.parseLong(read());
                if (addEmployeesCount <= 0){
                    System.out.println("Количество работником должно быть больше 0");
                    continue;
                }
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Вы ввели не число, попробуйте снова.");
            }
        }

        while (true){
            System.out.println("Введите тип организации из списка - COMMERCIAL, TRUST, OPEN_JOINT_STOCK_COMPANY");
            try{
                addType = OrganizationType.valueOf(read().toUpperCase(Locale.ROOT));
                break;
            }
            catch (IllegalArgumentException e){
                System.out.println("Вы ввели неизвестный тип, попробуйте снова.");
            }
        }

        while (true){
            System.out.println("Введите zipcode: ");
            addZipCode = read();
            if (addZipCode.equals("")){
                System.out.println("Вы ввели пустую строку, попробуйте снова.");
                continue;
            }
            break;
        }

        while (true){
            System.out.println("Введите координату x месторасположения: ");
            try{
                addLocationX = Long.parseLong(read());
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Вы ввели не число, попробуйте снова.");
            }
        }


        while (true){
            System.out.println("Введите координату y месторасположения: ");
            try{
                addLocationY = Long.parseLong(read());
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Вы ввели не число, попробуйте снова.");
            }
        }

        while (true){
            System.out.println("Введите координату z месторасположения: ");
            try{
                addLocationZ = Integer.parseInt(read());
                break;
            }
            catch (NumberFormatException e){
                System.out.println("Вы ввели не число, попробуйте снова.");
            }
        }

        Organization addOrg = new Organization(id, addName, new Coordinates(addCoordX, addCoordY), addAnnualTurnover, addEmployeesCount, addType, new Address(addZipCode, new Location(addLocationX, addLocationY, addLocationZ)));
        return addOrg;
    }

    public void add(){
        Organization newOrg = getNewOrg();
        organizations.add(newOrg);
        System.out.println("Организация успешно добавлена.");
    }

    public void update(long id){
        boolean flag = false;
        LinkedHashSet<Organization> oldOrg = new LinkedHashSet<>(organizations);
        for (Organization el: oldOrg){
            if (el.getId() == id){
                flag = true;
                Organization updatedOrg = getNewOrg();
                el.setName(updatedOrg.getName());
                el.setCoordinates(updatedOrg.getCoordinates());
                el.setAnnualTurnover(updatedOrg.getAnnualTurnover());
                el.setEmployeesCount(updatedOrg.getEmployeesCount());
                el.setType(updatedOrg.getType());
                el.setPostalAddress(updatedOrg.getPostalAddress());
            }
        }
        if (flag){
            System.out.println("Организация успешно обновлена.");
        }else{
            System.out.println("Не существует организации с id " + id);
        }
    }

    public void remove_by_id(long id){
        boolean flag = false;
        LinkedHashSet<Organization> oldOrg = new LinkedHashSet<>(organizations);
        for (Organization el: oldOrg){
            if (el.getId() == id){
                flag = true;
                organizations.remove(el);
            }
        }
        if (flag) {
            System.out.println("Организация с id " + id + " успешно удалена.");
        }else{
            System.out.println("Не существует организации с id " + id);
        }
    }

    public void clear(){
        if (organizations.isEmpty()){
            System.out.println("Коллекция уже пуста.");
        }
        else{
            organizations.clear();
            System.out.println("Коллекция очищена.");
        }
    }

    public void save(){
        StringBuilder toXmlFile = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        for (Organization el : organizations){
            toXmlFile.append("<organization>\n");
            toXmlFile.append(el.toXmlFormat());
            toXmlFile.append("</organization>\n");
        }

        try {
            FileWriter fileWriter = new FileWriter(workFile);
            fileWriter.write(toXmlFile.toString());
            fileWriter.flush();
            System.out.println("Коллекция успешно сохранена.");
        } catch (IOException e) {
            if (!workFile.canWrite()) {
                System.out.println("У файла нет доступа на запись.");
            }
            System.out.println("Не удалось сохранить коллекцию в файл.");
        }
    }

    public void execute_script(String filepath, CollectionManager manager){
        System.out.println("Запуск скрипта из файла: " + filepath);
        script.Scripting(filepath, manager);
    }

    public void add_if_min(){
        Organization newOrg = getNewOrg();
        int minAnnualTurnover = 999999999;
        for (Organization el: organizations){
            if (el.getAnnualTurnover() < minAnnualTurnover){
                minAnnualTurnover = el.getAnnualTurnover();
            }
        }
        if (newOrg.getAnnualTurnover() < minAnnualTurnover){
            organizations.add(newOrg);
            System.out.println("Организация успешно добавлена.");
        }
        else {
            System.out.println(minAnnualTurnover);
            System.out.println(newOrg.getAnnualTurnover());
            System.out.println("Организация не была добавлена.");
        }
    }

    public void remove_lower(){
        Organization newOrg = getNewOrg();
        int lowerAnnualTurnover = newOrg.getAnnualTurnover();
        LinkedList<String> removed = new LinkedList<>();
        LinkedHashSet<Organization> oldOrg = new LinkedHashSet<>(organizations);
        for (Organization el : oldOrg) {
            if (el.getAnnualTurnover() < lowerAnnualTurnover){
                removed.add(el.getName());
                organizations.remove(el);
            }
        }
        System.out.println("Удалены организации: " + removed);
    }

    public void filter_contains_name(String name){
        boolean flag = true;
        for (Organization el : organizations){
            if (el.getName().equals(name)){
                flag = false;
                System.out.println(el);
            }
        }
        if (flag){
            System.out.println("Извините, таких элементов нет");
        }
    }

    public void filter_greater_than_type(int inputAnnualTurnover){
        boolean flag = true;
        for (Organization el : organizations){
            if (el.getAnnualTurnover() > inputAnnualTurnover){
                flag = false;
                System.out.println(el);
            }
        }
        if (flag){
            System.out.println("Извините, таких элементов нет");
        }
    }

    public void print_field_descending_type(){
        boolean flag = true;
        TreeSet<Organization> sortedSet = new TreeSet<>(organizations);
        for (Organization el : sortedSet){
            flag = false;
            System.out.println(el);
        }
        if (flag){
            System.out.println("Извините, таких элементов нет");
        }
    }

}
