package extraPackage;

import database.storage.Enty;

import java.util.List;

public class DBCheck {
    public static int existThisIdIn(int id, List<? extends Enty> enties){
        int cant = enties.size();
        for(int i = 0 ; i < cant ; i++)
        {
            if(enties.get(i).getId() == id)
                return i;
        }

        return -1;
    }

    public static int existThisNameIn(String name, List<? extends  Enty> enties){
        int cant = enties.size();
        for(int i = 0 ; i < cant ; i++)
        {
            if(name.toLowerCase().equals(enties.get(i).getName().toLowerCase()))
                return i;
        }
        return -1;
    }

    public static int maxId(List <? extends Enty> enties){
        int num = 0;
        for(Enty enty: enties){
            if(enty.getId() > num)
                num = enty.getId();
        }

        return num;
    }
}
