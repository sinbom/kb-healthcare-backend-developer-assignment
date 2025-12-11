# KB 헬스케어 백엔드 개발자 과제

## Build & Run
```shell
# 빌드
./gradlew clean build

# 실행
docker compose up --build -d

# arm64(ex. apple silicon) 환경에서 실행 
docker compose -f docker-compose.yml -f docker-compose-amd64.yml up --build -d
```