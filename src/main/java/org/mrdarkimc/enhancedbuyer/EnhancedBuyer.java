package org.mrdarkimc.enhancedbuyer;

import org.bukkit.plugin.java.JavaPlugin;
import org.mrdarkimc.SatanicLib.Debugger;
import org.mrdarkimc.SatanicLib.SatanicLib;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.configsetups.Configs;
import org.mrdarkimc.SatanicLib.currency.PlayerPoints;
import org.mrdarkimc.SatanicLib.currency.Vault;
import org.mrdarkimc.SatanicLib.currency.interfaces.Currency;
import org.mrdarkimc.enhancedbuyer.buyer.CycleManager;
import org.mrdarkimc.enhancedbuyer.eventhandlers.MenuListener;
import org.mrdarkimc.enhancedbuyer.objectmanager.ObjectManager;

public final class EnhancedBuyer extends JavaPlugin {
    public static Configs config;
    private static EnhancedBuyer instance;
    public static Currency currency;
    public static ObjectManager manager;
    public static CycleManager cycleManager;

    public static EnhancedBuyer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        SatanicLib.setupLib(this);
        config = Configs.Defaults.setupConfig();
        Utils.startUp("EnhancedSeller Premium");
        setUpEconomy();
        getServer().getPluginCommand("sellermenus").setExecutor(new Command());
        getServer().getPluginManager().registerEvents(new MenuListener(),this);
        cycleManager = new CycleManager();
        manager = ObjectManager.initialize();
        new Debugger();

        // Plugin startup logic

    }
    public void setUpEconomy() {
        String currency = config.get().getString("SatanicLib.currency");
        switch (currency) {
            case "Vault":
                this.currency = new Vault();
                break;
            case "PlayerPoints":
                this.currency = new PlayerPoints();
                break;

        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
