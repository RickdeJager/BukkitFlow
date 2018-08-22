package me.TheFloatGoat.BukkitFlow.LevelCreation;

import me.TheFloatGoat.BukkitFlow.Helpers.InventoryHelpers;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RandomLevelCreator {

    InventoryHelpers IH = new InventoryHelpers();
    public static int colors = 4;
    public static boolean randomizePaths = true;
    Set<Integer> possible;
    Random random;

    public RandomLevelCreator() {
        random = new Random();
    }

    public ItemStack[] createLevel() {

        ItemStack[] level = new ItemStack[54];

        //1. Create a number of random paths
        //2. Try to fill the gaps by appending the neighbouring squares
        //3.fill the rest with bars

        possible = new HashSet();
        for(int i = 0; i<54; i++) {
            possible.add(i);
        }

        for(int i = 0; i < colors && possible.size() > 2; i++) {

            List<Integer> path = newPath();
            if (path.size() > 1) {  //We don't want single point paths, 2 anchor points next to each other is okay though
                for (int j = 0; j < path.size(); j++) {
                    //Sets the item to anchor point if it is the start or end of a path.
                    level[path.get(j)] = IH.genItem(j == 0 || j == path.size() - 1, i);
                }
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

        int startPos = (int) possible.toArray()[number];

        int maxPathLength = (54/colors) + random.nextInt(4);

        int curPos = startPos;
        possible.remove(curPos);
        path.add(curPos);

        for(int i = 0; i < maxPathLength-1; i++) {

            int[] dirs = new int[]{-9,-1,1,9};
            if (randomizePaths) Collections.shuffle(Arrays.asList(dirs));

            for (int curDir :dirs){
                int nextPos = curPos + curDir;
                //Check for screen wrapping.
                if(Math.abs(nextPos%9 - curPos%9) > 1) continue;

                if(possible.contains(nextPos)) {

                    path.add(nextPos);
                    possible.remove(nextPos);
                    curPos = nextPos;
                }
            }
        }

        return path;

    }

    public void setColors(int a) {
        this.colors = a;
    }

}
