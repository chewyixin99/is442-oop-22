## Running the application

1. Install [NodeJS](https://nodejs.org/en/)
2. Open Command Prompt in Windows and type `$ npm install -g @vue/cli`
3. Navigate to the 'frontend' directory by typing `$ cd frontend` in the terminal
4. In the terminal, type `$ npm i` if it's your first time running the application, followed by `$ npm run serve`

---

## For developers

### (Required) Setting up the database

1. Make sure to run `createTables.sql` located in `backend/oop/database` before testing 
    - local database is hosted on port: 8081
2. Alternatively, connect to the deployed database [here](https://spring-boot-oop.herokuapp.com/) (Link: https://spring-boot-oop.herokuapp.com/)
3. To switch between local database and heroku database:
    - navigate to file `frontend/src/constants.js`
    - comment and uncomment accordingly dpeending on which database to connect to
### (Required) Setting up Spring-Boot server

1. Download [Apache Maven](https://maven.apache.org/download.cgi), unzip the folder to a meaningful directory then open 'Environment Variables' in Windows.
2.  Inside 'User Variables' tab, select 'New', browse and select the unzipped Apache Maven bin folder, add the 'Variable name' as MAVEN_HOME.
3. Inside 'User Variables' tab, select 'New', browse and select the folder where you installed 'jdk-17.x.x.x', add the 'Variable name' as JAVA_HOME.
4. Restart your PC.
5. Navigate to the backend directory e.g., `$ cd backend/oop` where the 'mvnw' file is located.
6. Make sure Maven is installed first before running the following command
    
    `mvn spring-boot:run`
### (Optional) Enable live reload (Spring-Boot)

1. Download the [Chrome Extension for Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related?hl=en) to enable live reload within the browser (without needing to click refresh)

### (Required) Setting up the Vue frontend application

1. Install NodeJS(https://nodejs.org/en/)
2. Open Command Prompt in Windows and type `$ npm install -g @vue/cli`
3. Navigate to the 'frontend' directory e.g., `$ cd frontend`
4. run `$ npm i` if it's the first time running followed by `$ npm run serve`
