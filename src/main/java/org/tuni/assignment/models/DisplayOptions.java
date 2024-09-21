package org.tuni.assignment.models;

public class DisplayOptions {
    private boolean showElectricityPrice;
    private boolean showWindSpeed;

    public DisplayOptions(boolean showElectricityPrice, boolean showWindSpeed) {
        this.showElectricityPrice = showElectricityPrice;
        this.showWindSpeed = showWindSpeed;
    }

    public boolean isShowElectricityPrice() {
        return showElectricityPrice;
    }

    public void setShowElectricityPrice(boolean showElectricityPrice) {
        this.showElectricityPrice = showElectricityPrice;
    }

    public boolean isShowWindSpeed() {
        return showWindSpeed;
    }

    public void setShowWindSpeed(boolean showWindSpeed) {
        this.showWindSpeed = showWindSpeed;
    }
}
