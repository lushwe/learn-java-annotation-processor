package com.lushwe.processor;

import com.lushwe.annotation.Hello;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("com.lushwe.annotation.Hello")
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
public class HelloAnnotationProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Types typeUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {

        super.init(processingEnvironment);

        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (set.isEmpty()) {
            return false;
        }

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Hello.class);
        for (Element element : elements) {
            parseElement(element);
        }
        return true;

    }

    private void parseElement(Element element) {

        String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
        String className = element.getSimpleName().toString();

        messager.printMessage(Diagnostic.Kind.NOTE, "=HelloProcessor=" + packageName + "/" + className);

        FieldSpec id = FieldSpec.builder(Long.class, "id")
                .addModifiers(Modifier.PRIVATE)
                .addJavadoc("ID")
                .build();
        FieldSpec name = FieldSpec.builder(String.class, "name")
                .addModifiers(Modifier.PRIVATE)
                .addJavadoc("名称")
                .build();

        TypeSpec typeSpec = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Getter.class)
                .addAnnotation(Setter.class)
                .addField(id)
                .addField(name)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();

        // 写入
        try {
            javaFile.writeTo(filer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
