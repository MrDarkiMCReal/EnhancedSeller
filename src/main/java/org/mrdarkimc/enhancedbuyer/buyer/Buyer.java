package org.mrdarkimc.enhancedbuyer.buyer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.objectManager.interfaces.Reloadable;
import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IMenuButton;
import org.mrdarkimc.enhancedbuyer.objectmanager.ObjectManager;

import java.util.HashMap;
import java.util.Map;

public class Buyer implements InventoryHolder, Reloadable {
    private String displayName;

    private String configID;
    private Map<Integer, IMenuButton> contents;
    private Inventory inventory;
    private Map<Integer, IMenuButton> extraContents;
    private int shopSize;

    // Конструктор
    public Buyer(String id, int size, String displayName, Map<Integer, IMenuButton> contents, Map<Integer, IMenuButton> extraContents) {
        this.shopSize = size;
        this.configID = id;
        this.displayName = Utils.translateHex(displayName);
        this.contents = contents;
        //this.inventory = Bukkit.createInventory(this,54,displayName);
        this.extraContents = extraContents;
        //fillInventory();

    }
    public String getConfigID() {
        return configID;
    }
    public void fillInventory(){
        contents.forEach((k,v) -> inventory.setItem(k,v.getStack()));
    }
    public void fillDefaults(){

    }
    public void openByPlugin(Player player){
        Map<Integer, IMenuButton> combinedContents = new HashMap<>(contents);
        combinedContents.putAll(extraContents);
        new ShopView(this,combinedContents,shopSize).open(player);
    }
    public void updateItemInfo(){
        contents.forEach((k,v) -> v.updateInfo());
        extraContents.forEach((k,v) -> v.updateInfo());
    }
    public void updateItemPrice(){
        contents.forEach((k,v) -> v.updatePrice());
        extraContents.forEach((k,v) -> v.updatePrice());
    }
    public void openByCitizens(Player player){
        new ShopView(this,contents,shopSize).open(player);
    }

    public void deserealize(){
        ObjectManager.deserializeBuyer(configID); //todo wtf is this. текущий обьект будет удален GC
    }

    public String getDisplayName() {
        return displayName;
    }

public IMenuButton getBySlot(int slot){
        return contents.get(slot);
}
    private Map<Integer, IMenuButton> getContents() {
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
