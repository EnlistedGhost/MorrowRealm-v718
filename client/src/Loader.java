import java.applet.Applet;

import javax.swing.*;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Loader extends Applet {

	private static final long serialVersionUID = 7639088664641445302L;
	public static final double VERSION = 1.0;
	public static final String CLIENT_TITLE = "MorrowRealm";
	public static final String FRAME_TITLE = "MorrowRealm r718."+VERSION+"";
	public static final String SERVER_IP = "192.168.1.144";// 192.168.1.144
	public static final int SERVER_PORT = 43444;
	public static final int REVISION = 718;
	public static final int SUB_REVISION = 1;
	
	public static final boolean LOBBY_ENABLED = false;
	public static final boolean DISABLE_XTEA_CRASH = true;
	public static final boolean DISABLE_USELESS_PACKETS = true;
	public static final boolean DISABLE_RSA = false;
	public static final boolean useIsaac = false;
	public static final boolean usingRS = false;

	public static Loader instance;

	public static void main(String[] args) {
		Loader loader = instance = new Loader();
		loader.doFrame();
	}

	public void init() {
		instance = this;
		doApplet();
	}

	void doApplet() {
		setParams();
		startClient();
	}

	public void doFrame() {
		setParams();
		openFrame();
		startClient();
	}

	public static Properties client_parameters = new Properties();
	public JFrame client_frame;
	public JPanel client_panel = new JPanel();
	
	void setParams() {
		client_parameters.put("separate_jvm", "true");
		client_parameters.put("boxbgcolor", "black");
		client_parameters.put("image", "img/splash2.gif");
		client_parameters.put("centerimage", "true");
		client_parameters.put("boxborder", "false");
		client_parameters.put("java_arguments", "-Xmx256m -Xss2m -Dsun.java2d.noddraw=true -XX:CompileThreshold=1500 -Xincgc -XX:+UseConcMarkSweepGC -XX:+UseParNewGC");
		client_parameters.put("27", "0");
		client_parameters.put("1", "0");
		client_parameters.put("16", "false");
		client_parameters.put("17", "false");
		client_parameters.put("21", "1"); // WORLD ID
		client_parameters.put("30", "false");
		client_parameters.put("20", SERVER_IP);
		client_parameters.put("29", "");
		client_parameters.put("11", "true");
		client_parameters.put("25", "1378752098");
		client_parameters.put("28", "0");
		client_parameters.put("8", ".morrowrealm.xyz");
		client_parameters.put("23", "false");
		client_parameters.put("32", "0");
		client_parameters.put("15", "wwGlrZHF5gKN6D3mDdihco3oPeYN2KFybL9hUUFqOvk");
		client_parameters.put("0", "IjGJjn4L3q5lRpOR9ClzZQ");
		client_parameters.put("2", "");
		client_parameters.put("4", "1"); // WORLD ID
		client_parameters.put("14", "");
		client_parameters.put("5", "8194");
		client_parameters.put("-1", "QlwePyRU5GcnAn1lr035ag");
		client_parameters.put("6", "0");
		client_parameters.put("24", "true,false,0,43,200,18,0,21,354,-15,Verdana,11,0xF4ECE9,candy_bar_middle.gif,candy_bar_back.gif,candy_bar_outline_left.gif,candy_bar_outline_right.gif,candy_bar_outline_top.gif,candy_bar_outline_bottom.gif,loadbar_body_left.gif,loadbar_body_right.gif,loadbar_body_fill.gif,6");
		client_parameters.put("3", "hAJWGrsaETglRjuwxMwnlA/d5W6EgYWx");
		client_parameters.put("12", "false");
		client_parameters.put("13", "0");
		client_parameters.put("26", "0");
		client_parameters.put("9", "77");
		client_parameters.put("22", "false");
		client_parameters.put("18", "false");
		client_parameters.put("33", "");
		client_parameters.put("haveie6", "false");
	}

	void openFrame() {
		client_frame = new JFrame(FRAME_TITLE);
		client_frame.setLayout(new BorderLayout());
		client_panel.setLayout(new BorderLayout());
		client_panel.add(this);
		client_panel.setPreferredSize(new Dimension(765, 503));
		client_frame.setMinimumSize(new Dimension(780, 542));
		client_frame.getContentPane().add(client_panel, "Center");
		client_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client_frame.pack();
		client_frame.setVisible(true);
	}

	void startClient() {
		try {
			client clnt = new client();
			clnt.supplyApplet(this);
			clnt.init();
			clnt.start();
			//MusicChecker.check();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public String getParameter(String string) {
		return (String) client_parameters.get(string);
	}

	public URL getDocumentBase() {
		return getCodeBase();
	}

	public URL getCodeBase() {
		try {
			return new URL("http://" + SERVER_IP);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public static String getCacheDir() {
		return System.getProperty("user.home") + "/Guardian718/guardian/live";
	}

}
