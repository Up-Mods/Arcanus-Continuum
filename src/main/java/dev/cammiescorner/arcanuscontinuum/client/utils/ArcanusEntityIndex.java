package dev.cammiescorner.arcanuscontinuum.client.utils;

import dev.cammiescorner.arcanuscontinuum.common.entities.magic.AreaOfEffectEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.ManaShieldEntity;
import dev.cammiescorner.arcanuscontinuum.common.entities.magic.SmiteEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.function.AbortableIterationConsumer;
import net.minecraft.world.entity.EntityIndex;
import net.minecraft.world.entity.EntityLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

public class ArcanusEntityIndex<T extends EntityLike> extends EntityIndex<T> {
	private final EntityIndex<T> delegate;

	public ArcanusEntityIndex(EntityIndex<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public <U extends T> void forEach(TypeFilter<T, U> filter, AbortableIterationConsumer<U> consumer) {
		delegate.forEach(filter, consumer);
	}

	@Override
	public Iterable<T> iterate() {
		List<Class<? extends Entity>> renderLast = List.of(ManaShieldEntity.class, SmiteEntity.class, AreaOfEffectEntity.class);
		return StreamSupport.stream(delegate.iterate().spliterator(), false).sorted((o1, o2) -> renderLast.contains(o1.getClass()) ? renderLast.contains(o2.getClass()) ? 0 : 1 : -1).toList();
	}

	@Override
	public void add(T entity) {
		delegate.add(entity);
	}

	@Override
	public void remove(T entity) {
		delegate.remove(entity);
	}

	@Nullable
	@Override
	public T get(int id) {
		return delegate.get(id);
	}

	@Nullable
	@Override
	public T get(UUID uuid) {
		return delegate.get(uuid);
	}

	@Override
	public int size() {
		return delegate.size();
	}
}
