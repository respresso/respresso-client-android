# Introduction 
Respresso is a centralized resource manager for shared Android and iOS projects.
It allows you to simply import the latest assets into your workspace.
You may store several versions of the same resource in the cloud and pick the ones you need to import.
Respresso currently supports five types of resources:
* Images
* App icons
* Localization
* Fonts
* Colors

# Usage
The plugin aar needs to be defined in the classpath of your build script. It is directly available on Maven central. This snippet shows how to add repositories to your root build.gradle:
```groovy
buildscript {
    
    dependencies {
        classpath "hu.ponte.respresso:client-android:0.0.2"
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

### Automatic usage
Simply press the Run button for your "app". Respresso will synchronize all resources before builds since its plugin is built into your build flow.

### Manual usage
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

## Breakdown of the plugin configuration
* Include the project token received during initialization of your project in the Respresso web interface. This is done in the same manner as the host with the keyword "project_token" and your token inside double quotation marks
* Specify the required resources inside the resources section using the following format:
require "[resource_name]:[version_number]"

The currently accepted resource names are:
* image
* appIcon
* color
* localization
* font
        
Accepted version formats are:
* [major].[minor].[patch]          eg. 1.2.0
* [major].[minor].[patch]+         eg. 1.2.0+ (not available in strict mode)
* [major].[minor]+                 eg. 1.2+ (not available in strict mode)


### Optional arguments
###### server
Specify the server of your assets in the last line by writing "server" and your URL in double quotation marks separated by a space.
By omitting this argument Respresso will use its default host URL for syncing.
```
server "https://app.respresso.io"
```

###### strictMode
Enabling strict mode will guarantee you get the same results each time you sync your assets. If set to true, you may only specify exact version numbers in your Respressofile. This option is set to false by default. Usage example:  
```
strictMode true
```

## Tips
##### Android Studio is unable to find a resource
Android Studio may sometimes ignore new resource files. If this is the case, use the "File/Invalidate caches / Restart..." option.

##### Use your own resources
You can create a new module for Respresso. Respresso will synchronize all followed resources into the "module's res" folder in this case and your main module can contain unique resources.

# Licence
```
Copyright 2019 Ponte.hu Kft.

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```
