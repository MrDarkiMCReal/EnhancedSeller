package org.mrdarkimc.enhancedbuyer.objectmanager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.enhancedbuyer.EnhancedBuyer;
import org.mrdarkimc.enhancedbuyer.buyer.Buyer;
import org.mrdarkimc.enhancedbuyer.buyer.parts.Price;
import org.mrdarkimc.enhancedbuyer.buyer.parts.PurchaseItem;
import org.mrdarkimc.enhancedbuyer.objectmanager.interfaces.IManager;

import java.util.*;

public class ObjectManager {
    private ObjectManager() {
    }
    public static ObjectManager initialize(){
        ObjectManager manager = new ObjectManager();
        manager.deserialize();
        return manager;
    }
    List<Buyer> buyers = new ArrayList<>();

    List<IManager> managers = new ArrayList<>();
    public BuyerManager buyerManager = new BuyerManager();
    public IManager itemManager = new ItemManager();
    public PriceManager priceManager = new PriceManager();
//    {
//        managers.add(buyerManager); //сначала десереализируем скупщика
//        managers.add(itemManager); //потом предметы
//        managers.add(priceManager); //а потом уже цену
//    }

    public void deserialize(){
    FileConfiguration config = EnhancedBuyer.config.get();
    ConfigurationSection section = config.getConfigurationSection("menus.categories");
    Set<String> set = section.getKeys(false);


    for(String buyerKey :set){

        buyers.add(deserializeBuyer(buyerKey));
        Bukkit.getLogger().info("[EnhancedBuyer] зарегистрирован скупщик: " + buyerKey);
    }
        Bukkit.getLogger().info("[EnhancedBuyer] Успешно зарегистрировано: " + buyers.size() + " скупщиков.");
}

public static Buyer deserializeBuyer(String ID){
    FileConfiguration config = EnhancedBuyer.config.get();
    ConfigurationSection section = config.getConfigurationSection("menus.categories");
    ConfigurationSection buyerSection = section.getConfigurationSection(ID);
    String displayName = buyerSection.getString("displayName", "Unnamed");

    Map<Integer, PurchaseItem> contents = new HashMap<>();
    ConfigurationSection contentsSection = buyerSection.getConfigurationSection("contents");

    for (String itemKey : contentsSection.getKeys(false)) {
        ConfigurationSection itemSection = contentsSection.getConfigurationSection(itemKey);

        String itemId = itemSection.getString("id");
        String displayNameItem = itemSection.getString("displayname");
        List<String> lore = itemSection.getStringList("lore");
        int slotID = itemSection.getInt("slotID");

        ConfigurationSection priceSection = itemSection.getConfigurationSection("price");

        Price price = new Price(
                priceSection.getInt("default",-1),
                priceSection.getInt("min",-1),
                priceSection.getInt("max",-1),
                priceSection.getInt("increaseGradation",-1),
                priceSection.getInt("decreaseGradation",-1)
        );

        Material material = Material.getMaterial(itemId.toUpperCase());
        if (material == null) {
            Bukkit.getLogger().warning(" ");
            Bukkit.getLogger().warning("[EnhancedBuyer] Ошибка. Не найден предмет: "+ itemKey + " для скупщика: " + ID);
            Bukkit.getLogger().warning("[EnhancedBuyer] Пропускаю предмет: "+ itemKey + " для скупщика: " + ID);
            Bukkit.getLogger().warning(" ");
            continue;
        }
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(Utils.translateHex(displayNameItem));
            lore.replaceAll(Utils::translateHex);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
        }

        PurchaseItem purchaseItem = new PurchaseItem(itemStack, price);

        contents.put(slotID, purchaseItem);
    }
 return new Buyer(ID,displayName, contents);

}
public List<Buyer> getBuyers(){
        return buyers;
}
}
