App Deployment
--------------
1. Startup tomcat server
	
	/usr/local/apache-tomcat-8.0.32/startup.sh
2. Navigate to 
	   http://localhost:8080/java-refactoring-exercise-0.0.1-SNAPSHOT
3. Shutdown tomcat server

	/usr/local/apache-tomcat-8.0.32/shutdown.sh

Refactoring Changes
-------------------
**usermanagement/User.java**
   * Isolated processing of user data structure to the User class.

**usermanagement/UserDao.java**
   * Changed data structure for saved users from a list to a hash map, 
	 using email addresses as keys.
   * Changed error handling from exceptions to returning an error 
     indication which can then be handled at a higher level in a more
     user-friendly fashion.
   * Changed method arguments from User ojbect to individual fields
     (i.e., name, email & roles), to keep knowlege of User implementation 
     in User class.

**web/UserResource.java**
   * Changed to pass individual field values (i.e., name, email & roles)
     to UserDao class, keeping User implementation confined to User class.
   * Added check for number of roles, to implement required business logic.
   * Added processing of error conditions, such as duplicate user saves.

**test/unit/UserUnitTest.java**
   * New file to test User class.

**test/unit/UserDaoUnitTest.java**
   * Extensive changes and additions to test UserDao functionality.

**test/unit/UserResourceUnitTest.java**
   * Changed to support new UserDao interface.

**test/integration/UserIntegrationTest.java**
   * Changed to support new UserDao interface
   * Added delete user test.
