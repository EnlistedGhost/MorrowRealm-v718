// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShopsHandler.java

package com.rs.utils;

import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.content.Shop;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;


public class ShopsHandler {

	public ShopsHandler() {
	}

	public static void init() {
		File file = new File("data/items/packedShops.s");
			if (!file.exists()) {
				loadUnpackedShops();
			}
		loadPackedShops();
	}

	private static void loadUnpackedShops() {
		Logger.log("ShopsHandler", "Packing shops...");
		try {
			BufferedReader in = new BufferedReader(new FileReader(UNPACKED_PATH));
			DataOutputStream out = new DataOutputStream(new FileOutputStream("data/items/packedShops.s"));
			do {
				String line = in.readLine();
				if (line == null)
					break;
				if (!line.startsWith("//")) {
					String splitedLine[] = line.split(" - ", 3);
					if (splitedLine.length != 3)
						throw new RuntimeException((new StringBuilder("Invalid list for shop line: ")).append(line).toString());
					String splitedInform[] = splitedLine[0].split(" ", 3);
					if (splitedInform.length != 3)
						throw new RuntimeException((new StringBuilder("Invalid list for shop line: ")).append(line).toString());
					String splitedItems[] = splitedLine[2].split(" ");
					int key = Integer.valueOf(splitedInform[0]).intValue();
					int money = Integer.valueOf(splitedInform[1]).intValue();
					boolean generalStore = Boolean.valueOf(splitedInform[2]).booleanValue();
					Item items[] = new Item[splitedItems.length / 2];
					int count = 0;
					for (int i = 0; i < items.length; i++)
					items[i] = new Item(Integer.valueOf(splitedItems[count++]).intValue(), Integer.valueOf(splitedItems[count++]).intValue());
					out.writeInt(key);
					writeAlexString(out, splitedLine[1]);
					out.writeShort(money);
					out.writeBoolean(generalStore);
					out.writeByte(items.length);
					Item aitem[];
					int k = (aitem = items).length;
					for (int j = 0; j < k; j++) {
						Item item = aitem[j];

						out.writeShort(item.getId());
						out.writeInt(item.getAmount());
					}

					addShop(key, new Shop(splitedLine[1], money, items, generalStore));
				}
			} while (true);
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void loadPackedShops() {
		try {
			RandomAccessFile in = new RandomAccessFile("data/items/packedShops.s", "r");
			FileChannel channel = in.getChannel();
			int key;
			String name;
			int money;
			boolean generalStore;
			Item items[];
			for (ByteBuffer buffer = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, 0L, channel.size()); 
				buffer.hasRemaining(); 
				addShop(key, new Shop(name, money, items, generalStore))) {
				key = buffer.getInt();
				name = readAlexString(buffer);
				money = buffer.getShort() & 0xffff;
				generalStore = buffer.get() == 1;
				items = new Item[buffer.get() & 0xff];
				for (int i = 0; i < items.length; i++) {
				items[i] = new Item(buffer.getShort() & 0xffff, buffer.getInt());
				}
			}

			channel.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readAlexString(ByteBuffer buffer) {
		int count = buffer.get() & 0xfff;
		byte bytes[] = new byte[count];
		buffer.get(bytes, 0, count);
		return new String(bytes);
	}

	public static void writeAlexString(DataOutputStream out, String string)
			throws IOException {
		byte bytes[] = string.getBytes();
		out.writeByte(bytes.length);
		out.write(bytes);
	}

	public static void restoreShops() {
		Shop shop;
		for (Iterator<Shop> iterator = handledShops.values().iterator(); iterator.hasNext(); shop.restoreItems())
			shop = (Shop) iterator.next();

	}

	public static boolean openShop(Player player, int key) {
		Shop shop = getShop(key);
		if (shop == null) {
			return false;
		} else {
			shop.setShopId(key);
			shop.addPlayer(player);
			return true;
		}
	}

	public static boolean openrfdShop(Player player, int key) {
		Shop shop = getShop(key);
		boolean generalStore = true;
		addShop(key, new Shop("", 995, new Item[]{ new Item(1038, 1) }, generalStore));
		shop.addPlayer(player);
		return true;
	}

	public static Shop getShop(int key) {
		return (Shop) handledShops.get(Integer.valueOf(key));
	}

	public static void addShop(int key, Shop shop) {
		handledShops.put(Integer.valueOf(key), shop);
	}
	
	public static void reload() {
		handledShops.clear();
		init();
	}
	
	private static final HashMap<Integer, Shop> handledShops = new HashMap<Integer, Shop>();
	@SuppressWarnings("unused")
	private static final String PACKED_PATH = "data/items/packedShops.s";
	private static final String UNPACKED_PATH = "data/items/unpackedShops.txt";

}