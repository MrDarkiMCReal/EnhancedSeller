package org.mrdarkimc.enhancedbuyer.buyer.parts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.mrdarkimc.SatanicLib.Debugger;
import org.mrdarkimc.SatanicLib.Utils;
import org.mrdarkimc.SatanicLib.messages.KeyedMessage;
import org.mrdarkimc.SatanicLib.messages.Message;
import org.mrdarkimc.SatanicLib.prefixHandler.Prefix;
import org.mrdarkimc.enhancedbuyer.EnhancedBuyer;
import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IMenuButton;

import java.util.*;

public class PurchaseItem implements IMenuButton {
    private ItemStack displayItem;
    private Price price;

    private ItemStack stackToSell;
    private ItemInfo itemInfo;
    private String displayName;
    private List<String> lore;
    private List<String> actions;

    public PurchaseItem(ItemStack itemStack, Price price, String displayName, List<String> lore, List<String> actions) {
        this.itemInfo = new ItemInfo(displayName,lore);
        this.stackToSell = itemStack;
        this.displayItem = itemStack;
        this.price = price;
        this.displayName = displayName;
        this.lore = lore;
        this.actions = actions;
        updateStack();
    }
    public void buyFromPlayer(Player player, int amount){
        ItemStack[] stacks = player.getInventory().getContents();
        if (amount==-1) {
            //ItemStack[] stacklist = Arrays.stream(stacks).filter(stack -> stack.getType().equals(itemStack.getType())).toArray(ItemStack[]::new);
            amount = Arrays.stream(stacks).filter(stack -> stack.getType().equals(displayItem.getType())).mapToInt(ItemStack::getAmount).sum();
            //Arrays.stream(stacklist).forEach(is -> player.getInventory().remove(is));
            player.getInventory().remove(displayItem.getType());
        }
        EnhancedBuyer.currency.addMoney(player, (price.getCurrentPrice()*amount) );
    }
    public boolean isSellable(){
        return price.getCurrentPrice()!=-1;
    }


    public void setDisplayItem(ItemStack displayItem) {
        this.displayItem = displayItem;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
//    public void updateLore(){
//        List<String> sti = new ArrayList<>(itemInfo.getLore());
//        //todo иммутабельный лист
//        itemInfo.getLore().replaceAll(l -> {
//            l = l.replaceAll("\\{current}",String.valueOf(price.getCurrentPrice()));
//            l = l.replaceAll("\\{min}",String.valueOf(price.getMinPrice()));
//            l = l.replaceAll("\\{max}",String.valueOf(price.getMaxPrice()));
//            l = l.replaceAll("\\{sales}",String.valueOf(price.getSalesCount()));
//            l = l.replaceAll("\\{decrease}",String.valueOf(price.getDecreaseGradation()));
//            l = l.replaceAll("\\{increase}",String.valueOf(price.getIncreaseGradation()));
//            l = l.replaceAll("\\{min}",String.valueOf(price.getMinPrice()));
//            return l;
//        });
//    }
    public void updatePriceAndStack(){
        Debugger.chat("Обновляю: " + this.displayName + " Цена: " + price.getCurrentPrice() + " Продано: " + price.getSalesCount(),4);
        price.update();
//        lore.replaceAll(l -> l.replaceAll("\\{price}",String.valueOf(price.getCurrentPrice())));
//        lore.replaceAll(l -> {
//            l = l.replaceAll("\\{current}",String.valueOf(price.getCurrentPrice()));
//            l = l.replaceAll("\\{min}",String.valueOf(price.getMinPrice()));
//            l = l.replaceAll("\\{max}",String.valueOf(price.getMaxPrice()));
//            l = l.replaceAll("\\{sales}",String.valueOf(price.getSalesCount()));
//            l = l.replaceAll("\\{decrease}",String.valueOf(price.getDecreaseGradation()));
//            l = l.replaceAll("\\{increase}",String.valueOf(price.getIncreaseGradation()));
//            l = l.replaceAll("\\{min}",String.valueOf(price.getMinPrice()));
//            return l;
//        });
//        displayName = displayName.replaceAll("\\{price}",String.valueOf(price.getCurrentPrice()));
        updateStack();
    }
private void updateStack(){
    ItemMeta meta = displayItem.getItemMeta();
    meta.setDisplayName(Utils.translateHex(handlePlaceholders(displayName)));

    List<String> newLore = new ArrayList<>(lore);
    newLore.replaceAll(Utils::translateHex);
    newLore.replaceAll(this::handlePlaceholders);

    meta.setLore(newLore);

    displayItem.setItemMeta(meta);
}
    @Override
    public ItemStack getStack() {
        return displayItem;
    }
    public String handlePlaceholders(String text){
            return text.replaceAll("\\{current}",String.valueOf(price.getCurrentPrice()))
                .replaceAll("\\{min}",String.valueOf(price.getMinPrice()))
                .replaceAll("\\{max}",String.valueOf(price.getMaxPrice()))
                .replaceAll("\\{sales}",String.valueOf(price.getSalesCount()))
                .replaceAll("\\{decrease}",String.valueOf(price.getDecreaseGradation()))
                .replaceAll("\\{increase}",String.valueOf(price.getIncreaseGradation()))
                .replaceAll("\\{decreaseIfSalesUpwards}",String.valueOf(price.getDecreaseIfSalesUpwards()))
                .replaceAll("\\{increaseIfSalesUnder}",String.valueOf(price.getIncreaseIfSalesUnder()));
    }

    @Override
    public void pressLeft(Player player) {
        int sellAmount = 1;
        Inventory inv = player.getInventory();
        List<ItemStack> stackList = Arrays.stream(inv.getContents()).filter(Objects::nonNull).filter(stack -> stack.getType().equals(stackToSell.getType())).toList();
        if (stackList.isEmpty()){
            new KeyedMessage(player,"messages.bought-failure-all",Map.of("{amount}",String.valueOf(sellAmount))).send();
            return;
        }
        ItemStack stack = stackList.getFirst();
        stack.setAmount(stack.getAmount() - sellAmount);
        int totalmoney = sellAmount * this.price.getCurrentPrice();
        EnhancedBuyer.currency.addMoney(player, totalmoney);
        price.addSalesCount(sellAmount);
        new KeyedMessage(player,"messages.bought-success",Map.of("{amount}",String.valueOf(sellAmount),"{price}",String.valueOf(totalmoney))).send();
        action(player);
        //EnhancedBuyer.manager.priceManager.addSale(this);
    }
//
//
//
//
//
//    if (inv.contains(displayItem)){
//        List<ItemStack> stackList = Arrays.stream(inv.getContents()).filter(stack -> stack.isSimilar(displayItem)).collect(Collectors.toList());
//        int totalAmount = stackList.stream().mapToInt(ItemStack::getAmount).sum();
//        if (totalAmount >= sellAmount){
//
//            for (ItemStack stack : inv.getContents()) { //stackList for
//                if (sellAmount == 0){
//                    break;
//                }
//                if (stack.isSimilar(displayItem)){
//                int removedAmount = Math.min(stack.getMaxStackSize(),sellAmount);
//
//                stack.setAmount(removedAmount);
//                sellAmount -= removedAmount;
//                }
//            }
//            EnhancedBuyer.currency.addMoney(player,sellAmount*this.price.getCurrentPrice());
//            EnhancedBuyer.manager.priceManager.addSale(this);
//
////            int requiredStacks = (int) Math.ceil((float) 64 /itemStack.getMaxStackSize());
////            for (int i = 0; i < requiredStacks; i++) {
////                ItemStack stack = stackList.getFirst();
////                int itemsToRemove = Math.min(stack.getMaxStackSize(),sellAmount);
////                stackList.stream().findAny().ifPresent(stack -> {
////                    stack.setAmount(stack.getAmount()-);
////
////                });
////            }
////            (itemStack.getMaxStackSize().f)
////            stackList.stream().find
//    }else {
//        player.sendMessage("У вас нет такого предмета");
//    }



    @Override
    public void pressShiftLeft(Player player) {

    }

    @Override
    public void pressRight(Player player) {
        int amount = 64;
        Inventory inv = player.getInventory();

        List<ItemStack> stackList = Arrays.stream(inv.getContents())
                .filter(Objects::nonNull)
                .filter(stack -> stack.getType().equals(stackToSell.getType()))
                .toList();
        if (stackList.isEmpty()){
            new KeyedMessage(player,"messages.bought-failure-all",Map.of("{amount}",String.valueOf(amount))).send();
            return;
        }
        int totalAmount = stackList.stream().mapToInt(ItemStack::getAmount).sum();

        if (totalAmount >= amount) {
            int totalRemaining = amount;

            for (ItemStack stack : stackList) {
                int remainingItemsForThisStack = Math.min(totalRemaining, stack.getAmount());

                stack.setAmount(stack.getAmount() - remainingItemsForThisStack);

                totalRemaining -= remainingItemsForThisStack;

                if (totalRemaining == 0) {
                    break;
                }
            }
            player.updateInventory();
            int totalmoney = amount * this.price.getCurrentPrice();
            EnhancedBuyer.currency.addMoney(player, totalmoney);
            price.addSalesCount(amount);
            new KeyedMessage(player,"messages.bought-success",Map.of("{amount}",String.valueOf(amount),"{price}",String.valueOf(totalmoney))).send();
            action(player);
        }else {
            new KeyedMessage(player,"messages.bought-failure",Map.of("{amount}",String.valueOf(amount),"{playerAmount}",String.valueOf(totalAmount))).send();
        }
    }

    @Override
    public void pressShiftRight(Player player) {
        Inventory inv = player.getInventory();
        List<ItemStack> stackList = Arrays.stream(inv.getContents())
                .filter(Objects::nonNull)
                .filter(stack -> stack.getType().equals(stackToSell.getType()))
                .toList();
        int totalAmount = stackList.stream().mapToInt(ItemStack::getAmount).sum();
        if (totalAmount >0){
            stackList.forEach(s -> s.setAmount(0));
            player.updateInventory();
            EnhancedBuyer.currency.addMoney(player, totalAmount * this.price.getCurrentPrice());
            price.addSalesCount(totalAmount);
            int totalmoney = totalAmount * this.price.getCurrentPrice();
            new KeyedMessage(player,"messages.bought-success",Map.of("{amount}",String.valueOf(totalAmount),"{price}",String.valueOf(totalmoney))).send();
            action(player);
        }else {
            new KeyedMessage(player,"messages.bought-failure-all", null).send();
        }

    }
    public void action(Player player){
        if (actions ==null) {
            return;
        }
        if (actions.isEmpty())
            return;
        actions.forEach(text -> Prefix.handle(player,text, Map.of("{player}",player.getName())));
    }
    @Override
    public void updateInfo() {
        updateStack();
    }

    @Override
    public void updatePrice() {
        updatePriceAndStack();
    }
}

