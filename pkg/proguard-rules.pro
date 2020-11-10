#-------------------------------------------定制化区域----------------------------------------------
#---------------------------------1.实体类---------------------------------

#我的工程里没有。

#-------------------------------------------------------------------------

#---------------------------------2.第三方包-------------------------------

#我的工程里没有。

#-------------------------------------------------------------------------

#---------------------------------3.与js互相调用的类------------------------

#我的工程里没有。

#-------------------------------------------------------------------------

#---------------------------------4.反射相关的类和方法-----------------------

#我的工程里没有。

#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------

#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-printmapping proguardMapping.txt
-optimizations !code/simplification/cast,!field/*,!class/merging/*
-keepattributes *Annotation*,InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
#----------------------------------------------------------------------------

#---------------------------------默认保留区---------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View

#----------------------------------------------------------------------------

#---------------------------------webview------------------------------------

#----------------------------------------------------------------------------
#---------------------------------------------------------------------------------------------------