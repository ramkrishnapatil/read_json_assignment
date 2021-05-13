# read_json_assignment
Zendesk Search (Command Line Application)
Run the program using gradlew as below from command line:
Use the branch **develop**
* ./gradlew run --console=plain

Libraries and Tools used
* Using java 1.8+ 
* Gradle
* Jackson JSON Processor(1.12+)
* JUnit 4
* Used Intellij IDE for development
* Chosen Jackson library over Gson because if the number of records increase then Jackson performs better
* PlantUML is used for basic class/sequence diagram(package_info.java)

Assumption:
* Don't know the relation between ticket and users so not searched the related entity
* _id will exist for all the records. If _id does not exist the application will just ignore the record
* If the field is list e.g. tags then List contains is used rather than exact match

Improvements:
* While searching, if user provides wrong field name then searchable fields are displayed and 
user has been asked to provide the correct field name
* Implementation of ConfigFieldsUtil.
  This gives the flexibility to configure the fields to be displayed from related datastore


Class information:

Data:
* The entity class which will contain one record and stored in Map

Datastore:
* The abstract class to hold the entities of the data type e.g. Organization/User/Ticket
* The entities are stored by assuming the *_id* will always exist.
* The fields are stored as key-value pairs.
* This is very useful, if the fields get added/removed from the json records, code is not required to change

PrintUtil:
* The class is used to print the data.
* This is wrapper to System.print.
* It will make easy to re-direct the output if the requirement changes in future

DataSearchUtility:
* This is used to search the *Datastore* for matching key and its matching value.
* The advantage is generic class to search the *Datastore*
* If the field is list e.g. tags then List contains is used
* Provides Set of searchable fields for the datastore
* The searchable fields are not hard coded but dynamically created from the JDON data 

ConfigFieldsUtil:
* This class provides the configuration of fields to be included from the related entities.
* Assume searching organization by id matches tickets and users
* Using this file user can configure which fields to be displayed from matching Tickets/Users.
* e.g. User can configure Tickets for only name field but name, alias from Users.
* If configuration not defined then all the fields from matching entities will be displayed

ResourceLoader:
The class is used to read the json files and deserialize data into Java Object

ZendeskSearchMenu:
* This class prompts user for input and delegate the input to ZendeskSearchApp

ZendeskSearchApp:
* According to user option searches the Datastore and prints the results

Improvement:
* Improve the JUnit tests
* Implement LRU cache to improve the search performance
* Provide data print order option to user e.g. Ascending/Descending
* Provide AND/OR criteria for searching
* Implement Builder design pattern to include the results from related entity