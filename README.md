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
   目前流行的网络请求框架，里面有HttpLoggingInterceptor拦截器，拦截请求信息，输出Request、Response内容，可以清晰看到与后台接口对接的数据
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
 网络异常处理
 在BaseViewModel里面做了封装，当请求超时，服务器内部错误，解析错误等等，我会在BaseSubscriber里面处理异常，你可以回调onFail方法自行处理
```kotlin
 fun <T> flowable(flowable: Flowable<BaseResponse<T?>>): Flowable<T> {
        view.showLoading()
        val f = flowable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        return f.map {
            view.hideLoading()
            when (it.head.bzflag) {
                "200" -> {
                    it.body ?: Unit as T
                }
                else -> {
                    throw BaseException(it.head.bzflag, it.head.errmsg)
                }
            }
        }
    }
```
#### 5.辅助功能

> 我在里面也封装了一下辅助类，比如扩展函数，里面包括日常的吐司弹出，获取宽高还有view的显示隐藏等等，具体可以参考源码

##### 5.1 RxBus（事件总线通信）
写法很简单，用法也很简单，主要是用于发送事件/订阅事件
```kotlin
object RxBus {

    // 支持背压且线程安全的，保证线程安全需要调用 toSerialized() 方法
    private val mBus:FlowableProcessor<Any> by lazy { PublishProcessor.create<Any>().toSerialized() }

    //发送事件
    fun post(obj: Any) {
        mBus.onNext(obj)
    }

    //订阅事件
    fun <T> toFlowable(c: Class<T>) = mBus.ofType(c)

    fun toFlowable() = mBus

    fun hasSubscribers() = mBus.hasSubscribers()
}
```
在需要执行的地方发送
```kotlin
RxBus.post(object)
```
##### 5.2其他辅助工具类

SPUtil： SharedPreferences工具类

LogUtil：Log输出

GsonUtil：对象和json数据之间的转换

ContextExtension：context的扩展函数

ViewExtension：view的扩展函数
 
 #### 文章最后
 
 如果这个框架对大家有帮助的话，欢迎给个star，thank you！！！
 如果有意见也可以找本人QQ：1532737423
 

