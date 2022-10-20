package com.tianhua.datafactory.domain.support.kvpair;

import java.util.Objects;

/**
 * Description:具有父子级关系的KV对象,继承 KVGroupPair 对象,这里是把组看成对象的节点深度
 *
 *
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class KVParentPair <K, V, G, P> extends KVGroupPair<K, V, G> {
    private P p;
    public KVParentPair(){}

    public KVParentPair(K k,V v,G g,P p){
        super(k,v,g);
        this.p = p;
    }

    public P getP() {
        return p;
    }

    public void setP(P p) {
        this.p = p;
    }

    public String getPstr(){
        return  p.toString();
    }

    public static <K, V, G, P> KVParentPair<K, V, G, P> build(K k, V v, G g, P p) {
        return new KVParentPair<>(k, v, g, p);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        KVParentPair<?, ?, ?, ?> that = (KVParentPair<?, ?, ?, ?>) o;
        return Objects.equals(p, that.p);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), p);
    }

    @Override
    public String toString() {
        return "KVParentPair{" +
                "k=" + k +
                ", v=" + v +
                ", p=" + p +
                '}';
    }
}
