/* Class469 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Class469 {
	static int anInt5773 = 32768;
	static int anInt5774 = 1;
	public int anInt5775 = 0;
	CacheIndex aClass243_5776;
	public int anInt5777 = 0;
	CacheIndex aClass243_5778;
	CachingHashMap aClass348_5779 = new CachingHashMap(64);
	Interface20 anInterface20_5780 = null;

	public Class469(Class429 class429, CacheIndex class243, CacheIndex class243_0_, Interface20 interface20) {
		aClass243_5778 = class243;
		aClass243_5776 = class243_0_;
		anInterface20_5780 = interface20;
		if (null != aClass243_5778) {
			anInt5775 = (aClass243_5778.method2316(1, -331738803) * -1970727647);
		}
		if (aClass243_5776 != null) {
			anInt5777 = (aClass243_5776.method2316(1, 1090973479) * 1286548437);
		}
	}

	String method6044(Class367 class367, int[] is, long l) {
		try {
			if (anInterface20_5780 != null) {
				String string = anInterface20_5780.method241(class367, is, l);
				if (string != null) {
					return string;
				}
			}
			return Long.toString(l);
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("ti.f(").append(')').toString());
		}
	}

	public Class298_Sub37_Sub14 method6045(int i, short i_1_) {
		try {
			Class298_Sub37_Sub14 class298_sub37_sub14 = ((Class298_Sub37_Sub14) aClass348_5779.get(i));
			if (null != class298_sub37_sub14) {
				return class298_sub37_sub14;
			}
			byte[] is;
			if (i >= 32768) {
				is = aClass243_5776.getFileFromArchive(1, i & 0x7fff, (byte) -26);
			} else {
				is = aClass243_5778.getFileFromArchive(1, i, (byte) -31);
			}
			class298_sub37_sub14 = new Class298_Sub37_Sub14();
			class298_sub37_sub14.aClass469_9651 = this;
			if (is != null) {
				class298_sub37_sub14.method3457(new RsByteBuffer(is), 1219453152);
			}
			if (i >= 32768) {
				class298_sub37_sub14.method3456(-877048074);
			}
			aClass348_5779.method4194(class298_sub37_sub14, i);
			return class298_sub37_sub14;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("ti.a(").append(')').toString());
		}
	}

	public static int method6046(int i, int i_2_, byte i_3_) {
		try {
			int i_4_ = 0;
			for (/**/; i_2_ > 0; i_2_--) {
				i_4_ = i_4_ << 1 | i & 0x1;
				i >>>= 1;
			}
			return i_4_;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("ti.u(").append(')').toString());
		}
	}

	static void method6047(GraphicsToolkit class_ra, Interface_ma interface_ma, int i, int i_5_, int i_6_, int i_7_, int i_8_) {
		try {
			if (Class301_Sub1.anInt7624 * -1289394455 < 100) {
				Class429.method5761(class_ra, interface_ma, (byte) -95);
			}
			class_ra.r(i, i_5_, i + i_6_, i_7_ + i_5_);
			if (Class301_Sub1.anInt7624 * -1289394455 < 100) {
				int i_9_ = 20;
				int i_10_ = i + i_6_ / 2;
				int i_11_ = i_7_ / 2 + i_5_ - 18 - i_9_;
				class_ra.B(i, i_5_, i_6_, i_7_, -16777216, 0);
				class_ra.method5019(i_10_ - 152, i_11_, 304, 34, client.aColorArray8701[745003679 * client.anInt8971].getRGB(), 0);
				class_ra.B(i_10_ - 150, i_11_ + 2, Class301_Sub1.anInt7624 * 426783931, 30, client.aColorArray8689[745003679 * client.anInt8971].getRGB(), 0);
				Class101.aClass264_1084.method2490(Tradution.aClass470_5938.method6049(Class321.aClass429_3357, -875414210), i_10_, i_11_ + i_9_, client.aColorArray8691[client.anInt8971 * 745003679].getRGB(), -1, -863370132);
			} else {
				int i_12_ = (1196508279 * Class82_Sub14.anInt6875 - (int) (i_6_ / WorldMap.aFloat3233));
				int i_13_ = (1882038855 * Class376.anInt4090 + (int) (i_7_ / WorldMap.aFloat3233));
				int i_14_ = ((int) (i_6_ / WorldMap.aFloat3233) + Class82_Sub14.anInt6875 * 1196508279);
				int i_15_ = (Class376.anInt4090 * 1882038855 - (int) (i_7_ / WorldMap.aFloat3233));
				Class402.anInt5232 = ((1196508279 * Class82_Sub14.anInt6875 - (int) (i_6_ / WorldMap.aFloat3233)) * 1478623195);
				Class261.anInt2825 = ((Class376.anInt4090 * 1882038855 - (int) (i_7_ / WorldMap.aFloat3233)) * -1415233655);
				Class301_Sub1.anInt7625 = ((int) (2 * i_6_ / WorldMap.aFloat3233) * -830037779);
				Class413.anInt6590 = ((int) (i_7_ * 2 / WorldMap.aFloat3233) * 827686411);
				WorldMap.method3709(i_12_ + WorldMap.mapX, i_13_ + WorldMap.mapY, WorldMap.mapX + i_14_, i_15_ + WorldMap.mapY, i, i_5_, i_6_ + i, 1 + (i_5_ + i_7_));
				WorldMap.method3698(class_ra);
				Class453 class453 = WorldMap.method3702(class_ra);
				Class266.method2526(class_ra, class453, 0, 0, 1029726200);
				if (Class137_Sub1.anInt6991 * -1983068885 > 0) {
					Class88.anInt810 -= -1077128129;
					if (Class88.anInt810 * -1581933633 == 0) {
						Class137_Sub1.anInt6991 -= -63057533;
						Class88.anInt810 = -67726100;
					}
				}
				if (client.aBoolean8667) {
					int i_16_ = i + i_6_ - 5;
					int i_17_ = i_7_ + i_5_ - 8;
					Class343.aClass264_3673.method2474(new StringBuilder().append("Fps:").append(914379507 * Class291.anInt6467).toString(), i_16_, i_17_, 16776960, -1, (byte) -35);
					i_17_ -= 15;
					Runtime runtime = Runtime.getRuntime();
					int i_18_ = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
					int i_19_ = 16776960;
					if (i_18_ > 65536) {
						i_19_ = 16711680;
					}
					Class343.aClass264_3673.method2474(new StringBuilder().append("Mem:").append(i_18_).append("k").toString(), i_16_, i_17_, i_19_, -1, (byte) -2);
					i_17_ -= 15;
				}
			}
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("ti.bv(").append(')').toString());
		}
	}

	static Class298_Sub37_Sub13 method6048(CacheIndex index, int archive, int file, int i_21_) {
		try {
			RsByteBuffer buffer = new RsByteBuffer(index.getFileFromArchive(archive, file, (byte) 3));
			Class298_Sub37_Sub13 class298_sub37_sub13 = (new Class298_Sub37_Sub13(file, buffer.readString(-1014876746), buffer.readString(-346433981), buffer.readInt((byte) -81), buffer.readInt((byte) 27), buffer.readUnsignedByte() == 1, buffer.readUnsignedByte(), buffer.readUnsignedByte()));
			int hotlinkCount = buffer.readUnsignedByte();
			for (int i_23_ = 0; i_23_ < hotlinkCount; i_23_++) {
				class298_sub37_sub13.aClass453_9642.method5935(new Class298_Sub42(buffer.readUnsignedByte(), buffer.readUnsignedShort(), buffer.readUnsignedShort(), buffer.readUnsignedShort(), buffer.readUnsignedShort(), buffer.readUnsignedShort(), buffer.readUnsignedShort(), buffer.readUnsignedShort(), buffer.readUnsignedShort()), 2080542038);
			}
			class298_sub37_sub13.method3454(955144381);
			return class298_sub37_sub13;
		} catch (RuntimeException runtimeexception) {
			throw Class346.method4175(runtimeexception, new StringBuilder().append("ti.k(").append(')').toString());
		}
	}
}
