package org.mrdarkimc.enhancedbuyer.objectmanager;

import org.mrdarkimc.enhancedbuyer.buyer.Buyer;
import org.mrdarkimc.enhancedbuyer.objectmanager.interfaces.IManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerManager implements IManager {
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
    public void deserealize(String id) {

    }

    @Override
    public void deserealize() {

    }
    public static void refresh(){
        buyers.forEach((k,v) -> v.updateItemPrice());
    }

    @Override
    public Object getObjectList() {
        return buyers;
    }

    @Override
    public Object addObject() {
        return null;
    }
}
