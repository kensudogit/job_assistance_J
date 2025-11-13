# Vercel完全公開モードデプロイクイックガイド

## 概要

完全公開モードでVercelにデプロイするためのクイックガイドです。

## デプロイ手順

### 1. バックエンドAPIのデプロイ（Railway推奨）

```powershell
# Railwayにデプロイ
# 1. https://railway.app にアクセス
# 2. 新しいプロジェクトを作成
# 3. GitHubリポジトリを接続
# 4. Dockerfile.backendを使用してデプロイ
# 5. PostgreSQLデータベースを追加
# 6. 環境変数を設定:
#    - DB_HOST=postgres
#    - DB_PORT=5432
#    - DB_NAME=job_assistance
#    - DB_USER=postgres
#    - DB_PASSWORD=<自動生成>
#    - SERVER_PORT=5000
#    - SPRING_PROFILES_ACTIVE=production
#    - CORS_ORIGINS=*
# 7. バックエンドAPIのURLを取得（例: https://your-backend-api.railway.app）
```

### 2. フロントエンドをVercelにデプロイ

#### Vercel CLIを使用

```powershell
# Vercel CLIをインストール
npm install -g vercel

# プロジェクトディレクトリに移動
cd C:\devlop\job_assistance_J

# Vercelにログイン
vercel login

# 環境変数を設定
vercel env add VITE_API_BASE_URL production
# プロンプトでバックエンドAPIのURLを入力（例: https://your-backend-api.railway.app）

# 完全公開モードでデプロイ
vercel --prod
```

#### Vercel Web UIを使用

1. https://vercel.com にアクセス
2. "Add New..." > "Project" をクリック
3. GitHubリポジトリを選択
4. プロジェクト設定：
   - **Framework Preset**: Vite
   - **Build Command**: `npm run build`
   - **Output Directory**: `dist`
5. 環境変数を追加：
   - **Name**: `VITE_API_BASE_URL`
   - **Value**: バックエンドAPIのURL（例: `https://your-backend-api.railway.app`）
6. "Deploy" をクリック

### 3. デプロイ後の確認

1. **フロントエンド**: https://your-app.vercel.app
2. **バックエンドAPI**: https://your-backend-api.railway.app/api/health
3. **Swagger UI**: https://your-backend-api.railway.app/swagger-ui.html

## 完全公開モードの設定

### vercel.json

`vercel.json`で以下を設定済み：
- Viteフレームワーク対応
- SPAルーティング対応
- セキュリティヘッダー設定
- CORSヘッダー設定（APIエンドポイント用）

### バックエンドCORS設定

`application-prod.properties`で以下を設定済み：
- すべてのオリジンを許可（*）
- すべてのHTTPメソッドを許可
- すべてのヘッダーを許可
- 認証情報を許可

## セキュリティ注意事項

完全公開モードでは、すべてのオリジンからのアクセスを許可します。

**本番環境での推奨設定:**
1. 環境変数`CORS_ORIGINS`で特定のオリジンを指定
2. 適切な認証・認可を実装
3. APIのレート制限を実装
4. HTTPSを使用

詳細は `DEPLOY_PUBLIC.md` を参照してください。

