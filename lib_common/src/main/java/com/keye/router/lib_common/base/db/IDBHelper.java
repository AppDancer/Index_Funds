package com.keye.router.lib_common.base.db;

/**
 * Created by Administrator on 2019-07-18.
 */

public interface IDBHelper<T, K> {

    void insert(T entiry);

    void insertInTx(T... entities);

    void save(T entiry);

    void saveInTx(T... entities);

    void update(T entiry);

    void updateInTx(T... entities);

    void delete(T entiry);

    void deleteByKey(K key);

    void deleteAll();

    void deleteInTx(T... entities);

    void deleteByKeyInTx(T... keys);

}
