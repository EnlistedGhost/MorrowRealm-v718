package com.rs.game.player.dialogues;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import com.rs.utils.Logger;

public final class DialogueHandler {

	private static final HashMap<Object, Class<Dialogue>> handledDialogues = new HashMap<Object, Class<Dialogue>>();
	
	@SuppressWarnings("rawtypes" )
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class> classes = new ArrayList<Class>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final void init() {
		String fileLoc = "bin/com/rs/game/player/dialogues/impl";
		String packageDir = "com.rs.game.player.dialogues.impl";
		
		try {// Load Dialogues from files in package directory
			List<Class> files = findClasses(new File(fileLoc) , packageDir);
			for (Class<Dialogue> c : files) {
				if (Dialogue.class.isAssignableFrom(c)) {
					handledDialogues.put(""+c.getSimpleName()+"", (Class<Dialogue>) Class.forName(c.getCanonicalName()));
				}
			}
			// Halloween Event Dialogues 
			// (Should be auto loaded from above function,
			// Therefore it's disabled below)
			/*
			Class<Dialogue> value83 = (Class<Dialogue>) Class
					.forName(PumpkinPete.class.getCanonicalName());
			handledDialogues.put("PumpkinPete", value83);
			
			Class<Dialogue> value84 = (Class<Dialogue>) Class
					.forName(PumpkinPete2.class.getCanonicalName());
			handledDialogues.put("PumpkinPete2", value84);
			
			Class<Dialogue> value85 = (Class<Dialogue>) Class
					.forName(Zabeth.class.getCanonicalName());
			handledDialogues.put("Zabeth", value85);
			
			Class<Dialogue> value86 = (Class<Dialogue>) Class
					.forName(Zabeth2.class.getCanonicalName());
			handledDialogues.put("Zabeth2", value86);
			
			Class<Dialogue> value87 = (Class<Dialogue>) Class
					.forName(GrimReaper.class.getCanonicalName());
			handledDialogues.put("GrimReaper", value87);
			
			Class<Dialogue> value88 = (Class<Dialogue>) Class
					.forName(GrimReaper2.class.getCanonicalName());
			handledDialogues.put("GrimReaper2", value88);
			
			Class<Dialogue> value89 = (Class<Dialogue>) Class
					.forName(GrimReaper3.class.getCanonicalName());
			handledDialogues.put("GrimReaper3", value89);
			
			Class<Dialogue> value90 = (Class<Dialogue>) Class
					.forName(Zabeth3.class.getCanonicalName());
			handledDialogues.put("Zabeth3", value90);
			*/
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static final void reload() {
		handledDialogues.clear();
		init();
	}

	public static final Dialogue getDialogue(Object key) {
		if (key instanceof Dialogue)
			return (Dialogue) key;
		Class<Dialogue> classD = handledDialogues.get(key);
		if (classD == null)
			return null;
		try {
			return classD.newInstance();
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}

	private DialogueHandler() {

	}
}
