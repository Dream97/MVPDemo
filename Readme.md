### 资料
- https://blog.csdn.net/u012422440/article/details/61622335
- https://blog.csdn.net/qq_31852701/article/details/5294612

### 前言
从认识mvp模式，学习，开始使用mvp模式到现在也有一年多的时间了。这一年时间里，无论是自己要做一些Android的小玩意还是做项目,最先考虑的就是要不要用mvp模式开发。学了这么久的MVP模式，这次趁现在还有点空余时间，就好好做一个小结。

### 为什么要使用MVP模式
我们在设计软件的框架的时候，我们需要考虑很多因素，如

- 代码解耦
- 结构是否清晰
- 代码是否可复用
- 有没有具备扩展性
- 是否方便进行单元测试

现在我了解到的主流框架有MVC模式，MVP模式,MVVM模式。而MVC模式本来就是Android本身所采用的模式,想要了解可以看[Android之MVC设计模式在Android中的使用](https://blog.csdn.net/emptoney/article/details/52101844)，而MVP模式是对MVC模式的进一步演化
![MVC](https://gss1.bdstatic.com/9vo3dSag_xI4khGkpoWK1HF6hhy/baike/s=220/sign=947c311776f082022992963d7bfbfb8a/e824b899a9014c085731fd910f7b02087bf4f4f8.jpg)
MVC模式图解
![MVP模式](https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/s=220/sign=f2deeef01ed5ad6eaef963e8b1ca39a3/8b82b9014a90f603534849733c12b31bb051ed0e.jpg)
mvp模式图解
从图解可以看出，View层不在和Model直接交互，而是同过Presenter,而Presenter与View之间通过定义接口实现交互，View层与Model之间完全解耦。对于MVVM模式，现阶段对该模式了解不太深，不敢下笔，以免误导他人。

-------------------
### 如何实现
我写了一个简单的[Demo](https://github.com/Dream97/MVPDemo)，放在github上
![这里写图片描述](https://img-blog.csdn.net/20180605005007406?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0MjYxMjE0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
**逻辑解释**：View层按钮发起获取请求，调用Presenter的获取数据接口，Presenter层调用Model层的获取数据接口，Model层中通过延时操作模拟获取数据过程，然后通过回调调用Presenter层的获取成功接口，最后Presenter层调用View的显示数据接口

**代码结构**
![这里写图片描述](https://img-blog.csdn.net/20180605010748144?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM0MjYxMjE0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

- base包：这里放置BaseActivity,BaseActivity的抽象类
- listener：Model获取数据成功后的回调方法，在Presenter层中引用接口
- main:就是当前页面的一些内容
- mvp：里面的MainContract定义了View和Presenter所要用到的接口
- model:这里面存放的就是Model层的东西了 

**BaseActivity.java**
```java
public abstract class BaseActivity<T extends BasePresenter> extends Activity {

    protected T presenter;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(getLayout());
        init();
    }

    protected abstract void init();
    public abstract int getLayout() ;
}
```
**BasePresenter**

```java
import android.view.View;
public abstract class BasePresenter<T extends BaseActivity>{
    protected T view;
}
```
**MainModel**

```java
public class MainModel {
    public void getData(String s,MainListener mainListener){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainListener.onSuccess("获取成功");
    }
}
```
这里使用延时操作模拟获取数据过程，也就是我们平时访问网络资源的过程，然后通过回调MainListener通知Presenter
**MainListener**
```java

public interface MainListener {
    void onSuccess(String s);
    void onfail();
}
```
回调接口在Presenter中实现
**MainPresenter**

```java
public class MainPresenter extends BasePresenter<MainActivity> implements MainContract.Presenter,MainListener{

    private MainModel mainModel;
    public MainPresenter(MainActivity mainActivity){
        this.view = mainActivity;
        mainModel = new MainModel();
    }
    @Override
    public void getData() {
        mainModel.getData("结果ok啦",this);
    }

    @Override
    public void onSuccess(String s) {
        view.showResult(s);
    }
    @Override
    public void onfail() {

    }
}

```
Contract定义的接口
**MainContract**

```java
public interface MainContract {
    interface View {
        void showResult(String s);
        void showFail(String s);
    }
    interface Presenter{
        void getData();
    }
}
```
### 最后吧唧几句
以上基本是MVP模式实现的基础代码了,整个项目的架构也应该是能够清楚的表达出来了吧。代码之间的耦合程度降低了，测试也变得更加方便。现在想想以前没有考虑过架构写代码的日子，一个Activity中包含了各种逻辑代码，整个类变得臃肿难懂。特别是需求易变的情况下，更是各种伤神动脑。由此看出一个良好的架构对开发人员是多么重要。

Live and Lrean！
