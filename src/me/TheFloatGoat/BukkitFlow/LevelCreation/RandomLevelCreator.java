package me.TheFloatGoat.BukkitFlow.LevelCreation;

import me.TheFloatGoat.BukkitFlow.Helpers.InventoryHelpers;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class RandomLevelCreator {

    Plugin plugin;
    InventoryHelpers IH = new InventoryHelpers();

    public RandomLevelCreator(Plugin plugin) {
        this.plugin = plugin;
    }

    Set<Integer> possible;

    public ItemStack[] createLevel() {

        System.out.println("randomlevelcreator.createlevel");

        ItemStack[] level = new ItemStack[54];

        //1. Create a number of random paths - check
        //2. Try to fill the gaps by appending the neighbouring squares, check with pathchecker
        //3.fill the rest with bars

        int colors = 7;

        possible = new HashSet();
        for(int i = 0; i<54; i++) {
            possible.add(i);
        }

        for(int i = 0; i <= colors && possible.size() > 2; i++) {

            List<Integer> path = newPath();
            for(int j = 0; j < path.size(); j++) {
                //Sets the item to anchor point if it is the start or end of a path.
                level[path.get(j)] = IH.genItem(j == 0 || j == path.size()-1, i);
            }
        }

        return fillLevel(level);

    }

    ItemStack[] fillLevel(ItemStack[] level) {

        boolean fixed = false;

        for(int i = 0; i < level.length && !fixed; i++) {

            if(level[i] == null || level[i].getType() == Material.AIR) {


                for (int j = -3; j <= 3 && !fixed; j += 2) {

                    int nextPos = i + (int)(Math.signum(j)*j*j);   //-9, -1, 1, 9
                    //Check for screen wrapping.
                    if(Math.abs(nextPos%9 - i%9) > 1 || nextPos >= level.length || nextPos < 0) continue;

                    if(level[nextPos] != null && level[nextPos].getType() == Material.STAINED_GLASS_PANE) {

                        level[i] = level[nextPos];
                        level[nextPos] = IH.genItem(false, level[i].getDurability());
                        fixed = true;

                    }
                }
            }
        }

        if(!fixed) {
            for(int i = 0; i <level.length; i++) {

                if(level[i] == null) {

                    level[i] = IH.genItem(false, -2);
                }
            }
        }else {
            fillLevel(level);
        }


        return level;
    }

    List<Integer> newPath() {

        List path = new ArrayList<Integer>();

        Random random = new Random();
        int number = random.nextInt(possible.size());
        System.out.println(possible.size());

        int startPos = (int) possible.toArray()[number];

        int maxPathLength = 5 + random.nextInt(9);

        int curPos = startPos;
        possible.remove(curPos);
        path.add(curPos);

        for(int i = 0; i < maxPathLength-1; i++) {

            for (int j = -3; j <= 3; j += 2) {      //TODO USE A RANDOM ORDER HERE!

                int nextPos = curPos + (int)(Math.signum(j)*j*j);   //-9, -1, 1, 9
                //Check for screen wrapping.
                if(Math.abs(nextPos%9 - curPos%9) > 1) continue;

                if(possible.contains(nextPos)) {

                    path.add(nextPos);
                    possible.remove(nextPos);
                    curPos = nextPos;
                    j = 42; //to break the guard
                }
            }
        }

        return path;

    }


}
