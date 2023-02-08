package dev.cammiescorner.arcanuscontinuum.client.utils;

import dev.cammiescorner.arcanuscontinuum.common.entities.magic.ManaShieldEntity;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.function.AbortableIterationConsumer;
import net.minecraft.world.entity.EntityIndex;
import net.minecraft.world.entity.EntityLike;
import org.jetbrains.annotations.Nullable;

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
		return StreamSupport.stream(delegate.iterate().spliterator(), false).sorted((o1, o2) -> o1 instanceof ManaShieldEntity ? o2 instanceof ManaShieldEntity ? 0 : 1 : -1).toList();
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
