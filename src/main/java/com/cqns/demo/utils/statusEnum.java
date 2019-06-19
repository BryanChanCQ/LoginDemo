package com.cqns.demo.utils;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public enum statusEnum {
    //保存状态
    save("save","已保存"),

    submit("submit","已提交"),

    done("done","已完成"),

    close("close","已关闭"),

    handle("handle","处理中");

    private final String key;

    private final String value;

    statusEnum(String key,String value){

        this.key = key;

        this.value = value;
    }

    public static statusEnum getEnumByKey(String key){

        if(null == key){

            return null;

        }

        for(statusEnum temp:statusEnum.values()){

            if(temp.getKey().equals(key)){

                return temp;

            }

        }

        return null;
    }
    public String getKey() {

        return key;

    }

    public String getValue() {

        return value;

    }
}
