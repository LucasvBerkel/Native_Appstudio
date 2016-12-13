# Names (4)
	All names are descriptive and use the appropriate naming conventions.
	

# Headers (1)
	There are no header comments

	IMPROVEMENT:
		Add header comments


# Comments (1)
	There are very few comments

	IMPROVEMENT: 
		Add comments in places where there is potential for problems to occur, or where the code is not self-explanatory


# Layout (3)
	The layout of the code is generally optimised for readability, with block of code doing the same task grouped together. There are a few instances of lines being to long, for instance in ListsActivity.java on line 53. SignInActivity.java has a double line break on line 71.

	IMPROVEMENT: 
		Check all source files for lines that significantly exceed 100 characters, and break them into two lines.

# Formatting (3)
	As mentioned above, the code is generally grouped by task. An exception to this is lines 52-68 in ListsActivity.java, where there is one block of code which performs multiple tasks, including defining two listeners.

	IMPROVEMENT:
		Add some line breaks and comments in this block of code to make it more readable.
	
# Flow (2)
	Generally the flow is simple. However, in the case of lines 56-109 of ListsActivity.java a listener is defined inside a listener, all inside the onCreate method for an activity. This results in code for parsing the response from the API being defined in the onCreate method, which is not at all intuitive.

	IMPROVEMENT: 
		Move the AdapterView.OnItemSelectedListener definition out of the onCreate method. This could be done by defining it in a different method in ListsActivity.java, or creating a new class which implements this listener.

	There is also some code duplication present, namely the code for constructing the API request url and for checking the response. This code, as well as constants such as the API key is present in ListsActivity.java, SearchActivity.java and GameInforActivity.java.

	IMPROVEMENT:
		I would suggest creating a class which is able to producte a JSONObject of the type required for the activity.
	
# Idiom (4)
	Idiom is fine
	
# Expressions (4)
	Expressions are fine

# Decomposition (3)
	The code suffers from some very long onCreate methods, but that has already been discussed above.
	
# Modularization (4)
	The code consists of clearly seperated modules with distinct tasks.
	
# General
	Generally the code is easy to understand, but it is clear that it has not yet had the care that goes with a hard deadline.
	
