package com.rs.game.player.content;

import com.rs.game.player.Player;

/**
 * @author Nexon | Fuzen Seth
 */
public class AvatarShop {
public static int mainShop = 1;

           public static void openShop(Player player) {
                      player.getInterfaceManager().sendInterface(mainShop);
               player.sm("You have opened the avatar shop.");
           }
}
