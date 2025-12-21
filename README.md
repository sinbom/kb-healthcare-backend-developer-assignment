# KB 헬스케어 백엔드 개발자 과제

## Build & Run
```shell
# 빌드
./gradlew clean build

# 컨테이너 실행
docker compose up --build -d

# 컨테이너 실행 (arm64[ex. apple silicon] 환경) 
docker compose -f docker-compose-amd64.yml up -d --build
```