package dev.cammiescorner.arcanuscontinuum.common.registry;

import dev.cammiescorner.arcanuscontinuum.Arcanus;
import dev.cammiescorner.arcanuscontinuum.api.spells.Pattern;
import dev.cammiescorner.arcanuscontinuum.common.components.*;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.LivingEntity;

import java.util.List;

public class ArcanusComponents implements EntityComponentInitializer {
	public static final ComponentKey<ManaComponent> MANA_COMPONENT = createComponent("mana", ManaComponent.class);
	public static final ComponentKey<BurnoutComponent> BURNOUT_COMPONENT = createComponent("burnout", BurnoutComponent.class);
	public static final ComponentKey<CastingComponent> CASTING_COMPONENT = createComponent("casting", CastingComponent.class);
	public static final ComponentKey<PatternComponent> PATTERN_COMPONENT = createComponent("casting_pattern", PatternComponent.class);
	public static final ComponentKey<LastCastTimeComponent> LAST_CAST_TIME_COMPONENT = createComponent("last_cast_time", LastCastTimeComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(LivingEntity.class, MANA_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(ManaComponent::new);
		registry.beginRegistration(LivingEntity.class, BURNOUT_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(BurnoutComponent::new);
		registry.beginRegistration(LivingEntity.class, CASTING_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(CastingComponent::new);
		registry.beginRegistration(LivingEntity.class, PATTERN_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(PatternComponent::new);
		registry.beginRegistration(LivingEntity.class, LAST_CAST_TIME_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LastCastTimeComponent::new);
	}

	private static <T extends Component> ComponentKey<T> createComponent(String name, Class<T> component) {
		return ComponentRegistry.getOrCreate(Arcanus.id(name), component);
	}

	// ----- Helper Methods ----- //

	public static double getMana(LivingEntity entity) {
		return MANA_COMPONENT.get(entity).getMana();
	}

	public static void setMana(LivingEntity entity, double amount) {
		MANA_COMPONENT.get(entity).setMana(amount);
	}

	public static double getMaxMana(LivingEntity entity) {
		return MANA_COMPONENT.get(entity).getMaxMana();
	}

	public static boolean addMana(LivingEntity entity, double amount, boolean simulate) {
		return MANA_COMPONENT.get(entity).addMana(amount, simulate);
	}

	public static boolean drainMana(LivingEntity entity, double amount, boolean simulate) {
		return MANA_COMPONENT.get(entity).drainMana(amount, simulate);
	}

	public static double getBurnout(LivingEntity entity) {
		return BURNOUT_COMPONENT.get(entity).getBurnout();
	}

	public static void setBurnout(LivingEntity entity, double amount) {
		BURNOUT_COMPONENT.get(entity).setBurnout(amount);
	}

	public static boolean addBurnout(LivingEntity entity, double amount, boolean simulate) {
		return BURNOUT_COMPONENT.get(entity).addBurnout(amount, simulate);
	}

	public static boolean drainBurnout(LivingEntity entity, double amount, boolean simulate) {
		return BURNOUT_COMPONENT.get(entity).drainBurnout(amount, simulate);
	}

	public static boolean isCasting(LivingEntity entity) {
		return CASTING_COMPONENT.get(entity).isCasting();
	}

	public static void setCasting(LivingEntity entity, boolean casting) {
		CASTING_COMPONENT.get(entity).setCasting(casting);
	}

	public static List<Pattern> getPattern(LivingEntity entity) {
		return PATTERN_COMPONENT.get(entity).getPattern();
	}

	public static void setPattern(LivingEntity entity, List<Pattern> pattern) {
		PATTERN_COMPONENT.get(entity).setPattern(pattern);
	}

	public static void clearPattern(LivingEntity entity) {
		PATTERN_COMPONENT.get(entity).clearPattern();
	}

	public static long getLastCastTime(LivingEntity entity) {
		return LAST_CAST_TIME_COMPONENT.get(entity).getLastCastTime();
	}

	public static void setLastCastTime(LivingEntity entity, long time) {
		LAST_CAST_TIME_COMPONENT.get(entity).setLastCastTime(time);
	}
}
