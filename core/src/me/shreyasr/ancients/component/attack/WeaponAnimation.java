package me.shreyasr.ancients.component.attack;

import me.shreyasr.ancients.Asset;

public enum WeaponAnimation {
    
    SWORD(Asset.SWORD_SLASH, 48),
    DAGGER(Asset.DAGGER_SLASH, 48);
    
    public final Asset asset;
    public final int frameSize;
    
    WeaponAnimation(Asset asset, int frameSize) {
        this.asset = asset;
        this.frameSize = frameSize;
    }
}
