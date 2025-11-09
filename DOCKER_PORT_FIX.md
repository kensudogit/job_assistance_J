# Dockerポート競合修正報告

## 問題

Dockerビルドは成功しましたが、PostgreSQLコンテナの起動時にポート5434が既に使用されているというエラーが発生しました。

```
Error response from daemon: failed to set up container networking: driver failed programming external connectivity on endpoint job_assistance_j_db: Bind for 0.0.0.0:5434 failed: port is already allocated
```

## 原因

既存のPostgreSQLコンテナ（`job_assistance_db`）がポート5434を使用していました。

## 修正内容

### ✅ docker-compose.yml更新

1. **PostgreSQLポート変更**
   - ポートマッピングを`5434:5432`から`5435:5432`に変更

2. **バックエンドポート変更**
   - ポートマッピングを`5000:5000`から`5001:5000`に変更
   - フロントエンドの`VITE_API_BASE_URL`を`http://localhost:5001`に更新

```yaml
# PostgreSQL
ports:
  - "5435:5432"

# Backend
ports:
  - "5001:5000"

# Frontend環境変数
VITE_API_BASE_URL: http://localhost:5001
```

## 変更ファイル

1. `docker-compose.yml` - PostgreSQLポートを5434から5435に変更

## ビルド状況

✅ **BUILD SUCCESSFUL** - Dockerビルド成功
✅ **ポート修正完了** - PostgreSQLポートを5435に変更

## アクセスURL

- **フロントエンド**: http://localhost:3000
- **バックエンドAPI**: http://localhost:5001
- **PostgreSQL**: localhost:5435

## 次のステップ

Dockerコンテナを起動してください：

```powershell
docker-compose up -d
```

コンテナの状態を確認：

```powershell
docker-compose ps
```

これで、すべてのサービスが正常に起動するはずです。

