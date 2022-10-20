package com.tianhua.datafactory.domain.support.kvpair;

import java.util.Objects;

/**
 * Description:带有分组特性的kv对象
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class KVGroupPair <K,V,G> extends KVPair<K,V> {
    private G g;
    public KVGroupPair(){}

    public KVGroupPair(K k,V v ,G g){
        super(k,v);
        this.g = g;
    }

    public G getG() {
        return g;
    }

    public  String getGStr() {
        return g.toString();
    }

    public void setG(G p) {
        this.g = p;
    }

    public static <K, V, G> KVGroupPair<K, V, G> KVGroupPair(K k, V v,G g) {
        return new KVGroupPair<>(k, v, g);
    }

    //提供构建k-v实例的静态方法
    public static <K, V, G> KVGroupPair<K, V, G> build(K k, V v,G g) {
        return new KVGroupPair<>(k, v, g);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KVGroupPair<?, ?, ?> kvGroupPair = (KVGroupPair<?, ?, ?>) o;
        return Objects.equals(kvGroupPair.k, super.k) &&
                Objects.equals(kvGroupPair.v, super.v) && Objects.equals(kvGroupPair.g,this.g);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.k, super.v,g);
    }

    @Override
    public String toString() {
        return "KVGroupPair{" +
                "g=" + g +
                ", k=" + k +
                ", v=" + v +
                '}';
    }
}
