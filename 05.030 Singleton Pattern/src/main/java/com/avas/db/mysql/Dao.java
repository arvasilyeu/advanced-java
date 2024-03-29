package com.avas.db.mysql;

import java.util.List;
import java.util.Optional;

public interface Dao <T> {
	
	void save(T t);
	Optional<T> findById(int id);
	void update(T t);
	void delete(int id);
	List<T> getAll();
}
