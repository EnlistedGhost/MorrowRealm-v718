/* Class298_Sub17_Sub2 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class298_Sub17_Sub2 extends Class298_Sub17 {
	byte aByte9195;
	String aString9196;
	int anInt9197;
	int anInt9198;
	Class152 aClass152_9199;

	void method2922(RsByteBuffer class298_sub53) {
		((Class298_Sub17_Sub2) this).anInt9197 = class298_sub53.readUnsignedShort() * 857287155;
		((Class298_Sub17_Sub2) this).aByte9195 = class298_sub53.readByte(-12558881);
		((Class298_Sub17_Sub2) this).anInt9198 = class298_sub53.readUnsignedShort() * -1333007471;
		class298_sub53.readLong((short) 6536);
		((Class298_Sub17_Sub2) this).aString9196 = class298_sub53.readString(2076786795);
	}

	void method2917(RsByteBuffer class298_sub53, int i) {
		try {
			((Class298_Sub17_Sub2) this).anInt9197 = class298_sub53.readUnsignedShort() * 857287155;
			((Class298_Sub17_Sub2) this).aByte9195 = class298_sub53.readByte(-12558881);
			((Class298_Sub17_Sub2) this).anInt9198 = class298_sub53.readUnsignedShort() * -1333007471;
			class298_sub53.readLong((short) 17752);
			((Class298_Sub17_Sub2) this).aString9196 = class298_sub53.readString(1791520586);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("agj.a(").append(')').toString());
		}
	}

	void method2918(Class298_Sub25 class298_sub25, int i) {
		try {
			Class163 class163 = (class298_sub25.aClass163Array7359[((Class298_Sub17_Sub2) this).anInt9197 * 2029492027]);
			class163.aByte1683 = ((Class298_Sub17_Sub2) this).aByte9195;
			class163.anInt1682 = ((Class298_Sub17_Sub2) this).anInt9198 * -1537662205;
			class163.aString1681 = ((Class298_Sub17_Sub2) this).aString9196;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("agj.f(").append(')').toString());
		}
	}

	Class298_Sub17_Sub2(Class152 class152) {
		super();
		((Class298_Sub17_Sub2) this).anInt9197 = -857287155;
	}

	void method2921(Class298_Sub25 class298_sub25) {
		Class163 class163 = (class298_sub25.aClass163Array7359[((Class298_Sub17_Sub2) this).anInt9197 * 2029492027]);
		class163.aByte1683 = ((Class298_Sub17_Sub2) this).aByte9195;
		class163.anInt1682 = ((Class298_Sub17_Sub2) this).anInt9198 * -1537662205;
		class163.aString1681 = ((Class298_Sub17_Sub2) this).aString9196;
	}

	void method2920(RsByteBuffer class298_sub53) {
		((Class298_Sub17_Sub2) this).anInt9197 = class298_sub53.readUnsignedShort() * 857287155;
		((Class298_Sub17_Sub2) this).aByte9195 = class298_sub53.readByte(-12558881);
		((Class298_Sub17_Sub2) this).anInt9198 = class298_sub53.readUnsignedShort() * -1333007471;
		class298_sub53.readLong((short) 27860);
		((Class298_Sub17_Sub2) this).aString9196 = class298_sub53.readString(559048564);
	}

	void method2916(RsByteBuffer class298_sub53) {
		((Class298_Sub17_Sub2) this).anInt9197 = class298_sub53.readUnsignedShort() * 857287155;
		((Class298_Sub17_Sub2) this).aByte9195 = class298_sub53.readByte(-12558881);
		((Class298_Sub17_Sub2) this).anInt9198 = class298_sub53.readUnsignedShort() * -1333007471;
		class298_sub53.readLong((short) 23274);
		((Class298_Sub17_Sub2) this).aString9196 = class298_sub53.readString(-45402634);
	}

	void method2919(Class298_Sub25 class298_sub25) {
		Class163 class163 = (class298_sub25.aClass163Array7359[((Class298_Sub17_Sub2) this).anInt9197 * 2029492027]);
		class163.aByte1683 = ((Class298_Sub17_Sub2) this).aByte9195;
		class163.anInt1682 = ((Class298_Sub17_Sub2) this).anInt9198 * -1537662205;
		class163.aString1681 = ((Class298_Sub17_Sub2) this).aString9196;
	}
}
