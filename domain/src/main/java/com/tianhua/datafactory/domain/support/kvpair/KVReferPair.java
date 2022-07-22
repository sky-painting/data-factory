package com.tianhua.datafactory.domain.support.kvpair;

/**
 * Description:带有关联对象关联关系的kv
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 */
public class KVReferPair<K,V,G> extends KVGroupPair<K,V,G> {

    public KVReferPair(){

    }

    public KVReferPair(K k, V v , G g){
        super(k,v,g);
    }

    public KVReferPair(K k, V v , G g, String referObj, String referIdentifer){
        super(k,v,g);
        this.referObj = referObj;
        this.referIdentifer = referIdentifer;
    }


    /**
     * 关联了哪个对象
     */
    public String referObj;

    /**
     * 关联对象的标示
     */
    public String referIdentifer;

    //提供构建k-v实例的静态方法
    public static <K, V, G> KVReferPair<K, V, G> build(K k, V v, G g, String referObj, String referIdentifer) {
        return new KVReferPair<>(k, v, g, referObj, referIdentifer);
    }


}
