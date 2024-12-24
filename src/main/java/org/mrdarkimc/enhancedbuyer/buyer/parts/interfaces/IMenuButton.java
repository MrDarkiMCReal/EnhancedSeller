package org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IMenuButton {
    ItemStack getStack();
    public void pressLeft(Player player);
    public void pressShiftLeft(Player player);
    public void pressRight(Player player);
    public void pressShiftRight(Player player);
    void updateInfo();
    void updateInfo(Player player);
    void updatePrice();
}
