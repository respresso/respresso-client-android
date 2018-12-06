# About
This is the android client for Respresso (https://respresso.io/). Respresso is a collaborative resource management tool for multiplatform projects. It automatically transforms and delivers to your project without assistance. Your assets will be ready for use almost immediately.

# Demo
[Demo video](https://youtu.be/gpCc0ihXxfc?t=258)
 
# How to
 Add the Maven repository into your project level gradle:
 ```groovy
 allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
        maven { url "https://plugins.gradle.org/m2/" }
    }
  }
  ```
  
 Add the dependency into your app level gradle:
  ```groovy
apply plugin: 'hu.ponte.respresso'

respresso {
    projectToken "YOUR_PROJECT_TOKEN"
    resources{
        require "localization:1.0+"
        require "image:1.0+"
        require "font:1.0+"
        require "color:1.0+"
        require "appIcon:1.0+"
    }
    
// Optional
    server "YOUR_SERVER_ADDRESS"
}
  
dependencies {
	      implementation 'com.github.pontehu:respresso:0.0.1'
	}
```

# Licence
```
Copyright 2018 Ponte.hu Kft

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
