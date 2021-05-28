package extraPackage;

import database.storage.*;
import java.util.List;

public class OpeAndCond {
    public static int atoi(String str) {
        // Start typing your Java solution below
        // DO NOT write main() function
        // string start with -
        // string is overflow the Integer.MAX_VALUE
        if(str.length() == 0) return 0;

        int right = str.length() - 1;
        int res = 0;
        int digit = 1;

        if(right == 0){
            return str.charAt(right) - '0';
        }

        while(right >= 0){
            char c = str.charAt(right);
            //System.out.println(res);
            if(c == '+') {
                right--;
            } else if (c == '-'){
                res *= -1;
                right--;
            } else if (('0' <= c) && (c <= '9')) {
                //System.out.println(c);
                res += ((c - '0') * digit);
                digit *= 10;
                right--;
            } else {
                right--;
            }
        }

        return res;
    }

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