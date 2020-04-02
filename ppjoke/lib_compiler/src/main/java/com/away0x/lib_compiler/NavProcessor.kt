package com.away0x.lib_compiler

import com.away0x.lib_annotation.ActivityDestination
import com.away0x.lib_annotation.FragmentDestination
import com.google.auto.service.AutoService
import com.google.gson.GsonBuilder
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation
import kotlin.math.abs

/**
 * APP 页面导航信息收集注解处理器
 *
 * AutoService: 标记后使用 annotationProcessor/kapt 应用后，会自动执行该类
 * SupportedSourceVersion: 支持的 jdk 版本
 * SupportedAnnotationTypes: 声明想要处理的注解
 */
@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes(
    "com.away0x.lib_annotation.ActivityDestination",
    "com.away0x.lib_annotation.FragmentDestination"
)
class NavProcessor : AbstractProcessor() {

    private var messager: Messager? = null
    private var filer: Filer? = null
    private val OUPUT_FILE_NAME = "destination.json"

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        // 日志打印，java 环境下不能用 log
        messager = processingEnv.messager
        // 文件处理工具
        filer = processingEnv.filer
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        // 通过注解处理器环境上下文 roundEnv 分别获取项目中标记的注解
        val fragmentDestination =
            roundEnv?.getElementsAnnotatedWith(FragmentDestination::class.java)
        val activityDestination =
            roundEnv?.getElementsAnnotatedWith(ActivityDestination::class.java)

        if (fragmentDestination != null && activityDestination != null) {
            if (fragmentDestination.isNotEmpty() || activityDestination.isNotEmpty()) {
                val destMap = hashMapOf<String?, MutableMap<String, Any?>?>()
                // 分别处理 FragmentDestination & ActivityDestination 注解类型
                // 并收集到 destMap 中，以此就能记录下所有的页面信息了
                handleDestination(fragmentDestination, FragmentDestination::class.java, destMap)
                handleDestination(activityDestination, ActivityDestination::class.java, destMap)

                /**
                 * 生成文件
                 *
                 * createResource: 创建源文件
                 * CLASS_OUTPUT:  java 文件生成 class 文件的位置，/app/build/intermediates/javac/debug/classes/ 目录下
                 * SOURCE_OUTPUT: java 文件的位置，一般在 /ppjoke/app/build/generated/source/apt/ 目录下
                 */
                // 获取路径
                val resource =
                    filer!!.createResource(StandardLocation.CLASS_OUTPUT, "", OUPUT_FILE_NAME)
                val resourcePath = resource.toUri().path
                // 由于要将生成的 json 文件生成在 app/src/main/assets 目录下，所以做个截取
                val appPath = resourcePath.substring(0, resourcePath.indexOf("app") + 4)
                val assetsPath = appPath + "src/main/assets/"
                // 写入文件
                File(assetsPath).let { file ->
                    if (!file.exists()) file.mkdir()

                    File(file, OUPUT_FILE_NAME).let { newFile ->
                        if (newFile.exists()) newFile.delete()

                        newFile.createNewFile()

                        val content = GsonBuilder().setPrettyPrinting().create().toJson(destMap)
                        newFile.outputStream().bufferedWriter().use {
                            it.write(content)
                            it.flush()
                        }
                    }
                }
            }
        }

        return true
    }

    private fun handleDestination(
        elements: Set<Element>,
        clazz: Class<out Annotation>,
        destMap: MutableMap<String?, MutableMap<String, Any?>?>
    ) {
        for (element in elements) {
            // TypeElement 是 Element 的一种
            // 如果注解标记在了类名上，可以直接强转，使用他可以拿到全类名
            val typeElement = element as TypeElement
            // 获取全类名
            val clazzName = typeElement.qualifiedName.toString()
            // 页面 id 不能重复，使用 hashcode
            val id = abs(clazzName.hashCode())

            var pageUrl: String? = null // 相当于隐式跳转的 host://scheme/path 格式
            var needLogin = false
            var asStarter = false
            var isFragment = false

            // 获取具体的注解
            val annotation = element.getAnnotation(clazz)
            if (annotation is FragmentDestination) {
                pageUrl = annotation.pageUrl
                asStarter = annotation.asStarter
                needLogin = annotation.needLogin
                isFragment = true
            } else if (annotation is ActivityDestination) {
                pageUrl = annotation.pageUrl
                asStarter = annotation.asStarter
                needLogin = annotation.needLogin
            }

            if (destMap.containsKey(pageUrl)) {
                messager?.printMessage(Diagnostic.Kind.ERROR, "不同页面不允许使用相同的 page url $clazzName")
            } else {
                val data = mutableMapOf<String, Any?>()
                data["id"] = id
                data["needLogin"] = needLogin
                data["asStarter"] = asStarter
                data["isFragment"] = isFragment
                data["pageUrl"] = pageUrl
                data["className"] = clazzName

                destMap[pageUrl] = data
            }
        }
    }

}
