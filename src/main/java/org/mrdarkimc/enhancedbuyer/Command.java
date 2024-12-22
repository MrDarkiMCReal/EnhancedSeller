package org.mrdarkimc.enhancedbuyer;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.enhancedbuyer.objectmanager.BuyerManager;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        if (strings.length < 1) {
            commandSender.sendMessage(Utils.translateHex("   "));
            commandSender.sendMessage(Utils.translateHex("                   &#D40092MrDarkiMC's EnhancedBuyer"));
            commandSender.sendMessage(Utils.translateHex("   "));
            commandSender.sendMessage(Utils.translateHex("    &#D40092/" + command.getName() + " open <скупщик> <игрок>&r&7 - открыть меню без оверрайдов"));
            commandSender.sendMessage(Utils.translateHex("    &#D40092/" + command.getName() + " openCitizens <скупщик> <игрок>&r&7 - открыть меню с оверрайдами"));
            commandSender.sendMessage(Utils.translateHex("    &#D40092/" + command.getName() + " list&r&7 - список скупщиков"));
            commandSender.sendMessage(Utils.translateHex("    &#D40092/" + command.getName() + " updateInfo <скупщик>&r&7 - Обновить плейсхолдеры"));
            commandSender.sendMessage(Utils.translateHex("    &#D40092/" + command.getName() + " updatePrice <скупщик>&r&7 - обновить цены и ПХ"));
            commandSender.sendMessage(Utils.translateHex("   "));
            return true;
        }else {
            if (!commandSender.hasPermission("enhancedBuyer.admin"))
                return true;
            switch (strings[0]){
                case "open":
                    if (strings.length < 2)
                        return true;
                    Player player = Bukkit.getPlayer(strings[2]);
                    if (player!=null) {
                        BuyerManager.getBuyerByName(strings[1]).openByPlugin(player);
                        return true;
                    }
                commandSender.sendMessage("[EnhancedSeller] Игрок не найден");
                case "openCitizens":
                    if (strings.length < 2)
                        return true;
                    Player player2 = Bukkit.getPlayer(strings[2]);
                    if (player2!=null) {
                        BuyerManager.getBuyerByName(strings[1]).openByCitizens(player2);
                        return true;
                    }
                    commandSender.sendMessage("[EnhancedSeller] Игрок не найден");
                    return true;
                case "updateInfo":
                    BuyerManager.getBuyerByName(strings[1]).updateItemInfo();
                    return true;
                case "updatePrice":
                    BuyerManager.getBuyerByName(strings[1]).updateItemPrice();
                    return true;
            }
        }
        return true;
    }
}
