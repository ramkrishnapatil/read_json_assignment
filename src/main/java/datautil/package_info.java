/*
 @startuml
    abstract class Datastore
    Datastore  <|-- User
    Datastore <|-- Ticket
    Datastore <|-- Organization
    Datastore "1" *-- "many" Data : contains
 @enduml

 @startuml
    participant  -> ZendeskSearchMenu : Input
    ZendeskSearchMenu  -> ZendeskSearchApp : forward search option
    ZendeskSearchApp  -> DataSearchUtility : Search datastore
    loop 2 (search entity and its related entity)
        ZendeskSearchApp  -> Datastore : Search(User/Ticket/Organization)
        Datastore  -> DataSearchUtility : search data
        DataSearchUtility  -> ZendeskSearchApp : return data
    end loop
 @enduml
*/
package datautil;