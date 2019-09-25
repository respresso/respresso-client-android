[<div align="center"><img src="https://github.com/pontehu/respresso-client-android/blob/master/images/header_android.png" height="220" /></div>](https://respresso.io)  

# Respresso Android client [![Tweet](https://img.shields.io/twitter/url/http/shields.io.svg?style=social)](https://twitter.com/intent/tweet?text=Save%20development%20time%21%20Respresso%20automatically%20transforms%20and%20delivers%20your%20digital%20assets%20into%20your%20projects&url=https://respresso.io&via=respresso_io&hashtags=developer,tool,localization,image,resources,digital-assets,convert,automation)  

[Respresso](https://respresso.io) is a centralized resource manager for shared Android, [iOS](https://github.com/pontehu/respresso-client-ios) and [Web frontend](https://github.com/pontehu/respresso-sync-for-clients#web) projects.
It allows you to simply import the latest assets into your workspace.
You may store several versions of the same resource in the cloud and pick the ones you need to import.
Respresso currently supports six types of resources:
* Images
* App icons
* Localization
* Fonts
* Colors
* Raw

## Table of contents

- [Usage](#usage)
	- [Breakdown of the plugin configuration](#breakdown-of-the-plugin-configuration)
	- [Tips](#tips)
- [Live localization](#live-localization) 	
	- [Obligation](#obligation)
	- [Usage](#usage)
	- [How to turn on/off](#how-to-turn-on/off)
	- [How to setting up](#how-to-setting-up)
	- [Update text in a view](#update-text-in-a-view)  

## Usage
The plugin aar needs to be defined in the classpath of your build script. It is directly available on Maven central. This snippet shows how to add repositories to your root build.gradle:
```groovy
buildscript {

    dependencies {
        classpath "hu.ponte.respresso:client-android:1.0.0"
    }
}
```

To use the Respresso Android plugin, include this snippet in your app's build.gradle (app/build.gradle):
```groovy
apply plugin: "hu.ponte.respresso"

respresso {
    projectToken "YOUR_PROJECT_TOKEN_HERE"
    resources {
        require "localization:1.0.0"
        require "color:1.0.0"
        require "appIcon:1.0.0"
        require "image:1.0.0"
        require "font:1.0.0"
    }

    server "https://app.respresso.io"
}
```
Please note that you must remove or comment out unused resources types.
Now you can build your project and Respresso will import all resources that were stored at https://app.respresso.io previously. After the first successful build Respresso will create a "respressofile.lock" file on your project's root directory. This file contains information about current assets’ metadata.

#### Automatic usage
Simply press the Run button for your "app". Respresso will synchronize all resources before builds since its plugin is built into your build flow.

#### Manual usage
To synchronize resources without building, use gradle. Use the GUI "Tasks/other/getRespressoResForDebug"

![Respresso synchronize GUI](/images/respresso-gradle.png)

or use terminal and type:  
Windows:
```
gradlew getRespressoResForDebug
```

Linux:
```
./gradlew getRespressoResForDebug
```

Mac:
```
./gradlew getRespressoResForDebug
```

Note! Respresso will remove all resources that do not belong to it. If you would like to use unfollowed or client-specific resources, read the Tips section.

### Breakdown of the plugin configuration
* Include the project token received during initialization of your project in the Respresso web interface. This is done in the same manner as the host with the keyword "project_token" and your token inside double quotation marks
* Specify the required resources inside the resources section using the following format:
require "[resource_name]:[version_number]"

The currently accepted resource names are:
* image
* appIcon
* color
* localization
* font
* raw

Accepted version formats are:
* [major].[minor].[patch]          eg. 1.2.0
* [major].[minor].[patch]+         eg. 1.2.0+ (not available in strict mode)
* [major].[minor]+                 eg. 1.2+ (not available in strict mode)


#### Optional arguments
####### server
Specify the server of your assets in the last line by writing "server" and your URL in double quotation marks separated by a space.
By omitting this argument Respresso will use its default host URL for syncing.
```
server "https://app.respresso.io"
```

####### strictMode
Enabling strict mode will guarantee you get the same results each time you sync your assets. If set to true, you may only specify exact version numbers in your Respressofile. This option is set to false by default. Usage example:
```
strictMode true
```

####### enabled
You can turn off synchronization. Default value is true.
```
enabled false
```

### Tips
###### Android Studio is unable to find a resource
Android Studio may sometimes ignore new resource files. If this is the case, use the "File/Invalidate caches / Restart..." option.

###### Use your own resources
You can create a new module for Respresso. Respresso will synchronize all followed resources into the "module's res" folder in this case and your main module can contain unique resources.

## Live Localization
Respresso serve a real-time preview about your localized strings. It shows you that how the translations are going to look like in your mobile app or web. No need to wait for a next deployment.

### Obligation
Live localization is a higher abstraction layer over Respresso core. That is why Respresso usage is mandatory.

### Usage

Add this dependency in your app's build.gradle (app/build.gradle):

```groovy
dependencies {
	...
	implementation "hu.ponte.respresso:live-edit-android:1.0.0"
	...
}
```

### How to turn on/off
Use this snipet if you would like to build your apk without live localization option.
```groovy
buildTypes {
	...
	develop {
		buildConfigField "Boolean", "RespressoPreRelease", 'true'
	}
	release {
		buildConfigField "Boolean", "RespressoPreRelease", 'false'
	}
}
```

### How to setting up
Create an application class and register it in your manifest file. Append this snipet into the class:  
Kotlin:
```Koltlin
override fun onCreate() {
	super.onCreate()

	Respresso.init(this, BuildConfig.RespressoPreRelease)
}
```

Java:
```Java
@Override
public void onCreate() {
	super.onCreate();

	Respresso.init(this, BuildConfig.RespressoPreRelease);
}
```

Use these configurations in you activity. (It's recommended that use a BaseActivity).  
Kotlin:
``` Kotlin
override fun attachBaseContext(newBase: Context) {
	val context = Respresso.wrapContext(newBase)
	super.attachBaseContext(context)
}

override fun onResume() {
	super.onResume()
	Respresso.create(this)
}

override fun onDestroy() {
	super.onDestroy()
	Respresso.destroy(this)
}

```
Java:
``` Java
@Override
protected void attachBaseContext(Context newBase) {
	Context context = Respresso.wrapContext(newBase);
	super.attachBaseContext(context);
}

@Override
protected void onResume() {
	super.onResume();
	Respresso.create(this);
}

@Override
protected void onDestroy() {
	super.onDestroy();
	Respresso.destroy(this);
}
```

### Update text in a view
The easiest usage when you bind your text and view by Respresso.
``` Kotlin
Respresso.with(context).text(R.string.id_from_strings_xml).withAutoUpdate().into(view)
```

Let see some alternatives:
``` Kotlin
Respresso.with(context).hint(R.string.hint_id).withAutoUpdate().into(view)
Respresso.with(context).error(R.string.error_id).withAutoUpdate().into(view)
Respresso.with(context).title(R.string.title_id).withAutoUpdate().into(view)
Respresso.with(context).subtitle(R.string.subtitle_id).withAutoUpdate().into(view)
```

The technique is really simple. You have to use

Respresso.with(context).text()  -> instead of view.setText()
Respresso.with(context).hint()  -> instead of view.hint()
Respresso.with(context).title() -> instead of Toolbar/ActionBar/MenuItem.setTitle()
etc.

and you concatenate more modifiers when a view has more options, like an EditTextView:
Respresso.with(context).text(R.string.text_id_from_strings_xml).hint(R.string.hint_id_from_strings_xml).withAutoUpdate().into(edit_text_view)

#### Update text in a CustomView
Some cases Respresso unable to fill the view with demanded text(s). Don't worry, You can get notification about changes.
Use this form in case of single text:

``` Kotlin
Respresso.with(context).string(R.string.text_id_from_strings_xml).into(view).dataReadyText = { view.setTextSomehow = it }
```

```Java
Respresso.with(context).string(R.string.text_id_from_strings_xml).into(view).setOnDataReadyText(new DataReadyText() {
	@Override
	public void onDataReady(@Nullable String text) {
		view.setTextSomehow(text)
	}
});
```
Use this form in case of multiple texts:  
Kotlin:
``` Kotlin
Respresso.with(context).strings(arrayOf(
			RespressoStrings(R.string.first_text_id_from_strings_xml)
			RespressoStrings(R.string.second_text_id_from_strings_xml, "TAG")
		)
	).into(view).dataReadyTexts = {
		view.setTextSomehow1 = it[0].text
		if(it[1].TAG == "TAG")
			view.setTextSomehow2 = it[1].text
	}
```

Java:
```Java
Respresso.with(this).strings(new RespressoStrings[]{
			new RespressoStrings(R.string.name, 1),
			new RespressoStrings(R.string.name, 2)
        }).into(tv1).setOnDataReadyTexts(new DataReadyTexts() {
            @Override
            public void onDataReady(@Nullable RespressoText[] texts) {
                view.setTextSomehow1 = it[0].getText())
				if(it[1].getTAG() == "TAG")
					view.setTextSomehow2 = it[1].getText()
            }
        });
```

#### Update possibilities
withAutoUpdate will update your texts whenever it changes if it can.
``` Kotlin
Respresso.with(context).text(R.string.id).withAutoUpdate().into(view)
```
withNotifyUpdate will send you a notification into dataReady listener and you can handle it. It is not refresh your views content automatically.
``` Kotlin
Respresso.with(context).text(R.string.id).withNotifyUpdate().into(view)
```
noUpdate will fill your view only once. It is not send notification and not update your text.
``` Kotlin
Respresso.with(context).text(R.string.id).noUpdate().into(view)
```

#### How to use it
1. Check above instructions
2. Go to respresso(https://app.respresso.io) sign in and choose a project
3. Click localization in the left panel and select the same version that you synked into your project 
4. Get your phone and shake it
5. Switch on 'Localization / Connection' in the popup window
> Now your modifications are going to appearance when you click the 'Save' button on the web
6. For using Live Edit Module\* shake your phone again and switch on 'Localization / Live Editor' in the popup window
7. On web click on the 'Live Editor' button on the top right area
8. Scan the QR Code with your phone which visible in your web browser's top right section

Let see what happened after 7th option. You activated a visible items filter and your list have less elements than before and don't have to use Save button to get updated texts. Be careful, this modification stored just in your phone's memory and you can lose it. Use the Save button to store your modifications. 

\* Live Edit Module: shows real-time preview how the translations will look like in your mobile app. Check the translations’ accuracy, length and the UI experience in real-time.

