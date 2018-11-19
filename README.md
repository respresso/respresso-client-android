# About
This is the android client for Respresso (https://respresso.io/). Respresso is a collaborative resource management tool for multiplatform projects. It automatically transforms and delivers to your project without assistance. Your assets will be ready for use almost immediately.

[//]: <> # Demo
[//]: <> ![Demo video]()
 
[//]: <> # How to
 
 Add the Maven repository into your project level gradle:
 ```groovy
 allprojects {
    repositories {
        ...
       maven {
            url "https://github.com/pontehu/respresso-client-android/"
        }
    }
  }
  ```
  
 Add the dependency into your app level gradle:
  ```groovy
  apply plugin: 'hu.ponte.respresso'

respresso {
    projectToken "YOUR_PROJECT_TOKEN"
    resources{
//		[use this declaration format]
//        localization.version "1.0+"
//        appIcon.version "1.0+"
//		  font.version "1.0+"
//        icon.version "1.0+"
		
//		[OR this declaration format]
//        require ("color") {
//            version "1.0+"
//        }

		[OR this declaration format]
        require "font:1.1+"
        require "image:1.2+"
//         ... other requested resource categories
    }
//     Optional
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