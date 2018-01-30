package me.TheFloatGoat.BukkitFlow;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class PathChecker {

    boolean solved = false;
    ItemStack[] contents;
    short colorID;
    int anchorPoint = 0;

    public boolean CheckInventory(Inventory inventory, Set<Integer> slots, short color){

        contents = inventory.getContents();
        colorID = color;

        //Find the first anchor point matching that color

        for(int i = 0; i<inventory.getSize(); i++) {
            if(contents[i]!= null) {
                if (contents[i].getDurability() == colorID && contents[i].getType() == Material.STAINED_GLASS_PANE) {
                    anchorPoint = i;
                    break;
                }
            }
        }

        PathChecker(slots, anchorPoint);

        return solved;
    }

    /**
     * This method will recursively check if the entered path is a valid path.
     * @param set   This set should contain all remaining options in the path
     * @param curPos    The current position in the inventory
     */
    private void PathChecker(Set<Integer> set, int curPos) {

        if(!solved) {

            if(set.size() == 0) {

                for(int i = -3; i<=3; i+=2) {                           //A great hack-job to check up-left-right-down

                    int nextPos = curPos + (int)(Math.signum(i)*i*i);   //-9, -1, 1, 9
                    //Check for screen wrapping.
                    if(Math.abs(nextPos%9 - curPos%9) > 1) continue;

                    if(nextPos < contents.length && nextPos >= 0) {     //If nextPos is within the current inventory:

                        if(contents[nextPos] != null && nextPos != anchorPoint) {

                            if(contents[nextPos].getType() == Material.STAINED_GLASS_PANE
                                    && contents[nextPos].getDurability() == colorID) solved = true;
                        }
                    }
                }

            }else {

                for(int i = -3; i<=3; i+=2) {

                    int nextPos = curPos + (int) (Math.signum(i) * i * i);
                    //Check for screen wrapping.
                    if(Math.abs(nextPos%9 - curPos%9) > 1) continue;

                    if(set.contains(nextPos)) {

                        Set<Integer> nextSet = new HashSet<>();
                        nextSet.addAll(set);
                        nextSet.remove(nextPos);
                        PathChecker(nextSet, nextPos);

                    }
                }

            }

        }
    }

    public boolean win(Inventory inventory, Set<Integer> set) {

        ItemStack[] contents = inventory.getContents();

        for(int i = 0; i < inventory.getSize(); i++) {

            if(!set.contains(i) && (contents[i] == null || contents[i].getType() == Material.AIR)) {

                return false;
            }
        }

        return true;
    }
}
