# What is ExtFX?

*ExtFX* is a lightweight project intended to provide additional controls and utility classes for JavaFX 2.2, brought into being and to date developed only by me ;-).

You can read more about this project on my [blog](http://myjavafx.blogspot.de/).

The "Ext" in the name simply stands for "Extended" and is inspired by the JavaScript Framework [ExtJS](http://en.wikipedia.org/wiki/Ext_JS). 

There are also some binary downloads and JavaDoc available in the [downloads](downloads) section.

## New in version 0.3:
DateAxis and LogarithmicAxis!

# Features 
## Controls (`extfx.scene.control`)
### DatePicker
Lets you chose a date from a calendar.

![DatePicker](https://bitbucket.org/sco0ter/extfx/raw/tip/src/main/java/extfx/scene/control/doc-files/DatePicker.png)

### CalendarView
A view which represents the Java Calendar class.

### NumberSpinner
A TextField-like control, which allows for numeric input and let's the user spin through numbers in a given interval, also known as NumericStepper.

![NumberSpinner](https://bitbucket.org/sco0ter/extfx/raw/tip/src/main/java/extfx/scene/control/doc-files/NumberSpinner.png)

### RestrictiveTextField
Restricts the input on a text field by a maximal length and by regular expression, e.g. to allow only numeric input.

### TreeViewWithItems
Enhances the the JavaFX TreeView with a setItems method, so that it can use an underlying data source, just as ListView or TableView.

## Charts (`extfx.scene.chart`)
### DateAxis
Lets you set a date as value for an axis on a XYChart.

![DateAxis](https://bitbucket.org/sco0ter/extfx/raw/tip/src/main/java/extfx/scene/chart/doc-files/DateAxisMonths.png)

### LogarithmicAxis
Lets you set a logarithmic scale axis on a XYChart.

![Logarithmic](https://bitbucket.org/sco0ter/extfx/raw/tip/src/main/java/extfx/scene/chart/doc-files/LogarithmicAxisMonths.png)

## Animation (`extfx.animation`)
### BackInterpolator, BounceInterpolator, ElasticInterpolator, ...
A bunch of popular interpolators to smooth your animations. They allow you to make your animations look elastic or bounce them etc...

![Easing functions](https://bitbucket.org/sco0ter/extfx/raw/tip/src/main/java/extfx/animation/doc-files/EasingFunctions.png)

## Utility (`extfx.util`)
### ClickRepeater
Constantly fires a button's ActionEvent, while it is armed in a periodically interval. It is used e.g. in NumberSpinner allowing the user to keep a button pressed in order to quickly increment the number.

# License
The code is published under the [MIT License](http://opensource.org/licenses/MIT). I basically want to provide the code "as is" and want to allow you to use and modify it.