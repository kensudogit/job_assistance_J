# Vercelデプロイ手順（完全公開モード）

## 前提条件

- Vercelアカウント（https://vercel.com）
- GitHubリポジトリ（推奨）またはVercel CLI
- バックエンドAPIのデプロイ先（Railway、Render、Fly.ioなど）

## 完全公開モードについて

完全公開モードでは、フロントエンドとバックエンドの両方を公開し、すべてのオリジンからのアクセスを許可します。
- **CORS設定**: すべてのオリジンを許可（*）
- **セキュリティ**: 適切な認証・認可を実装することを推奨

## デプロイ手順

### 1. バックエンドAPIのデプロイ（Railway推奨）

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
   CORS_ORIGINS=*
   ```
8. デプロイ後、バックエンドAPIのURLを取得（例: `https://your-backend-api.railway.app`）

### 2. バックエンドのCORS設定（完全公開モード）

バックエンドの`application-prod.properties`でCORSを設定：

```properties
# 完全公開モード: すべてのオリジンを許可
spring.web.cors.allowed-origins=*
spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS,PATCH
spring.web.cors.allowed-headers=*
spring.web.cors.allow-credentials=true
spring.web.cors.max-age=86400
```

または、環境変数で設定：

```env
CORS_ORIGINS=*
```

**注意**: 完全公開モードではすべてのオリジンからのアクセスを許可します。本番環境では、特定のオリジンを指定することを推奨します：

```env
CORS_ORIGINS=https://your-app.vercel.app,https://your-custom-domain.com
```

### 3. フロントエンドをVercelにデプロイ

#### 方法A: Vercel CLIを使用

```powershell
# Vercel CLIをインストール（未インストールの場合）
npm install -g vercel

# プロジェクトディレクトリに移動
cd C:\devlop\job_assistance_J

# Vercelにログイン
vercel login

# 環境変数を設定
vercel env add VITE_API_BASE_URL production
# プロンプトでバックエンドAPIのURLを入力（例: https://your-backend-api.railway.app）

# デプロイ
vercel --prod
```

#### 方法B: Vercel Web UIを使用

1. https://vercel.com にアクセス
2. "Add New..." > "Project" をクリック
3. GitHubリポジトリを選択（またはインポート）
4. プロジェクト設定：
   - **Framework Preset**: Vite
   - **Root Directory**: `./`（プロジェクトルート）
   - **Build Command**: `npm run build`
   - **Output Directory**: `dist`
   - **Install Command**: `npm install`
5. 環境変数を追加：
   - **Name**: `VITE_API_BASE_URL`
   - **Value**: バックエンドAPIのURL（例: `https://your-backend-api.railway.app`）
   - **Environment**: Production, Preview, Development（すべて選択）
6. "Deploy" をクリック

### 4. デプロイ後の確認

1. **フロントエンド**: https://your-app.vercel.app
2. **バックエンドAPI**: https://your-backend-api.railway.app/api/health
3. **Swagger UI**: https://your-backend-api.railway.app/swagger-ui.html

### 5. カスタムドメインの設定（オプション）

1. Vercelダッシュボードでプロジェクトを選択
2. Settings > Domains に移動
3. カスタムドメインを追加
4. DNS設定を更新

## トラブルシューティング

### CORSエラー

**症状**: ブラウザコンソールにCORSエラーが表示される

**解決方法**:
1. バックエンドのCORS設定を確認
2. VercelのURLを`spring.web.cors.allowed-origins`に追加
3. バックエンドを再デプロイ

### API接続エラー

**症状**: フロントエンドからAPIに接続できない

**解決方法**:
1. 環境変数`VITE_API_BASE_URL`が正しく設定されているか確認
2. バックエンドAPIが起動しているか確認
3. バックエンドAPIのURLが正しいか確認
4. ネットワーク接続を確認

### ビルドエラー

**症状**: Vercelでのビルドが失敗する

**解決方法**:
1. ローカルで`npm run build`が成功するか確認
2. `package.json`の依存関係を確認
3. ビルドログを確認してエラーを特定
4. `.vercelignore`で不要なファイルを除外

## 参考リンク

- [Vercel Documentation](https://vercel.com/docs)
- [Railway Documentation](https://docs.railway.app)
- [Vite Deployment Guide](https://vitejs.dev/guide/static-deploy.html)
- [Spring Boot CORS Configuration](https://spring.io/guides/gs/rest-service-cors/)

