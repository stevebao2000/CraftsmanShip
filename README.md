# CraftsmanShip
About this project:
Android Studio 4.2.2, Kotlin 1.5.21, viewBinding.
Coroutine, Gson versions are in the dependencies of build.gradle file.

I use Android Studio open the project. Build->rebuild, then run the app.
It will start with a progress bar. When the max sum calculation finished,
the progress bar will hide and other ui will show up. 
Click any of driver's name, the shipping address will show in light yellow box and
the driver's name will show in light blue box.

Four major steps in the project:

1. read json file and convert it to data class (use Gson)
	The ShipmentsData is generated by a tool in Android Studio from json file.
	This step is quite simple with the help of Gson. If the json file is from the 
	RESTful service, then we need add proper service and repository. You can refer to
	my other project in this account if you want to see the implementation.
	
2. For each shipment, calculate suitability score for each driver
	So we get suitability score matrix.
	
3. Calculate bigest sum for the matrix for the above result (only select 1 row for 1 column)
	This could be the hardist part of the project if I do not borrow open source code
	from internet. It is Hangarian Algorithm. I get one from StackOverflow. The original
	code only return the max sum. I made some changes to get the track of the mapping from 
	drivers to shipments.
	As the calculation is quite heavy, I used Coroutine to put the calculation on (default)
	heavy calculation thread.
	
4. Show the result either in logcat or on screen.
	First, I print the max sum and the mapping for the index of drivers and shipments in logcat.
	Then, I used recyclerView to display the drivers (Can use listview too, as we only have 10 records).
	A OnItemClickListener callback is added to implement onClick on driver's name.
	
	Finally, rotating screen will preserve data. But the UI looks not great. 