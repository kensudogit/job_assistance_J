# デプロイメントガイド

## 本番環境へのデプロイ

### 前提条件

- Java 17以上
- PostgreSQL 12以上
- Docker 20.10以上（オプション）
- Docker Compose 2.0以上（オプション）

### 環境変数の設定

本番環境では、以下の環境変数を設定してください：

```env
# Database Configuration
DB_HOST=your-db-host
DB_PORT=5432
DB_NAME=job_assistance
DB_USER=your-db-user
DB_PASSWORD=your-db-password

# Spring Boot Configuration
SERVER_PORT=5000
SPRING_PROFILES_ACTIVE=prod

# CORS Configuration
CORS_ORIGINS=https://your-frontend-domain.com

# Security Configuration
ADMIN_USER=admin
ADMIN_PASSWORD=your-secure-password
```

### ビルド

```powershell
# プロダクションビルド
gradlew clean build -x test

# JARファイルの場所
# build/libs/job-assistance-1.0.0.jar
```

### 実行

```powershell
# アプリケーション起動
java -jar build/libs/job-assistance-1.0.0.jar

# または環境変数を指定
java -jar -Dspring.profiles.active=prod build/libs/job-assistance-1.0.0.jar
```

### Dockerを使用したデプロイ

```powershell
# イメージのビルド
docker build -f Dockerfile.backend -t job-assistance-backend .

# コンテナの起動
docker run -d \
  -p 5000:5000 \
  -e DB_HOST=your-db-host \
  -e DB_PORT=5432 \
  -e DB_NAME=job_assistance \
  -e DB_USER=your-db-user \
  -e DB_PASSWORD=your-db-password \
  -e SPRING_PROFILES_ACTIVE=prod \
  --name job-assistance-backend \
  job-assistance-backend
```

### Docker Composeを使用したデプロイ

```powershell
# 全サービスを起動
docker-compose up -d

# ログを確認
docker-compose logs -f

# サービスを停止
docker-compose down
```

## データベースマイグレーション

Spring Bootは自動的にデータベーススキーマを管理します：
- `spring.jpa.hibernate.ddl-auto=update` - 開発環境
- `spring.jpa.hibernate.ddl-auto=validate` - 本番環境

本番環境では、FlywayやLiquibaseなどのマイグレーションツールの使用を推奨します。

## ヘルスチェック

アプリケーションのヘルスチェック：

```bash
curl http://localhost:5000/api/health
```

## ログ設定

本番環境では、ログレベルを適切に設定してください：

```properties
logging.level.root=WARN
logging.level.com.jobassistance=INFO
logging.level.org.springframework.web=WARN
```

## セキュリティ設定

本番環境では、以下のセキュリティ設定を推奨します：

1. HTTPSの使用
2. 強力なパスワードの設定
3. CORS設定の適切な制限
4. セキュリティヘッダーの設定
5. レート制限の実装

## モニタリング

以下のエンドポイントでアプリケーションの状態を確認できます：

- ヘルスチェック: `/api/health`
- Swagger UI: `/swagger-ui.html`
- API ドキュメント: `/api-docs`

