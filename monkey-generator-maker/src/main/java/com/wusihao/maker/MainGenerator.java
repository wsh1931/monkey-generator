package com.wusihao.maker;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.wusihao.maker.generator.JarGenerator;
import com.wusihao.maker.generator.ScriptGenerator;
import com.wusihao.maker.generator.file.DynamicFileGenerator;
import com.wusihao.maker.meta.Meta;
import com.wusihao.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);

        // 输出根路径
        String projectPath = System.getProperty("user.dir") + File.separator + "monkey-generator-maker";
        // monkey-generator-maker/generated/acm-template-pro-generator
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
//        String outputPath = projectPath + File.separator + "generated";
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
        outputFilePath = outputBaseJavaPackagePath + File.separator + "model"+ File.separator + "DataModel.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // cli.command.ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "cli"+ File.separator + "command" + File.separator + "ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli" + File.separator + "command"+ File.separator + "ConfigCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // cli.command.GenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "cli"+ File.separator + "command" + File.separator + "GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli" + File.separator + "command"+ File.separator + "GenerateCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // cli.command.ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "cli"+ File.separator + "command" + File.separator + "ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli" + File.separator + "command"+ File.separator + "ListCommand.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // cli.CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "cli" + File.separator + "CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "cli" + File.separator + "CommandExecutor.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // Main
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java" + File.separator + "Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "Main.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // generator.DynamicGenerator
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "generator" + File.separator + "DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "generator" + File.separator + "DynamicGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // generator.MainGenerator
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "generator" + File.separator + "MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "generator" + File.separator + "MainGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // generator.StaticGenerator
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator + "java"+ File.separator + "generator" + File.separator + "StaticGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + File.separator + "generator" + File.separator + "StaticGenerator.java";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);


        // pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates"+ File.separator +"pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerate(inputFilePath , outputFilePath, meta);

        // 构建 jar 包
        JarGenerator.doGenerate(outputPath);

        // 封装脚本
        String shellOutputFilePath = outputPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target" + File.separator + jarName;
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);
    }
}
