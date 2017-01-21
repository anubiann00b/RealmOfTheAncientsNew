package me.shreyasr.ancients.component;

import com.badlogic.ashley.core.Component;
import me.shreyasr.ancients.util.CustomUUID;

public class IdComponent implements Component {

    public final CustomUUID uuid;

    public IdComponent(CustomUUID uuid) {
        this.uuid = uuid;
    }
}
