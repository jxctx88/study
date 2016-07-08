package cn.memedai.common.toolkit.redis.service;

public interface Function<T, E> {
	public T callback(E e);
}
