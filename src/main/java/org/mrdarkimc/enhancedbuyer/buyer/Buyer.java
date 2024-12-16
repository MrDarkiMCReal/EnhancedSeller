package org.mrdarkimc.enhancedbuyer.buyer;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.objectManager.interfaces.Reloadable;
import org.mrdarkimc.enhancedbuyer.buyer.parts.PurchaseItem;
import org.mrdarkimc.enhancedbuyer.objectmanager.ObjectManager;

import java.util.Map;

public class Buyer implements InventoryHolder, Reloadable {
    private String displayName;

    public String getConfigID() {
        return configID;
    }

    private String configID;
    private Map<Integer, PurchaseItem> contents;
    private Inventory inventory;

    // Конструктор
    public Buyer(String id, String displayName, Map<Integer, PurchaseItem> contents) {
        this.configID = id;
        this.displayName = Utils.translateHex(displayName);
        this.contents = contents;
    }
    public void deserealize(){
        ObjectManager.deserializeBuyer(configID);
    }

    // Геттеры и Сеттеры
    public String getDisplayName() {
        return displayName;
    }

public PurchaseItem getBySlot(int slot){
        return contents.get(slot);
}
    private Map<Integer, PurchaseItem> getContents() {
        return contents;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public void reload() {

    }
}
