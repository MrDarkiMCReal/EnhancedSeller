package org.mrdarkimc.enhancedbuyer.buyer;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.mrdarkimc.enhancedbuyer.EnhancedBuyer;
import org.mrdarkimc.enhancedbuyer.objectmanager.BuyerManager;

public class CycleManager extends BukkitRunnable {
    int timeInseconds = EnhancedBuyer.config.get().getInt("menus.refreshtime");
    boolean isRunning = false;
    @Override
    public void run() {
        BuyerManager.refresh();
    }
    public void startRefreshTask(){
        if (isRunning)
            return;
        isRunning = true;
        this.runTaskTimer(EnhancedBuyer.getInstance(),timeInseconds*20L,timeInseconds*20L);
        Bukkit.getLogger().info("[SatanicLib] Task has been started");
    }
    public void stopRefreshTask(){
        if (isRunning){
            this.cancel();
            isRunning = false;
            Bukkit.getLogger().info("[SatanicLib] Task has been stopped");
        }else {
            Bukkit.getLogger().info("[SatanicLib] Task has not been booted yet");
        }
    }
}
