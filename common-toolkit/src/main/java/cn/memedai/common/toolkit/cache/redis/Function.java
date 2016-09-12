package cn.memedai.common.toolkit.cache.redis;

public interface Function<T, E> {
	public T callback(E e);
}
