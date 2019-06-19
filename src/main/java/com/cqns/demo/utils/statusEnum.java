package com.cqns.demo.utils;

public enum statusEnum {

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
