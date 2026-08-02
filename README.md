
# Project: 🛒 E-Commerce Website Deployment (Jenkins + Tomcat + AWS EC2)



<img width="940" height="352" alt="image" src="https://github.com/user-attachments/assets/a381c33f-fd6e-43a7-ab9e-630006180b5f" />


<img width="940" height="472" alt="image" src="https://github.com/user-attachments/assets/f02b7095-b794-41f7-9227-fb4bb26be17b" />



This project demonstrates how to deploy a Java-based e-commerce web application using:

* ⚙️ Jenkins (CI/CD)
* ☁️ AWS EC2
* 🐱 Apache Tomcat
* ☕ Java (OpenJDK 21)
* 📦 Maven

---

# 🚀 Project Architecture

* **Jenkins Server** → Build & Deploy (CI/CD)
* **Tomcat Server** → Hosts the application

---

# 🧩 Step-by-Step Setup Guide

---

## 🔹 1. Launch EC2 Instances

* Launch **2 EC2 Ubuntu instances**

  * Instance 1 → Jenkins Server
  * Instance 2 → Tomcat Server
* Open required ports:

  * `8080` → Tomcat
  * `8080 / 8081` → Jenkins (default: 8080)

---

## 🔹 2. Install Java & Jenkins (On Jenkins Server)

```bash
sudo apt update
sudo apt install fontconfig openjdk-21-jre -y
java -version
```

### Install Jenkins

```bash
sudo wget -O /etc/apt/keyrings/jenkins-keyring.asc \
https://pkg.jenkins.io/debian-stable/jenkins.io-2026.key

echo "deb [signed-by=/etc/apt/keyrings/jenkins-keyring.asc]" \
https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
/etc/apt/sources.list.d/jenkins.list > /dev/null

sudo apt update
sudo apt install jenkins -y
```

---

## 🔹 3. Access Jenkins

* Open browser:

```
http://<JENKINS_PUBLIC_IP>:8080
```

---

## 🔹 4. Unlock Jenkins

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword
```

* Paste password in browser

---

## 🔹 5. Install Plugins

* Click: **Install Suggested Plugins**

---

## 🔹 6. Create Admin User

* Enter username, password, email
* Click **Save & Continue**

---

## 🔹 7. Jenkins Ready ✅

---

# 🐱 Setup Tomcat Server

---

## 🔹 8. Download Tomcat

* Search: *Tomcat download*
* Copy `.tar.gz` link

---

## 🔹 9. Install Java (Tomcat Server)

```bash
sudo apt update
sudo apt install fontconfig openjdk-21-jre -y
java -version
```

---

## 🔹 10. Download & Extract Tomcat

```bash
wget <TOMCAT_DOWNLOAD_LINK>

ls
tar -xvzf apache-tomcat-9.0.120.tar.gz
```

---

## 🔹 11. Start Tomcat

```bash
cd apache-tomcat-9.0.120/bin
./startup.sh
```

---

## 🔹 12. Access Tomcat

```
http://<TOMCAT_PUBLIC_IP>:8080
```

---

# ⚙️ Jenkins Configuration

---

## 🔹 13. Install Required Plugins

Go to:

**Manage Jenkins → Plugins**

Install:

* Pipeline: Stage View
* Maven Integration
* SSH Agent

---

## 🔹 14. Configure Maven

**Manage Jenkins → Tools → Maven**

* Click **Add Maven**
* Name: `maven3`
* Save

---

## 🔹 15. Configure SSH Credentials

**Manage Jenkins → Credentials**

* Add Credentials:

  * Kind: SSH Username with Private Key
  * ID: `tomcat-ssh-key`
  * Username: `ubuntu`
  * Private Key: Paste `.pem` content

---

# 🔁 Create CI/CD Pipeline

---

## 🔹 16. Create Pipeline Job

* Click **New Item**
* Select **Pipeline**
* Enter job name

---

## 🔹 17. Configure Pipeline from Git

* Select: **Pipeline script from SCM**
* Add:

  * Git URL
  * Branch (`main`)

---

## 🔹 18. Jenkinsfile (Pipeline Script)

```groovy
pipeline {
    agent any

    tools {
        maven 'maven3'
    }

    environment {
        TOMCAT_IP = "YOUR_TOMCAT_IP"
        APP_NAME = "ecommerce-app"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/parimallpradhan/ecommerce-app.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sshagent(['tomcat-ssh-key']) {

                    sh """
                    scp -o StrictHostKeyChecking=no target/${APP_NAME}.war ubuntu@${TOMCAT_IP}:/home/ubuntu/apache-tomcat-9.0.120/webapps/

                    ssh ubuntu@${TOMCAT_IP} '
                    cd /home/ubuntu/apache-tomcat-9.0.120/bin &&
                    ./shutdown.sh || true &&
                    ./startup.sh
                    '
                    """
                }
            }
        }
    }
}
```

---

## 🔹 19. Build & Deploy

* Click **Build Now**
* Jenkins will:

  * Pull code
  * Build WAR file
  * Deploy to Tomcat

---

# 🌐 Final Application URL

```
http://<TOMCAT_PUBLIC_IP>:8080/ecommerce-app/
```

---

# 🔥 Optional Enhancements

* ✅ Add Webhook (auto deploy on git push)
* ✅ Add HTTPS (SSL)
* ✅ Use Docker instead of Tomcat
* ✅ Add Database (MySQL)

---

# 📌 Summary

✔ Automated CI/CD using Jenkins
✔ Deployed Java app on Tomcat
✔ Used AWS EC2 for hosting
✔ Secure deployment using SSH

---

