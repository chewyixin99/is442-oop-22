## Enable live reload
Download the [Chrome Extension for Live Reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei/related?hl=en) to enable live reload within the browser (without needing to click refresh)

## run command
- Backend (Springboot) 

    Download Apache Maven(https://maven.apache.org/download.cgi), unzip the folder to a meaningful directory then open 'Environment Variables' in Windows.
    - Inside 'User Variables' tab, select 'New', browse and select the unzipped Apache Maven bin folder, add the 'Variable name' as MAVEN_HOME.
    - Inside 'User Variables' tab, select 'New', browse and select the folder where you installed 'jdk-17.x.x.x', add the 'Variable name' as JAVA_HOME.
    - Restart your PC.

    Navigate to the backend directory e.g., `cd backend/oop` where the 'mvnw' file is located.

    Make sure Maven is installed first before running the following command
    > mvn spring-boot:run

    (DATABASE) Make sure to run `createTables.sql` located in `backend/oop/database` before testing 

    - To edit host files to enable connection to 'auth-server'

        - https://phoenixnap.com/kb/how-to-edit-hosts-file-in-windows-mac-or-linux#ftoc-heading-8
        - add `127.0.0.1 auth-server` under localhost
    
    - Make sure that application-mail.yml is configured properly

    - For auth to work properly, we have to start the auth-server first
        
        - `cd oauth-authorization-server` followed by `mvn spring-boot:run`
        - `cd oop` followed by `mvn spring-boot:run`

- Frontend (Vue)
    
    Install NodeJS(https://nodejs.org/en/)

    Open Command Prompt in Windows and type `npm install -g @vue/cli`

    Navigate to the 'frontend' directory e.g., `cd frontend`
    
    run `npm i` if it's the first time running then
    > npm run serve
