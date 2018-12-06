# myMvvm
#### 1.在工程中的build.gradle里的android加入以下设置： 
```java
dataBinding {
        enabled = true
}
```
#### 2.关联viewmodel
  在activity_login.xml中关联LoginViewModel。
```java
<layout>
    <data>
        <variable name="loginVm" type="com.zlh.huahua.myproject.viewmodel.LoginViewModel"/>
    </data>
    <...>
</layout>
```

