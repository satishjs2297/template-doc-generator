@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;lib/ctsu-design-template-generator-1.0.jar

ECHO Choose any below option, for design document. & echo.
ECHO ***************************  Begin  *******************************

ECHO                            1 - SERVICE Design Document& echo.
ECHO                            2 - UI Design Document & echo.
ECHO                            3 - DB Design Document & echo.
:RETRY 
SET /P option=Please enter your choice: 
IF "%option%"== "1" (
  ECHO Service Design Document Generation is in progress.
  java -jar -Xms128m -Xmx1024m lib/ctsu-design-template-generator-1.0.jar --spring.config.location=file:./config/application.yml,file:./config/application-service.yml
  GOTO End
)
IF "%option%"== "2" (
  ECHO UI Design Document Generation is in progress.
  java -jar -Xms128m -Xmx1024m lib/ctsu-design-template-generator-1.0.jar --spring.config.location=file:./config/application.yml,file:./config/application-ui.yml
  GOTO End
)
IF "%option%"== "3" (
  ECHO DB Design Document Generation is in progress.
  java -jar -Xms128m -Xmx1024m lib/ctsu-design-template-generator-1.0.jar --spring.config.location=file:./config/application.yml,file:./config/application-db.yml
  GOTO End
) 
ECHO Invalid Selections. Please re-try.
GOTO RETRY
:End
ECHO ***************************  End  ********************************
