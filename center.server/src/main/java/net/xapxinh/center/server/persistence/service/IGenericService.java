package net.xapxinh.center.server.persistence.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import net.xapxinh.center.server.persistence.IPersistence;

@Transactional
public interface IGenericService<T extends Serializable> extends IPersistence<T> {

}
