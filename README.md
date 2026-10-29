
# Project: Freestyle Project : 🛒 E-Commerce Website Deployment (Jenkins + Tomcat + AWS EC2)

<img width="1907" height="826" alt="Screenshot 2026-10-29 224440" src="https://github.com/user-attachments/assets/fe28a62d-a93e-48b8-a23c-5b240e5aa973" />

<img width="1655" height="950" alt="Java E-Commerce CI_CD Pipeline" src="https://github.com/user-attachments/assets/a5aa5fe4-8abe-4053-a52d-09d539b6ffdd" />


A hands-on CI/CD project that uses a **Jenkins Freestyle job** to pull a Java web application from GitHub, build and test it with Maven, package it as a WAR file, and deploy it automatically to an **Apache Tomcat** server using the **Publish Over SSH** plugin.

---

## Table of Contents

1. [Project Overview](#1-project-overview)
2. [Architecture](#2-architecture)
3. [Prerequisites](#3-prerequisites)
4. [Step 1: Create the EC2 Instances](#4-step-1-create-the-ec2-instances)
5. [Step 2: Set Up the Jenkins Server](#5-step-2-set-up-the-jenkins-server)
6. [Step 3: Set Up the Tomcat Server](#6-step-3-set-up-the-tomcat-server)
7. [Step 4: Create the Jenkins Freestyle Job](#7-step-4-create-the-jenkins-freestyle-job)
8. [Step 5: Add Build and Test Steps](#8-step-5-add-build-and-test-steps)
9. [Step 6: Configure Publish Over SSH](#9-step-6-configure-publish-over-ssh)
10. [Step 7: Add the Post-build Deploy Action](#10-step-7-add-the-post-build-deploy-action)
11. [Step 8: Run the Job and Verify](#11-step-8-run-the-job-and-verify)
12. [Troubleshooting](#12-troubleshooting)
13. [Key Learning Outcomes](#13-key-learning-outcomes)

---

## 1. Project Overview

Jenkins retrieves the source code from GitHub, compiles it with Maven, runs automated tests, creates a WAR file, and copies it to Tomcat over SSH.

### Objectives

- Retrieve application source code from GitHub
- Compile Java source code using Maven
- Run automated tests
- Package the application as a WAR file
- Transfer the WAR file to a Tomcat server
- Deploy the application and verify it is accessible

---

## 2. Architecture


<img width="1224" height="1285" alt="DevOps CI_CD Deployment Pipeline" src="https://github.com/user-attachments/assets/1b0e73ce-e5f1-47f8-b4fb-ce26f751f602" />





---

## 3. Prerequisites

| Component | Purpose |
|---|---|
| Git / GitHub | Stores and downloads the application source code |
| JDK | Compiles and runs Java code |
| Jenkins | Automates the build, test, and deployment workflow |
| Maven | Builds and tests the application |
| Publish Over SSH (plugin) | Sends the WAR file to the Tomcat server |
| Apache Tomcat | Runs the Java web application |
| Ubuntu EC2 instances | Host Jenkins and Tomcat |

Jenkins and Tomcat run on **two separate Ubuntu EC2 instances**:

| Server | Example hostname |
|---|---|
| Jenkins server | `jenkins-server` |
| Tomcat server | `tomcat-server` |

> Replace example hostnames, IP addresses, repository URLs, and paths with your own values.

> **Tomcat compatibility:** Older Java EE apps using `javax.servlet` need **Tomcat 9**. Apps using `jakarta.servlet` need Tomcat 10 or newer.

---

## 4. Step 1: Create the EC2 Instances

Create two Ubuntu EC2 instances: one for Jenkins and one for Tomcat.

**1. Launch an EC2 instance**

<img width="1871" height="743" alt="Screenshot 2026-10-29 211649" src="https://github.com/user-attachments/assets/096fbd21-1941-44e7-bf43-47904e1934fc" />


**2. Select the instance type**

<img width="1178" height="345" alt="Screenshot 2026-10-29 211852" src="https://github.com/user-attachments/assets/7d1da2c5-a6fc-4341-8692-d413fbb8d656" />


**3. Open port 8080 (Jenkins and Tomcat) in the security group**

Also keep port 22 open for SSH.

<img width="1873" height="682" alt="Screenshot 2026-10-29 212022" src="https://github.com/user-attachments/assets/daecb357-ffb6-4547-a58b-4ffe33f5aa7d" />

---

## 5. Step 2: Set Up the Jenkins Server

SSH into the **Jenkins** EC2 instance.

### 2.1 Update the system

```bash
sudo apt update -y
```

<img width="1896" height="236" alt="Screenshot 2026-10-29 213913" src="https://github.com/user-attachments/assets/2ad4d003-eddc-4fd9-b3b1-b3bb8cdea054" />


### 2.2 Install Java

```bash
sudo apt install fontconfig openjdk-21-jre -y
java -version
```
<img width="1917" height="256" alt="Screenshot 2026-10-29 214300" src="https://github.com/user-attachments/assets/c1d87ee1-681f-416b-a32e-44a62c714d7e" />


### 2.3 Install Jenkins

```bash
sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
  https://pkg.jenkins.io/debian-stable/jenkins.io-2026.key

echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc] https://pkg.jenkins.io/debian-stable binary/" | \
  sudo tee /etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt update
sudo apt install jenkins -y
jenkins --version
```

<img width="1861" height="337" alt="Screenshot 2026-10-29 214348" src="https://github.com/user-attachments/assets/7adef208-1372-4697-ad95-79dddb880a7a" />


Start Jenkins and get the initial admin password:

```bash
sudo systemctl enable --now jenkins
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

Open `http://JENKINS_PUBLIC_IP:8080`, paste the password, install the suggested plugins, and create your admin user.

### 2.4 Install Maven

```bash
sudo apt install maven -y
mvn --version
```

<img width="1892" height="352" alt="Screenshot 2026-10-29 214538" src="https://github.com/user-attachments/assets/51880a19-e391-4a4d-a2a9-3bb24df12e50" />


---

## 6. Step 3: Set Up the Tomcat Server

SSH into the **Tomcat** EC2 instance.

### 3.1 Install Java

```bash
sudo apt update -y
sudo apt install openjdk-21-jre -y
java -version
```

### 3.2 Download Tomcat

Go to the Apache Tomcat download page and copy the link address of the **tar.gz** file.

<img width="1580" height="402" alt="Screenshot 2026-10-29 214931" src="https://github.com/user-attachments/assets/d0128d0e-5f95-4d03-9cea-c565c2af9882" />

<img width="1611" height="145" alt="Screenshot 2026-10-29 215005" src="https://github.com/user-attachments/assets/635969a8-0411-405d-8e68-b16daabd14b8" />



```bash
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.123/bin/apache-tomcat-9.0.123.tar.gz
```

> Tomcat versions change over time. If this link is no longer available, use the current link from the download page.

<img width="1876" height="268" alt="Screenshot 2026-10-29 215202" src="https://github.com/user-attachments/assets/eac31c28-83af-4083-9b2e-2f07856bba2a" />


### 3.3 Extract the archive

```bash
tar -xvzf apache-tomcat-9.0.123.tar.gz
```

<img width="1887" height="187" alt="Screenshot 2026-10-29 215420" src="https://github.com/user-attachments/assets/d8e16b95-5845-4b99-bc9b-b6262ff0cda9" />


### 3.4 Start Tomcat

```bash
cd apache-tomcat-9.0.123/bin
./catalina.sh start
```

<img width="1882" height="443" alt="Screenshot 2026-10-29 215916" src="https://github.com/user-attachments/assets/250f2abf-08f5-4f39-b00d-b7fe065a6e0f" />


### 3.5 Verify Tomcat in the browser

Open `http://TOMCAT_PUBLIC_IP:8080` and confirm the Tomcat welcome page appears.

<img width="1732" height="642" alt="Screenshot 2026-10-29 220051" src="https://github.com/user-attachments/assets/a3793c47-d2cf-4997-9b2e-ff6131bb545b" />


---

## 7. Step 4: Create the Jenkins Freestyle Job

### 4.1 Create the job

1. Open the Jenkins dashboard.
2. Click **New Item**.

<img width="1662" height="490" alt="Screenshot 2026-10-29 220651" src="https://github.com/user-attachments/assets/01cb9815-1cd6-458a-a9f0-912be9530c2f" />


3. Enter the job name: `Java-Build-Test-Deploy`.
4. Select **Freestyle project**.
5. Click **OK**.

<img width="1875" height="565" alt="Screenshot 2026-10-29 220757" src="https://github.com/user-attachments/assets/0f960dc4-032c-4783-878b-f68be29725ec" />


### 4.2 Configure Source Code Management

Under **Source Code Management**:

1. Select **Git**.
2. Enter your GitHub repository URL.
3. Set the branch, for example `*/main`.
4. Add credentials if the repository is private.

<img width="1838" height="542" alt="Screenshot 2026-10-29 220937" src="https://github.com/user-attachments/assets/ba202a88-6efe-43e6-8c76-0a9eb1cb4ea9" />

<img width="1802" height="382" alt="Screenshot 2026-10-29 221009" src="https://github.com/user-attachments/assets/260d6190-b160-4282-8af5-0116bc646753" />

Example repository URL:

```text
https://github.com/parimallpradhan/ecommerce-app.git
```

---

## 8. Step 5: Add Build and Test Steps

Add **two separate Execute shell build steps**. Separating them makes it easy to see which stage failed.

### 5.1 Build the application

Under **Build Steps** → **Add build step** → **Execute shell**:

<img width="1861" height="472" alt="Screenshot 2026-10-29 221056" src="https://github.com/user-attachments/assets/b9ee2b7a-bc65-43b2-affe-dec76ad9dc82" />


```bash


echo "Starting Maven build..."
mvn clean compile

echo "Build completed successfully"
```

<img width="1330" height="448" alt="Screenshot 2026-10-29 221217" src="https://github.com/user-attachments/assets/3fdd18db-f677-4011-9fb3-b5a079709a7c" />


**What happens?**

- Maven removes previous build output.
- Java source files are compiled.
- If compilation fails, the step fails and Jenkins stops the job.



### 5.2 Run tests and package the WAR

Add another **Execute shell** step:

```bash


echo "Running automated tests..."
mvn test

echo "Packaging WAR file..."
mvn package

echo "Checking generated WAR..."
ls -lh target/*.war
```

<img width="1308" height="438" alt="Screenshot 2026-10-29 221627" src="https://github.com/user-attachments/assets/2f2d3de2-6a15-4d9b-b1a8-468ae48f3473" />


**What happens?**

- Maven runs the project's automated tests.
- If tests fail, Jenkins stops before deployment.
- If tests pass, Maven packages the app into a WAR in the `target/` directory.

Example output file:

```text
target/ecommerce-app.war
```

> **Important:** The project must use WAR packaging (`<packaging>war</packaging>` in `pom.xml`). If it produces a JAR instead, adjust the deployment settings.

---

## 9. Step 6: Configure Publish Over SSH

### 6.1 Install the plugin

Go to **Jenkins Dashboard → Manage Jenkins → Plugins → Available plugins**, search for **Publish Over SSH**, and install it.

<img width="1860" height="703" alt="Screenshot 2026-10-29 221932" src="https://github.com/user-attachments/assets/a2962245-9f33-4d16-a9e5-4e63ade73eff" />

<img width="1895" height="482" alt="Screenshot 2026-10-29 221953" src="https://github.com/user-attachments/assets/0aa201d0-55b6-49d0-b10e-0db0db9d6e53" />

<img width="1897" height="481" alt="Screenshot 2026-10-29 222033" src="https://github.com/user-attachments/assets/ea661435-f84e-41d1-afb7-33e31f6349ea" />

Restart Jenkins when the installation is complete and no jobs are running.

### 6.2 Add the Tomcat server

Go to **Manage Jenkins → System → Publish over SSH**.

<img width="1817" height="758" alt="Screenshot 2026-10-29 222406" src="https://github.com/user-attachments/assets/11552e8c-6ec8-44c7-ba6a-19f8ca427bd0" />


Click **SSH Servers → Add** and fill in:

| Field | Value |
|---|---|
| Name | `tomcat-server` |
| Hostname | Tomcat server public/private IP |
| Username | `ubuntu` |
| Remote Directory | Path to Tomcat's `webapps` folder |

<img width="1797" height="780" alt="Screenshot 2026-10-29 222604" src="https://github.com/user-attachments/assets/cacf3e83-da3d-4ea6-bc85-dd6f711d7016" />


Get the `webapps` path on the Tomcat server (run `pwd` inside the `webapps` folder) and paste it as the Remote Directory:

<img width="1917" height="321" alt="Screenshot 2026-10-29 222618" src="https://github.com/user-attachments/assets/402caa16-0de7-4665-afc3-90a94729595c" />


Click **Advanced**, tick **Use password authentication, or use a different key**, and paste the contents of the Tomcat server's `.pem` private key:

<img width="1805" height="780" alt="Screenshot 2026-10-29 222912" src="https://github.com/user-attachments/assets/6be77c35-f0a1-4ff3-bca6-fb0a07ecf36f" />


Click **Test Configuration**. You should see `Success`.

<img width="1782" height="541" alt="Screenshot 2026-10-29 222934" src="https://github.com/user-attachments/assets/cc953208-4762-42f4-80e8-ce2b9a0dbc61" />


---

## 10. Step 7: Add the Post-build Deploy Action

Open the job `Java-Build-Test-Deploy` → **Configure** → **Post-build Actions** → **Send build artifacts over SSH**.

<img width="1762" height="762" alt="Screenshot 2026-10-29 223146" src="https://github.com/user-attachments/assets/7650041e-5c57-4e21-983b-ad2721132ceb" />


Configure:

| Field | Value |
|---|---|
| SSH Server Name | `tomcat-server` |
| Source files | `target/*.war` |
| Remove prefix | `target` |
| Remote directory | *(leave empty to use the server's Remote Directory)* |

<img width="1912" height="743" alt="Screenshot 2026-10-29 223303" src="https://github.com/user-attachments/assets/f2c9bc0b-965e-4d8f-a3e2-9f0a846d42b8" />


Click **Save**.

---

## 11. Step 8: Run the Job and Verify

### 8.1 Build the job

Click **Build Now** and open **Console Output**. A successful run ends with `Finished: SUCCESS`.

### 8.2 Check the deployed WAR on Tomcat

On the Tomcat server:

```bash
ls -lh apache-tomcat-9.0.123/webapps/
```

<img width="1861" height="148" alt="Screenshot 2026-10-29 223400" src="https://github.com/user-attachments/assets/61374151-b52a-4d05-8dfe-6c1c4291d33b" />

You should see:

```text
ecommerce-app.war
ecommerce-app/        <- appears once Tomcat unpacks the WAR
```

### 8.3 Check Tomcat is running and read the logs

Because Tomcat was installed manually from the tarball (not as a system service):

```bash
ps -ef | grep tomcat
tail -n 100 apache-tomcat-9.0.123/logs/catalina.out
```

### 8.4 Open the application

```text
http://TOMCAT_PUBLIC_IP:8080/ecommerce-app/
```

<img width="1890" height="813" alt="Screenshot 2026-10-29 224422" src="https://github.com/user-attachments/assets/51906687-cffa-4c3d-b99e-79b1461bb9fa" />


Replace `TOMCAT_PUBLIC_IP` with your Tomcat server's public IP. Make sure the Tomcat security group allows inbound traffic on port `8080`.

---

## 12. Troubleshooting

| Problem | What to check |
|---|---|
| `mvn: command not found` | Install Maven on the Jenkins server and check `PATH`. |
| Java compilation failure | Check the JDK version and Maven compiler configuration. |
| Tests fail | Review Maven test output and fix the failing tests. |
| `Permission denied (publickey)` | Check the SSH key, username, `authorized_keys`, and file permissions. |
| SSH connection refused / timeout | Check the SSH service, network connectivity, and security group rules (port 22). |
| WAR file not found | Check the Maven packaging type and the `target/` directory. |
| WAR not deployed in Tomcat | Check the Remote Directory path, folder permissions, and `catalina.out`. |
| Application not accessible | Check Tomcat is running, the context path, port `8080`, and security group rules. |

---

## 13. Final Pipeline Flow

```text
Developer pushes code to GitHub
              |
              v
     Jenkins Freestyle Job
              |
              v
     Build:   mvn clean compile
              |
              v
     Test:    mvn test
              |
              v
     Package: mvn package  -->  WAR file
              |
              v
     Publish Over SSH  -->  Tomcat webapps/
              |
              v
     Verify in the browser
```

---

## 14. Key Learning Outcomes

After completing this project, you will be able to:

- Configure a Jenkins Freestyle project
- Integrate Jenkins with a GitHub repository
- Build a Java application using Maven
- Run automated tests before deployment
- Package a Java web application as a WAR file
- Configure SSH key-based access from Jenkins to Tomcat
- Deploy an application using the Publish Over SSH plugin
- Read Jenkins console output and troubleshoot deployment issues

---

## Author

**Parimal Pradhan**


