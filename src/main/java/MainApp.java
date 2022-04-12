import commands.CollectionManager;
import commands.Command;
import exceptions.UnknownCommand;

import java.util.*;

public class MainApp  {

    final CollectionManager manager;
    final Command cmd = new Command();

    public MainApp(CollectionManager manager){
        this.manager = manager;
    }

    public void start() {
        Scanner scan = new Scanner(System.in);
        while (true){
            try{
                String[] userCommand = scan.nextLine().trim().split(" ", 2);
                cmd.Command(userCommand, manager);
            }catch (UnknownCommand e){
                System.out.println(e);
            }catch (NoSuchElementException e){
                System.exit(1);
            }
        }
    }
}
