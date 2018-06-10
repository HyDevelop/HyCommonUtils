<h1 align="center">
  <br>
  <br>
  HyCommonUtils
  <h4 align="center">
  一个超级普通的基础工具类库
  </h4>
  <h5 align="center">
<a href="https://circleci.com/gh/HyDevelop/PicqBotX">CircleCI</a>&nbsp;&nbsp;
<a href="#maven">Maven导入</a>&nbsp;&nbsp;
<a href="#development">开发文档</a>&nbsp;&nbsp;
<a href="#license">开源条款</a>
</h5>
  <br>
  <br>
  <br>
</h1>


<a name="maven"></a>
Maven 导入:
--------

没有添加JitPack的Repo的话首先添加Repo, 在pom里面把这些粘贴进去:

```xml
<repositories>
    <repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
    </repository>
</repositories>
```

然后添加这个库:

```xml
<dependency>
    <groupId>com.github.hykilpikonna</groupId>
    <artifactId>PicqBotX</artifactId>
    <version>1.0.4</version>
</dependency>
```

然后ReImport之后就导入好了!

<br>

<a name="gradle"></a>
Gradle 导入:
--------

没有添加JitPack的Repo的话首先添加Repo, 在pom里面把这些粘贴进去:

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

然后添加这个库:

```gradle
dependencies {
    implementation 'com.github.hydevelop:PicqBotX:1.0.4'
}
```

<!-- 每次更新都要手动改这些版本号好烦的_(:з」∠)_... -->

#### [其他导入(SBT / Leiningen)](https://jitpack.io/#hydevelop/PicqBotX/1.0.4)

<br>
