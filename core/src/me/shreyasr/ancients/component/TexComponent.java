package me.shreyasr.ancients.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import me.shreyasr.ancients.Asset;

public class TexComponent implements Component {

    public final Texture texture;

    public TexComponent(Asset asset) {
        texture = asset.getTex();
    }
}
