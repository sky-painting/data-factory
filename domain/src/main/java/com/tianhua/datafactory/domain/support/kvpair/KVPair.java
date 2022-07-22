package com.tianhua.datafactory.domain.support.kvpair;

import java.util.Objects;

/**
 * Description:抽象KV对象
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public  class KVPair<K,V> {
    public KVPair(){

    }
    public KVPair(K k, V v){
        this.k = k;
        this.v = v;
    }
    protected K k;
    protected V v;

    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    //提供构建k-v实例的静态方法
    public static <K, V> KVPair<K, V> build(K k, V v) {
        return new KVPair<>(k, v);
    }

    //提供判断k-v对象是否相同的能力
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KVPair<?, ?> kvPair = (KVPair<?, ?>) o;
        return Objects.equals(k, kvPair.k) &&
                Objects.equals(v, kvPair.v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(k, v);
    }


}
