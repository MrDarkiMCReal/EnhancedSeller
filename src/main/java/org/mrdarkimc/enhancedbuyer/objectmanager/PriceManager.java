package org.mrdarkimc.enhancedbuyer.objectmanager;

import org.mrdarkimc.enhancedbuyer.buyer.parts.Price;
import org.mrdarkimc.enhancedbuyer.buyer.parts.PurchaseItem;
import org.mrdarkimc.enhancedbuyer.objectmanager.interfaces.IManager;

import java.util.HashMap;
import java.util.Map;

public class PriceManager implements IManager {
    private Map<PurchaseItem, Price> map = new HashMap();
    private Map<PurchaseItem, Integer> salesNumber = new HashMap<>();
    public Price getPriceByItem(PurchaseItem item){
        return map.get(item);
    }
    public void addSale(PurchaseItem purchaseItem){
        if (!salesNumber.containsKey(purchaseItem)){
            salesNumber.put(purchaseItem,1);
        }else{
            int currentNum = salesNumber.get(purchaseItem);
            salesNumber.replace(purchaseItem,currentNum+1);
        }

    }
    public void updatePurchases(){
    //map.k
    }


    @Override
    public void deserealize() {

    }

    @Override
    public Object getObjectList() {
        return null;
    }

    @Override
    public Object addObject() {
        return null;
    }
}
