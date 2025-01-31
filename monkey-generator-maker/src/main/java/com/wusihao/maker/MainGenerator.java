package com.wusihao.maker;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.wusihao.maker.generator.file.DynamicFileGenerator;
import com.wusihao.maker.meta.Meta;
import com.wusihao.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);

        // 输出根路径
        String projectPath = System.getProperty("user.dir") + File.separator + "monkey-generator-maker";
        // monkey-generator-maker/generated/acm-template-pro-generator
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }

        // 读取 resources 目录
        String inputResourcePath = projectPath + File.separator + "src"+ File.separator + "main"+ File.separator + "resources";

        // Java 包基础路径 com.wusihao
        String outputBasePackage = meta.getBasePackage();
        // com/wusihao
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        // generated/acm-template-pro-generator/src/main/java/com/wusihao/xxx
        String outputBaseJavaPackagePath = outputPath + File.separator + "src"+ File.separator + "main"+ File.separator + "java" + File.separator + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;

        // model.DataModel
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "model"+ File.separator + "DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + ""+ File.separator + "model"+ File.separator + "DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);
    }
}
