# 🚀 Job Portal Web Application

mhmm hfoy jacu pamv

A full-stack **Job Portal Application** built using modern technologies to connect **job seekers** and **recruiters** with a seamless experience.

✨ Features a unique **Anti-Gravity UI Design** for a modern and visually appealing interface.

---

## 🌟 Features

### 👤 Candidate Features
- 🔍 Search and explore jobs
- 📄 Apply with resume upload
- 📊 Track application status (Pending / Accepted / Rejected)
- 👤 Manage profile

---

### 🏢 Recruiter Features
- ➕ Post job listings
- 📋 View applicants
- ✅ Accept / ❌ Reject candidates
- 📊 Manage job postings

---

### 🛠️ Admin Features
- 🔐 Recruiter verification system
- 📊 Dashboard analytics
- 👥 Manage users

---

## 🎨 UI Highlights

- ✨ Anti-Gravity modern UI
- 🌈 Glassmorphism & glowing effects
- 📱 Fully responsive design
- 🎯 Clean and intuitive interface

---

## 🧰 Tech Stack

### 💻 Frontend
- HTML5  
- CSS3  
- JavaScript  

### ⚙️ Backend
- Java  
- Spring Boot  
- Spring MVC  
- Spring Data JPA  

### 🗄️ Database
- MongoDB  

### 🔧 Tools & Technologies
- Git & GitHub  
- REST APIs  
- Thymeleaf  

---

## 📂 Project Structure

Job-Portal/
│
├── src/
│ ├── main/
│ │ ├── java/ # Backend code
│ │ ├── resources/ # Config & templates
│
├── static/ # CSS, JS, Images
├── templates/ # Thymeleaf HTML
│
├── uploads/ # General uploaded files
├── profilePic/ # User profile images
├── companyKyc/ # Recruiter verification documents
│
└── README.md

---

## ⚙️ Installation & Setup

### 🔹 1. Clone Repository
```bash
git clone https://github.com/neharajput591/Job-Portal.git
cd Job-Portal

### 🔹 2. Configure MongoDB

Ensure MongoDB is running and update the following property:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/jobportaldb

### 🔹 3. Configure File Upload Paths

file.upload-dir=uploads/
file.profile-dir=profilePic/
file.kyc-dir=companyKyc/

### 🔹 4. Run Application
mvn spring-boot:run

### 🔹 5. Open in Browser

http://localhost:8080

🔐 Authentication & Roles

| Role      | Access             |
| --------- | ------------------ |
| Candidate | Apply for jobs     |
| Recruiter | Post & manage jobs |
| Admin     | Verify recruiters  |

🚧 Future Enhancements

-> 📧 Email notifications

-> 🔔 Real-time updates

-> 🤖 AI-based job recommendations

-> 📱 Mobile app version

🤝 Contributing

1. Contributions are welcome!

2. Fork the repository

3. Create a new branch

4. Commit changes

5. Push and create a Pull Request

📄 License

This project is for educational purposes.

🙋‍♀️ Author

Neha Rajput
💻 Full Stack Java Developer

⭐ Show Your Support

If you like this project:

🌟 Star this repository
🍴 Fork it
📢 Share it
