# 🚀 AlarmApp_Server

**AlarmApp_Server**는 알람 애플리케이션의 백엔드 서버로, 사용자의 알람 정보를 관리하고 설정된 시간에 푸시 알림을 보내는 역할을 합니다.

## 🛠️ 기술 스택
- **언어**: Java 17
- **프레임워크**: Spring Boot 3.3.2
- **데이터베이스**: MySQL 8.0.33
- **ORM**: JPA (Hibernate)
- **알림 시스템**: Firebase Cloud Messaging (FCM)

---

## 📌 주요 기능

### 1️⃣ 사용자 알람 관리
- 사용자가 설정한 알람 시간 등록/수정/삭제
- 알람 활성화/비활성화 기능 제공
- 특정 요일 반복 설정 지원

### 2️⃣ 알림 스케줄링 (Spring Batch)
- 등록된 알람 시간에 맞춰 푸시 알림 전송
- **Scheduled Task + Spring Batch** 기반 스케줄링
- 비동기 알람 처리 (멀티 스레드 지원)

### 3️⃣ 푸시 알림 (FCM)
- Firebase Cloud Messaging (FCM) 연동
- 앱으로 푸시 알림 전송
