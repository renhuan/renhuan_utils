#  常用的自定义view和工具（持续增加）

## [同步码云gitee，点我直达](https://gitee.com/renhuan/renhuan_utils)

## [同步github，点我直达](https://github.com/renhuan/renhuan_utils)

- **App二维码下载**

![下载二维码](/img/qr.jpg)

- **导入**

  ```gradle
  	  allprojects {
  	  	repositories {
  	  		...
  	  		maven { url 'https://jitpack.io' }
  	  	}
  	  }
  ```

  ```css
      dependencies {
  	        implementation 'com.github.renhuan:renhuan_utils:1.0.1'
    	}
  ```

    最新版本：![](https://jitpack.io/v/renhuan/renhuan_utils.svg)



## 自定义view

- **EditTextWithClear**（带清除按钮的EditText，继承AppCompatEditText，如果什么都不设置，默认自带显示）

  ```
   <attr name="et_drawable" format="reference" />   清除按钮的图片
   <attr name="et_drawableWidth" format="dimension" /> 清除按钮的图片的宽度
   <attr name="et_drawableHeight" format="dimension" /> 清除按钮的图片高度
   <attr name="et_drawableColor" format="color" />  清除按钮的图片的颜色，可以着色
  ```

  ![效果图](/img/editTextWithClear.gif)

- **StrokeTextView**（带描边的TextView，继承AppCompatTextView）

  ```
  <attr name="st_color" format="reference" />  描边的颜色
  <attr name="st_width" format="dimension" />  描边的宽度
  ```

  ![效果图](/img/storkTextView.jpg)

- **PngImageStroke**（带描边的ImageView，继承View）

  ```
   <attr name="ps_color" format="color" /> 描边的颜色
   <attr name="ps_width" format="dimension" /> 描边的宽度
   <attr name="ps_scale" format="float" /> 图片的缩放，默认为1正常
   <attr name="ps_src" format="reference" /> 图片路径
  ```

  ```xml
    <ImageView
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:layout_gravity="center"
          android:src="@mipmap/bbb" />

      <com.renhuan.utils.PngImageStroke
          android:id="@+id/viewHighLight"
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:layout_gravity="center"
          android:layout_margin="10dp"
          app:ps_color="@color/teal_700"
          app:ps_scale="1"
          app:ps_src="@mipmap/bbb"
          app:ps_width="1dp" />

      <com.renhuan.utils.PngImageStroke
          android:layout_width="wrap_content"
          android:layout_height="wrap_content"
          android:layout_gravity="center"
          android:layout_margin="10dp"
          app:ps_color="@color/black"
          app:ps_scale="1.2"
          app:ps_src="@mipmap/bbb"
          app:ps_width="2dp" />

  ```
  ![效果图](/img/imgStorke.jpg)

- **EditTextWithClear**（带清除按钮的EditText，继承AppCompatEditText，如果什么都不设置，默认自带显示）

  ```
   <attr name="et_drawable" format="reference" />   清除按钮的图片
   <attr name="et_drawableWidth" format="dimension" /> 清除按钮的图片的宽度
   <attr name="et_drawableHeight" format="dimension" /> 清除按钮的图片高度
   <attr name="et_drawableColor" format="color" />  清除按钮的图片的颜色，可以着色
  ```

  ![效果图](/img/storkTextView.jpg)

- **TabLayout**（继承官方TabLayout，横向滑动栏，配合Viewpager，做出炫酷动画）

  <img src="/img/tab.gif" width = "450" alt="效果图" align=center />

  ```kotlin
        val viewPager = findViewById<ViewPager>(R.id.vp)
        val tab = findViewById<TabLayout>(R.id.tab)
        val tab1 = findViewById<TabLayout>(R.id.tab1)
        val tab2 = findViewById<TabLayout>(R.id.tab2)

        viewPager.adapter = MyFragmentPagerAdapter(
            supportFragmentManager,
            arrayListOf(
                DemoFragment.getInstance(),
                DemoFragment.getInstance(),
                DemoFragment.getInstance(),
                DemoFragment.getInstance(),
                DemoFragment.getInstance()
            ),
            arrayListOf("张三", "李四", "张无忌", "Java", "Android")
        )

        //样式1 默认没有图片
        tab.setupWithViewPager(viewPager)

        //样式2 加图片（所有tab都显示）
        tab1.setIcons(
            arrayListOf(
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher
            ), false
        )
        tab1.setupWithViewPager(viewPager)

        //样式3 加图片（当前选中tab显示）
        tab2.setIcons(
            arrayListOf(
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
                R.mipmap.ic_launcher,
                R.drawable.ic_baseline_add_alert_24,
            ), true
        )
        tab2.setupWithViewPager(viewPager)

  ```

  ```xml
      <com.renhuan.utils.tablayout.TabLayout
          android:id="@+id/tab2"
          android:layout_width="0dp"
          android:layout_height="40dp"
          app:layout_constraintEnd_toEndOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintTop_toBottomOf="@+id/tab1"
          app:tabIndicatorColor="@color/purple_200"
          app:tabIndicatorHeight="30dp"
          app:tabMode="scrollable"
          app:tabSelectedTextColor="#ffffff"
          app:tabTextColor="#333333" />

      <androidx.viewpager.widget.ViewPager
          android:id="@+id/vp"
          android:layout_width="0dp"
          android:layout_height="0dp"
          app:layout_constraintBottom_toBottomOf="parent"
          app:layout_constraintEnd_toEndOf="parent"
          app:layout_constraintStart_toStartOf="parent"
          app:layout_constraintTop_toBottomOf="@+id/tab2" />
  ```

- **圆角布局**（Android布局必备，[更多用法参考这里](https://github.com/JavaNoober/BackgroundLibrary)）

- **阴影布局【MaskViewGroup】**（继承viewgroup）

    <img src="/img/shadow.jpg" width = "450" alt="效果图" align=center />

    ```xml
    <com.renhuan.utils.MaskViewGroup
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        app:containerCornerRadius="25dp"
        app:containerDeltaLength="10dp"
        app:containerShadowColor="#ccBB86FC"
        app:containerShadowRadius="6dp"
        app:deltaX="3dp"
        app:deltaY="3dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <com.noober.background.view.BLButton
            android:id="@+id/button"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Button"
            android:textColor="@color/white"
            app:bl_corners_radius="25dp"
            app:bl_ripple_color="#ededed"
            app:bl_ripple_enable="true"
            app:bl_solid_color="#FFBB86FC" />
    </com.renhuan.utils.MaskViewGroup>
    ```
    ```java
    自定义属性介绍：
      containerShadowColor：阴影颜色
      containerShadowRadius：阴影半径
      containerCornerRadius:阴影圆角
      containerDeltaLength：阴影到边框距离
      deltaX：阴影X方向偏移
      deltaY：阴影Y
      enable：是否显示阴影
    ```
## 工具类

- **DrawableTintHelper**（单个drawable动态着色，不影响其他地方的使用（Android 默认一处地方着色，等于所有地方修改）

  ```kotlin
  class MainActivity : AppCompatActivity() {
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)

          findViewById<ImageView>(R.id.imageView).setImageDrawable(
              Ext.getDrawable(R.mipmap.ic_close)?.tintColor(Ext.getColor(R.color.red))
          )

          findViewById<ImageView>(R.id.imageView2).setImageDrawable(
              Ext.getDrawable(R.mipmap.ic_close)?.tintColor(Ext.getColor(R.color.purple_200))
          )
      }
  }
  ```

  ![效果图](/img/tint.jpg)

- **Ext（非常实用哦）**（静态方法和扩展函数）

  ```kotlin
   Ext.getHandler() //获取Handle示例
   Ext.getHandler().postDelayeds(1000) {}//延迟1s 运行 （延时器）
   Ext.getHandler().postDelayeds(1000, 1000) { time, runnable -> } //延迟1s并每秒运行 （定时器）
   Ext.getContext() //获取全局context
   Ext.getString(R.string.app_name) //获取资源字符串
   Ext.getColor(R.color.red) //获取资源颜色
   Ext.getDimension(R.dimen.dp_10) //获取资源尺寸
   Ext.getDrawable(R.mipmap.ic_close) //获取资源drawable
   Ext.onKeyDown(keyCode, event) //按两次返回桌面，重写MainActivity的onKeyDown(keyCode: Int, event: KeyEvent?)方法 返回 return Ext.onKeyDown(keyCode, event)
   "这是提示".toast() //toast
   view.setOnSafeClickListener{} //安全点击 防止多次点击 1s之内之内只能点击一次
   textview.checkEmpty("") //判断TextView是否为空,用法：phone.checkEmpty("手机号不能为空") ?: return
   textview.text() //获取Textview的值
   string.copy() //复制到剪切板
   drawable.tintColor(color) // 给drawable着色，返回新的drawable
   view.setScale() //给所有的View设置触摸缩放效果，类似小米手机点击缩放效果，默认缩放是0.9
  ```

- **MMKVOwner**（腾讯数据持久化，利用委托封装，替代SharedPreferences）

  ```kotlin
    //继承MMKVOwner，里面封装了委托的方法，所有的本地要存取的放这里
    //注意变量名不要重复了，因为默认key为属性名
    object MMKVRepository : MMKVOwner {
         var str by mmkvString("")
         var num by mmkvInt(0)
         var b by mmkvBool(false)
            ...
    }

    //调用的时候
    class MainActivity : AppCompatActivity() {
         override fun onCreate(savedInstanceState: Bundle?) {
             super.onCreate(savedInstanceState)
             setContentView(R.layout.activity_main)
             //赋值
             MMKVRepository.str = "张三"
             MMKVRepository.num = 100
             MMKVRepository.b = false
             //取值
             println("---------" + MMKVRepository.str)
             println("---------" + MMKVRepository.num)
             println("---------" + MMKVRepository.b)
      }
    }
  ```

- **ActivityMessenger**（代替startActivity，具有kt特性，方便好用，下面是模拟页面A -> 页面B最基本的用法，[更多用法参考这里](https://github.com/wuyr/ActivityMessenger)）

  ```kotlin
  class AActivity : AppCompatActivity() {
      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          //页面A需要跳转的时候调用
          BActivity.startAction(this)
          //BActivity.startAction(this,"张三")
      }
  }

  class BActivity : AppCompatActivity() {
      //页面B定义方法
      companion object {
          fun startAction(context: Context, value: String) {
              ActivityMessenger.startActivity<MainActivity>(context)
             //ActivityMessenger.startActivity<MainActivity>(context, "key" to value)
          }
      }

       override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_main)
          //页面B取值
          val value = intent.get<String>("key") //value == "张三"
      }
  }
  ```
- **Xpopu各种弹窗**（Android布局必备，[更多用法参考这里](https://github.com/li-xiaojun/XPopup)）
