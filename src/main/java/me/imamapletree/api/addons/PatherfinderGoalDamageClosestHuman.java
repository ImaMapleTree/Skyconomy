package me.imamapletree.api.addons;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.event.entity.EntityTargetEvent;
import net.minecraft.server.v1_16_R2.DamageSource;
import net.minecraft.server.v1_16_R2.EntityHuman;
import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.NavigationAbstract;
import net.minecraft.server.v1_16_R2.PathEntity;
import net.minecraft.server.v1_16_R2.PathfinderGoal;
import net.minecraft.server.v1_16_R2.PathfinderTargetCondition;

public class PatherfinderGoalDamageClosestHuman extends PathfinderGoal{

	private final EntityInsentient entity;
	private final Double speed;
	private final Float damage;
	private final NavigationAbstract navigation;
	private final Location loc;
	private EntityHuman target = null;
	
	public PatherfinderGoalDamageClosestHuman(EntityInsentient entity, Double speed, Float damage) {
		this.entity = entity;
		this.speed = speed;
		this.damage = damage;
		this.navigation = this.entity.getNavigation();
		this.loc = this.entity.getBukkitEntity().getLocation();
	}
	
	@Override
	public boolean a() {
		final List<EntityHuman> list = this.entity.world.a(PathfinderTargetCondition.a, this.entity, this.entity.getBoundingBox().grow(-1, 1, 1));
		if(list.isEmpty()) return false;
		else {
			this.target = list.get(0);
			return true;
		}
	}
	
	public void c() {
		PathEntity pE = this.navigation.a(this.loc.getX(), this.loc.getY(), this.loc.getZ(), 10);
		this.navigation.a(pE, this.speed);
		this.entity.setGoalTarget(this.target, EntityTargetEvent.TargetReason.CLOSEST_PLAYER, true);
		this.target.damageEntity(DamageSource.GENERIC, this.damage);
	}

}
