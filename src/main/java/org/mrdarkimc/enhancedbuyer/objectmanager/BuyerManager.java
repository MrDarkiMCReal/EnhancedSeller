package org.mrdarkimc.enhancedbuyer.objectmanager;

import org.mrdarkimc.enhancedbuyer.buyer.Buyer;
import org.mrdarkimc.enhancedbuyer.objectmanager.interfaces.IManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyerManager implements IManager {
    Map<String,Buyer> buyers = new HashMap<>();
    public void add(Buyer b){
        buyers.put(b.getConfigID(),b);
    }
    public Buyer getBuyerByName(String id){
        return buyers.get(id);
    }
    public void deserealize(String id) {

    }

    @Override
    public Object getObjectList() {
        return buyers;
    }
}
