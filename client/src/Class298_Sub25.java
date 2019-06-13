/* Class298_Sub25 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.util.BitSet;

public class Class298_Sub25 extends Class298 {
	boolean aBoolean7353;
	boolean aBoolean7354 = true;
	public byte aByte7355;
	public int anInt7356 = 0;
	int[] anIntArray7357;
	public String aString7358 = null;
	public Class163[] aClass163Array7359;
	public byte aByte7360;
	long aLong7361;

	public Class298_Sub25(RsByteBuffer class298_sub53) {
		method3098(class298_sub53, (byte) -94);
	}

	public int[] method3095(int i) {
		try {
			if (((Class298_Sub25) this).anIntArray7357 == null) {
				String[] strings = new String[anInt7356 * 649879491];
				((Class298_Sub25) this).anIntArray7357 = new int[649879491 * anInt7356];
				for (int i_0_ = 0; i_0_ < anInt7356 * 649879491; i_0_++) {
					strings[i_0_] = aClass163Array7359[i_0_].aString1681;
					((Class298_Sub25) this).anIntArray7357[i_0_] = i_0_;
				}
				Class298_Sub32_Sub32.method3345(strings, (((Class298_Sub25) this).anIntArray7357), 715814355);
			}
			return ((Class298_Sub25) this).anIntArray7357;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.f(").append(')').toString());
		}
	}

	void method3096(Class163 class163, int i) {
		try {
			if (aClass163Array7359 == null || 649879491 * anInt7356 >= aClass163Array7359.length)
				method3099(5 + 649879491 * anInt7356, (byte) 26);
			aClass163Array7359[(anInt7356 += 506595563) * 649879491 - 1] = class163;
			((Class298_Sub25) this).anIntArray7357 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.b(").append(')').toString());
		}
	}

	public int method3097(String string, byte i) {
		try {
			for (int i_1_ = 0; i_1_ < 649879491 * anInt7356; i_1_++) {
				if (aClass163Array7359[i_1_].aString1681.equalsIgnoreCase(string))
					return i_1_;
			}
			return -1;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.i(").append(')').toString());
		}
	}

	void method3098(RsByteBuffer class298_sub53, byte i) {
		try {
			int i_2_ = class298_sub53.readUnsignedByte();
			if ((i_2_ & 0x1) != 0)
				((Class298_Sub25) this).aBoolean7353 = true;
			if ((i_2_ & 0x2) != 0)
				((Class298_Sub25) this).aBoolean7354 = true;
			aLong3188 = (class298_sub53.readLong((short) 1568) * 4191220306876042991L);
			((Class298_Sub25) this).aLong7361 = (class298_sub53.readLong((short) 25426) * 8816161044679006451L);
			aString7358 = class298_sub53.readString(1262859658);
			class298_sub53.readUnsignedByte();
			aByte7355 = class298_sub53.readByte(-12558881);
			aByte7360 = class298_sub53.readByte(-12558881);
			anInt7356 = class298_sub53.readUnsignedShort() * 506595563;
			if (anInt7356 * 649879491 > 0) {
				aClass163Array7359 = new Class163[649879491 * anInt7356];
				for (int i_3_ = 0; i_3_ < anInt7356 * 649879491; i_3_++) {
					Class163 class163 = new Class163();
					if (((Class298_Sub25) this).aBoolean7353)
						class298_sub53.readLong((short) 26032);
					if (((Class298_Sub25) this).aBoolean7354)
						class163.aString1681 = class298_sub53.readString(254031265);
					class163.aByte1683 = class298_sub53.readByte(-12558881);
					class163.anInt1682 = class298_sub53.readUnsignedShort() * -62810701;
					aClass163Array7359[i_3_] = class163;
				}
			}
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.k(").append(')').toString());
		}
	}

	static {
		new BitSet(65536);
	}

	void method3099(int i, byte i_4_) {
		try {
			if (null != aClass163Array7359)
				Class425.method5737(aClass163Array7359, 0, aClass163Array7359 = new Class163[i], 0, anInt7356 * 649879491);
			else
				aClass163Array7359 = new Class163[i];
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.a(").append(')').toString());
		}
	}

	void method3100(int i, int i_5_) {
		try {
			anInt7356 -= 506595563;
			if (0 == anInt7356 * 649879491)
				aClass163Array7359 = null;
			else
				Class425.method5737(aClass163Array7359, 1 + i, aClass163Array7359, i, anInt7356 * 649879491 - i);
			((Class298_Sub25) this).anIntArray7357 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.p(").append(')').toString());
		}
	}

	static final void method3101(ClientScript2 class403, int i) {
		try {
			Class107.method1138(class403, -807637826);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.si(").append(')').toString());
		}
	}

	static final void method3102(ClientScript2 class403, int i) {
		try {
			Class298_Sub37_Sub13 class298_sub37_sub13 = WorldMap.method3705(((ClientScript2) class403).anIntArray5244[(((ClientScript2) class403).anInt5239 -= -391880689) * 681479919]);
			if (class298_sub37_sub13 == null || null == class298_sub37_sub13.aString9641)
				((ClientScript2) class403).anObjectArray5240[((((ClientScript2) class403).anInt5241 += 969361751) * -203050393) - 1] = "";
			else
				((ClientScript2) class403).anObjectArray5240[((((ClientScript2) class403).anInt5241 += 969361751) * -203050393) - 1] = class298_sub37_sub13.aString9641;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.adf(").append(')').toString());
		}
	}

	static final void method3103(ClientScript2 class403, int i) {
		try {
			int i_6_ = (((ClientScript2) class403).integerstack[1883543357 * ((ClientScript2) class403).integerPos]);
			Long var_long = (Long) Class313.anObjectArray3298[i_6_];
			if (var_long == null)
				((ClientScript2) class403).aLongArray5251[((((ClientScript2) class403).anInt5245 += -682569305) * 1685767703) - 1] = -1L;
			else
				((ClientScript2) class403).aLongArray5251[((((ClientScript2) class403).anInt5245 += -682569305) * 1685767703) - 1] = var_long.longValue();
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("abj.bs(").append(')').toString());
		}
	}
}
