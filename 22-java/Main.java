import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    public static void main(String[] args) {
        new Main();
    }
    
    public Main() {
        var list = new ArrayList<Wizard>();
        Wizard startWizard = new Wizard(50, 500);
        list.add(startWizard);
        int min = Integer.MAX_VALUE;
        
        boolean allDead = false;

        while (!allDead) {
            allDead = true;
            var list2 = clone(list);
            for (Wizard w : list2) {
                if (w.hasWon()) {
                    min = min(min, w.getManaCount());
                } else if (!w.hasLost() && w.getManaCount() < min) {
                    allDead = false;
                    var wizards = step(w, false);
                    list.addAll(wizards);
                }
                list.remove(w);
            }
        }
        System.out.println("Part 1: " + min);


        list = new ArrayList<Wizard>();
        startWizard = new Wizard(50, 500);
        list.add(startWizard);
        min = Integer.MAX_VALUE;
        
        allDead = false;

        while (!allDead) {
            allDead = true;
            var list2 = clone(list);
            for (Wizard w : list2) {
                if (w.hasWon()) {
                    min = min(min, w.getManaCount());
                } else if (!w.hasLost() && w.getManaCount() < min) {
                    allDead = false;
                    var wizards = step(w, true);
                    list.addAll(wizards);
                }
                list.remove(w);
            }
        }
        System.out.println("Part 2: " + min);
   }

    public List<Wizard> clone(List<Wizard> wizards) {
        List<Wizard> clone = new ArrayList<>();
        for (Wizard w : wizards) {
            clone.add(w);
        }
        return clone;
    }

    public List<Wizard> step(Wizard wizard, boolean part2) {
        ArrayList<Wizard> wizards = new ArrayList<Wizard>();
        for (Spells s : Spells.values()) {
            Wizard clone = wizard.clone();
            if (clone.step(s, part2)) {
                wizards.add(clone);
            }
        }
        for (EffectType e : EffectType.values()) {
            Wizard clone = wizard.clone();
            if (clone.step(e, part2)) {
                wizards.add(clone);
            }
        }
        return wizards;
    }

    public class Wizard extends Player {
        private int mana;
        private int armor = 0;
        private Boss boss;
        private Effect[] effects = new Effect[3];
        private int manaCount = 0;

        public Wizard(int points, int mana) {
            super(points);
            this.mana = mana;
            for (int i = 0; i < 3; i++) {
                effects[i] = new Effect(EffectType.values()[i]);
            }
            this.boss = new Boss(51, 9);
        }

        public void setManaCount(int count) {
            this.manaCount = count;
        }

        public int getManaCount() {
            return this.manaCount;
        }

        protected Wizard clone() {
            Wizard w = new Wizard(this.points, mana);
            Effect[] clone = new Effect[effects.length];
            for (int i = 0; i < clone.length; i++) {
                clone[i] = effects[i].clone();
            }
            w.setEffects(clone);
            w.setManaCount(getManaCount());
            w.setBoss(boss.clone());
            return w;
        }

        public boolean hasWon() {
            return boss.getPoints() <= 0;
        }

        public boolean hasLost() {
            return this.getPoints() <= 0;
        }

        protected void setEffects(Effect[] arr) {
            this.effects = arr.clone();
        }

        protected void setBoss(Boss b) {
            this.boss = b.clone();
        }

        private void effectStep() {
            this.armor = 0;
            for (int i = 0; i < effects.length; i++) {
                Effect e = effects[i];
                if (e.isActive()) {
                    switch (e.getType()) {
                    case POISON:
                        boss.dealDamage(3);
                        break;
                    case RECHARGE:
                        this.addMana(101);
                        break;
                    case SHIELD:
                        this.armor = 7;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown effect type " + e.getType());
                    }
                }
                effects[i].step();
            }
        }

        public boolean step(Spell s, boolean part2) {
            if (part2) {
                dealDamage(1);
                if (hasLost()) return true;
            }
            effectStep();
            if (s instanceof Spells) {
                switch ((Spells)s) {
                case MISSILE:
                    if (mana < 53) return false;
                    mana -= 53;
                    manaCount += 53;
                    boss.dealDamage(4);
                    break;
                case DRAIN:
                    if (mana < 73) return false;
                    mana -= 73;
                    manaCount += 73;
                    boss.dealDamage(2);
                    heal(2);
                    break;
                }
            } else {
                switch ((EffectType)s) {
                case POISON:
                    if (mana < 173) return false;
                    mana -= 173;
                    manaCount += 173;
                    if (effects[1].isActive()) return false;
                    effects[1].activate(6);
                    break;
                case SHIELD:
                    if (mana < 113) return false;
                    mana -= 113;
                    manaCount += 113;
                    if (effects[0].isActive()) return false;
                    effects[0].activate(6);
                    break;
                case RECHARGE:
                    if (mana < 229) return false;
                    mana -= 229;
                    manaCount += 229;
                    if (effects[2].isActive()) return false;
                    effects[2].activate(5);
                    break;
                default:
                    throw new IllegalArgumentException("Spell " + s + " not found!");
                }
            }
            if (points <= 0 && boss.getPoints() <= 0) return true;
            effectStep();
            this.dealDamage(max(1, boss.getDamage()-armor));

            return true;
        }

        public void addMana(int amount) {
            this.mana += amount;
        }

        public void loseMana(int amount) {
            this.mana -= amount;
        }

        public void setMana(int amount) {
            this.mana = amount;
        }

        public int getMana() {
            return this.mana;
        }

        public String toString() {
            return
            "Wizard: {\n" +
            "    Health: " + points + '\n' +
            "    Mana: " + mana + '\n' +
            "    Mana count: " + manaCount + '\n' +
            "    Armor: " + armor + '\n' +
            "    Effects: \n" + Arrays.toString(effects).indent(8) +
            "    Boss: \n" + boss.toString().indent(4) + 
            "}";
        }
    }

    public class Effect {
        private EffectType type;
        private boolean active;
        private int timer;

        public Effect(EffectType type) {
            this.type = type;
        }

        public EffectType getType() {
            return this.type;
        }

        public void step() {
            if (this.active) this.timer--;
            if (this.timer <= 0) active = false;
        }

        public boolean isActive() {
            return active;
        }

        public void setTimer(int to) {
            this.timer = to;
        }

        public void activate(int time) {
            this.active = true;
            this.timer = time;
        }

        public int getTimer() {
            return this.timer;
        }

        public String toString() {
            return
            "Effect: {\n" +
            "    Type: " + type + '\n' +
            "    Active: " + active + '\n' +
            "    Timer: " + timer + '\n' +
            "}";
        }
        
        public void setActive(boolean active) {
            this.active = active;
        }

        public Effect clone() {
            Effect e = new Effect(type);
            e.setTimer(getTimer());
            e.setActive(isActive());
            return e;
        }
    }

    public interface Spell {}

    public enum EffectType implements Spell {
        SHIELD,
        POISON,
        RECHARGE
    }

    public enum Spells implements Spell {
        MISSILE,
        DRAIN,
    }

    public class Boss extends Player {
        private int damage;

        public Boss(int points, int damage) {
            super(points);
            this.damage = damage;
        }

        public int getDamage() {
            return this.damage;
        }

        public Boss clone() {
            return new Boss(points, damage);
        }

        public String toString() {
            return
            "{\n" + 
            "   Health: " + points + '\n' +
            "   Damage: " + damage + '\n' +
            "}";
        }
    }

    public class Player {
        protected int points;
        
        public Player(int points) {
            this.points = points;
        }

        public int getPoints() {
            return this.points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public void dealDamage(int damage) {
            this.points -= damage;
        }

        public void heal(int amount) {
            this.points += amount;
        }
    }
}
