# Step 1: Use Tomcat as base image
FROM tomcat:9

# Step 2: Remove default apps (optional but good practice)
RUN rm -rf /usr/local/tomcat/webapps/*

# Step 3: Copy WAR file into Tomcat
COPY target/*.war /usr/local/tomcat/webapps/ecommerce-app.war

# Step 4: Expose port
EXPOSE 8080

# Step 5: Start Tomcat
CMD ["catalina.sh", "run"]
