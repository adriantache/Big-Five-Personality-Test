# Big-Five-Personality-Test
_An Android app that tests your personality traits using the 50-item IPIP version of the Big Five Markers_

The assignment for Project 3 of the Google Android Basics Nanodegree by Udacity was to create a quiz app. I wanted to 
make it interesting again, so I decided to make a personality quiz using real world methodology. After a bit of searching
I decided to implement a personality test for the [Big Five Markers](https://en.wikipedia.org/wiki/Big_Five_personality_traits). 
A cursory search led me to the IPIP and their open source IPIP questions and scales, and I ended up recreating the title, 
instructions and questions from [this page](http://ipip.ori.org/New_IPIP-50-item-scale.htm) in order to keep the scoring accurate.

In order to show the results of the test I used a [GraphView](https://github.com/appsthatmatter/GraphView) so thanks to @appsthatmatter
for that functionality. 

**UPDATE 09/2019: Completely rewrote app in Kotlin and replaced the fixed layout with one that is programatically generated. There was also an issue with scoring that is now fixed. Added extra test variants (from @alheimsins) and improved the automatically generated results text. Still some improvements to be made so keep an eye on the commits.**

**Please try out the app and report any bugs.**

**Features I'd like to implement:** Please see the [Issues section](https://github.com/adriantache/Big-Five-Personality-Test/issues)

**Known issues:**
* app is using hardcoded strings – won't fix, as translating the app would probably skew the results due to using translations that
have not been tested by a psychologist
* ~~app looks weird on small DPIs (<xxhdpi) or large text sizes (>small)~~

**Screenshots:**

![Main Screen](https://github.com/adriantache/Big-Five-Personality-Test/blob/master/1.png) ![Results](https://github.com/adriantache/Big-Five-Personality-Test/blob/master/2.png)
