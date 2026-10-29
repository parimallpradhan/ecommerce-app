
# Project: 🛒 E-Commerce Website Deployment (Jenkins + Tomcat + AWS EC2)

## Jenkins Freestyle Project: Build, Test, and Deploy a Java Web App to Tomcat

![Java E-Commerce CI/CD Pipeline](https://github.com/user-attachments/assets/06f91942-679d-45a4-82eb-24484a0f5a85)

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

<img width="1224" height="1285" alt="DevOps CI_CD Deployment Pipeline" src="https://github.com/user-attachments/assets/f709884f-d608-4ac7-b599-34cfce69c023" />






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

<img width="1871" height="743" alt="Launch EC2 instance" src="https://github.com/user-attachments/assets/1e24e090-ef39-4a7a-92ce-4a393345b9c4" />

**2. Select the instance type**

<img width="1178" height="345" alt="Select instance type" src="https://github.com/user-attachments/assets/4473c7a1-29df-426d-92fa-54a6f50ed249" />

**3. Open port 8080 (Jenkins and Tomcat) in the security group**

Also keep port 22 open for SSH.

<img width="1873" height="682" alt="Open port 8080" src="https://github.com/user-attachments/assets/49e0223a-28fc-42fc-ae81-5247ade06b36" />

---

## 5. Step 2: Set Up the Jenkins Server

SSH into the **Jenkins** EC2 instance.

### 2.1 Update the system

```bash
sudo apt update -y
```

<img width="1896" height="236" alt="apt update" src="https://github.com/user-attachments/assets/d0e42768-1989-4603-ba64-561627d15fb3" />

### 2.2 Install Java

```bash
sudo apt install fontconfig openjdk-21-jre -y
java -version
```

<img width="1917" height="256" alt="Java version" src="https://github.com/user-attachments/assets/7128211a-1d09-473e-878a-7eeb9425ec50" />

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

<img width="1861" height="337" alt="Jenkins version" src="https://github.com/user-attachments/assets/15a2f673-0132-4ae8-b502-eb7a3064a327" />

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

<img width="1892" height="352" alt="Maven version" src="https://github.com/user-attachments/assets/162d2d3b-107d-4033-bc6b-af336ab61255" />

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

<img width="1580" height="402" alt="Tomcat download page" src="https://github.com/user-attachments/assets/96aae47e-68b1-4812-ac50-4c0d738cd4ef" />

<img width="1611" height="145" alt="Copy tar.gz link" src="https://github.com/user-attachments/assets/0e7e5f1f-35c5-4387-8825-3cbfec4d1ec1" />

```bash
wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.123/bin/apache-tomcat-9.0.123.tar.gz
```

> Tomcat versions change over time. If this link is no longer available, use the current link from the download page.

<img width="1876" height="268" alt="wget Tomcat" src="https://github.com/user-attachments/assets/aab70a78-73ee-4ea5-8dc3-7924269ecfb4" />

### 3.3 Extract the archive

```bash
tar -xvzf apache-tomcat-9.0.123.tar.gz
```

<img width="1887" height="187" alt="Extract Tomcat" src="https://github.com/user-attachments/assets/e15d8f0c-f305-4a5f-824b-3a46a006aaa7" />

### 3.4 Start Tomcat

```bash
cd apache-tomcat-9.0.123/bin
./catalina.sh start
```

<img width="1882" height="443" alt="Start Tomcat" src="https://github.com/user-attachments/assets/36005d11-b1d7-4c39-bd7b-15f28e273af8" />

### 3.5 Verify Tomcat in the browser

Open `http://TOMCAT_PUBLIC_IP:8080` and confirm the Tomcat welcome page appears.

<img width="1732" height="642" alt="Tomcat welcome page" src="https://github.com/user-attachments/assets/84413143-b2d9-4382-aeb5-e54a1d01655a" />

---

## 7. Step 4: Create the Jenkins Freestyle Job

### 4.1 Create the job

1. Open the Jenkins dashboard.
2. Click **New Item**.

   <img width="1662" height="490" alt="New Item" src="https://github.com/user-attachments/assets/dce73bc5-2615-4b1d-b712-9cb10b34be4d" />

3. Enter the job name: `Java-Build-Test-Deploy`.
4. Select **Freestyle project**.
5. Click **OK**.

<img width="1875" height="565" alt="Freestyle project" src="https://github.com/user-attachments/assets/5ab54b62-1cb1-4ea7-b85b-e86dec840373" />

### 4.2 Configure Source Code Management

Under **Source Code Management**:

1. Select **Git**.
2. Enter your GitHub repository URL.
3. Set the branch, for example `*/main`.
4. Add credentials if the repository is private.

<img width="1838" height="542" alt="SCM Git" src="https://github.com/user-attachments/assets/2287e276-1a1d-43db-be63-e6053156731b" />

<img width="1802" height="382" alt="Git branch" src="https://github.com/user-attachments/assets/ed7b39bd-5155-4582-803a-aff21396bedc" />

Example repository URL:

```text
https://github.com/parimallpradhan/ecommerce-app.git
```

---

## 8. Step 5: Add Build and Test Steps

Add **two separate Execute shell build steps**. Separating them makes it easy to see which stage failed.

### 5.1 Build the application

Under **Build Steps** → **Add build step** → **Execute shell**:

<img width="1861" height="472" alt="Add build step" src="https://github.com/user-attachments/assets/859b6472-6c8c-48bb-bac9-285225ae7f0c" />

```bash
set -e

echo "Starting Maven build..."
mvn clean compile

echo "Build completed successfully"
```

<img width="1330" height="448" alt="Build step script" src="https://github.com/user-attachments/assets/b9039f3c-1bce-45ca-b591-d7b26b4a2d15" />

**What happens?**

- Maven removes previous build output.
- Java source files are compiled.
- If compilation fails, the step fails and Jenkins stops the job.

<img width="1308" height="438" alt="Build step result" src="https://github.com/user-attachments/assets/849a09b7-13b7-4251-8fd3-7f884ca73e35" />

### 5.2 Run tests and package the WAR

Add another **Execute shell** step:

```bash
set -e

echo "Running automated tests..."
mvn test

echo "Packaging WAR file..."
mvn package

echo "Checking generated WAR..."
ls -lh target/*.war
```

<img width="1860" height="703" alt="Test and package step" src="https://github.com/user-attachments/assets/e9923fbf-c2e8-4969-833b-37e1e350e62b" />

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

<img width="1895" height="482" alt="Plugins page" src="https://github.com/user-attachments/assets/7f861ccc-38e1-4600-96c3-eda5fdb6daf7" />

<img width="1897" height="481" alt="Install Publish Over SSH" src="https://github.com/user-attachments/assets/3a13de4b-015a-4551-a49e-195bfb115bc2" />

Restart Jenkins when the installation is complete and no jobs are running.

### 6.2 Add the Tomcat server

Go to **Manage Jenkins → System → Publish over SSH**.

<img width="1817" height="758" alt="Publish over SSH settings" src="https://github.com/user-attachments/assets/12f2eae8-a345-4d52-8c79-08f3e9cb4dd1" />

Click **SSH Servers → Add** and fill in:

| Field | Value |
|---|---|
| Name | `tomcat-server` |
| Hostname | Tomcat server public/private IP |
| Username | `ubuntu` |
| Remote Directory | Path to Tomcat's `webapps` folder |

<img width="1797" height="780" alt="Add SSH server" src="https://github.com/user-attachments/assets/8bc274c7-be0f-48f0-857c-1017b395da1b" />

Get the `webapps` path on the Tomcat server (run `pwd` inside the `webapps` folder) and paste it as the Remote Directory:

<img width="1917" height="321" alt="webapps path" src="https://github.com/user-attachments/assets/a1145b9c-d9f3-4e2c-a00d-9384bc4a6f56" />

Click **Advanced**, tick **Use password authentication, or use a different key**, and paste the contents of the Tomcat server's `.pem` private key:

<img width="1805" height="780" alt="Advanced key" src="https://github.com/user-attachments/assets/b8c4be84-253a-4e95-a463-ed233a30bede" />

Click **Test Configuration**. You should see `Success`.

<img width="1782" height="541" alt="Test configuration" src="https://github.com/user-attachments/assets/36f851ee-97ac-4b79-be5d-12a95e1a0796" />

---

## 10. Step 7: Add the Post-build Deploy Action

Open the job `Java-Build-Test-Deploy` → **Configure** → **Post-build Actions** → **Send build artifacts over SSH**.

<img width="1762" height="762" alt="Post-build action" src="https://github.com/user-attachments/assets/0c1c025c-9a35-4149-8bba-340e8f7caeec" />

Configure:

| Field | Value |
|---|---|
| SSH Server Name | `tomcat-server` |
| Source files | `target/*.war` |
| Remove prefix | `target` |
| Remote directory | *(leave empty to use the server's Remote Directory)* |

<img width="1912" height="743" alt="Send artifacts config" src="https://github.com/user-attachments/assets/c164ccb8-4fe6-4a07-90d8-07a14a9f2bdc" />

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

<img width="1861" height="148" alt="webapps listing" src="https://github.com/user-attachments/assets/782a814a-033e-461b-b7de-eee84ee02c57" />

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

<img width="1907" height="826" alt="Deployed application" src="https://github.com/user-attachments/assets/d75d5d95-ef43-4704-9a72-119c7b3e28cb" />

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


