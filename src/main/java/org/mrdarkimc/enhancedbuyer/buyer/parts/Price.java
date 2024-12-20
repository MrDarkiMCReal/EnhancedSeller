package org.mrdarkimc.enhancedbuyer.buyer.parts;

import org.mrdarkimc.SatanicLib.Debugger;
import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IPrice;

public class Price implements IPrice {
    private int defaultPrice;
    private int minPrice;
    private int maxPrice;
    private int increaseGradation;
    private int decreaseGradation;
    private int currentPrice;
    private int salesCount;
    private int decreaseIfSalesUpwards;
    private int increaseIfSalesUnder;

    public int getCurrentPrice() {
        return currentPrice;
    }

    public int getDecreaseIfSalesUpwards() {
        return decreaseIfSalesUpwards;
    }

    public int getIncreaseIfSalesUnder() {
        return increaseIfSalesUnder;
    }

    public Price(int defaultPrice, int minPrice, int maxPrice, int increaseGradation, int decreaseGradation, int increaseIfSalesUnder, int decreaseIfSalesUpwards) {
        this.defaultPrice = defaultPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.increaseGradation = increaseGradation;
        this.decreaseGradation = decreaseGradation;
        this.increaseIfSalesUnder = increaseIfSalesUnder;
        this.decreaseIfSalesUpwards = decreaseIfSalesUpwards;

        currentPrice = defaultPrice;
        salesCount = 0;
    }
    public void update(){
        if (salesCount >= decreaseIfSalesUpwards){
        currentPrice = Math.max(currentPrice-decreaseGradation,minPrice);
        salesCount = 0;
        return;
        }
        if (salesCount<=increaseIfSalesUnder){
            currentPrice = Math.min(currentPrice+increaseGradation,maxPrice);
            salesCount = 0;
        }

    }

    public int getSalesCount() {
        return salesCount;
    }

    public void addSalesCount(int itemCount) {
        this.salesCount += itemCount;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }


    public int getIncreaseGradation() {
        return increaseGradation;
    }


    public int getDecreaseGradation() {
        return decreaseGradation;
    }
}
