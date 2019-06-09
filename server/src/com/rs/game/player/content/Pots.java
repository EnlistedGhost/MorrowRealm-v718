package com.rs.game.player.content;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.item.Item;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.controlers.CrucibleControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/*
 * Revised and Organized by Ben
 */

public final class Pots {

	public static enum Pot {

		/*
		 * Start of Fishy Mixes
		 */
		ATTACK_FISHY(new int[] { 11429, 11431 }, Effects.ATTACK_POTION),

		ANTIPOISON_FISHY(new int[] { 11433, 11435 }, Effects.ANTIPOISON),

		STRENGTH_FISHY(new int[] { 11441, 11443 }, Effects.STRENGTH_POTION),

		COMBAT_FISHY(new int[] { 11445, 11447 }, Effects.COMBAT_POTION),

		RESTORE_FISHY(new int[] { 11449, 11451 }, Effects.RESTORE_POTION),

		ENERGY_FISHY(new int[] { 11453, 11455 }, Effects.ENERGY_POTION),

		DEFENCE_FISHY(new int[] { 11457, 11459 }, Effects.DEFENCE_POTION),

		AGILITY_FISHY(new int[] { 11461, 11463 }, Effects.AGILITY_POTION),

		PRAYER_FISHY(new int[] { 11465, 11467 }, Effects.PRAYER_POTION),

		SUPER_ATT_FISHY(new int[] { 11469, 11471 }, Effects.SUPER_ATT_POTION),

		SUPER_ANTIPOISON_FISHY(new int[] { 11473, 11475 }, Effects.SUPER_ANTIPOISON),

		FISHING_FISHY(new int[] { 11477, 11479 }, Effects.FISHING_POTION),

		SUPER_ENERGY_FISHY(new int[] { 11481, 11483 }, Effects.SUPER_ENERGY),

		SUPER_STR_FISHY(new int[] { 11485, 11487 }, Effects.SUPER_STR_POTION),

		SUPER_RESTORE_FISHY(new int[] { 11493, 11495 }, Effects.SUPER_RESTORE),

		SUPER_DEF_FISHY(new int[] { 11497, 11499 }, Effects.SUPER_DEF_POTION),

		ANTI_FIRE_FISHY(new int[] { 11505, 11507 }, Effects.ANTI_FIRE),

		RANGE_FISHY(new int[] { 11509, 11511 }, Effects.RANGE_POTION),

		MAGIC_FISHY(new int[] { 11513, 11515 }, Effects.MAGIC_POTION),

		HUNTER_FISHY(new int[] { 11517, 11519 }, Effects.HUNTER_POTION),

		ZAMORAK_BREW_FISHY(new int[] { 11521, 11523 }, Effects.ZAMORAK_BREW),
		/*
		 * End of Fishy Mixes
		 */

		/*
		 * Start of Stealing Creation Potions
		 */
		PRAYER_POTION_SC(new int[] { 14207, 14209, 14211, 14213, 14215 }, Effects.PRAYER_POTION),

		ENERGY_POTION_SC(new int[] { 14217, 14219, 14221, 14223, 14225 }, Effects.ENERGY_POTION),

		SUPER_ATT_POTION_SC(new int[] { 14227, 14229, 14231, 14233, 14235 }, Effects.SUPER_ATT_POTION),

		SUPER_STR_POTION_SC(new int[] { 14237, 14239, 14241, 14243, 14245 }, Effects.SUPER_STR_POTION),

		RANGE_POTION_SC(new int[] { 14247, 14247, 14247, 14247, 14247 }, Effects.RANGE_POTION),

		DEFENCE_POTION_SC(new int[] { 14257, 14259, 14261, 14263, 14265 }, Effects.DEFENCE_POTION),

		MAGIC_POTION_SC(new int[] { 14267, 14269, 14271, 14273, 14275 }, Effects.MAGIC_POTION),

		SUMMONING_POTION_SC(new int[] { 14277, 14279, 14281, 14283, 14285 }, Effects.SUMMONING_POT),
		/*
		 * End of Stealing Creation Potions
		 */

		/*
		 * Start of Dung Potions
		 */
		MAGIC_POTION_DUNG1(new int[] { 17556 }, Effects.MAGIC_POTION1), MAGIC_POTION_DUNG2(new int[] { 17582 }, Effects.MAGIC_POTION2), MAGIC_POTION_DUNG3(new int[] { 17606 }, Effects.MAGIC_POTION3),

		RANGE_POTION_DUNG1(new int[] { 17558 }, Effects.RANGE_POTION1), RANGE_POTION_DUNG2(new int[] { 17584 }, Effects.RANGE_POTION2), RANGE_POTION_DUNG3(new int[] { 17608 }, Effects.RANGE_POTION3),

		MELEE_POTION1(new int[] { 17560 }, Effects.MELEE_POTION1), MELEE_POTION2(new int[] { 17586 }, Effects.MELEE_POTION2), MELEE_POTION3(new int[] { 17610 }, Effects.MELEE_POTION3),

		DEFENCE_POTION_DUNG1(new int[] { 17562 }, Effects.DEFENCE_POTION1), DEFENCE_POTION_DUNG2(new int[] { 17588 }, Effects.DEFENCE_POTION2), DEFENCE_POTION_DUNG3(new int[] { 17612 }, Effects.DEFENCE_POTION3),

		RESTORE_POTION_DUNG1(new int[] { 17564 }, Effects.RESTORE_POTION1), RESTORE_POTION_DUNG2(new int[] { 17590 }, Effects.RESTORE_POTION2), RESTORE_POTION_DUNG3(new int[] { 17614 }, Effects.RESTORE_POTION3),

		ANTI_POISON_DUNG(new int[] { 17566 }, Effects.ANTIPOISON),

		CURE_POTION_DUNG(new int[] { 17568 }, Effects.CURE_POTION),

		REJUVE_POTION_DUNG1(new int[] { 17570 }, Effects.REJUVE_POTION1), REJUVE_POTION_DUNG2(new int[] { 17594 }, Effects.REJUVE_POTION2), REJUVE_POTION_DUNG3(new int[] { 17618 }, Effects.REJUVE_POTION3),

		GATHER_POTION_DUNG1(new int[] { 17574 }, Effects.GATHER_POTION1), GATHER_POTION_DUNG2(new int[] { 17598 }, Effects.GATHER_POTION2), GATHER_POTION_DUNG3(new int[] { 17622 }, Effects.GATHER_POTION3),

		ARTISAN_POTION_DUNG1(new int[] { 17576 }, Effects.ARTISAN_POTION1), ARTISAN_POTION_DUNG2(new int[] { 17600 }, Effects.ARTISAN_POTION2), ARTISAN_POTION_DUNG3(new int[] { 17624 }, Effects.ARTISAN_POTION3),

		NATURALIST_POTION_DUNG1(new int[] { 17578 }, Effects.NATURALIST_POTION1), NATURALIST_POTION_DUNG2(new int[] { 17602 }, Effects.NATURALIST_POTION2), NATURALIST_POTION_DUNG3(new int[] { 17626 }, Effects.NATURALIST_POTION3),

		SURVIVALIST_POTION_DUNG1(new int[] { 17580 }, Effects.SURVIVALIST_POTION1), SURVIVALIST_POTION_DUNG2(new int[] { 17604 }, Effects.SURVIVALIST_POTION2), SURVIVALIST_POTION_DUNG3(new int[] { 17628 }, Effects.SURVIVALIST_POTION3),
		/*
		 * End of Dung Potions
		 */

		ATTACK_POTION(new int[] { 2428, 121, 123, 125 }, Effects.ATTACK_POTION),

		STRENGTH_POTION(new int[] { 113, 115, 117, 119 }, Effects.STRENGTH_POTION),

		DEFENCE_POTION(new int[] { 2432, 133, 135, 137 }, Effects.DEFENCE_POTION),

		COMBAT_POTION(new int[] { 9739, 9741, 9743, 9745 }, Effects.COMBAT_POTION),

		RESTORE_POTION(new int[] { 2430, 127, 129, 131 }, Effects.RESTORE_POTION),

		AGILITY_POTION(new int[] { 3032, 3034, 3036, 3038 }, Effects.AGILITY_POTION),

		RANGE_POTION(new int[] { 2444, 169, 171, 173 }, Effects.RANGE_POTION),

		MAGIC_POTION(new int[] { 3040, 3042, 3044, 3046 }, Effects.MAGIC_POTION),

		MAGIC_FLASK(new int[] { 23423, 23425, 23427, 23429, 23431, 23433 }, Effects.MAGIC_POTION),

		ANTI_POISON(new int[] { 2446, 175, 177, 179 }, Effects.ANTIPOISON),

		SUPER_ANTI_POISON(new int[] { 2448, 181, 183, 185 }, Effects.SUPER_ANTIPOISON),

		ANTI_POISON_PLUS(new int[] { 5943, 5945, 5947, 5949 }, Effects.ANTIPOISON_PLUS),

		ANTI_POISON_PLUS_PLUS(new int[] { 5952, 5954, 5956, 5958 }, Effects.ANTIPOISON_PLUS_PLUS),

		PRAYER_POTION(new int[] { 2434, 139, 141, 143 }, Effects.PRAYER_POTION),

		SUPER_ATT_POTION(new int[] { 2436, 145, 147, 149 }, Effects.SUPER_ATT_POTION),

		SUPER_STR_POTION(new int[] { 2440, 157, 159, 161 }, Effects.SUPER_STR_POTION),

		SUPER_DEF_POTION(new int[] { 2442, 163, 165, 167 }, Effects.SUPER_DEF_POTION),

		ENERGY_POTION(new int[] { 3008, 3010, 3012, 3014}, Effects.ENERGY_POTION),

		SUPER_ENERGY(new int[] { 3016, 3018, 3020, 3022}, Effects.SUPER_ENERGY),

		EXTREME_ATT_POTION(new int[] { 15308, 15309, 15310, 15311 }, Effects.EXTREME_ATT_POTION),

		EXTREME_STR_POTION(new int[] { 15312, 15313, 15314, 15315 }, Effects.EXTREME_STR_POTION),

		EXTREME_DEF_POTION(new int[] { 15316, 15317, 15318, 15319 }, Effects.EXTREME_DEF_POTION),

		EXTREME_MAGE_POTION(new int[] { 15320, 15321, 15322, 15323 }, Effects.EXTREME_MAG_POTION),

		EXTREME_RANGE_POTION(new int[] { 15324, 15325, 15326, 15327 }, Effects.EXTREME_RAN_POTION),

		SUPER_RESTORE_POTION(new int[] { 3024, 3026, 3028, 3030 }, Effects.SUPER_RESTORE),

		SARADOMIN_BREW(new int[] { 6685, 6687, 6689, 6691 }, Effects.SARADOMIN_BREW),

		ZAMORAK_BREW(new int[] { 2450, 189, 191, 193 }, Effects.ZAMORAK_BREW),

		RECOVER_SPECIAL(new int[] { 15300, 15301, 15302, 15303 }, Effects.RECOVER_SPECIAL),

		SUPER_PRAYER(new int[] { 15328, 15329, 15330, 15331 }, Effects.SUPER_PRAYER),

		OVERLOAD(new int[] { 15332, 15333, 15334, 15335 }, Effects.OVERLOAD),

		ANTI_FIRE(new int[] { 2452, 2454, 2456, 2458 }, Effects.ANTI_FIRE),

		SUMMONING_POTION(new int[] { 12140, 12142, 12144, 12146 }, Effects.SUMMONING_POT),

		SUMMONING_FLASK(new int[] { 23621, 23623, 23625, 23627, 23629, 23631 }, Effects.SUMMONING_POT),

		SANFEW_SERUM(new int[] { 10925, 10927, 10929, 10931 }, Effects.SANFEW_SERUM),

		PRAYER_RENEWAL(new int[] {21630, 21632, 21634, 21636}, Effects.PRAYER_RENEWAL),

		PRAYER_RENEWAL_FLASK(new int[] {23609, 23611, 23613, 23615, 23617, 23619}, Effects.PRAYER_RENEWAL),


		/*JUJU_MINING_FLASK(new int[] { 23131, 23132, 23133, 23134, 23135, 23136 },
				null),

		JUJU_COOKING_FLASK(new int[] { 23137, 23138, 23139, 23140, 23141, 23142 },
				null),

		JUJU_FARMING_FLASK(new int[] { 23143, 23144, 23145, 23146, 23147, 23148 },
				null),

		JUJU_WOODCUTTING_FLASK(new int[] { 23149, 23150, 23151, 23152, 23153, 23154 },
				null),

		JUJU_FISHING_FLASK(new int[] { 23155, 23156, 23157, 23158, 23159, 23160 },
				null),

		JUJU_HUNTER_FLASK(new int[] { 23161, 23162, 23163, 23164, 23165, 23166 },
				null),

		SCENTLESS_FLASK(new int[] { 23167, 23168, 23169, 23170, 23171, 23172 },
				null),

		SARADOMINS_BLESSING_FLASK(new int[] { 23173, 23174, 23175, 23176, 23177, 23178 },
				null),

		GUTHIXS_GIFT_FLASK(new int[] { 23179, 23180, 23181, 23182, 23183, 23184 },
				null),

		ZAMORAKS_FAVOUR_FLASK(new int[] { 23185, 23186, 23187, 23188, 23189, 23190 },
				null),*/

		ATTACK_FLASK(new int[] { 23195, 23197, 23199, 23201, 23203, 23205 }, Effects.ATTACK_POTION),

		STRENGTH_FLASK(new int[] { 23207, 23209, 23211, 23213, 23215, 23217 }, Effects.STRENGTH_POTION),

		RESTORE_FLASK(new int[] { 23219, 23221, 23223, 23225, 23227, 23229 }, Effects.RESTORE_POTION),

		DEFENCE_FLASK(new int[] { 23231, 23233, 23235, 23237, 23239, 23241 }, Effects.DEFENCE_POTION),

		PRAYER_FLASK(new int[] { 23243, 23245, 23247, 23249, 23251, 23253 }, Effects.PRAYER_POTION),

		SUPER_ATTACK_FLASK(new int[] { 23255, 23257, 23259, 23261, 23263, 23265 }, Effects.SUPER_ATT_POTION),

		FISHING_FLASK(new int[] { 23267, 23269, 23271, 23273, 23275, 23277 }, Effects.FISHING_POTION),

		SUPER_STRENGTH_FLASK(new int[] { 23279, 23281, 23283, 23285, 23287, 23289 }, Effects.SUPER_STR_POTION),

		SUPER_DEFENCE_FLASK(new int[] { 23291, 23293, 23295, 23297, 23299, 23301 }, Effects.SUPER_DEF_POTION),

		RANGING_FLASK(new int[] { 23303, 23305, 23307, 23309, 23311, 23313 }, Effects.RANGE_POTION),

		ANTIPOISON_FLASK(new int[] { 23315, 23317, 23319, 23321, 23323, 23325 }, Effects.ANTIPOISON),

		SUPER_ANTIPOISON_FLASK(new int[] { 23327, 23329, 23331, 23333, 23335, 23337 }, Effects.SUPER_ANTIPOISON),

		ANTI_POISON_PLUS_FLASK(new int[] { 23579, 23581, 23583, 23585, 23587, 23589 }, Effects.ANTIPOISON_PLUS),

		ANTI_POISON_PLUS_PLUS_FLASK(new int[] { 23591, 23593, 23595, 23597, 23599, 23601 }, Effects.ANTIPOISON_PLUS_PLUS),

		ZAMORAK_BREW_FLASK(new int[] { 23339, 23341, 23343, 23345, 23347, 23349 }, Effects.ZAMORAK_BREW),

		SARADOMIN_BREW_FLASK(new int[] { 23351, 23353, 23355, 23357, 23359, 23361 }, Effects.SARADOMIN_BREW),

		ANTIFIRE_FLASK(new int[] { 23363, 23365, 23367, 23369, 23371, 23373 }, Effects.ANTI_FIRE),

		ENERGY_FLASK(new int[] { 23375, 23377, 23379, 23381, 23383, 23385 }, Effects.ENERGY_POTION),

		SUPER_ENERGY_FLASK(new int[] { 23387, 23389, 23391, 23393, 23395, 23397 }, Effects.SUPER_ENERGY),

		SUPER_RESTORE_FLASK(new int[] { 23399, 23401, 23403, 23405, 23407, 23409 }, Effects.SUPER_RESTORE),

		COMBAT_FLASK(new int[] { 23447, 23449, 23451, 23453, 23455, 23457 }, Effects.COMBAT_POTION),

		AGILITY_FLASK(new int[] { 23411, 23413, 23415, 23417, 23419, 23421 }, Effects.AGILITY_POTION),

		HUNTER_FLASK(new int[] { 23435, 23437, 23439, 23441, 23443, 23445 }, Effects.HUNTER_POTION),

		CRAFTING_FLASK(new int[] { 23459, 23461, 23463, 23465, 23467, 23469 }, Effects.CRAFTING_POTION),

		FLETCHING_FLASK(new int[] { 23471, 23473, 23475, 23477, 23479, 23481 }, Effects.FLETCHING_POTION),

		RECOVER_SPECIAL_FLASK(new int[] { 23483, 23484, 23485, 23486, 23487, 23488 }, Effects.RECOVER_SPECIAL),

		/*	SUPER_ANTIFIRE_FLASK(new int[] { 23489, 23490, 23491, 23492, 23493, 23494 },
				null),*/

		EXTREME_ATTACK_FLASK(new int[] { 23495, 23496, 23497, 23498, 23499, 23500 }, Effects.EXTREME_ATT_POTION),

		EXTREME_STRENGTH_FLASK(new int[] { 23501, 23502, 23503, 23504, 23505, 23506 }, Effects.EXTREME_STR_POTION),

		EXTREME_DEFENCE_FLASK(new int[] { 23507, 23508, 23509, 23510, 23511, 23512 }, Effects.EXTREME_DEF_POTION),

		EXTREME_MAGIC_FLASK(new int[] { 23513, 23514, 23515, 23516, 23517, 23518 }, Effects.EXTREME_MAG_POTION),

		EXTREME_RANGING_FLASK(new int[] { 23519, 23520, 23521, 23522, 23523, 23524 }, Effects.EXTREME_RAN_POTION),

		SUPER_PRAYER_FLASK(new int[] { 23525, 23526, 23527, 23528, 23529, 23530 }, Effects.SUPER_PRAYER),

		OVERLOAD_FLASK(new int[] { 23531, 23532, 23533, 23534, 23535, 23536 }, Effects.OVERLOAD),

		/*	RELICYMS_BALM_FLASK(new int[] { 23537, 23539, 23541, 23543, 23545, 23547 },
				null),

		SERUM_207_FLASK(new int[] { 23549, 23550, 23551, 23552, 23553, 23554 },
				null),

		GUTHIX_BALANCE_FLASK(new int[] { 23555, 23557, 23559, 23561, 23563, 23565 },
				null),*/

		SANFEW_SERUM_FLASK(new int[] { 23567, 23569, 23571, 23573, 23575, 23577 }, Effects.SANFEW_SERUM),

		/*ANTIPOISON_PLUS_FLASK(new int[] { 23579, 23581, 23583, 23585, 23587, 23589 },
				null),

		ANTIPOISON_PLUS_PLUS_FLASK(new int[] { 23591, 23593, 23595, 23597, 23599, 23601 },
				null),

		SERUM_208_FLASK(new int[] { 23603, 23604, 23605, 23606, 23607, 23608 },
				null)

				/*MAGIC_ESSENCE_FLASK(new int[] { 23633, 23634, 23635, 23636, 23637, 23638 },
				null)*/;

		private int[] id;
		private Effects effect;

		private Pot(int[] id, Effects effect) {
			this.id = id;
			this.effect = effect;
		}
	}

	private enum Effects {
		COMBAT_POTION(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		MELEE_POTION1(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		MELEE_POTION2(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		MELEE_POTION3(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		GATHER_POTION1(Skills.WOODCUTTING, Skills.MINING, Skills.FISHING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		GATHER_POTION2(Skills.WOODCUTTING, Skills.MINING, Skills.FISHING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		GATHER_POTION3(Skills.WOODCUTTING, Skills.MINING, Skills.FISHING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		ARTISAN_POTION1(Skills.SMITHING, Skills.CRAFTING, Skills.FLETCHING, Skills.CONSTRUCTION, Skills.FIREMAKING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		ARTISAN_POTION2(Skills.SMITHING, Skills.CRAFTING, Skills.FLETCHING, Skills.CONSTRUCTION, Skills.FIREMAKING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		ARTISAN_POTION3(Skills.SMITHING, Skills.CRAFTING, Skills.FLETCHING, Skills.CONSTRUCTION, Skills.FIREMAKING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		NATURALIST_POTION1(Skills.COOKING, Skills.FARMING, Skills.HERBLORE, Skills.RUNECRAFTING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		NATURALIST_POTION2(Skills.COOKING, Skills.FARMING, Skills.HERBLORE, Skills.RUNECRAFTING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		NATURALIST_POTION3(Skills.COOKING, Skills.FARMING, Skills.HERBLORE, Skills.RUNECRAFTING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		SURVIVALIST_POTION1(Skills.AGILITY, Skills.THIEVING, Skills.HUNTER, Skills.SLAYER) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		SURVIVALIST_POTION2(Skills.AGILITY, Skills.THIEVING, Skills.HUNTER, Skills.SLAYER) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		SURVIVALIST_POTION3(Skills.AGILITY, Skills.THIEVING, Skills.HUNTER, Skills.SLAYER) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		AGILITY_POTION(Skills.AGILITY) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		HUNTER_POTION(Skills.HUNTER) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		CRAFTING_POTION(Skills.CRAFTING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		FLETCHING_POTION(Skills.FLETCHING) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		ATTACK_POTION(Skills.ATTACK) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		FISHING_POTION(Skills.FISHING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (level + 3);
			}
		},
		ZAMORAK_BREW(Skills.ATTACK) {
			int toAdd;
			@Override
			public void extra(Player player) {
				toAdd = (player.getSkills().getLevelForXp(Skills.ATTACK));
				player.getSkills().set(Skills.ATTACK, toAdd);
			}

		},
		SANFEW_SERUM(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE,
				Skills.MAGIC, Skills.RANGE, Skills.AGILITY, Skills.COOKING,
				Skills.CRAFTING, Skills.FARMING, Skills.FIREMAKING,
				Skills.FISHING, Skills.FLETCHING, Skills.HERBLORE,
				Skills.MINING, Skills.RUNECRAFTING, Skills.SLAYER,
				Skills.SMITHING, Skills.THIEVING, Skills.WOODCUTTING,
				Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}

			@Override
			public void extra(Player player) {
				player.getPrayer().restorePrayer(
						(int) ((int) (player.getSkills().getLevelForXp(
								Skills.PRAYER) * 0.33 * 10) * player
								.getAuraManager()
								.getPrayerPotsRestoreMultiplier()));
				player.addPoisonImmune(180000);
				//TODO DISEASE HEALING
			}

		},
		SUMMONING_POT(Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int restore = (int) (Math.floor(player.getSkills().getLevelForXp(Skills.SUMMONING) * 0.25) + 7);
				if(actualLevel + restore > realLevel)
					return realLevel;
				return actualLevel + restore;
			}
			@Override
			public void extra(Player player) {
				Familiar familiar = player.getFamiliar();
				if(familiar != null)
					familiar.restoreSpecialAttack(15);
			}
		},
		ANTIPOISON() {
			@Override
			public void extra(Player player) {
				player.addPoisonImmune(180000);
				player.getPackets().sendGameMessage("You are now immune to poison.");
			}
		},
		ANTIPOISON_PLUS() {
			@Override
			public void extra(Player player) {
				player.addPoisonImmune(518000);
				player.getPackets().sendGameMessage("You are now immune to poison.");
			}
		},
		ANTIPOISON_PLUS_PLUS() {
			@Override
			public void extra(Player player) {
				player.addPoisonImmune(720000);
				player.getPackets().sendGameMessage("You are now immune to poison.");
			}
		},
		CURE_POTION() {
			@Override
			public void extra(final Player player) {
				player.addPoisonImmune(180000);
				player.addFireImmune(360000);
				final long current = player.getFireImmune();
				player.getPackets().sendGameMessage( "You are now immune to poison, dragonfire and diseases.");
				WorldTasksManager.schedule(new WorldTask() {
					boolean stop = false;
					@Override
					public void run() {
						if (current != player.getFireImmune()) {
							stop();
							return;
						}
						if (!stop) {
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion is about to run out...</col>");
							stop = true;
						} else {
							stop();
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion has ran out...</col>");
						}		
					}
				}, 500, 100);
			}
		},
		SUPER_ANTIPOISON() {
			@Override
			public void extra(Player player) {
				player.addPoisonImmune(346000);
				player.getPackets().sendGameMessage("You are now immune to poison.");
			}
		},
		ENERGY_POTION() {
			@Override
			public void extra(Player player) {
				int restoredEnergy = player.getRunEnergy() + 20;
				player.setRunEnergy(restoredEnergy > 100 ? 100 : restoredEnergy);
			}
		},
		SUPER_ENERGY() {
			@Override
			public void extra(Player player) {
				int restoredEnergy = player.getRunEnergy() + 40;
				player.setRunEnergy(restoredEnergy > 100 ? 100 : restoredEnergy);
			}
		},
		ANTI_FIRE() {
			@Override
			public void extra(final Player player) {
				player.addFireImmune(360000);
				final long current = player.getFireImmune();
				player.getPackets().sendGameMessage( "You are now immune to dragonfire.");
				WorldTasksManager.schedule(new WorldTask() {
					boolean stop = false;
					@Override
					public void run() {
						if (current != player.getFireImmune()) {
							stop();
							return;
						}
						if (!stop) {
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion is about to run out...</col>");
							stop = true;
						} else {
							stop();
							player.getPackets().sendGameMessage("<col=480000>Your antifire potion has ran out...</col>");
						}		
					}
				}, 500, 100);
			}
		},
		STRENGTH_POTION(Skills.STRENGTH) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		DEFENCE_POTION(Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.1));
			}
		},
		DEFENCE_POTION1(Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		DEFENCE_POTION2(Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		DEFENCE_POTION3(Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		RANGE_POTION(Skills.RANGE) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.1));
			}
		},
		RANGE_POTION1(Skills.RANGE) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		RANGE_POTION2(Skills.RANGE) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		RANGE_POTION3(Skills.RANGE) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		MAGIC_POTION(Skills.MAGIC) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return level + 5;
			}
		},
		MAGIC_POTION1(Skills.MAGIC) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2 + (realLevel * 0.07));
			}
		},
		MAGIC_POTION2(Skills.MAGIC) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 2.5 + (realLevel * 0.11));
			}
		},
		MAGIC_POTION3(Skills.MAGIC) {
			@Override
			public int getAffectedSkill(Player player, int skillId, int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 3 + (realLevel * 0.15));
			}
		},
		PRAYER_POTION() {
			@Override
			public void extra(Player player) {
				player.getPrayer().restorePrayer((int) ((int) (Math.floor(player.getSkills().getLevelForXp(Skills.PRAYER) * 2.5) + 70) 
						* player.getAuraManager().getPrayerPotsRestoreMultiplier()));
			}
		},
		REJUVE_POTION1(Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int restore = (int) (Math.floor(player.getSkills().getLevelForXp(Skills.SUMMONING) * 0.25) + 4);
				if(actualLevel + restore > realLevel)
					return realLevel;
				return actualLevel + restore;
			}
			@Override
			public void extra(Player player) {
				Familiar familiar = player.getFamiliar();
				player.getPrayer().restorePrayer((int) ((int) (Math.floor(player.getSkills().getLevelForXp(Skills.PRAYER) * 2.5) + 30) 
						* player.getAuraManager().getPrayerPotsRestoreMultiplier()));
				if(familiar != null)
					familiar.restoreSpecialAttack(15);
			}
		},
		REJUVE_POTION2(Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int restore = (int) (Math.floor(player.getSkills().getLevelForXp(Skills.SUMMONING) * 0.25) + 7);
				if(actualLevel + restore > realLevel)
					return realLevel;
				return actualLevel + restore;
			}
			@Override
			public void extra(Player player) {
				Familiar familiar = player.getFamiliar();
				player.getPrayer().restorePrayer((int) ((int) (Math.floor(player.getSkills().getLevelForXp(Skills.PRAYER) * 2.5) + 70) 
						* player.getAuraManager().getPrayerPotsRestoreMultiplier()));
				if(familiar != null)
					familiar.restoreSpecialAttack(15);
			}
		},
		REJUVE_POTION3(Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int restore = (int) (Math.floor(player.getSkills().getLevelForXp(Skills.SUMMONING) * 0.25) + 14);
				if(actualLevel + restore > realLevel)
					return realLevel;
				return actualLevel + restore;
			}
			@Override
			public void extra(Player player) {
				Familiar familiar = player.getFamiliar();
				player.getPrayer().restorePrayer((int) ((int) (Math.floor(player.getSkills().getLevelForXp(Skills.PRAYER) * 2.5) + 140) 
						* player.getAuraManager().getPrayerPotsRestoreMultiplier()));
				if(familiar != null)
					familiar.restoreSpecialAttack(15);
			}
		},
		SUPER_STR_POTION(Skills.STRENGTH) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},
		SUPER_DEF_POTION(Skills.DEFENCE) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},
		SUPER_ATT_POTION(Skills.ATTACK) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.15));
			}
		},
		EXTREME_STR_POTION(Skills.STRENGTH) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler
						|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.22));
			}
		},
		EXTREME_DEF_POTION(Skills.DEFENCE) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler || FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.22));
			}
		},
		EXTREME_ATT_POTION(Skills.ATTACK) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler || FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 5 + (realLevel * 0.22));
			}
		},
		EXTREME_RAN_POTION(Skills.RANGE) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler || FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return (int) (level + 4 + (Math.floor(realLevel / 5.2)));
			}
		},
		EXTREME_MAG_POTION(Skills.MAGIC) {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int level = actualLevel > realLevel ? realLevel : actualLevel;
				return level + 7;
			}
		},
		RECOVER_SPECIAL() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				Long time = (Long) player.getAttributes().get("Recover_Special_Pot");
				if (time != null && Utils.currentTimeMillis() - time < 30000) {
					player.getPackets().sendGameMessage("You may only use this pot every 30 seconds.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(Player player) {
				player.getAttributes().put("Recover_Special_Pot", Utils.currentTimeMillis());
				player.getCombatDefinitions().restoreSpecialAttack(25);
			}
		},
		SARADOMIN_BREW("You drink some of the foul liquid.", Skills.ATTACK, Skills.DEFENCE, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE) {

			@Override
			public boolean canDrink(Player player) {
				return true;
			}

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				if (skillId == Skills.DEFENCE) {
					int boost = (int) (realLevel * 0.25);
					int level = actualLevel > realLevel ? realLevel
							: actualLevel;
					return level + boost;
				} else {
					return (int) (actualLevel * 0.90);
				}
			}

			@Override
			public void extra(Player player) {
				int hitpointsModification = (int) (player.getMaxHitpoints() * 0.15);
				player.heal(hitpointsModification + 20, hitpointsModification);
			}
		},

		OVERLOAD() {

			@Override
			public boolean canDrink(Player player) {
				if (player.getControlerManager().getControler() instanceof Wilderness
						|| player.getControlerManager().getControler() instanceof CrucibleControler|| FfaZone.isOverloadChanged(player)) {
					player.getPackets().sendGameMessage(
							"You cannot drink this potion here.");
					return false;
				}
				/*		if (player.getOverloadDelay() > 0) {
					player.getPackets().sendGameMessage(
							"You may only use this potion every five minutes.");
					return false;
				}*/
				if (player.getHitpoints() <= 500 || player.getOverloadDelay() > 480) {
					player.getPackets().sendGameMessage(
							"You need more than 500 life points to survive the power of overload.");
					return false;
				}
				return true;
			}

			@Override
			public void extra(final Player player) {
				player.setOverloadDelay(501);
				WorldTasksManager.schedule(new WorldTask() {
					int count = 4;

					@Override
					public void run() {
						if (count == 0)
							stop();
						player.setNextAnimation(new Animation(3170));
						player.setNextGraphics(new Graphics(560));
						player.applyHit(new Hit(player, 100,
								HitLook.REGULAR_DAMAGE, 0));
						count--;
					}
				}, 0, 2);
			}
		},
		SUPER_PRAYER() {
			@Override
			public void extra(Player player) {
				player.getPrayer().restorePrayer((int) ((int) (Math.floor(player.getSkills().getLevelForXp(Skills.PRAYER) * 3.5) + 154) 
						* player.getAuraManager().getPrayerPotsRestoreMultiplier()));
			}
		},
		PRAYER_RENEWAL() {
			@Override
			public void extra(Player player) {
				player.setPrayerRenewalDelay(501);
			}
		},
		SUPER_RESTORE(Skills.ATTACK, Skills.STRENGTH, Skills.DEFENCE,
				Skills.MAGIC, Skills.RANGE, Skills.AGILITY, Skills.COOKING,
				Skills.CRAFTING, Skills.FARMING, Skills.FIREMAKING,
				Skills.FISHING, Skills.FLETCHING, Skills.HERBLORE,
				Skills.MINING, Skills.RUNECRAFTING, Skills.SLAYER,
				Skills.SMITHING, Skills.THIEVING, Skills.WOODCUTTING,
				Skills.SUMMONING) {
			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}

			@Override
			public void extra(Player player) {
				player.getPrayer().restorePrayer(
						(int) ((int) (player.getSkills().getLevelForXp(
								Skills.PRAYER) * 0.33 * 10) * player
								.getAuraManager()
								.getPrayerPotsRestoreMultiplier()));
			}

		},
		RESTORE_POTION1(Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE, Skills.PRAYER) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.20);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}
		},
		RESTORE_POTION2(Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE, Skills.PRAYER) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}
		},
		RESTORE_POTION3(Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE, Skills.PRAYER) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.48);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}
		},
		RESTORE_POTION(Skills.ATTACK, Skills.STRENGTH, Skills.MAGIC, Skills.RANGE, Skills.PRAYER) {

			@Override
			public int getAffectedSkill(Player player, int skillId,
					int actualLevel, int realLevel) {
				int boost = (int) (realLevel * 0.33);
				if (actualLevel > realLevel)
					return actualLevel;
				if (actualLevel + boost > realLevel)
					return realLevel;
				return actualLevel + boost;
			}
		};

		private int[] affectedSkills;
		private String drinkMessage;

		private Effects(int... affectedSkills) {
			this(null, affectedSkills);
		}

		private Effects(String drinkMessage, int... affectedSkills) {
			this.drinkMessage = drinkMessage;
			this.affectedSkills = affectedSkills;
		}

		public int getAffectedSkill(Player player, int skillId,
				int actualLevel, int realLevel) {
			return actualLevel;
		}

		public boolean canDrink(Player player) {
			// usualy unused
			return true;
		}

		public void extra(Player player) {
			// usualy unused
		}
	}

	public static Pot getPot(int id) {
		for (Pot pot : Pot.values())
			for (int potionId : pot.id) {
				if (id == potionId)
					return pot;
			}
		return null;
	}

	public static boolean pot(Player player, final int itemId, Item item, int slot) {
		Pot pot = getPot(item.getId());
		if (pot == null)
			return false;
		if (player.getPotDelay() > Utils.currentTimeMillis())
			return true;
		if (!player.getControlerManager().canPot(pot))
			return true;
		if (!pot.effect.canDrink(player))
			return true;
		player.addPotDelay(1075);
		String name = item.getDefinitions().getName();
		int index = name.indexOf("(");
		int dosesLeft = 0;
		if (index != -1)
			dosesLeft = Integer.parseInt(name.substring(index).replace("(", "")
					.replace(")", "")) - 1;
		int toPot = pot.id.length - dosesLeft;
		if (name.contains("flask") && dosesLeft <= 0 && toPot >= pot.id.length) {
			player.getInventory().deleteItem(slot, new Item(pot.id[5]));
		}
		if (itemId >= 17556 && itemId <= 17628) {
			player.getInventory().getItems().set(slot, new Item(dosesLeft > 0 && toPot < pot.id.length ? pot.id[toPot] : 20878, 1));
		} else
			player.getInventory().getItems().set(slot, new Item(dosesLeft > 0 && toPot < pot.id.length ? pot.id[toPot] : 229, 1));
		player.getInventory().refresh(slot);
		for (int skillId : pot.effect.affectedSkills)
			player.getSkills().set(
					skillId,
					pot.effect.getAffectedSkill(player, skillId, player
							.getSkills().getLevel(skillId), player.getSkills()
							.getLevelForXp(skillId)));
		pot.effect.extra(player);
		player.setNextAnimation(new Animation(829));
		player.getPackets().sendSound(4580, 0, 1);
		player.getPackets().sendGameMessage(
				pot.effect.drinkMessage != null ? pot.effect.drinkMessage
						: "You drink some of your "
						+ name.toLowerCase().replace(" (1)", "")
						.replace(" (2)", "")
						.replace(" (3)", "")
						.replace(" (4)", "")
						.replace(" (5)", "")
						.replace(" (6)", "")+ ".", true);

		player.getPackets().sendGameMessage(
				dosesLeft == 0 ? "You have finished your " + (name.contains("flask") ? "flask and the glass shatters to peices." : "potion.") : "You have "
						+ dosesLeft + " dose of potion left.", true);
		return true;
	}

	public static boolean mixPotion(Player player, int usedSlot, int withSlot) {
		/*Item itemUsed = player.getInventory().getItem(usedSlot);
		Item usedWith = player.getInventory().getItem(withSlot);
		Pot first = getPot(itemUsed.getId());
		Pot second = getPot(usedWith.getId());
		String name = usedWith.getDefinitions().getName();
		if (first == null || second == null)
			return false;
		if (first.effect != second.effect)
			return false;
		else if (name.contains("4")) {
			player.getPackets().sendGameMessage("That potion is full.");
			return false;
		} else if (itemUsed.getId() == 229)// empty vial
			return false;*/
		return true;
	}

	public static void resetOverLoadEffect(Player player) {
		if (!player.isDead()) {
			int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
			int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.ATTACK, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
			realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.STRENGTH, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
			realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.DEFENCE, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.MAGIC);
			realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.MAGIC, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.RANGE);
			realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.RANGE, realLevel);
			player.heal(500);
		}
		player.setOverloadDelay(0);
		player.getPackets().sendGameMessage(
				"<col=480000>The effects of overload have worn off and you feel normal again.");
	}

	public static void applyOverLoadEffect(Player player) {
		if (player.getControlerManager().getControler() instanceof Wilderness
				|| player.getControlerManager().getControler() instanceof CrucibleControler
				|| FfaZone.isOverloadChanged(player)) {
			int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
			int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
			int level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.ATTACK,
					(int) (level + 5 + (realLevel * 0.15)));

			actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
			realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.STRENGTH,
					(int) (level + 5 + (realLevel * 0.15)));

			actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
			realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.DEFENCE,
					(int) (level + 5 + (realLevel * 0.15)));

			actualLevel = player.getSkills().getLevel(Skills.MAGIC);
			realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.MAGIC, level + 5);

			actualLevel = player.getSkills().getLevel(Skills.RANGE);
			realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.RANGE,
					(int) (level + 5 + (realLevel * 0.1)));
		} else {
			int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
			int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
			int level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.ATTACK,
					(int) (level + 5 + (realLevel * 0.22)));

			actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
			realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.STRENGTH,
					(int) (level + 5 + (realLevel * 0.22)));

			actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
			realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.DEFENCE,
					(int) (level + 5 + (realLevel * 0.22)));

			actualLevel = player.getSkills().getLevel(Skills.MAGIC);
			realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.MAGIC, level + 7);

			actualLevel = player.getSkills().getLevel(Skills.RANGE);
			realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
			level = actualLevel > realLevel ? realLevel : actualLevel;
			player.getSkills().set(Skills.RANGE,
					(int) (level + 4 + (Math.floor(realLevel / 5.2))));
		}
	}

	public static void resetBoosts(Player player) {
		if (!player.isDead()) {
			int actualLevel = player.getSkills().getLevel(Skills.ATTACK);
			int realLevel = player.getSkills().getLevelForXp(Skills.ATTACK);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.ATTACK, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.STRENGTH);
			realLevel = player.getSkills().getLevelForXp(Skills.STRENGTH);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.STRENGTH, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.DEFENCE);
			realLevel = player.getSkills().getLevelForXp(Skills.DEFENCE);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.DEFENCE, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.MAGIC);
			realLevel = player.getSkills().getLevelForXp(Skills.MAGIC);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.MAGIC, realLevel);
			actualLevel = player.getSkills().getLevel(Skills.RANGE);
			realLevel = player.getSkills().getLevelForXp(Skills.RANGE);
			if (actualLevel > realLevel)
				player.getSkills().set(Skills.RANGE, realLevel);
		}
		player.setOverloadDelay(0);
	}
	
	private Pots() {

	}
}