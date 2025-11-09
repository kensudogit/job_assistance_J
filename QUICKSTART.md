# クイックスタートガイド

## 前提条件

- Java 17以上
- Gradle 8.5以上（またはGradle Wrapperを使用）
- PostgreSQL 12以上
- Node.js 18以上（フロントエンド用）

## セットアップ手順

### 1. データベースの準備

```sql
CREATE DATABASE job_assistance;
```

### 2. 環境変数の設定

`.env`ファイルを作成：

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=job_assistance
DB_USER=postgres
DB_PASSWORD=postgres
SERVER_PORT=5000
SPRING_PROFILES_ACTIVE=dev
```

### 3. バックエンドの起動

```powershell
# 依存関係のインストールとビルド
gradlew build

# アプリケーションの起動
gradlew bootRun
```

バックエンドAPIは `http://localhost:5000` で起動します。

### 4. フロントエンドの起動（別のターミナル）

```powershell
# 依存関係のインストール
npm install

# 開発サーバーの起動
npm run dev
```

フロントエンドは `http://localhost:3000` で起動します。

## Dockerを使用した起動（推奨）

```powershell
# 全サービスを起動
docker-compose up -d

# ログを確認
docker-compose logs -f

# サービスを停止
docker-compose down
```

## アクセスURL

- **フロントエンド**: http://localhost:3000
- **バックエンドAPI**: http://localhost:5000
- **API ヘルスチェック**: http://localhost:5000/api/health

## トラブルシューティング

### データベース接続エラー

- PostgreSQLが起動していることを確認
- データベース名、ユーザー名、パスワードが正しいことを確認
- ポート番号が正しいことを確認（デフォルト: 5432）

### ポートが既に使用されている

- 別のポート番号を指定: `SERVER_PORT=5001`
- 使用中のプロセスを確認して停止

### ビルドエラー

- Java 17がインストールされていることを確認
- Gradle Wrapperを使用: `gradlew build`

