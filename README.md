# <img src="https://github.com/TeamDilly/Packy-Server/assets/65899774/ebf6bb49-d870-4fad-b685-ec280fb7e5ca" align="left" width="100"> 패키(Packy), 마음으로 채우는 특별한 선물박스 앱 🎁
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FTeamDilly%2FPacky-Server&count_bg=%235744E3&title_bg=%23000000&icon=&icon_color=%23FFFFFF&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
[![codecov](https://codecov.io/gh/TeamDilly/Packy-Server/branch/develop/graph/badge.svg?token=389TQONAR9)](https://codecov.io/gh/TeamDilly/Packy-Server)

기프티콘으로 가득한 세상, 패키는 더욱 특별한 온라인 선물문화를 만들어요. <br>
온라인 상에서 선물이 아니었던 것도, 패키와 함께라면 선물이 돼요! <br>

<div align="center">
    <img src="https://github.com/TeamDilly/Packy-Server/assets/65899774/d72788bc-693f-4bfa-bdb6-f47f9aec9073" alt="앱 실행 화면">
</div>


### 🔗 서비스 링크
- iOS : https://m.site.naver.com/1jrUo <br>
- AOS : https://m.site.naver.com/1jrUu <br>
- 피드백 구글폼 링크 : https://m.site.naver.com/1jrV5

# 🖥️ Tech
## 🏛️ Architecture
<div align="center">
<img width="800" alt="image" src="https://github.com/TeamDilly/Packy-Server/assets/65899774/b3b8caa3-0a32-43bc-81e2-e2180d3cb60f">
</div>

## 📚 Multi Module
역할과 책임에 따라 모듈을 분리하여 의존도를 낮추고, 서비스 확장 시 독립적인 확장이 가능한 구조로 설계하였습니다.
```
├── packy-api                    # 모바일 클라이언트와 통신하는 API 모듈
├── packy-domain                 # 도메인 및 데이터베이스 관련 모듈
├── packy-infra                  # 외부 API 연동 모듈
├── packy-support                # 부가 로직 모듈 (로깅)
└── packy-common                 # 공통으로 사용하는 기능을 전역적으로 제공하는 모듈
```

## 🛠️ Tech Stack
<div align="left">
<table>
    <tr>
        <td>Language & Framework</td>
        <td>
          <img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white">
          <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
          <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring boot&logoColor=white">
          <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
          <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
        </td>
    </tr>
    <tr>
      <td>ORM</td>
      <td>
          <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white">
          <img src="https://img.shields.io/badge/QueryDsl-02A8EF?style=for-the-badge&logo=QueryDsl&logoColor=white">
      </td>
    </tr>
    <tr>
        <td>Database</td>
        <td>
          <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
          <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
          <img src="https://img.shields.io/badge/H2-004088?style=for-the-badge&logo=db&logoColor=white">
          <img src="https://img.shields.io/badge/Flyway-CC0200.svg?style=for-the-badge&logo=flyway&logoColor=white">
        </td>
    </tr>
    <tr>
      <td>Deploy</td>
      <td>
        <img src="https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white">
        <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white">
        <img src="https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white">
      </td>
    </tr>
    <tr>
      <td>Monitoring</td>
      <td>
        <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
        <img src="https://img.shields.io/badge/Sentry-362D59?style=for-the-badge&logo=sentry&logoColor=white">
      </td>
    </tr>
    <tr>
      <td>Authorization</td>
      <td>
        <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white">
      </td>
    </tr>
    <tr>
      <td>Docs</td>
      <td>
        <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white"
      </td>
    </tr>
</table>
</br></br>

## 🧑🏻‍💻 Developers
| <img src="https://avatars.githubusercontent.com/u/65899774?v=4" alt="이정연" width="150" height="150"> |
| :--: |
| [이정연](https://github.com/leeeeeyeon) |
