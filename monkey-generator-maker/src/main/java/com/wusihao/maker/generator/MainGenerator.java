package com.wusihao.maker.generator;

import com.wusihao.maker.meta.Meta;
import com.wusihao.maker.meta.MetaManager;

public class MainGenerator {

    public static void main(String[] args) {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);
    }
}
