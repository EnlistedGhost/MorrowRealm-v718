/* Class298_Sub42 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class298_Sub42 extends Class298 {
	int anInt7457;
	int anInt7458;
	int anInt7459;
	int anInt7460;
	int anInt7461;
	int anInt7462;
	int anInt7463;
	int anInt7464;
	int anInt7465;

	boolean method3520(int i, int i_0_, int i_1_, int i_2_) {
		try {
			if (anInt7464 * -206354885 == i && i_0_ >= anInt7459 * -731405573 && i_0_ <= 480895455 * anInt7460 && i_1_ >= -1728316981 * anInt7462 && i_1_ <= -176293349 * anInt7458) {
				return true;
			}
			return false;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("aca.f(").append(')').toString());
		}
	}

	boolean method3521(int i, int i_3_, int i_4_) {
		try {
			if (i >= anInt7459 * -731405573 && i <= 480895455 * anInt7460 && i_3_ >= -1728316981 * anInt7462 && i_3_ <= -176293349 * anInt7458) {
				return true;
			}
			return false;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("aca.a(").append(')').toString());
		}
	}

	boolean method3522(int i, int i_5_, byte i_6_) {
		try {
			if (i >= anInt7457 * 1528024175 && i <= anInt7461 * 37578241 && i_5_ >= 50981941 * anInt7463 && i_5_ <= anInt7465 * 1374138429) {
				return true;
			}
			return false;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("aca.b(").append(')').toString());
		}
	}

	void method3523(int i, int i_7_, int[] is, int i_8_) {
		try {
			is[0] = -206354885 * anInt7464;
			is[1] = (-731405573 * anInt7459 - 1528024175 * anInt7457 + i);
			is[2] = i_7_ + (anInt7462 * -1728316981 - 50981941 * anInt7463);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("aca.p(").append(')').toString());
		}
	}

	Class298_Sub42(int i, int i_9_, int i_10_, int i_11_, int i_12_, int i_13_, int i_14_, int i_15_, int i_16_) {
		//System.out.println("Them values! " + i + ", " + i_9_ + ", " + i_10_ + ", " + i_11_ + ", " + i_12_ + ", " + i_13_ + ", " + i_14_ + ", " + i_15_ + ", " + i_16_);
		anInt7464 = 301572851 * i;
		anInt7459 = i_9_ * 868816947;
		anInt7462 = -1243061277 * i_10_;
		anInt7460 = i_11_ * 992156191;
		anInt7458 = 878333971 * i_12_;
		anInt7457 = i_13_ * -134278513;
		anInt7463 = 1339754013 * i_14_;
		anInt7461 = i_15_ * 1785108993;
		anInt7465 = i_16_ * 734947093;
	}

	void method3524(int i, int i_17_, int[] is, int i_18_) {
		try {
			is[0] = 0;
			is[1] = (anInt7457 * 1528024175 - anInt7459 * -731405573 + i);
			is[2] = (50981941 * anInt7463 - -1728316981 * anInt7462 + i_17_);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("aca.i(").append(')').toString());
		}
	}
}
