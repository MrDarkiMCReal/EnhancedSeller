package org.mrdarkimc.enhancedbuyer.objectmanager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.mrdarkimc.enhancedbuyer.EnhancedBuyer;
import org.mrdarkimc.enhancedbuyer.buyer.Buyer;
import org.mrdarkimc.enhancedbuyer.buyer.parts.DecorativeItem;
import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IMenuButton;
import org.mrdarkimc.enhancedbuyer.buyer.parts.Price;
import org.mrdarkimc.enhancedbuyer.buyer.parts.PurchaseItem;

import java.util.*;

public class ObjectManager {
    private ObjectManager() {
    }
    public static ObjectManager initialize(){
        ObjectManager manager = new ObjectManager();
        manager.deserialize();
        return manager;
    }
//    List<Buyer> buyers = new ArrayList<>();

//    List<IManager> managers = new ArrayList<>();
//    public BuyerManager buyerManager = new BuyerManager();
//    public IManager itemManager = new ItemManager();
//    public PriceManager priceManager = new PriceManager();
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
        BuyerManager.add(deserializeBuyer(buyerKey));
        Bukkit.getLogger().info("[EnhancedBuyer] зарегистрирован скупщик: " + buyerKey);
    }
        Bukkit.getLogger().info("[EnhancedBuyer] Успешно зарегистрировано: " + BuyerManager.size() + " скупщиков.");
        Bukkit.getLogger().info("[EnhancedBuyer] Запускаю таск на обновление скупщиков");
        EnhancedBuyer.cycleManager.startRefreshTask();

}
public static Price deserializePrice(Map<?, ?> priceMap){
        if (priceMap==null)
            return null;
        int increaseIfSalesUnder = priceMap.get("increaseIfSalesUnder")!=null ? (int)priceMap.get("increaseIfSalesUnder") : -1;
        int decreaseIfSalesUpwards =  priceMap.get("decreaseIfSalesUpwards")!=null ? (int)priceMap.get("decreaseIfSalesUpwards") : -1;
        int min = priceMap.get("min")!=null ? (int) priceMap.get("min") : -1;
        int max = priceMap.get("max")!=null ? (int)priceMap.get("max") : -1;
        int increaseGradation =  priceMap.get("increaseGradation")!=null ? (int)priceMap.get("increaseGradation") : -1;
        int decreaseGradation =  priceMap.get("decreaseGradation")!=null ? (int)priceMap.get("decreaseGradation") : -1;
        int default2 =  priceMap.get("default")!=null ? (int)priceMap.get("default") : -1;
        return default2 > 0 ? new Price(default2,min,max,increaseGradation,decreaseGradation,increaseIfSalesUnder,decreaseIfSalesUpwards) : null;
}
public static Map<Integer, IMenuButton> deserializeButtons(List<Map<?, ?>> contentsSection){
    Map<Integer, IMenuButton> contents = new HashMap<>();
    for (Map<?, ?> map : contentsSection) {
        Material material = Material.valueOf( ((String)map.get("id")).toUpperCase() );
        String displayName2 = (String)map.get("displayname");
        int slotID2 =  (int) map.get("slotID");
        List<String> lore2 = (List<String>) map.get("lore");

        Map<?, ?> priceMap = null;
        if (map.get("price") instanceof Map<?,?>){
            priceMap = (Map<?, ?>) map.get("price");
        }
        Price price2 = deserializePrice(priceMap);


        List<String> success_commands = null;
        if (map.get("success_commands") instanceof List<?>){
            success_commands = (List<String>) map.get("success_commands");
        }
        IMenuButton button;
        if (price2 == null){
            button = new DecorativeItem(new ItemStack(material),displayName2,lore2,success_commands);
        }else{
            button = new PurchaseItem(new ItemStack(material),price2,displayName2,lore2,success_commands);
        }
        contents.put(slotID2,button);
    }
    return contents;
}

public static Buyer deserializeBuyer(String ID) {
    FileConfiguration config = EnhancedBuyer.config.get();
    ConfigurationSection section = config.getConfigurationSection("menus.categories");
    ConfigurationSection buyerSection = section.getConfigurationSection(ID);
    String displayName = buyerSection.getString("displayName", "Unnamed");
    int size = buyerSection.getInt("size",54);

    Map<Integer, IMenuButton> contents = deserializeButtons(buyerSection.getMapList("contents"));
    Map<Integer, IMenuButton> extraContents = deserializeButtons(buyerSection.getMapList("citizensOverride"));
    return new Buyer(ID,size,displayName, contents,extraContents);
}







//    for (String itemKey : contentsSection.getKeys(false)) {
//        ConfigurationSection itemSection = contentsSection.getConfigurationSection(itemKey);
//
//        String itemId = itemSection.getString("id");
//        String displayNameItem = itemSection.getString("displayname");
//        List<String> lore = itemSection.getStringList("lore");
//        int slotID = itemSection.getInt("slotID");
//
//        ConfigurationSection priceSection = itemSection.getConfigurationSection("price");
//
//        Price price = new Price(
//                priceSection.getInt("default",-1),
//                priceSection.getInt("min",-1),
//                priceSection.getInt("max",-1),
//                priceSection.getInt("increaseGradation",-1),
//                priceSection.getInt("decreaseGradation",-1),
//                priceSection.getInt("increaseIfSalesUnder",-1),
//                priceSection.getInt("decreaseIfSalesUpwards",-1)
//        );
//
//        Material material = Material.getMaterial(itemId.toUpperCase());
//        if (material == null) {
//            Bukkit.getLogger().warning(" ");
//            Bukkit.getLogger().warning("[EnhancedBuyer] Ошибка. Не найден предмет: "+ itemKey + " для скупщика: " + ID);
//            Bukkit.getLogger().warning("[EnhancedBuyer] Пропускаю предмет: "+ itemKey + " для скупщика: " + ID);
//            Bukkit.getLogger().warning(" ");
//            continue;
//        }
////        ItemStack itemStack = new ItemStack(material);
////                ItemMeta meta = itemStack.getItemMeta();
////        if (meta != null) {
////            meta.setDisplayName(Utils.translateHex(displayNameItem));
////            lore.replaceAll(Utils::translateHex);
////            meta.setLore(lore);
////            itemStack.setItemMeta(meta);
////        }
//        List<String> actionList = itemSection.getStringList("success_commands");
//        IMenuButton purchaseItem;
//        if (price.getCurrentPrice() >0){
//            purchaseItem = new PurchaseItem(new ItemStack(material), price, displayNameItem,lore, actionList);
//        }else{
//            purchaseItem = new DecorativeItem(new ItemStack(material),displayNameItem,lore, actionList);
//        }
//
//
//        contents.put(slotID, purchaseItem);
//
//
//        ConfigurationSection extraContentsSection = buyerSection.getConfigurationSection("citizensOverride");
//
//        for (String extraItemOverride : extraContentsSection.getKeys(false)) {
//            ConfigurationSection extraItemSection = extraContentsSection.getConfigurationSection(extraItemOverride);
//            String extraItemId = extraItemSection.getString("id");
//            String extraDisplayNameItem = extraItemSection.getString("displayname");
//            List<String> extraLore = extraItemSection.getStringList("lore");
//            int extraSlotID = extraItemSection.getInt("slotID");
//            List<String> extraActions = extraItemSection.getStringList("success_commands");
//            DecorativeItem item = new DecorativeItem(new ItemStack(Material.valueOf(extraItemId)),extraDisplayNameItem,extraLore,extraActions);
//            extraContents.put(extraSlotID,item);
//        }
//
//    }
  //  return new Buyer(ID,displayName, contents,extraContents);

//}
public void updateBuyers(){
}
}
