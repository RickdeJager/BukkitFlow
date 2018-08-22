# BukkitFlow
BukkitFlow a bukkit port for \"Flow Free\". It works by using the drag feature in the inventory to simulate drawing a line in the game. The game comes with a handful of levels, but more can be added using the level creator which also has the ability to create random levels.  

## Installation
To install the plugin, get the latest build from [spigotmc](https://www.spigotmc.org/resources/bukkitflow.60129/) or build it from source.

## Commands

* \/bukkitflow - Prints the help page (/bf also works).
* \/bukkitflow score - Shows your current level.
* \/bukkitflow [ID] - To play level [ID].
* \/bukkitflow levels help - Show all command related to importing and creating levels.
* \/bukkitflow levels import - Resets the first 'n' levels by the ones that come with the plugin.
* \/bukkitflow levels create - Opens the level creation inventory.

## Default Permissions
```YAML
permissions:
  bukkitflow.*:
    description: Gives access to all BukkitFLow commands
    children:
        bukkitflow.create: true
        bukkitflow.play: true
        bukkitflow.score: true
  bukkitflow.create:
    description: Allows you to create a new level
    default: op
  bukkitflow.score:
    description: Allows you to check your score
    default: true
  bukkitflow.play:
    description: Allows you to play levels
    default: true
```

## How it works
When you drag to evenly distribute items in MC, the server gets notified by an event on release. This however does not tell the server in what order you dragged your mouse. Therefore I have to check if the combination of inventory slots contains a valid path, since I don't know what the actual path looks like.  
To do this I simply start at one of the fixed points and recursively try to follow all possible paths to see if I end up in the other fixed point. While this may seem inefficient, in practice most paths get terminated quickly since the inventory is quite small.  
  
The random level creator is using a very simplified version of the algorithm described in [this paper](www.mdpi.com/1999-4893/5/2/176/pdf). Basically, I generate a path by picking a starting point and adding random directions to it until it reaches a certain length or it has nowhere to go.  After drawing 'n' paths, I try to fill any empty spaces next to a fixed point by moving the fixed point into that space and extending the path by one. Finally, if there are any spaces left I fill them with 'barriers' such that the level is still playable.


## Screenshots

### Level Creation
![alt-text](https://i.imgur.com/iu9AXsj.png)
### Game Screen
![alt-text](https://i.imgur.com/wEGzwr9.png)
