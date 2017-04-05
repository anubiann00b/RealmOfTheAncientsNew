package me.shreyasr.ancients.component.attack;

import com.esotericsoftware.minlog.Log;
import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;

import java.util.Arrays;
import java.util.function.Predicate;

public class AttackDirections {
    
    private final AttackDirection[] attackDirections;
    
    protected AttackDirections() {
        this((AttackDirection[]) null);
    }
    
    public AttackDirections(AttackDirection... attackDirections) {
        this.attackDirections = attackDirections;
    }
    
    public Attack.AnimFrame[] getFramesForMousePos(Pos pos) {
        for (AttackDirection possibleAttack : attackDirections) {
            if (possibleAttack.isMousePosInThisAttackDirection.test(pos)) {
                return possibleAttack.frames;
            }
        }
        Log.error("attackdirections", "Unable to find AttackDirection for mouse: " + pos + ", " + this);
        return new Attack.AnimFrame[0];
    }
    
    @Override
    public String toString() {
        return "AttackDirections{" +
                "attackDirections=" + Arrays.toString(attackDirections) +
                '}';
    }
    
    public static class AttackDirection {
    
        private final Predicate<Pos> isMousePosInThisAttackDirection;
        private final Attack.AnimFrame[] frames;
        
        private AttackDirection() {
            this(null, (Attack.AnimFrame[]) null);
        }
    
        public AttackDirection(Predicate<Pos> isMousePosInThisAttackDirection, Attack.AnimFrame... frames) {
            this.isMousePosInThisAttackDirection = isMousePosInThisAttackDirection;
            this.frames = frames;
        }
        
//        protected abstract boolean isMousePosInThisAttackDirection(Pos relativeMousePos);
    
        @Override
        public String toString() {
            return "AttackDirection{" +
                    "frames=" + Arrays.toString(frames) +
                    '}';
        }
    }
}
