package com.chenlx.crouter_compiler;

import com.chenlx.crouter_annotation.ModuleAdd;
import com.chenlx.crouter_compiler.utils.Consts;
import com.chenlx.crouter_compiler.utils.Logger;
import com.google.auto.service.AutoService;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import static com.chenlx.crouter_compiler.utils.Consts.ACTIVITY;
import static com.chenlx.crouter_compiler.utils.Consts.FRAGMENT;
import static com.chenlx.crouter_compiler.utils.Consts.SERVICE;


@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.chenlx.crouter_annotation.ModuleAdd"})
public class ModuleAddProcessor extends AbstractProcessor {

    Logger logger;

    Elements elementUtils;

    Filer mFiler;


    private Map<String, String> rootMap = new TreeMap<>();  // Map of root metas, used for
    // generate class file in order.


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        logger = new Logger(processingEnv.getMessager());
        elementUtils = processingEnv.getElementUtils();


        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        if (CollectionUtils.isNotEmpty(annotations)) {

            rootMap.clear();
            Set<? extends Element> routeElements =
                    roundEnv.getElementsAnnotatedWith(ModuleAdd.class);
            try {
                logger.info(">>> Found routes, start... <<<");
                this.parseRoutes(routeElements);

            } catch (Exception e) {
                logger.error(e);
            }


//            StringBuilder builder = new StringBuilder()
//                    .append("package com.yuntao.annotationprocessor.generated;\n\n")
//                    .append("public class GeneratedClass {\n\n") // open class
//                    .append("\tpublic String getMessage() {\n") // open method
//                    .append("\t\treturn \"");
//
//
//            // for each javax.lang.model.element.Element annotated with the CustomAnnotation
//            for (Element element : roundEnv.getElementsAnnotatedWith(ModuleAdd.class)) {
//                String objectType = element.getSimpleName().toString();
//
//
//                // this is appending to the return statement
//                builder.append(objectType).append(" says hello!\\n");
//            }
//
//
//            builder.append("\";\n") // end return
//                    .append("\t}\n") // close method
//                    .append("}\n"); // close class
//
//
//            try { // write the file
//                JavaFileObject source = processingEnv.getFiler().createSourceFile("com.yuntao" +
//                        ".annotationprocessor.generated.GeneratedClass");
//
//
//                Writer writer = source.openWriter();
//                writer.write(builder.toString());
//                writer.flush();
//                writer.close();
//            } catch (IOException e) {
//                // Note: calling e.printStackTrace() will print IO errors
//                // that occur from the file already existing after its first run, this is normal
//            }

            return true;
        }


        return false;
    }

    private void parseRoutes(Set<? extends Element> routeElements) throws IOException {
        if (CollectionUtils.isNotEmpty(routeElements)) {
            // prepare the type an so on.

            logger.info(">>> Found routes, size is " + routeElements.size() + " <<<");

            rootMap.clear();


//            ClassName activity = ClassName.get("android.app", "Activity");
//
//            TypeSpec.Builder mainActivityBuilder = TypeSpec.classBuilder("MainActivity")
//                    .addModifiers(Modifier.PUBLIC)
//                    .superclass(activity);
//
//            ClassName override = ClassName.get("java.lang", "Override");
//
//            ClassName bundle = ClassName.get("android.os", "Bundle");
//
//            ClassName nullable = ClassName.get("android.support.annotation", "Nullable");
//
//            ParameterSpec savedInstanceState = ParameterSpec.builder(bundle, "savedInstanceState")
//                    .addAnnotation(nullable)
//                    .build();
//
//            MethodSpec onCreate = MethodSpec.methodBuilder("onCreate")
//                    .addAnnotation(override)
//                    .addModifiers(Modifier.PROTECTED)
//                    .addParameter(savedInstanceState)
//                    .addStatement("super.onCreate(savedInstanceState)")
//                    .addStatement("setContentView(R.layout.activity_main)")
//                    .build();
//
//            TypeSpec mainActivity = mainActivityBuilder.addMethod(onCreate)
//                    .build();
//
//            JavaFile file = JavaFile.builder("com.test", mainActivity).build();
//
//            try {
//                file.writeTo(mFiler);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


            TypeMirror type_Activity = elementUtils.getTypeElement(ACTIVITY).asType();
            TypeMirror type_Service = elementUtils.getTypeElement(SERVICE).asType();
            TypeMirror fragmentTm = elementUtils.getTypeElement(FRAGMENT).asType();
            TypeMirror fragmentTmV4 = elementUtils.getTypeElement(Consts.FRAGMENT_V4).asType();

            // Interface of ARouter
//            TypeElement type_IRouteGroup = elementUtils.getTypeElement(IROUTE_GROUP);
//            TypeElement type_IProviderGroup = elementUtils.getTypeElement(IPROVIDER_GROUP);
//            ClassName routeMetaCn = ClassName.get(RouteMeta.class);
//            ClassName routeTypeCn = ClassName.get(RouteType.class);

            /*
               Build input type, format as :

               ```Map<String, Class<? extends IRouteGroup>>```
             */
        }
    }

}
