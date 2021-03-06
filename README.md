<h1 align="center">
  <br>
  <br>
  HyCommonUtils
  <h4 align="center">
  一个超级普通的基础工具类库
  </h4>
  <h5 align="center">
<a href="https://circleci.com/gh/HyDevelop/HyCommonUtils">CircleCI</a>&nbsp;&nbsp;
<a href="#maven">Maven 导入</a>&nbsp;&nbsp;
<a href="https://hydevelop.github.io/HyCommonUtils/">开发文档</a>&nbsp;&nbsp;
<a href="#license">开源条款</a>
</h5>
  <br>
  <br>
  <br>
</h1>


<a name="maven"></a>
Maven 导入:
--------

没有添加 JitPack 的 Repo 的话首先添加 Repo, 在 pom 里面把这些粘贴进去:

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
    <groupId>com.github.hydevelop</groupId>
    <artifactId>HyCommonUtils</artifactId>
    <version>1.4.0.207</version>
</dependency>
```

然后 ReImport 之后就导入好了!

<br>

<a name="gradle"></a>
Gradle 导入:
--------

没有添加 JitPack 的 Repo 的话首先添加 Repo, 在 pom 里面把这些粘贴进去:

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
    implementation 'com.github.hydevelop:HyCommonUtils:1.4.0.207'
}
```

#### [其他导入(SBT / Leiningen)](https://jitpack.io/#hydevelop/HyCommonUtils/1.4.0.207)

<br>

<a name="license"></a>
[开源条款](https://github.com/HyDevelop/HyCommonUtils/blob/master/LICENSE): MIT License
--------
