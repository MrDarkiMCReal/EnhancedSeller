package org.mrdarkimc.enhancedbuyer.eventhandlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.mrdarkimc.enhancedbuyer.buyer.ShopView;
import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IMenuButton;

public class MenuListener implements Listener {
    @EventHandler
    void onClick(InventoryClickEvent e){
        if (e.getInventory().getHolder() instanceof ShopView shop){
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            IMenuButton button = shop.getBySlot(e.getSlot());
            if (e.getCurrentItem()==null)
                return;
            if (button.getStack().equals(clicked)) {
                Player player = (Player) e.getWhoClicked();
                    switch (e.getClick()) {
                        case LEFT -> button.pressLeft(player);
                        case RIGHT -> button.pressRight(player);
                        case SHIFT_LEFT -> button.pressShiftLeft(player);
                        case SHIFT_RIGHT -> button.pressShiftRight(player);
                        //case MIDDLE -> button.addToAutoBuy
                    }
                    //purchaseItem.buyFromPlayer((Player) e.getWhoClicked());
            }
        }
    }
}
