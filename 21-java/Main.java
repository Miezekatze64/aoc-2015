import java.util.ArrayList;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class Main {
    public static final Item[][] SHOP = new Item[][] {
        new Item[] {
            new Item("Dagger",       8, 4, 0),
            new Item("Shortsword",  10, 5, 0),
            new Item("Warhammer",   25, 6, 0),
            new Item("Longsword",   40, 7, 0),
            new Item("Greataxe",    74, 8, 0),
        },
        new Item[] {
	        new Item("Leather",     13, 0, 1),
	        new Item("Chainmail",   31, 0, 2),
	        new Item("Splintmail",  53, 0, 3),
	        new Item("Bandedmail",  75, 0, 4),
	        new Item("Platemail",  102, 0, 5),
        },
        new Item[] {
            new Item("Damage  +1",  25, 1, 0),
            new Item("Damage  +2",  50, 2, 0),
            new Item("Damage  +3", 100, 3, 0),
            new Item("Defence +1",  20, 0, 1),
            new Item("Defence +2",  40, 0, 2),
            new Item("Defence +3",  80, 0, 3),
        },
    };

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        Player boss = new Player(103, 9, 2, 0);

        Player[] players = getAll();
        
        int min = Integer.MAX_VALUE;
        for (Player player : players) {
            if (play(player, boss)) {
                min = min(min, player.gold());
            }
        }
        System.out.printf("Part 1: %d\n", min);

        int max = Integer.MIN_VALUE;
        for (Player player : players) {
            if (!play(player, boss)) {
                max = max(max, player.gold());
            }
        }
        System.out.printf("Part 2: %d\n", max);

    }

    private boolean play(Player player, Player boss) {
        int playerHealth = player.points();
        int bossHealth = boss.points();
        
        boolean bossTurn = false;
        while (playerHealth > 0 && bossHealth > 0) {
            if (bossTurn) {
                int attack = max(1, boss.damage()-player.armor());
                playerHealth -= attack;
            } else {
                int attack = max(1, player.damage()-boss.armor());
                bossHealth -= attack;
            }
            bossTurn = !bossTurn;
        }
        if (playerHealth > 0) return true;
        return false;
    }

    public Player[] getAll() {
        var players = new ArrayList<Player>();

        for (int weapon = 0; weapon < SHOP[0].length; weapon++) {
            int damage = SHOP[0][weapon].damage();
            int armor = SHOP[0][weapon].armor();
            int gold = SHOP[0][weapon].cost();
            
            for (int armori = -1; armori < SHOP[1].length; armori++) {
                int damage2 = (armori==-1)?damage:(SHOP[1][armori].damage() + damage);
                int armor2 = (armori==-1)?armor:(SHOP[1][armori].armor() + armor);
                int gold2 = (armori==-1)?gold:(SHOP[1][armori].cost() + gold);

                for (int ring = -1; ring < SHOP[2].length; ring++) {
                    int damage3 = (ring==-1)?damage2:(SHOP[2][ring].damage() + damage2);
                    int armor3 = (ring==-1)?armor2:(SHOP[2][ring].armor() + armor2);
                    int gold3 = (ring==-1)?gold2:(SHOP[2][ring].cost() + gold2);

                    for (int ring2 = -1; ring2 < SHOP[2].length; ring2++) {
                        if (ring2 == ring && ring != -1) continue;
                        int damage4 = (ring2==-1)?damage3:(SHOP[2][ring2].damage() + damage3);
                        int armor4 = (ring2==-1)?armor3:(SHOP[2][ring2].armor() + armor3);
                        int gold4 = (ring2==-1)?gold3:(SHOP[2][ring2].cost() + gold3);
                        players.add(new Player(100, damage4, armor4, gold4));
                    }
                }
            }
        }
        
        return players.toArray(new Player[0]);
    }

    public record Player(int points, int damage, int armor, int gold) {}
    public record Item(String name, int cost, int damage, int armor) {}
}
