package org.mrdarkimc.enhancedbuyer.buyer.parts;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.mrdarkimc.enhancedbuyer.EnhancedBuyer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PurchaseItem implements IMenuButton {
    private ItemStack itemStack;
    private Price price;

    public PurchaseItem(ItemStack itemStack, Price price) {
        this.itemStack = itemStack;
        this.price = price;
    }
    public void buyFromPlayer(Player player, int amount){
        ItemStack[] stacks = player.getInventory().getContents();
        if (amount==-1) {
            //ItemStack[] stacklist = Arrays.stream(stacks).filter(stack -> stack.getType().equals(itemStack.getType())).toArray(ItemStack[]::new);
            amount = Arrays.stream(stacks).filter(stack -> stack.getType().equals(itemStack.getType())).mapToInt(ItemStack::getAmount).sum();
            //Arrays.stream(stacklist).forEach(is -> player.getInventory().remove(is));
            player.getInventory().remove(itemStack.getType());
        }
        EnhancedBuyer.currency.addMoney(player, (price.getCurrentPrice()*amount) );
    }
    public boolean isSellable(){
        return price.getCurrentPrice()!=-1;
    }


    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    @Override
    public ItemStack getStack() {
        return itemStack;
    }

    @Override
    public void pressLeft(Player player) {
        int sellAmount = 1;
        Inventory inv = player.getInventory();
    if (inv.contains(itemStack)){
        List<ItemStack> stackList = Arrays.stream(inv.getContents()).filter(stack -> stack.isSimilar(itemStack)).collect(Collectors.toList());
        int totalAmount = stackList.stream().mapToInt(ItemStack::getAmount).sum();
        if (totalAmount >= sellAmount){

            for (ItemStack stack : inv.getContents()) { //stackList for
                if (sellAmount == 0){
                    break;
                }
                if (stack.isSimilar(itemStack)){
                int removedAmount = Math.min(stack.getMaxStackSize(),sellAmount);

                stack.setAmount(removedAmount);
                sellAmount -= removedAmount;
                }
            }
            EnhancedBuyer.currency.addMoney(player,sellAmount*this.price.getCurrentPrice());
            EnhancedBuyer.manager.priceManager.addSale(this);

            int requiredStacks = (int) Math.ceil((float) 64 /itemStack.getMaxStackSize());
            for (int i = 0; i < requiredStacks; i++) {
                ItemStack stack = stackList.getFirst();
                int itemsToRemove = Math.min(stack.getMaxStackSize(),sellAmount);
                stackList.stream().findAny().ifPresent(stack -> {
                    stack.setAmount(stack.getAmount()-);

                });
            }
            (itemStack.getMaxStackSize().f)
            stackList.stream().find
        }
    }else {
        player.sendMessage("У вас нет такого предмета");
    }

    }

    @Override
    public void pressShiftLeft(Player player) {

    }

    @Override
    public void pressRight(Player player) {

    }

    @Override
    public void pressShiftRight(Player player) {

    }
}

