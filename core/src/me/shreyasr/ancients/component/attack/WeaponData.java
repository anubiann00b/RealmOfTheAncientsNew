package me.shreyasr.ancients.component.attack;

import com.esotericsoftware.minlog.Log;
import lombok.ToString;
import me.shreyasr.ancients.component.Attack;
import me.shreyasr.ancients.component.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ToString
public class WeaponData {
    
    private final List<AttackDirection> attackDirections;
    private final List<DirectionPredicate> directionPredicates;
    
    public WeaponData() {
        attackDirections = new ArrayList<>();
        directionPredicates = new ArrayList<>();
    }
    
    public WeaponData addAttackDirection(int directionIndex, Attack.AnimFrame... frames) {
        attackDirections.add(new AttackDirection(directionIndex, frames));
        return this;
    }
    
    public WeaponData addDirectionPredicate(int directionIndex, Predicate<Pos> isMousePosInThisDirection) {
        directionPredicates.add(new DirectionPredicate(directionIndex, isMousePosInThisDirection));
        return this;
    }
    
    public WeaponData addAttackDirectionWithPredicate(Predicate<Pos> isMousePosInThisDirection, Attack.AnimFrame... frames) {
        int directionIndex = attackDirections.size();
        addAttackDirection(directionIndex, frames);
        addDirectionPredicate(directionIndex, isMousePosInThisDirection);
        return this;
    }
    
    public Attack.AnimFrame[] getFramesForDirectionIndex(int index) {
        if (index >= 0 && index < attackDirections.size()) {
            return attackDirections.get(index).frames;
        } else {
            return new Attack.AnimFrame[0];
        }
    }
    
    public int getDirectionIndexForMousePos(Pos pos) {
        for (DirectionPredicate directionPredicate : directionPredicates) {
            if (directionPredicate.isMousePosInThisDirection.test(pos)) {
                return directionPredicate.directionIndex;
            }
        }
        Log.error("attackdirections", "Unable to find AttackDirection for mouse: " + pos + ", " + this);
        return -1;
    }
    
    @ToString(exclude = {"isMousePosInThisDirection"})
    public static class DirectionPredicate {
        
        private final int directionIndex;
        private final Predicate<Pos> isMousePosInThisDirection;
    
        public DirectionPredicate(int directionIndex, Predicate<Pos> isMousePosInThisDirection) {
            this.directionIndex = directionIndex;
            this.isMousePosInThisDirection = isMousePosInThisDirection;
        }
    }
    
    @ToString
    public static class AttackDirection {
        
        private final int directionIndex;
        private final Attack.AnimFrame[] frames;
        
        public AttackDirection(int directionIndex, Attack.AnimFrame... frames) {
            this.directionIndex = directionIndex;
            this.frames = frames;
        }
    }
}
