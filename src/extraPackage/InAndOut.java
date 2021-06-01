package extraPackage;

import database.Database;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InAndOut {
    public static boolean serialize (Database database)
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.puga"))){
            oos.writeObject(database);
            return true;
        }catch(IOException e)
        {
            return false;
        }
    }

    public static Database deserialize ()
    {
        Database database = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.puga")))
        {
            database = (Database) ois.readObject();

        }catch (ClassNotFoundException | IOException e)
        {
            //e.printStackTrace();
        }
        return database;
    }
}
