### Mvvm（项目全程使用kotlin语言编写）

#### 1.在工程中的build.gradle里的android加入以下设置： 
```java
dataBinding {
        enabled = true
}
```
#### 2.关联viewmodel
  在activity_login.xml中关联LoginViewModel
```java
<layout>
    <data>
        <variable name="loginVm" type="com.zlh.huahua.myproject.viewmodel.LoginViewModel"/>
    </data>
    <...>
</layout>
```
#### 3.activity使用
    LoginActivity继承BaseActivity
 ```kotlin
 class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() 
    
    override fun bindContentLayout(): Int = R.layout.activity_login
    
    override fun onActivityCreate(savedInstanceState: Bundle?) {
        setTitle(getString(R.string.string_login))
        binding?.loginVm = viewModel
    }

    override fun initViewModel(): LoginViewModel = LoginViewModel(this)
}
```
> 保存activity_login.xml后databinding会生成一个ActivityLoginBinding类。（如果没有生成，试着点击Build->Clean Project） 
BaseActivity是一个抽象类，里面bindContentLayout返回布局id，onActivityCreate初始化数据使用，initViewModel返回ViewModel，里面有设置标题内容等，具体可看源码

#### 3.数据绑定

绑定用户名:
在LoginViewModel中定义
```kotlin
val phone = ObservableField<String>("")
```
在用户名EditText标签中绑定
```kotlin
 android:text="@={loginVm.passWord}"
```
点击事件绑定
```kotlin
fun login() {
}
```
在登录按钮标签中绑定
```kotlin
 android:onClick="@{()->loginVm.login()}"
```
#### 4.网络请求（retrofit+rxjava+okhttp）
```kotlin
  val builder = OkHttpClient.Builder()
  builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
  builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
  builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)

  val interceptor = HttpLoggingInterceptor()
  if(BuildConfig.DEBUG){
     //显示日志
     interceptor.level = HttpLoggingInterceptor.Level.BODY
  else {
     interceptor.level = HttpLoggingInterceptor.Level.NONE
  }
  builder.addInterceptor(interceptor)

  retrofit = Retrofit.Builder().client(builder.build()).baseUrl(BASE_URL)
  .addConverterFactory(ScalarsConverterFactory.create())
  .addConverterFactory(GsonConverterFactory.create())
  .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
  .build()
 ```





 

