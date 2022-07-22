package com.tianhua.datafactory.vo.datasource;

import com.alibaba.fastjson.annotation.JSONField;
import com.tianhua.datafactory.domain.support.kvpair.KVPair;
import com.tianhua.datafactory.domain.support.kvpair.KVTypeEnum;
import lombok.Data;

/**
 * Description: kv业务实体
 *
 *
 * date: 2022/1/11
 *
 * @author fanchunshuai
 * @version 1.0.0
 * @since JDK 1.8
 *
 * 建造者模式
 */
@Data
public class KVPairVO {
    private String key;
    private String value;
    private String groupKey;
    private String parentKey;

    /**
     * 关联了哪个对象
     */
    private String referObj;

    /**
     * 关联对象的标示
     */
    private String referIdentifer;

    /**
     * value值类型
     */
    private Integer valueType;

    /**
     * key值类型
     */
    private Integer keyType;

    /**
     * json格式的对象存储
     */
    private Object valueObject;

    /**
     * kv状态，跟着主对象走
     */
    private Integer status;



    public KVPairVO(){}

    public KVPairVO(String key, String value){
        this.key = key;
        this.value = value;
    }

    public KVPairVO(String key, String value, Integer valueType){
        KVPairVO kvPairBO = new KVPairVO(key,value);
        kvPairBO.valueType(valueType);
    }

    public static KVPairVO instance(String key, String value){
        return new KVPairVO(key,value);
    }

    public static KVPairVO instance(){
        return new KVPairVO();
    }

    public String getKey() {
        return key;
    }

    public KVPairVO Key(String key) {
        this.key = key;
        return this;
    }

    public Integer getStatus() {
        return status;
    }


    public KVPairVO status(Integer status){
        this.status = status;
        return this;
    }


    public Object getValueObject() {
        return valueObject;
    }

    public String getValue() {
        return value;
    }

    public KVPairVO value(String value) {
        this.value = value;
        return this;
    }

    public KVPairVO valueObject(Object valueObject){
        this.valueObject = valueObject;
        return this;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public KVPairVO groupKey(String groupKey) {
        this.groupKey = groupKey;
        return this;
    }

    public String getParentKey() {
        return parentKey;
    }

    public KVPairVO parentKey(String parentKey) {
        this.parentKey = parentKey;
        return this;
    }

    public String getReferObj() {
        return referObj;
    }

    public KVPairVO referObj(String referObj) {
        this.referObj = referObj;
        return this;
    }

    public String getReferIdentifer() {
        return referIdentifer;
    }

    public KVPairVO referIdentifer(String referIdentifer) {
        this.referIdentifer = referIdentifer;
        return this;
    }

    public Integer getValueType() {
        return valueType;
    }

    public Integer getKeyType() {
        return keyType;
    }


    public KVPairVO valueType(Integer valueType) {
        this.valueType = valueType;
        return this;
    }

    public KVPairVO keyType(Integer keyType) {
        this.keyType = keyType;
        return this;
    }



    private KVPair convert2KVPair(){
        if(KVTypeEnum.isInteger(this.getKeyType()) && KVTypeEnum.isInteger(this.getValueType())){
            return KVPair.build(getIntegerKey(),getIntegerValue());
        }

        if(KVTypeEnum.isInteger(this.getKeyType()) && KVTypeEnum.isLong(this.getValueType())){
            return KVPair.build(getIntegerKey(),getLongValue());
        }

        if(KVTypeEnum.isInteger(this.getKeyType()) && KVTypeEnum.isString(this.getValueType())){
            return KVPair.build(getIntegerKey(),getStringValue());
        }

        if(KVTypeEnum.isInteger(this.getKeyType()) && KVTypeEnum.isJSONObject(this.getValueType())){
            return KVPair.build(getIntegerKey(),getObjectValue());
        }


        if(KVTypeEnum.isLong(this.getKeyType()) && KVTypeEnum.isInteger(this.getValueType())){
            return KVPair.build(getLongKey(),getIntegerValue());
        }

        if(KVTypeEnum.isLong(this.getKeyType()) && KVTypeEnum.isLong(this.getValueType())){
            return KVPair.build(getLongKey(),getLongValue());
        }

        if(KVTypeEnum.isLong(this.getKeyType()) && KVTypeEnum.isString(this.getValueType())){
            return KVPair.build(getLongKey(),getStringValue());
        }

        if(KVTypeEnum.isLong(this.getKeyType()) && KVTypeEnum.isJSONObject(this.getValueType())){
            return KVPair.build(getLongKey(),getObjectValue());
        }

        return KVPair.build(getStringKey(),getStringValue());

    }




    @JSONField(serialize = false)
    public Integer getIntegerKey(){
        return Integer.parseInt(this.key);
    }

    @JSONField(serialize = false)
    public Integer getIntegerValue(){
        return Integer.parseInt(this.value);
    }

    @JSONField(serialize = false)
    public Long getLongKey(){
        return Long.parseLong(this.key);
    }

    @JSONField(serialize = false)
    public Long getLongValue(){
        return Long.parseLong(this.value);
    }

    @JSONField(serialize = false)
    public String getStringKey(){
        return this.key;
    }

    @JSONField(serialize = false)
    public String getStringValue(){
        return this.value;
    }

    @JSONField(serialize = false)
    public Object getObjectValue(){
        return this.valueObject;
    }


    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public void setReferObj(String referObj) {
        this.referObj = referObj;
    }

    public void setReferIdentifer(String referIdentifer) {
        this.referIdentifer = referIdentifer;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public void setKeyType(Integer keyType) {
        this.keyType = keyType;
    }

    public void setValueObject(Object valueObject) {
        this.valueObject = valueObject;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
