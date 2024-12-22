package org.mrdarkimc.enhancedbuyer.buyer.parts;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.prefixHandler.Prefix;
import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IMenuButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DecorativeItem implements IMenuButton {
    private ItemStack displayItem;
    private String displayName;
    private List<String> lore;
    private List<String> actions;

    public DecorativeItem(ItemStack itemStack, String displayName, List<String> lore, List<String> actions) {
        this.displayItem = itemStack;
        this.displayName = displayName;
        //lore.replaceAll(Utils::translateHex);
        this.lore = lore;
        this.actions = actions;
        updateStack();
    }

    private void updateStack() {
        ItemMeta meta = displayItem.getItemMeta();
        meta.setDisplayName(displayName); //sets default value to store data
        meta.setLore(lore); //sets default value to store data

        meta.setDisplayName(Utils.translateHex(meta.getDisplayName()));
            if (lore!=null) {
                List<String> newLore = new ArrayList<>(lore);
                newLore.replaceAll(Utils::translateHex);

                meta.setLore(newLore);
            }


        displayItem.setItemMeta(meta);
    }

    @Override
    public ItemStack getStack() {
        return displayItem;
    }

    public void action(Player player) {
        if (actions == null) {
            return;
        }
        if (actions.isEmpty())
            return;
        actions.forEach(text -> Prefix.handle(player, text, Map.of("{player}", player.getName())));
    }

    @Override
    public void pressLeft(Player player) {
        action(player);

    }

    @Override
    public void pressShiftLeft(Player player) {
        action(player);
    }

    @Override
    public void pressRight(Player player) {
        action(player);
    }

    @Override
    public void pressShiftRight(Player player) {
        action(player);
    }

    @Override
    public void updateInfo() {

    }

    @Override
    public void updatePrice() {

    }
}
