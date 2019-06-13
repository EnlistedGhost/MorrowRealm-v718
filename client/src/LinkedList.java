/* Class437 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import java.util.Iterator;

public final class LinkedList implements Iterable {
	Class298[] aClass298Array5512;
	Class298 aClass298_5513;
	long aLong5514;
	int maxSize;
	Class298 aClass298_5516;
	int anInt5517 = 0;
	static int anInt5518;
	public static Class377 aClass377_5519;
	public static Class371 aClass371_5520;

	public void method5811(byte i) {
		try {
			for (int i_0_ = 0; i_0_ < 577108745 * maxSize; i_0_++) {
				Class298 class298 = aClass298Array5512[i_0_];
				for (;;) {
					Class298 class298_1_ = class298.aClass298_3187;
					if (class298 == class298_1_) {
						break;
					}
					class298_1_.method2839(-1460969981);
				}
			}
			aClass298_5513 = null;
			aClass298_5516 = null;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.i(").append(')').toString());
		}
	}

	public Class298 method5812(long l) {
		try {
			aLong5514 = l * -1039549200087996967L;
			Class298 class298 = (aClass298Array5512[(int) (l & 577108745 * maxSize - 1)]);
			for (aClass298_5513 = class298.aClass298_3187; aClass298_5513 != class298; aClass298_5513 = aClass298_5513.aClass298_3187) {
				if ((aClass298_5513.aLong3188 * 7051297995265073167L) == l) {
					Class298 class298_2_ = aClass298_5513;
					aClass298_5513 = aClass298_5513.aClass298_3187;
					return class298_2_;
				}
			}
			aClass298_5513 = null;
			return null;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.a(").append(')').toString());
		}
	}

	public Class298 method5813(int i) {
		try {
			if (null == aClass298_5513) {
				return null;
			}
			for (Class298 class298 = (aClass298Array5512[(int) ((aLong5514 * -6131383980927776151L) & (maxSize * 577108745) - 1)]); aClass298_5513 != class298; aClass298_5513 = aClass298_5513.aClass298_3187) {
				if (aLong5514 * -6131383980927776151L == (aClass298_5513.aLong3188 * 7051297995265073167L)) {
					Class298 class298_3_ = aClass298_5513;
					aClass298_5513 = aClass298_5513.aClass298_3187;
					return class298_3_;
				}
			}
			aClass298_5513 = null;
			return null;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.f(").append(')').toString());
		}
	}

	public int method5814(int i) {
		try {
			int i_4_ = 0;
			for (int i_5_ = 0; i_5_ < maxSize * 577108745; i_5_++) {
				Class298 class298 = aClass298Array5512[i_5_];
				for (Class298 class298_6_ = class298.aClass298_3187; class298 != class298_6_; class298_6_ = class298_6_.aClass298_3187) {
					i_4_++;
				}
			}
			return i_4_;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.b(").append(')').toString());
		}
	}

	public Class298 method5815(byte i) {
		try {
			if (-1375920843 * anInt5517 > 0 && (aClass298_5516 != (aClass298Array5512[anInt5517 * -1375920843 - 1]))) {
				Class298 class298 = aClass298_5516;
				aClass298_5516 = class298.aClass298_3187;
				return class298;
			}
			while_102_: do {
				Class298 class298;
				do {
					if (anInt5517 * -1375920843 >= 577108745 * maxSize) {
						break while_102_;
					}
					class298 = (aClass298Array5512[((anInt5517 += -1552413411) * -1375920843) - 1].aClass298_3187);
				} while (class298 == (aClass298Array5512[-1375920843 * anInt5517 - 1]));
				aClass298_5516 = class298.aClass298_3187;
				return class298;
			} while (false);
			return null;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.d(").append(')').toString());
		}
	}

	public Iterator iterator() {
		try {
			return new Class460(this);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.iterator(").append(')').toString());
		}
	}

	public Class298 method5816(int i) {
		try {
			anInt5517 = 0;
			return method5815((byte) 67);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.k(").append(')').toString());
		}
	}

	public void method5817(Class298 class298, long l) {
		try {
			if (null != class298.aClass298_3189) {
				class298.method2839(-1460969981);
			}
			Class298 class298_7_ = (aClass298Array5512[(int) (l & maxSize * 577108745 - 1)]);
			class298.aClass298_3189 = class298_7_.aClass298_3189;
			class298.aClass298_3187 = class298_7_;
			class298.aClass298_3189.aClass298_3187 = class298;
			class298.aClass298_3187.aClass298_3189 = class298;
			class298.aLong3188 = 4191220306876042991L * l;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.p(").append(')').toString());
		}
	}

	public LinkedList(int i) {
		maxSize = 1594491705 * i;
		aClass298Array5512 = new Class298[i];
		for (int i_8_ = 0; i_8_ < i; i_8_++) {
			Class298 class298 = aClass298Array5512[i_8_] = new Class298();
			class298.aClass298_3187 = class298;
			class298.aClass298_3189 = class298;
		}
	}

	static void method5818(int i, int i_9_, int i_10_, int i_11_, int i_12_, int i_13_, int i_14_, int i_15_, int i_16_, int i_17_, int i_18_, int i_19_, int i_20_, int i_21_, boolean bool, int i_22_) {
		try {
			if (i_9_ != 0 && -1 != i_11_) {
				Entity class365_sub1_sub1_sub2 = null;
				if (i_9_ < 0) {
					int i_23_ = -i_9_ - 1;
					if (i_23_ == -442628795 * client.playerIndex) {
						class365_sub1_sub1_sub2 = Class287.myPlayer;
					} else {
						class365_sub1_sub1_sub2 = (client.aClass365_Sub1_Sub1_Sub2_Sub2Array8805[i_23_]);
					}
				} else {
					int i_24_ = i_9_ - 1;
					Class298_Sub29 class298_sub29 = ((Class298_Sub29) client.aClass437_8696.method5812(i_24_));
					if (class298_sub29 != null) {
						class365_sub1_sub1_sub2 = ((Entity) class298_sub29.anObject7366);
					}
				}
				if (class365_sub1_sub1_sub2 != null) {
					Class350 class350 = class365_sub1_sub1_sub2.method4426(95049522);
					if (null != class350.anIntArrayArray3710 && null != class350.anIntArrayArray3710[i_11_]) {
						i_12_ -= class350.anIntArrayArray3710[i_11_][1];
					}
					if (class350.anIntArrayArray3753 != null && null != class350.anIntArrayArray3753[i_11_]) {
						i_12_ -= class350.anIntArrayArray3753[i_11_][1];
					}
				}
			}
			Class365_Sub1_Sub1_Sub1 class365_sub1_sub1_sub1 = (new Class365_Sub1_Sub1_Sub1(client.aClass283_8716.method2675(-1611682495), i, Class375.basePlane * -191892109, Class375.basePlane * -191892109, i_14_, i_15_, i_12_, client.anInt8884 * 443738891 + i_18_, client.anInt8884 * 443738891 + i_19_, i_20_, i_21_, i_9_, i_10_, i_13_, bool, i_11_));
			class365_sub1_sub1_sub1.method4405(i_16_, i_17_, Class356.method4271(i_16_, i_17_, Class375.basePlane * -191892109, -968708982) - i_13_, 443738891 * client.anInt8884 + i_18_, 1068285759);
			client.aClass453_8824.method5935(new Class298_Sub37_Sub2(class365_sub1_sub1_sub1), 520361177);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("sa.nu(").append(')').toString());
		}
	}
}
