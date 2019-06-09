package com.rs.utils.spawning;

import com.rs.game.World;
import com.rs.game.WorldTile;

public enum RemoveObjects {
	
	GATE_01(2585, 3532, 0, true),
	GATE_02(2586, 3532, 0, true),
	GATE_03(2580, 3532, 0, true),
	GATE_04(2579, 3532, 0, true),
	
	GATE_05(2576, 3526, 0, true),
	GATE_06(2576, 3525, 0, true),
	GATE_07(2589, 3526, 0, true),
	GATE_08(2589, 3525, 0, true);
    
    private int coordX;
    private int coordY;
    private int plane;
    private boolean clipped;

    RemoveObjects(int x, int y, int plane, boolean clipped) {
        this.coordX = x;
        this.coordY = y;
        this.plane = plane;
        this.clipped = clipped;
    }

    public static void removeObjects() {
        for (RemoveObjects obj : RemoveObjects.values()) {
        	WorldTile tile = new WorldTile(obj.coordX, obj.coordY, obj.plane);
        	World.getRegion(tile.getRegionId()).forceGetRegionMap().removeObject(tile.getPlane(), tile.getX(), tile.getY(), 2, 2, obj.clipped, true);
        }
    }
    
}
