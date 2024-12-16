package org.mrdarkimc.enhancedbuyer;

import org.bukkit.plugin.java.JavaPlugin;
import org.mrdarkimc.SatanicLib.SatanicLib;
import org.mrdarkimc.SatanicLib.configsetups.Configs;
import org.mrdarkimc.SatanicLib.currency.Vault;
import org.mrdarkimc.SatanicLib.currency.interfaces.Currency;
import org.mrdarkimc.enhancedbuyer.objectmanager.ObjectManager;

public final class EnhancedBuyer extends JavaPlugin {
    public static Configs config;
    public static EnhancedBuyer instance;
    public static Currency currency;
    public static ObjectManager manager;

    public static EnhancedBuyer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        SatanicLib.setupLib(this);
        config = Configs.Defaults.setupConfig();
        currency = new Vault();
        manager = ObjectManager.initialize();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
