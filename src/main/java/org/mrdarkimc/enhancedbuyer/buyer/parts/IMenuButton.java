package org.mrdarkimc.enhancedbuyer.buyer.parts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IMenuButton {
    ItemStack getStack();
    public void pressLeft(Player player);
    public void pressShiftLeft(Player player);
    public void pressRight(Player player);
    public void pressShiftRight(Player player);
}
