package org.mrdarkimc.enhancedbuyer.buyer.parts;

import java.util.List;

public class ItemInfo{
    String displayName;
    List<String> lore;

    public ItemInfo(String displayName, List<String> lore) {
        this.displayName = displayName;
        this.lore = lore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLore() {
        return lore;
    }
}
