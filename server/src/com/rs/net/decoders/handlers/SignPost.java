package com.rs.net.decoders.handlers;

import com.rs.game.WorldObject;
import com.rs.game.player.Player;

public class SignPost {

    public static boolean handleSigns(Player player, WorldObject object) {
        String name = SignPost.toMessage(object.getDefinitions().name);
        if (name == null)
            return false;

        SignPost.print(player, object, name);
        return true;
    }

    private static void print(Player player, WorldObject object, String message) {
        String option = object.getDefinitions().getOption(1);
        option = option.substring(0, option.length() - 1);
        player.getPackets().sendObjectMessage(0, 15263739, object, option + " " + message + ".");
        player.getPackets().sendResetMinimapFlag();
    }

    private static String toMessage(String original) {
        switch (original.toLowerCase()) {
            case "lumbridge castle":
                return "into Lumbridge";
            case "lumbridge":
                return "to Lumbridge";
            case "lumbridge farms":
                return "to Lumbridge Farms";
            case "graveyard and swamp, daemonheim (via boat)":
                return "to Lumbridge's graveyard/swamp and a boat to Daemonheim";
            case "al kharid":
                return "to Al Kharid";
            case "varrock":
                return "to Varrock";
            case "draynor":
                return "to Draynor and the Wizards' Tower";
            case "draynor village":
                return "to Draynor";
            case "draynor village, wizards' tower":
                return "into Draynor Village and towards the Wizards' Tower";
            case "draynor manor":
                return "to Draynor Manor";
            case "falador, port sarim, clan camp":
                return "to Falador, Port Sarim and the Clan Camp";
            case "wizards' tower":
                return "to the Wizards' Tower";
            case "port sarim":
                return "to Port Sarim and Mudskipper Point";
            case "clan camp, falador":
                return "to the Clan Camp and Falador";
            case "rimmington":
                return "to Rimmington";
            case "falador":
                return "to Falador";
            case "clan camp, port sarim":
                return "to the Clan Camp and Port Sarim";
            case "gunnarsgrunn":
                return "to Gunnarsgrunn and Varrock";
            case "taverley":
                return "to Taverley";
            case "goblin village":
                return "to Goblin Village";
            case "burthorpe":
                return "to Burthorpe and Death Plateau";
            case "white wolf mountain":
                return "to White Wolf Mountain (leading to Catherby)";
            case "burthorpe castle, death plateau":
                return "to Burthorpe Castle and Death Plateau";
            case "daemonheim (via boat)":
                return "to the boat to Daemonheim";
            case "catherby (over white wolf mountain)":
                return "over White Wolf Mountain to Catherby";
            case "falador, taverly":
                return "to Falador, Taverly and Burthorpe";
            case "edgeville monastery, dwarven mine":
                return "to the monastery, the Dwarven Mine and Ice Mountain";
            case "gunnarsgrunn, draynor":
                return "to Gunnarsgrunn and Draynor";
            case "ice mountain, dwarven mine":
                return "to Ice Mountain and the Dwarven Mine, also the Black Knights' Fortress";
            case "edgeville monastery, wilderness":
                return "to Edgeville Monastery and the Wilderness";
            case "cooks' guild":
                return "to the Cooks' Guild and on to the Grand Exchange";
            case "grand exchange":
                return "to the Grand Exchange";
            case "lumber yard":
                return "to the Lumber Yard and the Jolly Boar Inn";
            case "paterdomus":
                return "to Paterdomus and Morytania";
            case "champions' guild":
                return "to the Champions' Guild";
            case "lumbridge, al kharid":
                return "to Lumbridge and Al Kharid";
            case "exam centre, digsite":
                return "to the Exam Centre and Digsite";
            case "lumbridge, varrock":
                return "to Lumbridge and Varrock";
            case "al kharid, shantay pass":
                return "to Al Kharid and Shantay Pass, beyond which lies the Kharidian Desert";
            case "citharede abbey":
                return "to the Abbey of St.Elspeth";
            case "duel arena":
                return "to the Duel Arena";
            case "shantay pass":
                return "to Shantay Pass, beyond which lies the Kharidian Desert";
            case "lumbridge mill, varrock":
                return "to Lumbridge mill and Varrock";
        }

        return null;
    }
}