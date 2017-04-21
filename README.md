# biblio
Bibliography managment system is a Java/Spring framework web application that allows display, management and storage of bibliography entries in BibTeX format. Current configuration uses an embedded HSQL database and embedded Tomcat instance for the web server. 

Features include:

- article entries 
- fields - author, title, year, journal, bibtexkey, pages, volume, number
- list of all bibliographies
- add 
- update
- delete
- search by title
- import articles from local bibtex file biblio-import.bib
- export articles to local file biblio-export.bib
- check for duplicates

Build and run from command line or from Eclipse

- from command line - from within pom.xml directory

mvn tomcat:run

- from Eclipse 

import as maven project:

File->Import->Existing Maven Projects->Root Directory->[ location of pom.xml ]

to build and run:

create a new Maven build/Run configuration with 

right click on project->Run As->Run Configurations->Maven Build->right click->New

Name: biblio 

Base Directory: ${workspace_loc:/project-biblio} 

Goals: clean tomcat7:run 

- in browser go to http://localhost:8080/project-biblio

