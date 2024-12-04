package si.model;

import java.awt.*;

public interface Hittable {
	boolean isAlive();
	int getPoints();
	boolean isPlayer();
	boolean isEnemyShip();
	boolean isHit(Bullet b);
	boolean isHitPlayer(Player player);
	Rectangle getHitBox();
	AlienType getAlienType();
}

