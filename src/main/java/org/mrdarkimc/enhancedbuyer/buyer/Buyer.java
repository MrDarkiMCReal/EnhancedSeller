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

public class Buyer implements Reloadable {
    private String displayName;

    private String configID;
    private Map<Integer, IMenuButton> contents;
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
    public void openByPlugin(Player player){
        Map<Integer, IMenuButton> combinedContents = new HashMap<>(contents);
        combinedContents.putAll(extraContents);
        updateItemInfo(player);
        new ShopView(this,combinedContents,shopSize).open(player);
    }
    public void updateItemInfo(){
        contents.forEach((k,v) -> v.updateInfo());
        extraContents.forEach((k,v) -> v.updateInfo());
    }
    public void updateItemInfo(Player player){
        contents.forEach((k,v) -> v.updateInfo(player));
        extraContents.forEach((k,v) -> v.updateInfo(player));
    }
    public void updateItemPrice(){
        contents.forEach((k,v) -> v.updatePrice());
        extraContents.forEach((k,v) -> v.updatePrice());
    }
    public void openByCitizens(Player player){
        updateItemInfo(player);
        new ShopView(this,contents,shopSize).open(player);
    }


    public String getDisplayName() {
        return displayName;
    }


    @Override
    public void reload() {

    }
}
