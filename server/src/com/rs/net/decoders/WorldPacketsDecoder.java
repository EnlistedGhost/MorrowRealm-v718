package com.rs.net.decoders;

import com.rs.Settings;
import com.rs.content.utils.MoneyPouch;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.clanwars.ClanWars;
import com.rs.game.minigames.creations.StealingCreation;
import com.rs.game.npc.NPC;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.npc.familiar.Familiar.SpecialAttack;
import com.rs.game.player.CoordsEvent;
import com.rs.game.player.Inventory;
import com.rs.game.player.LogicPacket;
import com.rs.game.player.Player;
import com.rs.game.player.PublicChatMessage;
import com.rs.game.player.QuickChatMessage;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.game.player.actions.PlayerFollow;
import com.rs.game.player.content.Commands;
import com.rs.game.player.content.DisplayNameAction;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.Magic;
import com.rs.game.player.content.Notes.Note;
import com.rs.game.player.content.Shop;
import com.rs.game.player.content.SkillCapeCustomizer;
//import com.rs.game.player.content.custom.PlayerLoginTimeout;
import com.guardian.ItemManager;
import com.rs.game.player.content.interfaces.PlayerStats;
import com.rs.io.InputStream;
import com.rs.net.Session;
import com.rs.net.decoders.handlers.ButtonHandler;
import com.rs.net.decoders.handlers.InventoryOptionsHandler;
import com.rs.net.decoders.handlers.NPCHandler;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.utils.DisplayNames;
import com.rs.utils.ItemExamines;
import com.rs.utils.Logger;
import com.rs.utils.Utils;
import com.rs.utils.huffman.Huffman;

public final class WorldPacketsDecoder extends Decoder {

	private static final byte[] PACKET_SIZES = new byte[104];

	private final static int WALKING_PACKET = 8;
	private final static int MINI_WALKING_PACKET = 58;
	private final static int AFK_PACKET = -1;
	public final static int ACTION_BUTTON1_PACKET = 14;
	public final static int ACTION_BUTTON2_PACKET = 67;
	public final static int ACTION_BUTTON3_PACKET = 5;
	public final static int ACTION_BUTTON4_PACKET = 55;
	public final static int ACTION_BUTTON5_PACKET = 68;
	public final static int ACTION_BUTTON6_PACKET = 90;
	public final static int ACTION_BUTTON7_PACKET = 6;
	public final static int ACTION_BUTTON8_PACKET = 32;
	public final static int ACTION_BUTTON9_PACKET = 27;
	public final static int WORLD_MAP_CLICK = 38;
	public final static int ACTION_BUTTON10_PACKET = 96;
	public final static int RECEIVE_PACKET_COUNT_PACKET = 33;
	private final static int MAGIC_ON_ITEM_PACKET = -1;
	private final static int PLAYER_OPTION_4_PACKET = 17;
	private final static int PLAYER_OPTION_5_PACKET = 77;
	private final static int MOVE_CAMERA_PACKET = 103;
	private final static int INTERFACE_ON_OBJECT = 37;
	private final static int CLICK_PACKET = -1;
	private final static int MOUVE_MOUSE_PACKET = -1;
	private final static int KEY_TYPED_PACKET = -1;
	private final static int CLOSE_INTERFACE_PACKET = 54;
	private final static int COMMANDS_PACKET = 60;
	private final static int ITEM_ON_ITEM_PACKET = 3;
	private final static int IN_OUT_SCREEN_PACKET = -1;
	private final static int DONE_LOADING_REGION_PACKET = 30;
	private final static int PING_PACKET = 21;
	private final static int SCREEN_PACKET = 98;
	private final static int CHAT_TYPE_PACKET = 83;
	private final static int CHAT_PACKET = 53;
	private final static int PUBLIC_QUICK_CHAT_PACKET = 86;
	private final static int ADD_FRIEND_PACKET = 89;
	private final static int ADD_IGNORE_PACKET = 4;
	private final static int REMOVE_IGNORE_PACKET = 73;
	private final static int JOIN_FRIEND_CHAT_PACKET = 36;
	private final static int CHANGE_FRIEND_CHAT_PACKET = 22;
	private final static int KICK_FRIEND_CHAT_PACKET = 74;
	private final static int REMOVE_FRIEND_PACKET = 24;
	private final static int SEND_FRIEND_MESSAGE_PACKET = 82;
	private final static int SEND_FRIEND_QUICK_CHAT_PACKET = 0;
	private final static int OBJECT_CLICK1_PACKET = 26;
	private final static int OBJECT_CLICK2_PACKET = 59;
	private final static int OBJECT_CLICK3_PACKET = 40;
	private final static int OBJECT_CLICK4_PACKET = 23;
	private final static int OBJECT_CLICK5_PACKET = 80;
	private final static int OBJECT_EXAMINE_PACKET = 25;
	private final static int NPC_CLICK1_PACKET = 31;
	private final static int NPC_CLICK2_PACKET = 101;
	private final static int NPC_CLICK3_PACKET = 34;
	private final static int ATTACK_NPC = 20;
	private final static int PLAYER_OPTION_1_PACKET = 42;
	private final static int PLAYER_OPTION_2_PACKET = 46;
	private final static int ITEM_TAKE_PACKET = 57;
	private final static int DIALOGUE_CONTINUE_PACKET = 72;
	private final static int ENTER_INTEGER_PACKET = 81;
	private final static int ENTER_LONGSTRING_PACKET = 48;
	private final static int ENTER_NAME_PACKET = 29;
	private final static int ENTER_STRING_PACKET = -1;
	private final static int SWITCH_INTERFACE_ITEM_PACKET = 76;
	private final static int INTERFACE_ON_PLAYER = 50;
	private final static int INTERFACE_ON_NPC = 66;
	private final static int COLOR_ID_PACKET = 97;
	private static final int NPC_EXAMINE_PACKET = 9;
	private final static int REPORT_ABUSE_PACKET = -1;
	private final static int ITEM_ON_FLOOR_EXAMINE = 102;

	static {
		loadPacketSizes();
	}

	public static void loadPacketSizes() {
		PACKET_SIZES[0] = -1;
		PACKET_SIZES[1] = -2;
		PACKET_SIZES[2] = -1;
		PACKET_SIZES[3] = 16;
		PACKET_SIZES[4] = -1;
		PACKET_SIZES[5] = 8;
		PACKET_SIZES[6] = 8;
		PACKET_SIZES[7] = 3;
		PACKET_SIZES[8] = -1;
		PACKET_SIZES[9] = 3;
		PACKET_SIZES[10] = -1;
		PACKET_SIZES[11] = -1;
		PACKET_SIZES[12] = -1;
		PACKET_SIZES[13] = 7;
		PACKET_SIZES[14] = 8;
		PACKET_SIZES[15] = 6;
		PACKET_SIZES[16] = 2;
		PACKET_SIZES[17] = 3;
		PACKET_SIZES[18] = -1;
		PACKET_SIZES[19] = -2;
		PACKET_SIZES[20] = 3;
		PACKET_SIZES[21] = 0;
		PACKET_SIZES[22] = -1;
		PACKET_SIZES[23] = 9;
		PACKET_SIZES[24] = -1;
		PACKET_SIZES[25] = 9;
		PACKET_SIZES[26] = 9;
		PACKET_SIZES[27] = 8;
		PACKET_SIZES[28] = 4;
		PACKET_SIZES[29] = -1;
		PACKET_SIZES[30] = 0;
		PACKET_SIZES[31] = 3;
		PACKET_SIZES[32] = 8;
		PACKET_SIZES[33] = 4;
		PACKET_SIZES[34] = 3;
		PACKET_SIZES[35] = -1;
		PACKET_SIZES[36] = -1;
		PACKET_SIZES[37] = 17;
		PACKET_SIZES[38] = 4;
		PACKET_SIZES[39] = 4;
		PACKET_SIZES[40] = 9;
		PACKET_SIZES[41] = -1;
		PACKET_SIZES[42] = 3;
		PACKET_SIZES[43] = 7;
		PACKET_SIZES[44] = -2;
		PACKET_SIZES[45] = 7;
		PACKET_SIZES[46] = 3;
		PACKET_SIZES[47] = 4;
		PACKET_SIZES[48] = -1;
		PACKET_SIZES[49] = 3;
		PACKET_SIZES[50] = 11;
		PACKET_SIZES[51] = 3;
		PACKET_SIZES[52] = -1;
		PACKET_SIZES[53] = -1;
		PACKET_SIZES[54] = 0;
		PACKET_SIZES[55] = 8;
		PACKET_SIZES[56] = 3;
		PACKET_SIZES[57] = 7;
		PACKET_SIZES[58] = -1;
		PACKET_SIZES[59] = 9;
		PACKET_SIZES[60] = -1;
		PACKET_SIZES[61] = 7;
		PACKET_SIZES[62] = 7;
		PACKET_SIZES[63] = 12;
		PACKET_SIZES[64] = 4;
		PACKET_SIZES[65] = 3;
		PACKET_SIZES[66] = 11;
		PACKET_SIZES[67] = 8;
		PACKET_SIZES[68] = 8;
		PACKET_SIZES[69] = 15;
		PACKET_SIZES[70] = 1;
		PACKET_SIZES[71] = 2;
		PACKET_SIZES[72] = 6;
		PACKET_SIZES[73] = -1;
		PACKET_SIZES[74] = -1;
		PACKET_SIZES[75] = -2;
		PACKET_SIZES[76] = 16;
		PACKET_SIZES[77] = 3;
		PACKET_SIZES[78] = 1;
		PACKET_SIZES[79] = 3;
		PACKET_SIZES[80] = 9;
		PACKET_SIZES[81] = 4;
		PACKET_SIZES[82] = -2;
		PACKET_SIZES[83] = 1;
		PACKET_SIZES[84] = 1;
		PACKET_SIZES[85] = 3;
		PACKET_SIZES[86] = -1;
		PACKET_SIZES[87] = 4;
		PACKET_SIZES[88] = 3;
		PACKET_SIZES[89] = -1;
		PACKET_SIZES[90] = 8;
		PACKET_SIZES[91] = -2;
		PACKET_SIZES[92] = -1;
		PACKET_SIZES[93] = -1;
		PACKET_SIZES[94] = 9;
		PACKET_SIZES[95] = -2;
		PACKET_SIZES[96] = 8;
		PACKET_SIZES[97] = 2;
		PACKET_SIZES[98] = 6;
		PACKET_SIZES[99] = 2;
		PACKET_SIZES[100] = -2;
		PACKET_SIZES[101] = 3;
		PACKET_SIZES[102] = 7;
		PACKET_SIZES[103] = 4;
	}

	private Player player;
	private int chatType;

	public WorldPacketsDecoder(Session session, Player player) {
		super(session);
		this.player = player;
	}

	@Override
	public void decode(InputStream stream) {
		while (stream.getRemaining() > 0 && session.getChannel().isConnected() && !player.hasFinished()) {
			// Reset player timeout
        	//PlayerLoginTimeout.PlayerTimeoutReset();
			int packetId = stream.readPacket(player);
			if (packetId == 71) {
				/*final short itemId = (short) stream.readShort();
				final ItemOffer offer = new ItemOffer(player, itemId, player.offerType, player.getGESlot());
				offer.setPrice((int) (offer.getPrice() * 1.25));
				player.getGeOffers()[offer.getSlot()] = offer;
				offer.getOwner().getPackets().sendConfig(1109, offer.getId());
				offer.getOwner().getPackets().sendConfig(1110, 1);
				offer.getOwner().getPackets().sendConfig(1111, offer.getPrice());
				offer.getOwner().getPackets().sendConfig(1114, offer.getPrice());
				offer.getOwner().getPackets().sendConfig(1115, ItemManager.getPrice(offer.getId()) / 4);
				offer.getOwner().getPackets().sendConfig(1116, ItemManager.getPrice(offer.getId()) / 3);
				offer.getOwner().getPackets().sendIComponentText(105, 143, ItemExamines.getExamine(new Item(offer.getId())));*/
				break;
			}

			if (packetId >= PACKET_SIZES.length || packetId < 0) {
				if (Settings.DEBUG)
					System.out.println("PacketId " + packetId + " has fake packet id.");
				break;
			}
			int length = PACKET_SIZES[packetId];
			if (length == -1)
				length = stream.readUnsignedByte();
			else if (length == -2)
				length = stream.readUnsignedShort();
			else if (length == -3)
				length = stream.readInt();
			else if (length == -4) {
				length = stream.getRemaining();
				if (Settings.DEBUG)
					System.out.println("Invalid size for PacketId " + packetId + ". Size guessed to be " + length);
			}
			if (length > stream.getRemaining()) {
				length = stream.getRemaining();
				if (Settings.DEBUG)
					System.out.println("PacketId " + packetId + " has fake size. - expected size " + length);
			}
			int startOffset = stream.getOffset();
			processPackets(packetId, stream, length);
			stream.setOffset(startOffset + length);
		}
	}

	public static void decodeLogicPacket(final Player player, LogicPacket packet) {
		int packetId = packet.getId();
		InputStream stream = new InputStream(packet.getData());

		// Reset player timeout
        //PlayerLoginTimeout.PlayerTimeoutReset();
		
		if (Settings.ENABLE_PACKET_DEBUG && player.getRights() == 2) 
			player.getPackets().sendGameMessage("Logic Packet Id: "+packet.getId()+"");
		
		if (packetId == PLAYER_OPTION_5_PACKET) {
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player other = World.getPlayers().get(playerIndex);
		    if (other == null || other.isDead() || other.hasFinished() || !player.getMapRegionsIds().contains(other.getRegionId()))
		        return;
		    if (player.isLocked())
		        return;
		    if (!other.withinDistance(player, 14)) {
		        player.getPackets().sendGameMessage("Unable to find target "+other.getDisplayName());
		        return;
		    }
			player.getDialogueManager().startDialogue("ModPanel", playerIndex);
		}
		if (packetId == PlayerStats.PACKET_ID) {
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player other = World.getPlayers().get(playerIndex);
			if (other == null || other.isDead() || other.hasFinished() || !player.getMapRegionsIds().contains(other.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			if (player.getInterfaceManager().containsScreenInter()) {
				player.getPackets().sendGameMessage("The other player is busy.");
				return;
			}
			if (!other.withinDistance(player, 14)) {
				player.getPackets().sendGameMessage("Unable to find target "+other.getDisplayName());
				return;
			}
			if (player.getAttackedByDelay() + 10000 > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage("<col=B00000>You must wait 10 seconds after the end of combat to view stats.");
				return;
			}
			PlayerStats.openStatMenu(player, other);
		}
		
		if (packetId == WALKING_PACKET || packetId == MINI_WALKING_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion() || player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() > currentTime)
				return;
			if (player.getFreezeDelay() >= currentTime) {
				player.getPackets().sendGameMessage("A magical force prevents you from moving.");
				return;
			}
			int length = stream.getLength();
			/*if (packetId == MINI_WALKING_PACKET)
				length -= 13;*/
			int baseX = stream.readUnsignedShort128();
			boolean forceRun = stream.readUnsigned128Byte() == 1;
			int baseY = stream.readUnsignedShort128();
			int steps = (length - 5) / 2;
			if (steps > 25)
				steps = 25;
			player.stopAll();
			
			if(forceRun)
				player.setRun(forceRun);
			for (int step = 0; step < steps; step++)
				if (!player.addWalkSteps(baseX + stream.readUnsignedByte(),
					baseY + stream.readUnsignedByte(), 25, true))
					break;
		} else if (packetId == INTERFACE_ON_OBJECT) {
			boolean forceRun = stream.readByte128() == 1;
			int itemId = stream.readShortLE128();
			int y = stream.readShortLE128();
			int objectId = stream.readIntV2();
			int interfaceHash = stream.readInt();
			final int interfaceId = interfaceHash >> 16;
			int slot = stream.readShortLE();
			int x = stream.readShort128();
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() >= currentTime || player.getEmotesManager().getNextEmoteEnd() >= currentTime)
				return;
			final WorldTile tile = new WorldTile(x, y, player.getPlane());
			int regionId = tile.getRegionId();
			if (!player.getMapRegionsIds().contains(regionId))
				return;
			WorldObject mapObject = World.getRegion(regionId).getObject(objectId, tile);
			if (mapObject == null || mapObject.getId() != objectId)
				return;
			final WorldObject object = !player.isAtDynamicRegion() ? mapObject : new WorldObject(objectId, mapObject.getType(), mapObject.getRotation(), x, y, player.getPlane());
			final Item item = player.getInventory().getItem(slot);
			if (player.isDead() || Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (item == null || item.getId() != itemId)
				return;
			player.stopAll(false); // false
			if(forceRun)
				player.setRun(forceRun);
			switch (interfaceId) {
			case Inventory.INVENTORY_INTERFACE: // inventory
				ObjectHandler.handleItemOnObject(player, object, interfaceId, item);
				break;
			}
		} else if (packetId == PLAYER_OPTION_2_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion() || player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerFollow(p2));
		} else if (packetId == PLAYER_OPTION_4_PACKET) {
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished() || !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			player.stopAll(false);
			if(player.isCantTrade()) {
				player.getPackets().sendGameMessage("You are busy.");
				return;
			}
			if (player.getSkills().getTotalLevel(player) < 1) {
				player.sendMessage("You must have a total level of 1 to trade!");
				return;
			}
			if (player.isWikiEditor() || p2.isWikiEditor()) {
				player.getPackets().sendGameMessage("<col=FF0000>Trade request has been denied.");
				return;
			}
			if (player.isTradeLocked() || p2.isTradeLocked()) {
				player.getPackets().sendGameMessage("<col=FF0000>Trade request has been denied.");
				return;
			}
			if (p2.getInterfaceManager().containsScreenInter()) {
				player.getPackets().sendGameMessage("The other player is busy.");
				return;
			}
			if (!p2.withinDistance(player, 14)) {
				player.getPackets().sendGameMessage("Unable to find target "+p2.getDisplayName());
				return;
			}

			if (p2.getAttributes().get("TradeTarget") == player) {
				p2.getAttributes().remove("TradeTarget");
				player.getTrade().openTrade(p2);
				p2.getTrade().openTrade(player);
				return;
			}
			player.getAttributes().put("TradeTarget", p2);
			player.getPackets().sendGameMessage("Sending " + p2.getDisplayName() + " a request...");
			p2.getPackets().sendTradeRequestMessage(player);
		} else if (packetId == PLAYER_OPTION_1_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int playerIndex = stream.readUnsignedShortLE128();
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis()
					|| !player.getControlerManager().canPlayerOption1(p2))
				return;
			int weaponId_snbl = player.getEquipment().getWeaponId();
			if (!player.isCanPvp()) {
				if (weaponId_snbl != 10501) {// snowball handling
					player.getPackets().sendGameMessage("You are not in a PvP zone.");
					return;
				}
			}
			if (!player.getControlerManager().canAttack(p2))
				return;

			if (!player.isCanPvp() || !p2.isCanPvp()) {
				if (weaponId_snbl != 10501) {// snowball handling
					player.getPackets().sendGameMessage("You can only attack players in a player-vs-player area.");
					return;
				}
			}
			if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
				if (player.getAttackedBy() != p2 && player.getAttackedByDelay() > Utils.currentTimeMillis()) {
					if (weaponId_snbl != 10501) {// snowball handling
						player.getPackets().sendGameMessage("You are already in combat.");
						return;
					}
				}
				if (p2.getAttackedBy() != player && p2.getAttackedByDelay() > Utils.currentTimeMillis()) {
					if (p2.getAttackedBy() instanceof NPC) {
						p2.setAttackedBy(player);
					} else {
						if (weaponId_snbl != 10501) {// snowball handling
							player.getPackets().sendGameMessage("That player is already in combat.");
							return;
						}
					}
				}
			}
			player.stopAll(false);
			if (weaponId_snbl == 10501) {// Snowball combat handling
				player.getPackets().sendGameMessage("You pelt " + p2.getUsername() + " with a snowball!");
				player.getActionManager().setAction(new PlayerCombat(p2));
			} else {
				player.getActionManager().setAction(new PlayerCombat(p2));
			}
		} else if (packetId == ATTACK_NPC) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead()) {
				return;
			}
			if (player.getLockDelay() > Utils.currentTimeMillis()) {
				return;
			}
			int npcIndex = stream.readUnsignedShort128();
			boolean forceRun = stream.read128Byte() == 1;
			if(forceRun)
				player.setRun(forceRun);
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc.isDead() || npc.hasFinished()
					|| !player.getMapRegionsIds().contains(npc.getRegionId())
					|| !npc.getDefinitions().hasAttackOption()) {
				return;
			}
			if (!player.getControlerManager().canAttack(npc)) {
				return;
			}
			if (npc instanceof Familiar) {
				Familiar familiar = (Familiar) npc;
				if (familiar == player.getFamiliar()) {
					player.getPackets().sendGameMessage("You can't attack your own familiar.");
					return;
				}
				if (!familiar.canAttack(player)) {
					player.getPackets().sendGameMessage("You can't attack this npc.");
					return;
				}
			} else if (!npc.isForceMultiAttacked()) {
				if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
					if (player.getAttackedBy() != npc && player.getAttackedByDelay() > Utils.currentTimeMillis()) {
						player.getPackets().sendGameMessage("You are already in combat.");
						return;
					}
					if (npc.getAttackedBy() != player
							&& npc.getAttackedByDelay() > Utils
							.currentTimeMillis()) {
						player.getPackets().sendGameMessage(
								"This npc is already in combat.");
						return;
					}
				}
			}
			player.stopAll(false);
			player.getActionManager().setAction(new PlayerCombat(npc));
		} else if (packetId == INTERFACE_ON_PLAYER) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			@SuppressWarnings("unused")
			int junk1 = stream.readUnsignedShort();
			int playerIndex = stream.readUnsignedShort();
			int interfaceHash = stream.readIntV2();
			@SuppressWarnings("unused")
			int junk2 = stream.readUnsignedShortLE128();
			@SuppressWarnings("unused")
			boolean unknown = stream.read128Byte() == 1;
			int interfaceId = interfaceHash >> 16;
			int componentId = interfaceHash - (interfaceId << 16);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (componentId == 65535)
				componentId = -1;
			if (componentId != -1
					&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId)
				return;
			Player p2 = World.getPlayers().get(playerIndex);
			if (p2 == null || p2.isDead() || p2.hasFinished()
					|| !player.getMapRegionsIds().contains(p2.getRegionId()))
				return;
			player.stopAll(false);
			switch (interfaceId) {
			case 662:
			case 747:
				if (player.getFamiliar() == null)
					return;
				player.resetWalkSteps();
				if ((interfaceId == 747 && componentId == 15)
						|| (interfaceId == 662 && componentId == 65)
						|| (interfaceId == 662 && componentId == 74)
						|| interfaceId == 747 && componentId == 18) {
					if ((interfaceId == 662 && componentId == 74
							|| interfaceId == 747 && componentId == 24 || interfaceId == 747
							&& componentId == 18)) {
						if (player.getFamiliar().getSpecialAttack() != SpecialAttack.ENTITY)
							return;
					}
					if (!player.isCanPvp() || !p2.isCanPvp()) {
						player.getPackets()
						.sendGameMessage(
								"You can only attack players in a player-vs-player area.");
						return;
					}
					if (!player.getFamiliar().canAttack(p2)) {
						player.getPackets()
						.sendGameMessage(
								"You can only use your familiar in a multi-zone area.");
						return;
					} else {
						player.getFamiliar().setSpecial(
								interfaceId == 662 && componentId == 74
								|| interfaceId == 747
								&& componentId == 18);
						player.getFamiliar().setTarget(p2);
					}
				}
				break;
			case 193:
				switch (componentId) {
				case 28:
				case 32:
				case 24:
				case 20:
				case 30:
				case 34:
				case 26:
				case 22:
				case 29:
				case 33:
				case 25:
				case 21:
				case 31:
				case 35:
				case 27:
				case 23:
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(p2
								.getCoordFaceX(p2.getSize()), p2
								.getCoordFaceY(p2.getSize()), p2.getPlane()));
						if (!player.getControlerManager().canAttack(p2))
							return;
						if (!player.isCanPvp() || !p2.isCanPvp()) {
							player.getPackets()
							.sendGameMessage(
									"You can only attack players in a player-vs-player area.");
							return;
						}
						if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
							if (player.getAttackedBy() != p2
									&& player.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								player.getPackets()
								.sendGameMessage(
										"That "
												+ (player
														.getAttackedBy() instanceof Player ? "player"
																: "npc")
																+ " is already in combat.");
								return;
							}
							if (p2.getAttackedBy() != player
									&& p2.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								if (p2.getAttackedBy() instanceof NPC) {
									p2.setAttackedBy(player); // changes enemy
									// to player,
									// player has
									// priority over
									// npc on single
									// areas
								} else {
									player.getPackets()
									.sendGameMessage(
											"That player is already in combat.");
									return;
								}
							}
						}
						player.getActionManager()
						.setAction(new PlayerCombat(p2));
					}
					break;
				}
			case 192:
				switch (componentId) {
				case 25: // air strike
				case 28: // water strike
				case 30: // earth strike
				case 32: // fire strike
				case 34: // air bolt
				case 39: // water bolt
				case 42: // earth bolt
				case 45: // fire bolt
				case 49: // air blast
				case 52: // water blast
				case 58: // earth blast
				case 63: // fire blast
				case 70: // air wave
				case 73: // water wave
				case 77: // earth wave
				case 80: // fire wave
				case 86: // teleblock
				case 84: // air surge
				case 87: // water surge
				case 89: // earth surge
				case 91: // fire surge
				case 99: // storm of armadyl
				case 36: // bind
				case 66: // Sara Strike
				case 67: // Guthix Claws
				case 68: // Flame of Zammy
				case 55: // snare
				case 81: // entangle
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(p2
								.getCoordFaceX(p2.getSize()), p2
								.getCoordFaceY(p2.getSize()), p2.getPlane()));
						if (!player.getControlerManager().canAttack(p2))
							return;
						if (!player.isCanPvp() || !p2.isCanPvp()) {
							player.getPackets()
							.sendGameMessage(
									"You can only attack players in a player-vs-player area.");
							return;
						}
						if (!p2.isAtMultiArea() || !player.isAtMultiArea()) {
							if (player.getAttackedBy() != p2
									&& player.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								player.getPackets()
								.sendGameMessage(
										"That "
												+ (player
														.getAttackedBy() instanceof Player ? "player"
																: "npc")
																+ " is already in combat.");
								return;
							}
							if (p2.getAttackedBy() != player
									&& p2.getAttackedByDelay() > Utils
									.currentTimeMillis()) {
								if (p2.getAttackedBy() instanceof NPC) {
									p2.setAttackedBy(player); // changes enemy
									// to player,
									// player has
									// priority over
									// npc on single
									// areas
								} else {
									player.getPackets()
									.sendGameMessage(
											"That player is already in combat.");
									return;
								}
							}
						}
						player.getActionManager()
						.setAction(new PlayerCombat(p2));
					}
					break;
				}
				break;
			}
			if (Settings.DEBUG)
				System.out.println("Spell:" + componentId);
		} else if (packetId == INTERFACE_ON_NPC) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			if (player.getLockDelay() > Utils.currentTimeMillis())
				return;
			@SuppressWarnings("unused")
			boolean unknown = stream.readByte() == 1;
			int interfaceHash = stream.readInt();
			int npcIndex = stream.readUnsignedShortLE();
			int interfaceSlot = stream.readUnsignedShortLE128();
			@SuppressWarnings("unused")
			int junk2 =stream.readUnsignedShortLE();
			int interfaceId = interfaceHash >> 16;
			int componentId = interfaceHash - (interfaceId << 16);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId)
				return;
			if (!player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if (componentId == 65535)
				componentId = -1;
			if (componentId != -1
					&& Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId)
				return;
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc.isDead() || npc.hasFinished()
					|| !player.getMapRegionsIds().contains(npc.getRegionId()))
				return;
			player.stopAll(false);
			if (interfaceId != Inventory.INVENTORY_INTERFACE) {
				if (!npc.getDefinitions().hasAttackOption()) {
					player.getPackets().sendGameMessage(
							"You can't attack this npc.");
					return;
				}
			}
			switch (interfaceId) {
			case Inventory.INVENTORY_INTERFACE:
				Item item = player.getInventory().getItem(interfaceSlot);
				if (item == null || !player.getControlerManager().processItemOnNPC(npc, item))
					return;
				InventoryOptionsHandler.handleItemOnNPC(player, npc, item);
				break;
			case 662:
			case 747:
				if (player.getFamiliar() == null)
					return;
				player.resetWalkSteps();
				if ((interfaceId == 747 && componentId == 15)
						|| (interfaceId == 662 && componentId == 65)
						|| (interfaceId == 662 && componentId == 74)
						|| interfaceId == 747 && componentId == 18
						|| interfaceId == 747 && componentId == 24) {
					if ((interfaceId == 662 && componentId == 74 || interfaceId == 747
							&& componentId == 18)) {
						if (player.getFamiliar().getSpecialAttack() != SpecialAttack.ENTITY)
							return;
					}
					if(npc instanceof Familiar) {
						Familiar familiar = (Familiar) npc;
						if (familiar == player.getFamiliar()) {
							player.getPackets().sendGameMessage("You can't attack your own familiar.");
							return;
						}
						if (!player.getFamiliar().canAttack(familiar.getOwner())) {
							player.getPackets().sendGameMessage("You can only attack players in a player-vs-player area.");
							return;
						}
					}
					if (!player.getFamiliar().canAttack(npc)) {
						player.getPackets()
						.sendGameMessage(
								"You can only use your familiar in a multi-zone area.");
						return;
					} else {
						player.getFamiliar().setSpecial(
								interfaceId == 662 && componentId == 74
								|| interfaceId == 747
								&& componentId == 18);
						player.getFamiliar().setTarget(npc);
					}
				}
				break;
			case 193:
				switch (componentId) {
				case 28:
				case 32:
				case 24:
				case 20:
				case 30:
				case 34:
				case 26:
				case 22:
				case 29:
				case 33:
				case 25:
				case 21:
				case 31:
				case 35:
				case 27:
				case 23:
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(npc
								.getCoordFaceX(npc.getSize()), npc
								.getCoordFaceY(npc.getSize()), npc.getPlane()));
						if (!player.getControlerManager().canAttack(npc))
							return;
						if (npc instanceof Familiar) {
							Familiar familiar = (Familiar) npc;
							if (familiar == player.getFamiliar()) {
								player.getPackets().sendGameMessage(
										"You can't attack your own familiar.");
								return;
							}
							if (!familiar.canAttack(player)) {
								player.getPackets().sendGameMessage(
										"You can't attack this npc.");
								return;
							}
						} else if (!npc.isForceMultiAttacked()) {
							if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
								if (player.getAttackedBy() != npc
										&& player.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"You are already in combat.");
									return;
								}
								if (npc.getAttackedBy() != player
										&& npc.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"This npc is already in combat.");
									return;
								}
							}
						}
						player.getActionManager().setAction(
								new PlayerCombat(npc));
					}
					break;
				}
			case 192:
				switch (componentId) {
				case 25: // air strike
				case 28: // water strike
				case 30: // earth strike
				case 32: // fire strike
				case 34: // air bolt
				case 39: // water bolt
				case 42: // earth bolt
				case 45: // fire bolt
				case 49: // air blast
				case 52: // water blast
				case 58: // earth blast
				case 63: // fire blast
				case 70: // air wave
				case 73: // water wave
				case 77: // earth wave
				case 80: // fire wave
				case 84: // air surge
				case 87: // water surge
				case 89: // earth surge
				case 66: // Sara Strike
				case 67: // Guthix Claws
				case 68: // Flame of Zammy
				case 93:
				case 91: // fire surge
				case 99: // storm of Armadyl
				case 36: // bind
				case 55: // snare
				case 81: // entangle
					if (Magic.checkCombatSpell(player, componentId, 1, false)) {
						player.setNextFaceWorldTile(new WorldTile(npc
								.getCoordFaceX(npc.getSize()), npc
								.getCoordFaceY(npc.getSize()), npc.getPlane()));
						if (!player.getControlerManager().canAttack(npc))
							return;
						if (npc instanceof Familiar) {
							Familiar familiar = (Familiar) npc;
							if (familiar == player.getFamiliar()) {
								player.getPackets().sendGameMessage(
										"You can't attack your own familiar.");
								return;
							}
							if (!familiar.canAttack(player)) {
								player.getPackets().sendGameMessage(
										"You can't attack this npc.");
								return;
							}
						} else if (!npc.isForceMultiAttacked()) {
							if (!npc.isAtMultiArea() || !player.isAtMultiArea()) {
								if (player.getAttackedBy() != npc
										&& player.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"You are already in combat.");
									return;
								}
								if (npc.getAttackedBy() != player
										&& npc.getAttackedByDelay() > Utils
										.currentTimeMillis()) {
									player.getPackets().sendGameMessage(
											"This npc is already in combat.");
									return;
								}
							}
						}
						player.getActionManager().setAction(
								new PlayerCombat(npc));
					}
					break;
				}
				break;
			}
			if (Settings.DEBUG)
				System.out.println("Spell:" + componentId);
		} else if (packetId == NPC_CLICK1_PACKET)
			NPCHandler.handleOption1(player, stream);
		else if (packetId == NPC_CLICK2_PACKET)
			NPCHandler.handleOption2(player, stream);
		else if (packetId == NPC_CLICK3_PACKET)
			NPCHandler.handleOption3(player, stream);
		else if (packetId == OBJECT_CLICK1_PACKET)
			ObjectHandler.handleOption(player, stream, 1);
		else if (packetId == OBJECT_CLICK2_PACKET)
			ObjectHandler.handleOption(player, stream, 2);
		else if (packetId == OBJECT_CLICK3_PACKET)
			ObjectHandler.handleOption(player, stream, 3);
		else if (packetId == OBJECT_CLICK4_PACKET)
			ObjectHandler.handleOption(player, stream, 4);
		else if (packetId == OBJECT_CLICK5_PACKET)
			ObjectHandler.handleOption(player, stream, 5);
		else if (packetId == ITEM_TAKE_PACKET) {
			if (!player.hasStarted() || !player.clientHasLoadedMapRegion()
					|| player.isDead())
				return;
			long currentTime = Utils.currentTimeMillis();
			if (player.getLockDelay() > currentTime)
				// || player.getFreezeDelay() >= currentTime)
				return;
			int y = stream.readUnsignedShort();
			int x = stream.readUnsignedShortLE();
			final int id = stream.readUnsignedShort();
			boolean forceRun = stream.read128Byte() == 1;
			final WorldTile tile = new WorldTile(x, y, player.getPlane());
			final int regionId = tile.getRegionId();
			if (!player.getMapRegionsIds().contains(regionId))
				return;
			final FloorItem item = World.getRegion(regionId).getGroundItem(id,
					tile, player);
			if (item == null)
				return;
			player.stopAll(false);
			if(forceRun)
				player.setRun(forceRun);
			player.setCoordsEvent(new CoordsEvent(tile, new Runnable() {
				@Override
				public void run() {
					final FloorItem item = World.getRegion(regionId).getGroundItem(id, tile, player);
					if (item == null)
						return;
			/*		if (player.getRights() > 0 || player.isSupporter()) 
						player.getPackets().sendGameMessage("This item was dropped by [Username] "+item.getOwner().getUsername()+ " [DiplayName] "+item.getOwner().getDisplayName());
			*/		player.setNextFaceWorldTile(tile);
					player.addWalkSteps(tile.getX(), tile.getY(), 1);
					World.removeGroundItem(player, item);
				}
			}, 1, 1));
		}
	}

	public void processPackets(final int packetId, InputStream stream, int length) {
		
		if (player.getRights() == 2 && player.isPacketDebug() && packetId != 21) {
			player.sendMessage("Packet Processed: "+packetId+", length: "+length+"");
		}
		
		player.setPacketsDecoderPing(Utils.currentTimeMillis());
		if (packetId == PING_PACKET) {
			// kk we ping :)
		} else if (packetId == MOUVE_MOUSE_PACKET) {
			// USELESS PACKET
		} else if (packetId == KEY_TYPED_PACKET) {
			// USELESS PACKET
			//
			player.afkTimer = Utils.currentTimeMillis() + (10*60*1000);//10 minute timeout login
			if (player.getRights() > 0) {
				// Users with rights (Moderator, Admin, Owner, BetaTester)
				// override the afk logout timer
				player.afkTimer = Utils.currentTimeMillis() + (2*60*60*1000);//2 hour timeout login
			}
		} else if (packetId == RECEIVE_PACKET_COUNT_PACKET) {
			// interface packets
			stream.readInt();
		} else if (packetId == ITEM_ON_ITEM_PACKET) {
			InventoryOptionsHandler.handleItemOnItem(player, stream);
		} else if (packetId == MAGIC_ON_ITEM_PACKET) {
			
		} else if (packetId == AFK_PACKET) {
			player.getSession().getChannel().close();
		} else if (packetId == CLOSE_INTERFACE_PACKET) {
			if (player.hasStarted() && !player.hasFinished() && !player.isRunning()) { //used for old welcome screen
				player.run();
				return;
			}
			player.stopAll();
		} else if (packetId == MOVE_CAMERA_PACKET) {
			player.afkTimer = Utils.currentTimeMillis() + (10*60*1000);//10 minute timeout login
			if (player.getRights() > 0) {
				// Users with rights (Moderator, Admin, Owner, BetaTester)
				// override the afk logout timer
				player.afkTimer = Utils.currentTimeMillis() + (2*60*60*1000);//2 hour timeout login
			}
			// not using it atm
			stream.readUnsignedShort();
			stream.readUnsignedShort();
		} else if (packetId == IN_OUT_SCREEN_PACKET) {
			player.afkTimer = Utils.currentTimeMillis() + (10*60*1000);//10 minute timeout login
			if (player.getRights() > 0) {
				// Users with rights (Moderator, Admin, Owner, BetaTester)
				// override the afk logout timer
				player.afkTimer = Utils.currentTimeMillis() + (2*60*60*1000);//2 hour timeout login
			}
			// not using this check because not 100% efficient
			@SuppressWarnings("unused")
			boolean inScreen = stream.readByte() == 1;
		} else if (packetId == SCREEN_PACKET) {
			int displayMode = stream.readUnsignedByte();
			player.setScreenWidth(stream.readUnsignedShort());
			player.setScreenHeight(stream.readUnsignedShort());
			@SuppressWarnings("unused")
			boolean switchScreenMode = stream.readUnsignedByte() == 1;
			if (!player.hasStarted() || player.hasFinished()
					|| displayMode == player.getDisplayMode() || !player.getInterfaceManager().containsInterface(742))
				return;
			player.setDisplayMode(displayMode);
			player.getInterfaceManager().removeAll();
			player.getInterfaceManager().sendInterfaces();
			player.getInterfaceManager().sendInterface(742);
		} else if (packetId == CLICK_PACKET) {
			int mouseHash = stream.readShortLE128();
			int mouseButton = mouseHash >> 15;
			int time = mouseHash - (mouseButton << 15);
			int positionHash = stream.readIntV1();
			int y = positionHash >> 16; // y;
			int x = positionHash - (y << 16); // x
			@SuppressWarnings("unused")
			boolean clicked;
			if (time <= 1 || x < 0 || x > player.getScreenWidth() || y < 0 || y > player.getScreenHeight()) {
				clicked = false;
				return;
			}
			player.afkTimer = Utils.currentTimeMillis() + (10*60*1000);//10 minute timeout login
			if (player.getRights() > 0) {
				// Users with rights (Moderator, Admin, Owner, BetaTester)
				// override the afk logout timer
				player.afkTimer = Utils.currentTimeMillis() + (2*60*60*1000);//2 hour timeout login
			}
			clicked = true;
		} else if (packetId == DIALOGUE_CONTINUE_PACKET) {
			int interfaceHash = stream.readInt();
			int junk = stream.readShort128();
			int interfaceId = interfaceHash >> 16;
			int buttonId = (interfaceHash & 0xFF);
			if (Utils.getInterfaceDefinitionsSize() <= interfaceId) {
				// hack, or server error or client error
				// player.getSession().getChannel().close();
				return;
			}
			if (!player.isRunning() || !player.getInterfaceManager().containsInterface(interfaceId))
				return;
			if(Settings.DEBUG)
				Logger.log(this, "Dialogue: "+interfaceId+", "+buttonId+", "+junk);
			int componentId = interfaceHash - (interfaceId << 16);
			player.getDialogueManager().continueDialogue(interfaceId,
					componentId);
		} else if (packetId == WORLD_MAP_CLICK) {
			int coordinateHash = stream.readInt();
			int x = coordinateHash >> 14;
			int y = coordinateHash & 0x3fff;
			int plane = coordinateHash >> 28;
            Integer hash  =  (Integer)player.getAttributes().get("worldHash");
			if (hash == null || coordinateHash != hash)
				player.getAttributes().put("worldHash", coordinateHash);
			else {
				player.getAttributes().remove("worldHash");
				player.getHintIconsManager().addHintIcon(x, y, plane, 20, 0, 2, -1, true);
				player.getPackets().sendConfig(1159, coordinateHash);
            }
		} else if (packetId == ACTION_BUTTON1_PACKET
				|| packetId == ACTION_BUTTON2_PACKET
				|| packetId == ACTION_BUTTON4_PACKET
				|| packetId == ACTION_BUTTON5_PACKET
				|| packetId == ACTION_BUTTON6_PACKET
				|| packetId == ACTION_BUTTON7_PACKET
				|| packetId == ACTION_BUTTON8_PACKET
				|| packetId == ACTION_BUTTON3_PACKET
				|| packetId == ACTION_BUTTON9_PACKET
				|| packetId == ACTION_BUTTON10_PACKET) {
			ButtonHandler.handleButtons(player, stream, packetId);
		} else if (packetId == ENTER_NAME_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			String value = stream.readString();
			if (value.equals(""))
				return;
			if (player.getAttributes().get("editing_shop_item") != null) {
				player.getAttributes().remove("editing_shop_item");
			}
			
			if (player.getInterfaceManager().containsInterface(1108)) 
				player.getFriendsIgnores().setChatPrefix(value);
			else if (player.getAttributes().get("yellname") == Boolean.TRUE) {
					player.getAttributes().put("yellname", Boolean.FALSE);
			} else if(player.getAttributes().get("grand_exchange_offer") == Boolean.TRUE) {
				
			} else if (player.getAttributes().get("yellcolor") == Boolean.TRUE) {
				if(value.length() != 6) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The HEX yell color you wanted to pick cannot be longer and shorter then 6.");
				} else if(Utils.containsInvalidCharacter(value) || value.contains("_")) {
					player.getDialogueManager().startDialogue("SimpleMessage", "The requested yell color can only contain numeric and regular characters.");
				} else {
					player.setYellColor(value);
					player.getDialogueManager().startDialogue("SimpleMessage", "Your yell color has been changed to <col="+player.getYellColor()+">"+player.getYellColor()+"</col>.");
				}
				player.getAttributes().put("yellcolor", Boolean.FALSE);
			} else if (player.getAttributes().get("view_name") == Boolean.TRUE) {
				player.getAttributes().remove("view_name");
				Player other = World.getPlayerByDisplayName(value);
				if (other == null) {
					player.getPackets().sendGameMessage("Couldn't find player.");
					return;
				}
				ClanWars clan = other.getCurrentFriendChat() != null ? other.getCurrentFriendChat().getClanWars() : null;
				if (clan == null) {
					player.getPackets().sendGameMessage("This player's clan is not in war.");
					return;
				}
				if (clan.getSecondTeam().getOwnerDisplayName() != other.getCurrentFriendChat().getOwnerDisplayName()) {
					player.getAttributes().put("view_prefix", 1);
				}
				player.getAttributes().put("view_clan", clan);
				ClanWars.enter(player);
			} else if (player.getAttributes().remove("setdisplay") != null) {
				if(Utils.invalidAccountName(Utils.formatPlayerNameForProtocol(value))) {
					player.getPackets().sendGameMessage("Invalid name!");
					return;
				}
				if(!DisplayNames.setDisplayName(player, value)) {
					player.getPackets().sendGameMessage("Name already in use!");
					return;
				}
			}
		} else if (packetId == ENTER_STRING_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			String value = stream.readString();
			if (value.equals(""))
				return;
			
			if (player.getAttributes().get("entering_note") == Boolean.TRUE) {
				player.getNotes().add(value);
				player.getNotes().refresh();
				player.getAttributes().put("entering_note", Boolean.FALSE);
				return;
			}
			
		} else if (packetId == ENTER_LONGSTRING_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			
			String value = stream.readString();
			
			if (value.equals(""))
				return;
			
			if (player.getAttributes().get("editing_note") == Boolean.TRUE) {
				Note edit = (Note) player.getAttributes().get("noteToEdit");
				edit.setText(value);
				player.getNotes().refresh();
				player.getAttributes().put("editing_note", Boolean.FALSE);
				return;
			}
			
			if (player.getAttributes().get("entering_note") == Boolean.TRUE) {
				player.getNotes().add(value);
				player.getNotes().refresh();
				player.getAttributes().put("entering_note", Boolean.FALSE);
				return;
			}
			
		} else if (packetId == ENTER_INTEGER_PACKET) {
			if (!player.isRunning() || player.isDead())
				return;
			int value = stream.readInt();
			
			if (value < 0)
				return;
			
			if ((player.getInterfaceManager().containsInterface(762) && player.getInterfaceManager().containsInterface(763)) || player.getInterfaceManager().containsInterface(11)) {
				if (value < 0) {
					return;
				}
				if (player.getAttributes().get("bank_isWithdraw") == Boolean.TRUE) {
					Integer bankSlot = (Integer) player.getAttributes().get("bank_item_X_Slot");
					player.getBank().withdrawItem(bankSlot, value);
					player.getBank().setLastX(value);
					player.getBank().refreshLastX();
					player.getAttributes().put("bank_isWithdraw", Boolean.FALSE);
					return;
				} else if (player.getAttributes().get("deposit_x_bank") == Boolean.TRUE) {
					Integer invSlot = (Integer) player.getAttributes().get("deposit_x_slot");
					Item item = player.getInventory().getItem(invSlot);
					if (!player.getInventory().containsItem(item.getId(), 1))
						return;
					player.getBank().depositItem(invSlot, value, true);
					player.getAttributes().put("deposit_x_bank", Boolean.FALSE);
				}
			} else if (player.getInterfaceManager().containsInterface(206)
					&& player.getInterfaceManager().containsInterface(207)) {
				if (value < 0)
					return;
				Integer pc_item_X_Slot = (Integer) player.getAttributes().remove("pc_item_X_Slot");
				if (pc_item_X_Slot == null)
					return;
				if (player.getAttributes().remove("pc_isRemove") != null)
					player.getPriceCheckManager().removeItem(pc_item_X_Slot, value);
				else
					player.getPriceCheckManager().addItem(pc_item_X_Slot, value);
			} else if (player.getAttributes().remove("money_remove") != null) {
				int amnt = value;
				MoneyPouch.removeMoney(amnt, player, true);
			} else if (player.getAttributes().get("bones") != null) {
				int amnt = value;
				int itemId = ((Integer) player.getAttributes().remove("bitem")).intValue();
			} else if (player.getInterfaceManager().containsInterface(671)
					&& player.getInterfaceManager().containsInterface(665)) {
				if (player.getFamiliar() == null
						|| player.getFamiliar().getBob() == null)
					return;
				if (value < 0)
					return;
				Integer bob_item_X_Slot = (Integer) player
						.getAttributes().remove("bob_item_X_Slot");
				if (bob_item_X_Slot == null)
					return;
				if (player.getAttributes().remove("bob_isRemove") != null)
					player.getFamiliar().getBob()
					.removeItem(bob_item_X_Slot, value);
				else
					player.getFamiliar().getBob().addItem(bob_item_X_Slot, value);
			} else if (player.getInterfaceManager().containsInterface(335)
					&& player.getInterfaceManager().containsInterface(336)) {
				if (value < 0)
					return;
				Integer trade_item_X_Slot = (Integer) player
						.getAttributes().remove("trade_item_X_Slot");
				if (trade_item_X_Slot == null)
					return;
				if (player.getAttributes().remove("trade_isRemove") != null)
					player.getTrade().removeItem(trade_item_X_Slot, value);
				else
					player.getTrade().addItem(trade_item_X_Slot, value);
			} else if (player.getAttributes().get("skillId") != null) {
				if (player.getEquipment().wearingArmour()) {
					player.getDialogueManager().finishDialogue();
					player.getDialogueManager().startDialogue("SimpleMessage", "You cannot do this while having armour on!");
					return;
				}
				int skillId = (Integer) player.getAttributes().remove("skillId");
				if (skillId == Skills.HITPOINTS && value <= 9)
					value = 10;
				else if (value < 1)
					value = 1;
				else if (value > 99)
					value = 99;
				player.getSkills().set(skillId, value);
				player.getSkills().setXp(skillId, Skills.getXPForLevel(value));
				player.getAppearence().generateAppearenceData();
				player.getDialogueManager().finishDialogue();
			} else if (player.getAttributes().get("kilnX") != null) {
				int index = (Integer) player.getAttributes().get("scIndex");
				int componentId = (Integer) player.getAttributes().get("scComponentId");
				int itemId = (Integer) player.getAttributes().get("scItemId");
				player.getAttributes().remove("kilnX");
				if (StealingCreation.proccessKilnItems(player, componentId, index, itemId, value))
					return;
			}
		} else if (packetId == SWITCH_INTERFACE_ITEM_PACKET) {
			stream.readShortLE128();
			int fromInterfaceHash = stream.readIntV1();
			int toInterfaceHash = stream.readInt();
			int fromSlot = stream.readUnsignedShort();
			int toSlot = stream.readUnsignedShortLE128();
			stream.readUnsignedShortLE();

			int toInterfaceId = toInterfaceHash >> 16;
				int toComponentId = toInterfaceHash - (toInterfaceId << 16);
				int fromInterfaceId = fromInterfaceHash >> 16;
				int fromComponentId = fromInterfaceHash - (fromInterfaceId << 16);

				if (Utils.getInterfaceDefinitionsSize() <= fromInterfaceId
						|| Utils.getInterfaceDefinitionsSize() <= toInterfaceId)
					return;
				if (!player.getInterfaceManager()
						.containsInterface(fromInterfaceId)
						|| !player.getInterfaceManager().containsInterface(
								toInterfaceId))
					return;
				if (fromComponentId != -1
						&& Utils.getInterfaceDefinitionsComponentsSize(fromInterfaceId) <= fromComponentId)
					return;
				if (toComponentId != -1
						&& Utils.getInterfaceDefinitionsComponentsSize(toInterfaceId) <= toComponentId)
					return;
				if (fromInterfaceId == 1265 && toInterfaceId == 1266 && player.getAttributes().get("shop_buying") != null) {
					if ((boolean) player.getAttributes().get("shop_buying") == true) {
						Shop shop = (Shop) player.getAttributes().get("Shop");
						if (shop == null)
							return;
						shop.buy(player, fromSlot, 1);
					}
				}
				if (fromInterfaceId == Inventory.INVENTORY_INTERFACE
						&& fromComponentId == 0
						&& toInterfaceId == Inventory.INVENTORY_INTERFACE
						&& toComponentId == 0) {
					toSlot -= 28;
					if (toSlot < 0
							|| toSlot >= player.getInventory().getItemsContainerSize()
							|| fromSlot >= player.getInventory().getItemsContainerSize())
						return;
					player.getInventory().switchItem(fromSlot, toSlot);
				} else if (fromInterfaceId == 763 && fromComponentId == 0 && toInterfaceId == 763 && toComponentId == 0) {
					if (toSlot >= player.getInventory().getItemsContainerSize() || fromSlot >= player.getInventory().getItemsContainerSize())
						return;
					player.getInventory().switchItem(fromSlot, toSlot);
				} else if (fromInterfaceId == 762 && toInterfaceId == 762) {
					player.getBank().switchItem(fromSlot, toSlot, fromComponentId, toComponentId);
				}
				if (Settings.DEBUG)
					System.out.println("Switch item " + fromInterfaceId + ", "
							+ fromSlot + ", " + toSlot);
		} else if (packetId == DONE_LOADING_REGION_PACKET) {
			/*
			 * if(!player.clientHasLoadedMapRegion()) { //load objects and items
			 * here player.setClientHasLoadedMapRegion(); }
			 * //player.refreshSpawnedObjects(); //player.refreshSpawnedItems();
			 */
		} else if (packetId == WALKING_PACKET
				|| packetId == MINI_WALKING_PACKET
				|| packetId == ITEM_TAKE_PACKET
				|| packetId == PLAYER_OPTION_2_PACKET
				|| packetId == PLAYER_OPTION_4_PACKET
				|| packetId == PLAYER_OPTION_5_PACKET
				|| packetId == PLAYER_OPTION_1_PACKET 
				|| packetId == ATTACK_NPC
				|| packetId == INTERFACE_ON_PLAYER
				|| packetId == INTERFACE_ON_NPC
				|| packetId == NPC_CLICK1_PACKET
				|| packetId == NPC_CLICK2_PACKET
				|| packetId == NPC_CLICK3_PACKET
				|| packetId == OBJECT_CLICK1_PACKET
				|| packetId == SWITCH_INTERFACE_ITEM_PACKET
				|| packetId == OBJECT_CLICK2_PACKET
				|| packetId == OBJECT_CLICK3_PACKET
				|| packetId == OBJECT_CLICK4_PACKET
				|| packetId == OBJECT_CLICK5_PACKET
				|| packetId == INTERFACE_ON_OBJECT
				|| packetId == PlayerStats.PACKET_ID) {
			player.afkTimer = Utils.currentTimeMillis() + (10*60*1000);//10 minute timeout login
			if (player.getRights() > 0) {
				// Users with rights (Moderator, Admin, Owner, BetaTester)
				// override the afk logout timer
				player.afkTimer = Utils.currentTimeMillis() + (2*60*60*1000);//2 hour timeout login
			}
			player.addLogicPacketToQueue(new LogicPacket(packetId, length, stream));
		}
		else if (packetId == OBJECT_EXAMINE_PACKET) {
			ObjectHandler.handleOption(player, stream, -1);
		}else if (packetId == NPC_EXAMINE_PACKET) {
			NPCHandler.handleExamine(player, stream);
		} else if (packetId == JOIN_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			FriendChatsManager.joinChat(stream.readString(), player);
		} else if (packetId == KICK_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 1000);
			player.kickPlayerFromFriendsChannel(stream.readString());
		} else if (packetId == CHANGE_FRIEND_CHAT_PACKET) {
			if (!player.hasStarted()
					|| !player.getInterfaceManager().containsInterface(1108))
				return;
			player.getFriendsIgnores().changeRank(stream.readString(),
					stream.readUnsignedByte128());
		} else if (packetId == ADD_FRIEND_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().addFriend(stream.readString());
		} else if (packetId == REMOVE_FRIEND_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().removeFriend(stream.readString());
		} else if (packetId == ADD_IGNORE_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().addIgnore(stream.readString(), stream.readUnsignedByte() == 1);
		} else if (packetId == REMOVE_IGNORE_PACKET) {
			if (!player.hasStarted())
				return;
			player.getFriendsIgnores().removeIgnore(stream.readString());
		} else if (packetId == SEND_FRIEND_MESSAGE_PACKET) {
			if (!player.hasStarted())
				return;
			if (player.getMuted() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage("You temporary muted. Recheck in 30 minutes.");
				return;
			}
			String username = stream.readString();
			Player p2 = World.getPlayerByDisplayName(username);
			if (p2 == null)
				return;

			player.getFriendsIgnores().sendMessage(p2, Utils.fixChatMessage(Huffman.readEncryptedMessage(150, stream)));
		} else if (packetId == SEND_FRIEND_QUICK_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			String username = stream.readString();
			int fileId = stream.readUnsignedShort();
			byte[] data = null;
			if (length > 3 + username.length()) {
				data = new byte[length - (3 + username.length())];
				stream.readBytes(data);
			}
			data = Utils.completeQuickMessage(player, fileId, data);
			Player p2 = World.getPlayerByDisplayName(username);
			if (p2 == null)
				return;
			player.getFriendsIgnores().sendQuickChatMessage(p2, new QuickChatMessage(fileId, data));
		} else if (packetId == PUBLIC_QUICK_CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			if (player.getLastPublicMessage() > Utils.currentTimeMillis())
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 300);
			boolean secondClientScript = stream.readByte() == 1;
			int fileId = stream.readUnsignedShort();
			byte[] data = null;
			if (length > 3) {
				data = new byte[length - 3];
				stream.readBytes(data);
			}
			data = Utils.completeQuickMessage(player, fileId, data);
			if (chatType == 0)
				player.sendPublicChatMessage(new QuickChatMessage(fileId, data));
			else if (chatType == 1)
				player.sendFriendsChannelQuickMessage(new QuickChatMessage(fileId, data));
			else if (Settings.DEBUG)
				Logger.log(this, "Unknown chat type: " + chatType);
		} else if (packetId == CHAT_TYPE_PACKET) {
			chatType = stream.readUnsignedByte();
		} else if (packetId == CHAT_PACKET) {
			if (!player.hasStarted())
				return;
			if (player.getLastPublicMessage() > Utils.currentTimeMillis())
				return;
			player.setLastPublicMessage(Utils.currentTimeMillis() + 300);
			int colorEffect = stream.readUnsignedByte();
			int moveEffect = stream.readUnsignedByte();
			String message = Huffman.readEncryptedMessage(200, stream);
			if (message == null || message.replaceAll(" ", "").equals(""))
				return;
			if (message.startsWith("::") || message.startsWith(";;")) {
				Logger.log("PacketsDecoder", ""+player.getDisplayName()+" command: " +  message.replace("::", "").replace(";;", ""));
				Commands.processCommands(player, message.replace("::", "").replace(";;", ""), false, false);
				return;
			}
			if (player.getMuted() > Utils.currentTimeMillis()) {
				player.getPackets().sendGameMessage("You temporary muted. Recheck in 48 hours.");
				return;
			}
			if(message.contains("0hdr2ufufl9ljlzlyla") || message.contains("0hdr"))
				return;
			int effects = (colorEffect << 8) | (moveEffect & 0xff) & ~0x8000;
			if (chatType == 1)
				player.sendFriendsChannelMessage(Utils.fixChatMessage(message));
			else
				player.sendPublicChatMessage(new PublicChatMessage(Utils.fixChatMessage(message), effects));
				player.setLastMsg(message);
			if (Settings.DEBUG)
				Logger.log("Chat", "[Type: " + (chatType == 0 ? "Public" : chatType == 1 ? "Friend" : "" )+"] "+player.getDisplayName()+": "+message+"");
		} else if (packetId == COMMANDS_PACKET) {
			if (!player.isRunning())
				return;
			boolean clientCommand = stream.readUnsignedByte() == 1;
			boolean unknown = stream.readUnsignedByte() == 1;
			String command = stream.readString();
			if (Commands.processCommands(player, command, true, clientCommand) && Settings.DEBUG)
				System.out.println("["+player.getDisplayName()+"] cmd: " + command);
		} else if (packetId == COLOR_ID_PACKET) {
			if (!player.hasStarted())
				return;
			int colorId = stream.readUnsignedShort();
			if (player.getAttributes().get("SkillcapeCustomize") != null)
				SkillCapeCustomizer.handleSkillCapeCustomizerColor(player, colorId);
		}else if (packetId == REPORT_ABUSE_PACKET) {
			if (!player.hasStarted())
				return;
			@SuppressWarnings("unused")
			String username = stream.readString();
			@SuppressWarnings("unused")
			int type = stream.readUnsignedByte();
			@SuppressWarnings("unused")
			boolean mute = stream.readUnsignedByte() == 1;
			@SuppressWarnings("unused")
			String unknown2 = stream.readString();
		}else if (packetId == ITEM_ON_FLOOR_EXAMINE) {
			int y = stream.readUnsignedShort(); //CLIENT SENDS THEM BACKWARDS ON PURPOSE
			int x = stream.readUnsignedShortLE();
			int id = stream.readUnsignedShort();
			boolean forceRun = stream.readUnsigned128Byte() == 1;
			player.getPackets().sendItemMessage(0, 15263739, id, x, y, ItemExamines.getExamine(new Item(id))); // ChatboxMessage
		}else {
			//if (Settings.DEBUG)
			//Logger.log(this, "Missing packet " + packetId + ", expected size: " + length + ", actual size: "+ PACKET_SIZES[packetId]);
		}
	}

}
