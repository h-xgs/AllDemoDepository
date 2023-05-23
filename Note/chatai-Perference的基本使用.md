## try()括号的意思
`try-with-resources`语句是Java 7中引入的一种编写`try-catch-finally`语句的简化方式。这种方式能够在代码块结束后自动关闭指定资源，并且处理可能发生的异常。

在`try-with-resources`语句中，可以将需要关闭的资源放在括号中，例如：

```
try (InputStream inputStream = new FileInputStream("example.txt")) {
  // 使用inputStream读取数据
} catch (IOException e) {
  // 处理异常
}
```

这个语句中，`try`块内使用了一个`InputStream`对象`inputStream`，并且在括号中使用了`InputStream`的构造函数创建了这个对象。在代码块执行完毕后，try-with-resources语句会自动关闭这个资源。如果在关闭资源的过程中发生了异常，这个异常会被捕获和处理。

通过使用`try-with-resources`语句可以使代码更简洁、更易读，并且可以避免资源泄漏的风险。需要注意的是，只有实现了`java.lang.AutoCloseable`或者`java.io.Closeable`接口的类才可以作为`try-with-resources`语句的资源。

## SwitchPreference的基本使用
`SwitchPreference`是Android系统提供的一个控件，可以用于在应用中显示一个开关控件。当开关打开时，执行某些操作；当开关关闭时，执行另外的操作。

下面是一个简单的使用`SwitchPreference`的例子：

1. 在res/xml/目录下创建preference.xml文件，添加SwitchPreference控件

```
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">

    <SwitchPreference
        android:key="preference_key"
        android:title="Switch Preference"
        android:summary="This is a switch preference"
        android:defaultValue="false"/>

</PreferenceScreen>
```

这里我们设置了一个SwitchPreference控件，设置了key、title、summary和defaultValue等属性。

2. 在Activity中加载preference.xml布局，并在onCreate方法中获取SwitchPreference对象

```
public class ExampleActivity extends AppCompatActivity {

    private SwitchPreference mSwitchPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);

        // 获取SwitchPreference对象
        mSwitchPreference = (SwitchPreference) findPreference("preference_key");
    }
}
```

在onCreate方法中，我们通过调用findPreference方法，传入SwitchPreference控件的key，来获取SwitchPreference对象。

3. 设置SwitchPreference的监听器，并实现打开和关闭开关的操作

```
mSwitchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean isChecked = (Boolean) newValue;
        if (isChecked) {
            // 执行打开开关的操作
            Toast.makeText(ExampleActivity.this, "Switch ON", Toast.LENGTH_SHORT).show();
        } else {
            // 执行关闭开关的操作
            Toast.makeText(ExampleActivity.this, "Switch OFF", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
});
```

在这里，我们为SwitchPreference控件设置了一个OnPreferenceChangeListener监听器，在监听到开关状态改变时，调用onPreferenceChange方法。根据开关状态执行相应的操作，并返回true，表示已经处理了开关状态改变的事件。

通过这个例子，我们可以学到如何使用SwitchPreference控件来实现开关操作，并设置开关状态改变的监听器。

## SwitchPreference和controller基本使用
介绍SwitchPreference的基本使用之后，我们再来介绍一下SwitchPreference与PreferenceController配合使用的方法。通过自定义PreferenceController，我们可以在应用中更加灵活地管理和控制SwitchPreference的状态，以及实现更多的功能。

下面是一个使用SwitchPreference和PreferenceController的例子：

1. 在res/xml/目录下创建preference.xml文件，并在其中添加SwitchPreference控件，为其设置key、title、summary和defaultValue等属性。

```
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">

  <SwitchPreference
      android:key="example_switch_preference"
      android:title="Switch Preference"
      android:summary="This is a switch preference"
      android:defaultValue="false" />

</PreferenceScreen>
```

2. 创建自定义PreferenceController类

在这个例子中，我们创建了一个继承自`PreferenceController`的`ExampleSwitchPreferenceController`类。通过这个类，我们可以控制和管理`SwitchPreference`控件。

```
public class ExampleSwitchPreferenceController extends PreferenceController<SwitchPreference> {

    public ExampleSwitchPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override
    public SwitchPreference getPreference() {
        SwitchPreference switchPreference = new SwitchPreference(getContext());
        switchPreference.setKey("example_switch_preference");
        switchPreference.setTitle("Custom Switch Preference");
        switchPreference.setSummary("This is a custom switch preference");
        return switchPreference;
    }

    @Override
    public void updateState(SwitchPreference preference) {
        // 获取当前SwitchPreference的状态
        boolean isChecked = preference.isChecked();
        if (isChecked) {
            // 如果开关被打开，执行相应的操作
            Toast.makeText(getContext(), "Switch ON", Toast.LENGTH_SHORT).show();
        } else {
            // 如果开关被关闭，执行相应的操作
            Toast.makeText(getContext(), "Switch OFF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        // 处理点击事件，例如打开对话框、启动Activity等操作
        return super.handlePreferenceTreeClick(preference);
    }
}
```

在自定义的PreferenceController中，我们通过覆盖`getPreference`方法来返回一个`SwitchPreference`对象，并设置其属性。`updateState`方法在SwitchPreference的状态改变时会被调用，我们可以在这里根据开关状态执行相应的操作。

3. 在Activity中使用自定义PreferenceController

```
public class ExampleActivity extends AppCompatActivity {

    private ExampleSwitchPreferenceController mSwitchPreferenceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 创建ExampleSwitchPreferenceController对象
        mSwitchPreferenceController = new ExampleSwitchPreferenceController(this, getLifecycle());

        // 将ExampleSwitchPreferenceController与SwitchPreference绑定
        mSwitchPreferenceController.onCreatePreferences(null, null);
        PreferenceScreen preferenceScreen = getPreferenceManager().createPreferenceScreen(this);
        preferenceScreen.addPreference(mSwitchPreferenceController.getPreference());
        setPreferenceScreen(preferenceScreen);
    }
}
```

在Activity中，我们创建了自定义的PreferenceController对象，并将其与`SwitchPreference`绑定，使用`getPreference`方法来获取SwitchPreference对象。`createPreferences`方法用于初始化PreferenceController，并向Preference集合中添加Preference对象。最后调用`setPreferenceScreen`方法将Preference集合设置为Activity的PreferenceScreen。

通过使用自定义的PreferenceController，我们可以更加灵活地管理SwitchPreference控件。同时，我们可以使用`handlePreferenceTreeClick`方法来处理点击事件，例如打开对话框、启动Activity等操作。

## android studio 没有 PreferenceController 类
对不起，我之前的回答可能有误。比较新的Android Studio版本（例如4.1以上版本）可能还没有原生提供PreferenceController类，需要自己定义。

要实现类似于PreferenceController的功能，可以继承Preference类，并在Activity或Fragment中管理和控制它的状态。下面是一个基本的实现方式：

1. 创建一个继承自Preference的类，例如我们这里创建一个MySwitchPreference类。在这个类中可以实现对于SwitchPreference的控制逻辑。

```
public class MySwitchPreference extends SwitchPreference {

    public MySwitchPreference(Context context) {
        super(context);
        setDefaultValue(false);
        setKey("my_switch_preference");
        setTitle("My Switch Preference");
        setSummary("This is a custom switch preference");
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean isChecked = (Boolean) newValue;
        if (isChecked) {
            // 执行打开开关的操作
            Toast.makeText(getContext(), "Switch ON", Toast.LENGTH_SHORT).show();
        } else {
            // 执行关闭开关的操作
            Toast.makeText(getContext(), "Switch OFF", Toast.LENGTH_SHORT).show();
        }
        // 返回true表示已经处理了开关状态改变的事件
        return true;
    }

}
```

2. 在Activity中加载preference.xml布局，并在onCreate方法中获取MySwitchPreference对象，并将其添加到PreferenceScreen中

```
public class ExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);

        // 获取MySwitchPreference对象
        MySwitchPreference switchPreference = new MySwitchPreference(this);

        // 将MySwitchPreference添加到PreferenceScreen中
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(this);
        screen.addPreference(switchPreference);
        setPreferenceScreen(screen);
    }
}
```

在Activity的onCreate方法中，我们创建并获取了MySwitchPreference对象，并将其添加到PreferenceScreen中。

3. 设置SwitchPreference的监听器

为MySwitchPreference设置监听器，以便在开关状态改变时执行相应的操作。

```
switchPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return preference.onPreferenceChange(preference, newValue);
    }
});
```

通过自定义Preference的方式，我们可以实现类似于PreferenceController的效果，不过需要更多的代码。另外需要注意的是，代码中的MySwitchPreference需要提供额外的控制逻辑，这些逻辑不会像PreferenceController一样自动被绑定。

## androidx的preference
AndroidX库中提供了一组用于管理应用程序设置的Preference组件。这些组件构成了一个基于XML的GUI层次结构，能够解耦逻辑和UI，并以简单的方式提供各种UI元素，如选项卡，开关按钮，列表值等。

要使用AndroidX.preference库，您需要将其添加为应用程序的依赖项。在您的应用程序的build.gradle文件中，添加以下依赖项：

```
dependencies {
    implementation 'androidx.preference:preference:1.1.0'
}
```

然后，您可以使用以下常见的Preference类来构建应用程序的设置屏幕：

- `Preference`: 常规设置项目
- `CheckBoxPreference`: 显示具有标志状态的设置项目
- `ListPreference`: 显示由用户选择的设置选项列表
- `EditTextPreference`: 显示用户可以输入文本的设置项
- `SwitchPreference`: 显示具有开/关状态的设置项目

然后，您可以使用以下步骤构建设置屏幕：

1. 在res/xml文件夹中创建一个新的XML文件，如pref_general.xml，并添加一个首选项屏幕。然后，添加需要的首选项。

```
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">

    <PreferenceCategory android:title="@string/pref_header_general">
        <EditTextPreference
            android:defaultValue="@string/pref_default_display_name"
            android:key="@string/pref_key_display_name"
            android:selectAllOnFocus="true"
            android:singleLine="true"
            android:title="@string/pref_title_display_name" />
    </PreferenceCategory>
  
    <PreferenceCategory android:title="@string/pref_header_system">
        <CheckBoxPreference
            android:defaultValue="true"
            android:key="@string/pref_key_background_updates"
            android:title="@string/pref_title_background_updates" />
    </PreferenceCategory>

</PreferenceScreen>
```

在这个例子中，我们创建了一个PreferenceScreen，并添加了一个EditTextPreference和一个CheckBoxPreference。

2. 在Activity中加载该布局，并注册SharedPreferences的监听器。

```
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 加载SettingsFragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        // 注册SharedPreferences监听器
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.registerOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 取消SharedPreferences监听器注册
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        preferences.unregisterOnSharedPreferenceChangeListener(mOnSharedPreferenceChangeListener);
    }

    // SharedPreferences监听器
    private SharedPreferences.OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(getString(R.string.pref_key_display_name))) {
                // 执行更新显示名称的操作
            } else if (key.equals(getString(R.string.pref_key_background_updates))) {
                // 执行更新后台更新的操作
            }
        }
    };

}
```

在Activity的onCreate方法中，我们使用SettingsFragment替换了Activity的布局，并注册了SharedPreferences的监听器。在onDestroy方法中，我们取消SharedPreferences监听器的注册，并在监听器回调方法中实现相应的操作。

3. 创建SettingsFragment类，并将其用于管理PreferenceScreen。

```
public class SettingsFragment extends PreferenceFragmentCompat {
    
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref_general, rootKey);
    }

}
```

在SettingsFragment类的onCreatePreferences方法中，我们设置了PreferenceScreen的根元素，并使用`setPreferencesFromResource`方法将其与XML布局文件相关联。

通过以上步骤，我们就可以实现一个基本的设置屏幕。而且，在开发过程中，系统也会提供给我们各种方法来简化开发，例如使用`bindPreferenceSummaryToValue`方法自动将偏好值展示在屏幕上，等等。

