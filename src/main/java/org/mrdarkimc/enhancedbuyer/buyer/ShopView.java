package org.mrdarkimc.enhancedbuyer.buyer;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.mrdarkimc.enhancedbuyer.EnhancedBuyer;
import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IMenuButton;

import java.util.Map;

public class ShopView implements InventoryHolder {
    public Buyer buyer;
    public Inventory inventory;
    public Map<Integer, IMenuButton> contents;


    public ShopView(Buyer buyer, Map<Integer, IMenuButton> contents, int size) {
        this.buyer = buyer;
        this.contents = contents;
        this.inventory = EnhancedBuyer.getInstance().getServer().createInventory(this,size,buyer.getDisplayName());
        fill();
    }
    public void fill(){
        contents.forEach((k,v) -> inventory.setItem(k,v.getStack()));
    }
    public IMenuButton getBySlot(int slot){
        return contents.get(slot);
    }
    public void open(Player player){
    player.openInventory(getInventory());
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
