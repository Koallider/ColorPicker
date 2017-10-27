# ColorPicker
Simple color picker widget for Android

![screenshot_20171027-113132](https://user-images.githubusercontent.com/2699619/32088078-e4a5ddfc-bb0a-11e7-929e-26837b4b79d5.jpg)

# Download

Grab via gradle

```
dependencies {
    compile 'ru.ryakovlev.android:color-picker:0.0.1'
}
```
or [AAR from JCenter](https://jcenter.bintray.com/ru/ryakovlev/android/color-picker/0.0.1/)

# Usage

```
<ru.ryakovlev.colorpicker.ColorPicker
    android:id="@+id/colorPicker"
    android:layout_width="match_parent"
    android:layout_height="50dp"
    color:layoutType="linear_horizontal" 
    color:colors="@array/colorList"/>
```

```
colorPicker.onColorSelected { color -> root.setBackgroundColor(color) }
```

# License
```
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
