package org.mrdarkimc.enhancedbuyer.buyer.parts;

import org.mrdarkimc.enhancedbuyer.buyer.parts.interfaces.IPrice;

public class Price implements IPrice {
    private int defaultPrice;
    private int minPrice;
    private int maxPrice;
    private int increaseGradation;
    private int decreaseGradation;
    private int currentPrice;
    private int salesCount;

    public int getCurrentPrice() {
        return currentPrice;
    }

    // Конструктор
    public Price(int defaultPrice, int minPrice, int maxPrice, int increaseGradation, int decreaseGradation) {
        this.defaultPrice = defaultPrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.increaseGradation = increaseGradation;
        this.decreaseGradation = decreaseGradation;

        currentPrice = defaultPrice;
        salesCount = 0;
    }
    public void update(){

    }

    public int getSalesCount() {
        return salesCount;
    }

    public void addSalesCount() {
        this.salesCount += 1;
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
