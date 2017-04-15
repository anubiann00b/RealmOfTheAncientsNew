package me.shreyasr.ancients.component.attack;

import com.esotericsoftware.minlog.Log;
import lombok.ToString;
import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;

import java.util.function.Predicate;

@ToString(includeFieldNames = false)
public class AttackDirections {
    
    private final AttackDirection[] attackDirections;
    
    public AttackDirections(AttackDirection... attackDirections) {
        this.attackDirections = attackDirections;
    }
    
    public Attack.AnimFrame[] getFramesForIndex(int index) {
        if (index >= 0 && index < attackDirections.length) {
            return attackDirections[index].frames;
        } else {
            return new Attack.AnimFrame[0];
        }
    }
    
    public int getFramesForMousePos(Pos pos) {
        for (int i = 0; i < attackDirections.length; i++) {
            AttackDirection possibleAttack = attackDirections[i];
            if (possibleAttack.isMousePosInThisAttackDirection.test(pos)) {
                return i;
            }
        }
        Log.error("attackdirections", "Unable to find AttackDirection for mouse: " + pos + ", " + this);
        return -1;
    }
    
    @ToString(includeFieldNames = false, exclude = {"isMousePosInThisAttackDirection"})
    public static class AttackDirection {
    
        private final Predicate<Pos> isMousePosInThisAttackDirection;
        private final Attack.AnimFrame[] frames;
        
        public AttackDirection(Predicate<Pos> isMousePosInThisAttackDirection, Attack.AnimFrame... frames) {
            this.isMousePosInThisAttackDirection = isMousePosInThisAttackDirection;
            this.frames = frames;
        }
    }
}
