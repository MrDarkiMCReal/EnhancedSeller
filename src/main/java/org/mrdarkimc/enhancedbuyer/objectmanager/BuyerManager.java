package org.mrdarkimc.enhancedbuyer.objectmanager;

import org.mrdarkimc.enhancedbuyer.buyer.Buyer;

import java.util.HashMap;
import java.util.Map;

public class BuyerManager {
    private static Map<String,Buyer> buyers = new HashMap<>();
    public static void add(Buyer b){
        buyers.put(b.getConfigID(),b);
    }
    public static int size(){
        return buyers.size();
    }
    public static Buyer getBuyerByName(String id){
        return buyers.get(id);
    }
public static String getContents(){
        return buyers.keySet().toString();
}

    public static void refresh(){
        buyers.forEach((k,v) -> v.updateItemPrice());
    }


}
