FROM tomcat:8.5-jdk8-openjdk
WORKDIR $CATALINA_HOME
ENV Project1_UName postgres
ENV Project1_UPassword Cavalier93!
ENV Project1_URL jdbc:postgresql://database-1.cdr3hmlqxdcv.us-east-2.rds.amazonaws.com:5432/project1
ENV inactivityTimeoutMinutes 5
ENV cookieDurationMinutes 30
ENV numberGamesPerWeekendDay 4
ENV gameTime1 10:00:00
ENV gameTime2 12:00:00
ENV gameTime3 14:00:00
ENV gameTime4 16:00:00
ARG WAR_FILE=./target/*.war
COPY $WAR_FILE ./webapps
ARG ConfigFile=./config.cfg
COPY $ConfigFile ./webapps
EXPOSE 8080
CMD ["catalina.sh", "run"]