
# 실행방법
```
docker build --build-arg DEPENDENCY=build/dependency -t barogo-user-api:latest ./barogo-user-api/.
docker-compose --file ./infra/docker-compose-barogo.yml up -d
```