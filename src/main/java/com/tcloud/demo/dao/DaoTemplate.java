package com.tcloud.demo.dao;

import java.util.List;

public interface DaoTemplate<T> {
	public List<T> getAll();
	public T create(T t);
	public T update(T t);
	public void delete(Integer id);
}
