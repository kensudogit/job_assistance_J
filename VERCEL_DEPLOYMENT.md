# Vercelデプロイメントガイド（完全互換モード）

## デプロイメント構成

Vercelはフロントエンド（Vite/React）をデプロイし、バックエンド（Spring Boot）は別のサービスにデプロイする必要があります。

### フロントエンド（Vercel）
- Vite/Reactアプリケーション
- 自動デプロイ対応
- 完全互換モード設定済み

### バックエンド（別サービス推奨）
- Spring Bootアプリケーション
- Railway、Render、Fly.io、AWS、GCPなど

## デプロイ手順

### 1. フロントエンドをVercelにデプロイ

#### Vercel CLIを使用する場合

```powershell
# Vercel CLIをインストール（未インストールの場合）
npm install -g vercel

# プロジェクトディレクトリに移動
cd C:\devlop\job_assistance_J

# Vercelにログイン
vercel login

# デプロイ
vercel --prod
```

#### Vercel Web UIを使用する場合

1. https://vercel.com にアクセス
2. GitHubリポジトリを接続
3. プロジェクトをインポート
4. 設定：
   - Framework Preset: Vite
   - Build Command: `npm run build`
   - Output Directory: `dist`
   - Install Command: `npm install`

### 2. 環境変数の設定

Vercelのダッシュボードで以下の環境変数を設定：

**必須環境変数:**
```
VITE_API_BASE_URL=https://your-backend-api-url.com
```

**設定方法:**
1. Vercelダッシュボードにアクセス
2. プロジェクトを選択
3. Settings > Environment Variables に移動
4. 以下の環境変数を追加：
   - `VITE_API_BASE_URL`: バックエンドAPIのURL（例: `https://your-backend-api.railway.app`）
5. 環境を選択（Production, Preview, Development）
6. Save をクリック

### 3. バックエンドのデプロイ（Railway推奨）

#### Railwayを使用する場合

1. https://railway.app にアクセス
2. 新しいプロジェクトを作成
3. GitHubリポジトリを接続
4. 新しいサービスを追加
5. Dockerfile.backendを使用してデプロイ
6. PostgreSQLデータベースを追加
7. 環境変数を設定：
   ```
   DB_HOST=postgres
   DB_PORT=5432
   DB_NAME=job_assistance
   DB_USER=postgres
   DB_PASSWORD=<自動生成>
   SERVER_PORT=5000
   SPRING_PROFILES_ACTIVE=production
   ```

### 4. CORS設定

バックエンドの`application.properties`でCORSを設定：

```properties
spring.web.cors.allowed-origins=https://your-vercel-app.vercel.app
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
spring.web.cors.allow-credentials=true
```

## 完全互換モード設定

### vercel.json設定

`vercel.json`ファイルで以下を設定済み：

1. **ビルド設定** - Viteフレームワーク対応
2. **リライト設定** - SPAルーティング対応（すべてのリクエストを`/index.html`にリダイレクト）
3. **セキュリティヘッダー設定** - XSS保護、フレーム保護など

### バックエンドAPI URL設定

フロントエンドの`lib/api.ts`でバックエンドAPI URLを環境変数から取得：

```typescript
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 
  (import.meta.env.PROD ? '' : 'http://localhost:5001');
```

**本番環境での動作:**
- 環境変数`VITE_API_BASE_URL`が設定されている場合、そのURLを使用
- 設定されていない場合、相対パス（空文字列）を使用（同一ドメインのAPIを想定）

### CORS設定

バックエンドの`application.properties`でCORSを設定：

```properties
spring.web.cors.allowed-origins=https://your-vercel-app.vercel.app,https://your-custom-domain.com
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
spring.web.cors.allow-credentials=true
```

## デプロイ後の確認

1. **フロントエンド**: https://your-app.vercel.app
2. **バックエンドAPI**: https://your-backend-api-url.com/api/health
3. **Swagger UI**: https://your-backend-api-url.com/swagger-ui.html

## トラブルシューティング

### CORSエラー
- バックエンドのCORS設定を確認
- Vercelの環境変数`VITE_API_BASE_URL`を確認

### API接続エラー
- バックエンドAPIのURLを確認
- ネットワーク接続を確認
- バックエンドのログを確認

## 参考リンク

- [Vercel Documentation](https://vercel.com/docs)
- [Railway Documentation](https://docs.railway.app)
- [Vite Deployment Guide](https://vitejs.dev/guide/static-deploy.html)

