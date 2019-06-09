package com.rs.cache.loaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedList;

import com.alex.utils.Constants;
import com.rs.cache.Cache;
import com.rs.game.player.Equipment;
import com.rs.game.player.Skills;
import com.rs.io.InputStream;
import com.rs.utils.Utils;

@SuppressWarnings("unused")
public final class ItemDefinitions {

	private static final ItemDefinitions[] itemsDefinitions;

	static {
		itemsDefinitions = new ItemDefinitions[Utils.getItemDefinitionsSize()];
	}
	private int id;
	private boolean loaded;

	public void setValue(int value) {
		this.value = value;
	}
	public int modelId;
	private String name;
	private int modelZoom;
	private int modelRotation1;
	private int modelRotation2;
	private int modelOffset1;
	private int modelOffset2;

	// extra information
	private int stackable;
	private int value;
	private boolean membersOnly;

	// wearing model information
	public int maleEquip1;
	public int femaleEquip1;
	public int maleEquip2;
	public int femaleEquip2;

	// 718/735 Opcodes
	private int opcode44;
	private int opcode144;
	private int opcode145;
	private int opcode150;
	private int opcode151;
	private int opcode152;
	private int opcode153;
	private int opcode146;
	private int opcode142;
	private int opcode154;
	private int opcode9;
	private int opcode117;
	private int opcode17;
	private int opcode99;
	private int opcode87;
	private int opcode55;
	private int opcode62;
	private int opcode118;
	private int opcode56;
	private int opcode46;
	private int opcode47;
	private int opcode83;

	// options
	private String[] groundOptions;
	public String[] inventoryOptions;

	// model information
	public int[] originalModelColors;
	public int[] modifiedModelColors;
	public short[] originalTextureColors;
	private short[] modifiedTextureColors;
	private byte[] unknownArray1;
	private int[] unknownArray2;
	// extra information, not used for newer items
	private boolean unnoted;

	private int maleEquipModelId3;
	private int femaleEquipModelId3;
	private int unknownInt1;
	private int unknownInt2;
	private int unknownInt3;
	private int unknownInt4;
	private int unknownInt5;
	private int unknownInt6;
	private int certId;
	private int certTemplateId;
	private int[] stackIds;
	private int[] stackAmounts;
	private int unknownInt7;
	private int unknownInt8;
	private int unknownInt9;
	private int unknownInt10;
	private int unknownInt11;
	public int teamId;
	private int lendId;
	private int lendTemplateId;
	private int unknownInt12;
	private int unknownInt13;
	private int unknownInt14;
	private int unknownInt15;
	private int unknownInt16;
	private int unknownInt17;
	private int unknownInt18;
	private int unknownInt19;
	private int unknownInt20;
	private int unknownInt21;
	private int unknownInt22;
	private int unknownInt23;
	private int equipSlot;
	private int equipType;
	private int unknownInt25;
	private int unknownInt26;
	private int unknownInt27;
	private int unknownInt28;
	private int unknownInt29;
	private int unknownInt30;
	private int unknownInt31;
	private int unknownInt32;
	private int opcode13;
	private int opcode52;
	private int opcode53;
	private int opcode82;
	private int opcode66;
	private int opcode116;
	private int opcode157;
	private int opcode68;
	private int opcode244;
	private int opcode170;
	private int opcode75;
	private int opcode14;
	private int opcode27;
	private int opcode232;
	private int opcode211;
	// more opcodes
	private int opcode254;
	private int opcode228;
	private int opcode226;
	private int opcode255;
	private int opcode253;
	private int opcode252;
	private int opcode156;
	private int opcode215;
	private int opcode171;
	private int opcode3;
	private int opcode199;
	private int opcode223;
	private int opcode198;
	private int opcode186;
	private int opcode29;
	private int opcode238;
	private int opcode155;
	private int opcode251;
	private int opcode22;
	private int opcode192;
	private int opcode245;
	private int opcode45;
	private int opcode248;
	private int opcode237;
	private int opcode243;
	private int opcode185;
	private int opcode221;
	private int opcode240;
	private int opcode158;
	private int opcode137;
	private int opcode143;
	private int opcode61;
	private int opcode80;
	private int opcode196;
	private int opcode85;
	private int opcode239;
	private int opcode177;
	private int opcode163;
	private int opcode135;
	private int opcode120;
	private int opcode204;
	private int opcode81;
	private int opcode208;
	private int opcode242;
	private int opcode15;
	private int opcode233;
	private int opcode213;
	private int opcode207;
	private int opcode216;
	private int opcode206;
	private int opcode50;
	private int opcode193;
	private int opcode71;
	private int opcode10;
	private int opcode235;
	private int opcode188;
	private int opcode241;
	private int opcode236;
	private int opcode182;
	private int opcode169;
	private int opcode190;
	private int opcode178;
	private int opcode88;
	private int opcode200;
	private int opcode184;
	private int opcode176;
	private int opcode197;
	private int opcode247;
	private int opcode218;
	private int opcode250;
	private int opcode174;
	private int opcode210;
	private int opcode164;
	private int opcode148;
	private int opcode133;
	private int opcode222;
	private int opcode138;
	private int opcode194;
	private int opcode119;
	private int opcode202;
	private int opcode149;
	private int opcode64;
	private int opcode147;
	private int opcode214;
	private int opcode74;
	private int opcode86;
	private int opcode167;
	private int opcode161;
	private int opcode58;
	private int opcode59;
	private int opcode187;
	private int opcode77;
	private int opcode229;
	private int opcode230;
	private int opcode67;
	private int opcode131;
	private int opcode225;
	private int opcode203;
	private int opcode19;
	private int opcode43;
	private int opcode168;
	private int opcode209;
	private int opcode166;
	private int opcode54;
	private int opcode21;
	private int opcode73;
	private int opcode159;
	private int opcode123;
	private int opcode180;
	private int opcode20;
	private int opcode165;
	private int opcode84;
	private int opcode28;
	private int opcode175;
	private int opcode141;
	private int opcode205;
	private int opcode220;
	private int opcode136;
	private int opcode212;
	private int opcode49;
	private int opcode69;
	private int opcode72;
	private int opcode60;
	private int opcode219;
	private int opcode227;
	private int opcode76;
	private int opcode234;
	private int opcode57;
	private int opcode51;
	private int opcode124;
	private int opcode70;
	private int opcode231;
	private int opcode162;
	private int opcode160;
	private int opcode181;
	private int opcode183;
	private int opcode191;
	private int opcode189;
	private int opcode179;
	private int opcode173;
	private int opcode48;
	private int opcode172;
	private int opcode42;
	private int opcode246;
	private int opcode89;
	private int opcode195;
	private int opcode224;
	private int opcode63;
	private int opcode94;
	private int opcode201;
	private int opcode217;

	// extra added
	private boolean noted;
	private boolean lended;

	private HashMap<Integer, Object> clientScriptData;
	private HashMap<Integer, Integer> itemRequiriments;
	private int unknownInt24;

	public static final ItemDefinitions getItemDefinitions(int itemId) {
		if (itemId < 0 || itemId >= itemsDefinitions.length)
			itemId = 0;
		ItemDefinitions def = itemsDefinitions[itemId];
		if (def == null)
			itemsDefinitions[itemId] = def = new ItemDefinitions(itemId);
		return def;
	}

	public static final void clearItemsDefinitions() {
		for (int i = 0; i < itemsDefinitions.length; i++)
			itemsDefinitions[i] = null;
	}

	public static ItemDefinitions forName(String name) {
		for (ItemDefinitions definition : itemsDefinitions) {
			if (definition.name.equalsIgnoreCase(name)) {
				return definition;
			}
		}
		return null;
	}

	public ItemDefinitions(int id) {
		this.id = id;
		setDefaultsVariableValues();
		setDefaultOptions();
		loadItemDefinitions();
	}

	public boolean isLoaded() {
		return loaded;
	}

	private final void loadItemDefinitions() {
		byte[] data = Cache.STORE.getIndexes()[Constants.ITEM_DEFINITIONS_INDEX]
				.getFile(getArchiveId(), getFileId());
		if (data == null) {
			// System.out.println("Failed loading Item " + id+".");
			return;
		}
		readOpcodeValues(new InputStream(data));
		if (certTemplateId != -1)
			toNote();
		if (lendTemplateId != -1)
			toLend();
		if (unknownValue1 != -1)
			toBind();
		loaded = true;
	}

	private void toNote() {
		// ItemDefinitions noteItem; //certTemplateId
		ItemDefinitions realItem = getItemDefinitions(certId);
		membersOnly = realItem.membersOnly;
		value = realItem.value;
		name = realItem.name;
		stackable = 1;
		noted = true;
	}

	private void toBind() {
		// ItemDefinitions lendItem; //lendTemplateId
		ItemDefinitions realItem = getItemDefinitions(unknownValue2);
		originalModelColors = realItem.originalModelColors;
		maleEquipModelId3 = realItem.maleEquipModelId3;
		femaleEquipModelId3 = realItem.femaleEquipModelId3;
		teamId = realItem.teamId;
		value = 0;
		membersOnly = realItem.membersOnly;
		name = realItem.name;
		inventoryOptions = new String[5];
		groundOptions = realItem.groundOptions;
		if (realItem.inventoryOptions != null)
			for (int optionIndex = 0; optionIndex < 4; optionIndex++)
				inventoryOptions[optionIndex] = realItem.inventoryOptions[optionIndex];
		inventoryOptions[4] = "Discard";
		maleEquip1 = realItem.maleEquip1;
		maleEquip2 = realItem.maleEquip2;
		femaleEquip1 = realItem.femaleEquip1;
		femaleEquip2 = realItem.femaleEquip2;
		clientScriptData = realItem.clientScriptData;
		equipSlot = realItem.equipSlot;
		equipType = realItem.equipType;
	}

	private void toLend() {
		// ItemDefinitions lendItem; //lendTemplateId
		ItemDefinitions realItem = getItemDefinitions(lendId);
		originalModelColors = realItem.originalModelColors;
		maleEquipModelId3 = realItem.maleEquipModelId3;
		femaleEquipModelId3 = realItem.femaleEquipModelId3;
		teamId = realItem.teamId;
		value = 0;
		membersOnly = realItem.membersOnly;
		name = realItem.name;
		inventoryOptions = new String[5];
		groundOptions = realItem.groundOptions;
		if (realItem.inventoryOptions != null)
			for (int optionIndex = 0; optionIndex < 4; optionIndex++)
				inventoryOptions[optionIndex] = realItem.inventoryOptions[optionIndex];
		inventoryOptions[4] = "Discard";
		maleEquip1 = realItem.maleEquip1;
		maleEquip2 = realItem.maleEquip2;
		femaleEquip1 = realItem.femaleEquip1;
		femaleEquip2 = realItem.femaleEquip2;
		clientScriptData = realItem.clientScriptData;
		equipSlot = realItem.equipSlot;
		equipType = realItem.equipType;
		lended = true;
	}

	public int getArchiveId() {
		return getId() >>> 8;
	}

	public int getFileId() {
		return 0xff & getId();
	}

	public boolean isDestroyItem() {
		if (inventoryOptions == null)
			return false;
		for (String option : inventoryOptions) {
			if (option == null)
				continue;
			if (option.equalsIgnoreCase("destroy"))
				return true;
		}
		return false;
	}

	public boolean containsOption(int i, String option) {
		if (inventoryOptions == null || inventoryOptions[i] == null
				|| inventoryOptions.length <= i)
			return false;
		return inventoryOptions[i].equals(option);
	}

	public boolean containsOption(String option) {
		if (inventoryOptions == null)
			return false;
		for (String o : inventoryOptions) {
			if (o == null || !o.equals(option))
				continue;
			return true;
		}
		return false;
	}

	public boolean isWearItem() {
		return equipSlot != -1;
	}

	public boolean isWearItem(boolean male) {
		if (equipSlot < Equipment.SLOT_RING
				&& (male ? getMaleWornModelId1() == -1
						: getFemaleWornModelId1() == -1))
			return false;
		return equipSlot != -1;
	}

	public boolean hasSpecialBar() {
		if (clientScriptData == null)
			return false;
		Object specialBar = clientScriptData.get(686);
		if (specialBar != null && specialBar instanceof Integer)
			return (Integer) specialBar == 1;
		return false;
	}

	public int getRenderAnimId() {
		if (clientScriptData == null)
			return 1426;
		Object animId = clientScriptData.get(644);
		if (animId != null && animId instanceof Integer)
			return (Integer) animId;
		return 1426;
	}

	public int getModelZoom() {
		return modelZoom;
	}

	public int getModelOffset1() {
		return modelOffset1;
	}

	public int getModelOffset2() {
		return modelOffset2;
	}

	public int getQuestId() {
		if (clientScriptData == null)
			return -1;
		Object questId = clientScriptData.get(861);
		if (questId != null && questId instanceof Integer)
			return (Integer) questId;
		return -1;
	}

	public HashMap<Integer, Integer> getCreateItemRequirements() {
		if (clientScriptData == null)
			return null;
		HashMap<Integer, Integer> items = new HashMap<Integer, Integer>();
		int requiredId = -1;
		int requiredAmount = -1;
		for (int key : clientScriptData.keySet()) {
			Object value = clientScriptData.get(key);
			if (value instanceof String)
				continue;
			if (key >= 538 && key <= 770) {
				if (key % 2 == 0)
					requiredId = (Integer) value;
				else
					requiredAmount = (Integer) value;
				if (requiredId != -1 && requiredAmount != -1) {
					items.put(requiredAmount, requiredId);
					requiredId = -1;
					requiredAmount = -1;
				}
			}
		}
		return items;
	}

	public HashMap<Integer, Object> getClientScriptData() {
		return clientScriptData;
	}

	public HashMap<Integer, Integer> getWearingSkillRequiriments() {
		if (clientScriptData == null)
			return null;
		if (itemRequiriments == null) {
			HashMap<Integer, Integer> skills = new HashMap<Integer, Integer>();
			for (int i = 0; i < 10; i++) {
				Integer skill = (Integer) clientScriptData.get(749 + (i * 2));
				if (skill != null) {
					Integer level = (Integer) clientScriptData
							.get(750 + (i * 2));
					if (level != null)
						skills.put(skill, level);
				}
			}
			Integer maxedSkill = (Integer) clientScriptData.get(277);
			if (maxedSkill != null)
				skills.put(maxedSkill, getId() == 19709 ? 120 : 99);
			itemRequiriments = skills;
			if (getId() == 7462)
				itemRequiriments.put(Skills.DEFENCE, 40);
			else if (name.equals("Dragon defender")) {
				itemRequiriments.put(Skills.ATTACK, 60);
				itemRequiriments.put(Skills.DEFENCE, 60);
			}
		}

		return itemRequiriments;
	}

	/*
	 * public HashMap<Integer, Integer> getWearingSkillRequiriments() { if
	 * (clientScriptData == null) return null; HashMap<Integer, Integer> skills
	 * = new HashMap<Integer, Integer>(); int nextLevel = -1; int nextSkill =
	 * -1; for (int key : clientScriptData.keySet()) { Object value =
	 * clientScriptData.get(key); if (value instanceof String) continue; if(key
	 * == 277) { skills.put((Integer) value, id == 19709 ? 120 : 99); }else if
	 * (key == 23 && id == 15241) { skills.put(4, (Integer) value);
	 * skills.put(11, 61); } else if (key >= 749 && key < 797) { if (key % 2 ==
	 * 0) nextLevel = (Integer) value; else nextSkill = (Integer) value; if
	 * (nextLevel != -1 && nextSkill != -1) { skills.put(nextSkill, nextLevel);
	 * nextLevel = -1; nextSkill = -1; } }
	 * 
	 * } return skills; }
	 */

	private void setDefaultOptions() {
		groundOptions = new String[] { null, null, "take", null, null };
		inventoryOptions = new String[] { null, null, null, null, "drop" };
	}

	private void setDefaultsVariableValues() {
		name = "null";
		maleEquip1 = -1;
		maleEquip2 = -1;
		femaleEquip1 = -1;
		femaleEquip2 = -1;
		modelZoom = 2000;
		lendId = -1;
		lendTemplateId = -1;
		certId = -1;
		certTemplateId = -1;
		unknownInt9 = 128;
		value = 1;
		maleEquipModelId3 = -1;
		femaleEquipModelId3 = -1;
		unknownValue1 = -1;
		unknownValue2 = -1;
		teamId = -1;
		equipSlot = -1;
		equipType = -1;
	}

	private final void readValues(InputStream stream, int opcode) {
		if (opcode == 1)
			modelId = stream.readBigSmart();
		else if (opcode == 2)
			name = stream.readString();
		else if (opcode == 4)
			modelZoom = stream.readUnsignedShort();
		else if (opcode == 5)
			modelRotation1 = stream.readUnsignedShort();
		else if (opcode == 6)
			modelRotation2 = stream.readUnsignedShort();
		else if (opcode == 7) {
			modelOffset1 = stream.readUnsignedShort();
			if (modelOffset1 > 32767)
				modelOffset1 -= 65536;
			modelOffset1 <<= 0;
		} else if (opcode == 8) {
			modelOffset2 = stream.readUnsignedShort();
			if (modelOffset2 > 32767)
				modelOffset2 -= 65536;
			modelOffset2 <<= 0;
		} else if (opcode == 11)
			stackable = 1;
		else if (opcode == 12)
			value = stream.readInt();
		else if (opcode == 13) {
			equipSlot = stream.readUnsignedByte();
		} else if (opcode == 14) {
			equipType = stream.readUnsignedByte();
		} else if (opcode == 16)
			membersOnly = true;
		else if (opcode == 18)
			stream.readUnsignedShortLE();
		else if (opcode == 23)
			maleEquip1 = stream.readBigSmart();
		else if (opcode == 24)
			maleEquip2 = stream.readBigSmart();
		else if (opcode == 25)
			femaleEquip1 = stream.readBigSmart();
		else if (opcode == 26)
			femaleEquip2 = stream.readBigSmart();
		else if (opcode == 27)
			stream.readUnsignedByte();
		else if (opcode >= 30 && opcode < 35)
			groundOptions[opcode - 30] = stream.readString();
		else if (opcode >= 35 && opcode < 40)
			inventoryOptions[opcode - 35] = stream.readString();
		else if (opcode == 40) {
			int length = stream.readUnsignedByte();
			originalModelColors = new int[length];
			modifiedModelColors = new int[length];
			for (int index = 0; index < length; index++) {
				originalModelColors[index] = stream.readUnsignedShort();
				modifiedModelColors[index] = stream.readUnsignedShort();
			}
		} else if (opcode == 41) {
			int length = stream.readUnsignedByte();
			originalTextureColors = new short[length];
			modifiedTextureColors = new short[length];
			for (int index = 0; index < length; index++) {
				originalTextureColors[index] = (short) stream
						.readUnsignedShort();
				modifiedTextureColors[index] = (short) stream
						.readUnsignedShort();
			}
		} else if (opcode == 42) {
			int length = stream.readUnsignedByte();
			unknownArray1 = new byte[length];
			for (int index = 0; index < length; index++)
				unknownArray1[index] = (byte) stream.readByte();
		} else if (opcode == 65)
			unnoted = true;
		else if (opcode == 78)
			maleEquipModelId3 = stream.readBigSmart();
		else if (opcode == 79)
			femaleEquipModelId3 = stream.readBigSmart();
		else if (opcode == 90)
			unknownInt1 = stream.readBigSmart();
		else if (opcode == 91)
			unknownInt2 = stream.readBigSmart();
		else if (opcode == 92)
			unknownInt3 = stream.readBigSmart();
		else if (opcode == 93)
			unknownInt4 = stream.readBigSmart();
		else if (opcode == 95)
			unknownInt5 = stream.readUnsignedShort();
		else if (opcode == 96)
			unknownInt6 = stream.readUnsignedByte();
		else if (opcode == 97)
			certId = stream.readUnsignedShort();
		else if (opcode == 98)
			certTemplateId = stream.readUnsignedShort();
		else if (opcode >= 100 && opcode < 110) {
			if (stackIds == null) {
				stackIds = new int[10];
				stackAmounts = new int[10];
			}
			stackIds[opcode - 100] = stream.readUnsignedShort();
			stackAmounts[opcode - 100] = stream.readUnsignedShort();
		} else if (opcode == 110)
			unknownInt7 = stream.readUnsignedShort();
		else if (opcode == 111)
			unknownInt8 = stream.readUnsignedShort();
		else if (opcode == 112)
			unknownInt9 = stream.readUnsignedShort();
		else if (opcode == 113)
			unknownInt10 = stream.readByte();
		else if (opcode == 114)
			unknownInt11 = stream.readByte() * 5;
		else if (opcode == 115)
			teamId = stream.readUnsignedByte();
		else if (opcode == 121)
			lendId = stream.readUnsignedShort();
		else if (opcode == 122)
			lendTemplateId = stream.readUnsignedShort();
		else if (opcode == 125) {
			unknownInt12 = stream.readByte() << 0;
			unknownInt13 = stream.readByte() << 0;
			unknownInt14 = stream.readByte() << 0;
		} else if (opcode == 126) {
			unknownInt15 = stream.readByte() << 0;
			unknownInt16 = stream.readByte() << 0;
			unknownInt17 = stream.readByte() << 0;
		} else if (opcode == 127) {
			unknownInt18 = stream.readUnsignedByte();
			unknownInt19 = stream.readUnsignedShort();
		} else if (opcode == 128) {
			unknownInt20 = stream.readUnsignedByte();
			unknownInt21 = stream.readUnsignedShort();
		} else if (opcode == 129) {
			unknownInt20 = stream.readUnsignedByte();
			unknownInt21 = stream.readUnsignedShort();
		} else if (opcode == 130) {
			unknownInt22 = stream.readUnsignedByte();
			unknownInt23 = stream.readUnsignedShort();
		} else if (opcode == 132) {
			int length = stream.readUnsignedByte();
			unknownArray2 = new int[length];
			for (int index = 0; index < length; index++)
				unknownArray2[index] = stream.readUnsignedShort();
		} else if (opcode == 134) {
			int unknownValue = stream.readUnsignedByte();
		} else if (opcode == 139) {
			unknownValue2 = stream.readUnsignedShort();
		} else if (opcode == 140) {
			unknownValue1 = stream.readUnsignedShort();
		} else if (opcode == 249) {
			int length = stream.readUnsignedByte();
			if (clientScriptData == null)
				clientScriptData = new HashMap<Integer, Object>(length);
			for (int index = 0; index < length; index++) {
				boolean stringInstance = stream.readUnsignedByte() == 1;
				int key = stream.read24BitInt();
				Object value = stringInstance ? stream.readString() : stream
						.readInt();
				clientScriptData.put(key, value);
			}
		} else if (opcode == 44)
			opcode44 = stream.readUnsignedShort();
		else if (opcode == 55)
			opcode55 = stream.readUnsignedShort();
		else if (opcode == 62)
			opcode62 = stream.readUnsignedShort();
		else if (opcode == 144)
			opcode144 = stream.readUnsignedShort();
		else if (opcode == 118)
			opcode118 = stream.readUnsignedShort();
		else if (opcode == 46)
			opcode46 = stream.readUnsignedShort();
		else if (opcode == 56)
			opcode56 = stream.readUnsignedShort();
		else if (opcode == 47)
			opcode47 = stream.readUnsignedShort();
		else if (opcode == 83)
			opcode83 = stream.readUnsignedShort();
		else if (opcode == 146)
			opcode146 = stream.readUnsignedShort();
		else if (opcode == 87)
			opcode87 = stream.readUnsignedShort();
		else if (opcode == 99)
			opcode99 = stream.readUnsignedShort();
		else if (opcode == 17)
			opcode17 = stream.readUnsignedShort();
		else if (opcode == 117)
			opcode117 = stream.readUnsignedShort();
		else if (opcode == 9)
			opcode9 = stream.readUnsignedShort();
		else if (opcode == 142)
			opcode142 = stream.readUnsignedShort();
		else if (opcode == 145)
			opcode145 = stream.readUnsignedShort();
		else if (opcode == 150)
			opcode150 = stream.readUnsignedShort();
		else if (opcode == 151)
			opcode151 = stream.readUnsignedShort();
		else if (opcode == 152)
			opcode152 = stream.readUnsignedShort();
		else if (opcode == 153)
			opcode153 = stream.readUnsignedShort();
		else if (opcode == 154)
			opcode154 = stream.readUnsignedShort();
		else
			throw new RuntimeException("MISSING OPCODE " + opcode
					+ " FOR ITEM " + getId());
	}

	private int unknownValue1;
	private int unknownValue2;

	private final void readOpcodeValues(InputStream stream) {
		while (true) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			readValues(stream, opcode);
		}
	}

	public String getName() {
		return name;
	}

	public int getFemaleWornModelId1() {
		return femaleEquip1;
	}

	public int getFemaleWornModelId2() {
		return femaleEquip2;
	}

	public int getMaleWornModelId1() {
		return maleEquip1;
	}

	public int getMaleWornModelId2() {
		return maleEquip2;
	}

	public boolean isOverSized() {
		return modelZoom > 5000;
	}

	public boolean isLended() {
		return lended;
	}

	public boolean isMembersOnly() {
		return membersOnly;
	}

	public boolean isStackable() {
		return stackable == 1;
	}

	public boolean isNoted() {
		return noted;
	}

	public int getLendId() {
		return lendId;
	}

	public int getCertId() {
		return certId;
	}

	public int getValue() {
		return value;
	}

	public int getId() {
		return id;
	}

	public int getEquipSlot() {
		return equipSlot;
	}

	public int getEquipType() {
		return equipType;
	}

	public int getGEPrice() {
		try {
			for (String lines : FileUtilities.readFile("./data/items/grand_exchange/tipit_dump.txt")) {
				String[] data = lines.split(" - ");
				if (Integer.parseInt(data[0]) == id) return Integer.parseInt(data[1]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static class FileUtilities {



		public static final int BUFFER = 1024;



		public static boolean exists(String name) {

			File file = new File(name);

			return file.exists();

		}



		public static ByteBuffer fileBuffer(String name) throws IOException {

			File file = new File(name);

			if(!file.exists())

				return null;

			FileInputStream in = new FileInputStream(name);



			byte[] data = new byte[BUFFER];

			int read;

			try {

				ByteBuffer buffer = ByteBuffer.allocate(in.available() + 1);

				while ((read = in.read(data, 0, BUFFER)) != -1) {

					buffer.put(data, 0, read);

				}

				buffer.flip();

				return buffer;

			} finally {

				if (in != null)

					in.close();

				in = null;

			}

		}



		public static void writeBufferToFile(String name, ByteBuffer buffer) throws IOException {

			File file = new File(name);

			if(!file.exists())

				file.createNewFile();

			FileOutputStream out = new FileOutputStream(name);

			out.write(buffer.array(), 0, buffer.remaining());

			out.flush();

			out.close();

		}



		public static LinkedList<String> readFile(String directory) throws IOException {

			LinkedList<String> fileLines = new LinkedList<String>();

			BufferedReader reader = null;

			try {

				reader = new BufferedReader(new FileReader(directory));

				String string;

				while ((string = reader.readLine()) != null) {

					fileLines.add(string);

				}

			} finally {

				if (reader != null) {

					reader.close();

					reader = null;

				}

			}

			return fileLines;
		}
		}
	}