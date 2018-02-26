package me.TheFloatGoat.BukkitFlow.LevelCreation;

import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomLevelCreator {

    Plugin plugin;

    public RandomLevelCreator(Plugin plugin) {
        this.plugin = plugin;
    }

    Set<Integer> possible;

    public int[] createLevel() {

        System.out.println("randomlevelcreator.createlevel");

        int[] level = new int[54];

        //1. Create a number of random paths
        //2. Try to fill the gaps by appending the neighbouring squares, check with pathchecker
        //3.fill the rest with bars

        int colors = 6;

        possible = new HashSet();
        for(int i = 0; i<54; i++) {
            possible.add(i);
        }

        for (int i = 0; i <= colors && possible.size() > 2; i++) {

            Set<Integer> path = newPath();
            for(int a: path) {
                level[a] = i;
            }
        }

        return level;

    }

    Set<Integer> newPath() {

        Set<Integer> path = new HashSet<>();

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
