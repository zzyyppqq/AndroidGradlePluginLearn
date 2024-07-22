#  AndroidGradlePluuginPlugin

Gradle插件支持groovy,java,kotlin语言，所以，根据我们使用的语言，可以把插件代码分别放到下面几个不同的目录下
rootProjectDir/buildSrc/src/main/groovy
rootProjectDir/buildSrc/src/main/java
rootProjectDir/buildSrc/src/main/kotlin

比如，我们想使用java语言来开发插件代码，我们就需要把我们的java代码放到rootProjectDir/buildSrc/src/main/java这个目录下。

**创建properties文件，关联插件**

我们需要创建一个properties的文件，把插件的名字跟插件关联起来。

创建目录及文件 main/META-INF/gradle-plugins/插件名.properties文件。