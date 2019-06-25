# android-navigation-bar: Flarebar 
Flarebar is fully customizable navigation bar, followed modern material design guidelines. it is newly designed bottom bar (can also use as top navigation bar).

## Demo with different designs

### Design#1
![flarebar-gif](https://user-images.githubusercontent.com/30389552/60095042-49fb1f80-976b-11e9-9ace-974342329687.gif)

### Design#2
![flarebar-2](https://user-images.githubusercontent.com/30389552/60095635-a743a080-976c-11e9-891c-e40fc85458dd.gif)

### Design#3
![flarebar-3](https://user-images.githubusercontent.com/30389552/60096291-2c7b8500-976e-11e9-80f4-969f74af3965.gif)

### Tab Badges
![screenshot_20190625-180617](https://user-images.githubusercontent.com/30389552/60099132-b1699d00-9774-11e9-970d-6ac9a6ae5ccf.jpg)After Select
![Webp net-resizeimage](https://user-images.githubusercontent.com/30389552/60099227-db22c400-9774-11e9-9eb9-d0d2ee5c0a58.jpg)

## Installation
Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Then add the dependency:

```
dependencies {
	        implementation 'com.github.vaibhav1929:android-navigation-bar:1.0'
	}

```
## Documentation
### STEP 1: Add following code in XML file:
```xml
     <com.flarebit.flarebarlib.FlareBar
        android:id="@+id/bottomBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"/>
```

You can set gravity to BOTTOM or TOP as per your requirement.

### STEP 2: Add following code to your java file:

Create object of FlareTab class for each tab. create arraylist of your all flaretab objects. and call <b>setTabList(ArrayList)</b> method of Flarebar object.

```java
   final FlareBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setBarBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Flaretab> tabs = new ArrayList<>();
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.inboxb),"Inbox","#FFECB3"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.searchb),"Search","#80DEEA"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.phoneb),"Call Log","#B39DDB"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.avatarb),"Profile","#EF9A9A"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.settingsb),"Settings","#B2DFDB"));

        bottomBar.setTabList(tabs);
        bottomBar.attachTabs(MainActivity.this);
```
### STEP 3: Attach Listener to get notified when tab is changed:
```java
 bottomBar.setTabChangedListener(new TabEventObject.TabChangedListener() {
            @Override
            public void onTabChanged(LinearLayout selectedTab, int selectedIndex, int oldIndex) {
                //tabIndex starts from 0 (zero). Example : 4 tabs = last Index - 3
                Toast.makeText(MainActivity.this,"Tab "+ selectedIndex+" Selected.",Toast.LENGTH_SHORT).show();
            }
        });
```

Available constructor of FlareTab Object.

```java
public Flaretab(Drawable tabImage, String tabText, String tabColorString);
```

This constructor accepts three arguments:

(1)TabImage: represents drawable file to be displayed as icon of tab.

(2)tabText : represents string value shown as text on tab.

(3)tabColorString : represents color HEX code string, this color is displayed when tab is selected.

To set the badge on the tab use following constructor:

```java
public Flaretab(Drawable tabImage, String tabText, String tabColorString,String badge);
```

Send badge string as last argument.

Other available methods of FlareTab object are:

```java
Drawable getTabImage()
void setTabImage(Drawable tabImage)
String getTabText()
void setTabText(String tabText)
String getTabColorString()
void setTabColorString(String tabColorString)
String getBadge()
void setBadge(String badge)
boolean isBadgeGiven()
```
 
available methods of Flarebar object are:

```java
setTabList(ArrayList<Flaretab> tabs)
ArrayList<Flaretab> getTabList()
setSelectedIndex(int selectedIndex) // to set the inital selected tab. using tabIndex.
getSelectedIndex()
attachTabs(Context ctx)
removeBadge(int tabIndex) // Used to remove the badge from tab. send tabIndex as argument.
void selectTab(int index) // Used to select tab of specified index programmatically.
void setTabChangedListener(TabEventObject.TabChangedListener tabChangedListener) // To attach listener
void setBarBackgroundColor(int color)
int getBarBackgroundColor()
void hideBar()
void showBar()
boolean isBarVisible()
```

Note: for more details, checkout the sample project.
This is initial release of Flarebar, future updates will make it more efficient.and also feel free to contribute.
