# Vercel完全公開モードデプロイガイド

## 概要

完全公開モードでは、フロントエンドとバックエンドの両方を公開し、すべてのオリジンからのアクセスを許可します。

## デプロイ構成

### フロントエンド（Vercel）
- **URL**: `https://your-app.vercel.app`
- **フレームワーク**: Vite/React
- **完全公開モード**: 有効

### バックエンド（Railway/Render/Fly.io推奨）
- **URL**: `https://your-backend-api.railway.app`
- **フレームワーク**: Spring Boot
- **CORS**: すべてのオリジンを許可（*）

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
   ```env
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

#### Renderを使用する場合

1. https://render.com にアクセス
2. 新しいWebサービスを作成
3. GitHubリポジトリを接続
4. 設定：
   - **Build Command**: `./gradlew build -x test`
   - **Start Command**: `java -jar build/libs/*.jar`
   - **Environment**: Docker
5. PostgreSQLデータベースを追加
6. 環境変数を設定（Railwayと同様）
7. デプロイ

#### Fly.ioを使用する場合

1. https://fly.io にアクセス
2. `flyctl` CLIをインストール
3. プロジェクトを初期化：
   ```powershell
   flyctl launch
   ```
4. PostgreSQLデータベースを追加：
   ```powershell
   flyctl postgres create
   ```
5. 環境変数を設定
6. デプロイ：
   ```powershell
   flyctl deploy
   ```

### 2. バックエンドのCORS設定（完全公開モード）

バックエンドの`application-prod.properties`でCORSを設定：

```properties
# CORS Configuration (完全公開モード)
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

# 完全公開モードでデプロイ
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

#### フロントエンド（Vercel）

1. Vercelダッシュボードでプロジェクトを選択
2. Settings > Domains に移動
3. カスタムドメインを追加（例: `app.yourdomain.com`）
4. DNS設定を更新

#### バックエンド（Railway）

1. Railwayダッシュボードでサービスを選択
2. Settings > Networking に移動
3. カスタムドメインを追加（例: `api.yourdomain.com`）
4. DNS設定を更新

## 完全公開モードの設定

### vercel.json設定

`vercel.json`ファイルで以下を設定済み：

1. **ビルド設定** - Viteフレームワーク対応
2. **リライト設定** - SPAルーティング対応
3. **セキュリティヘッダー設定** - XSS保護、フレーム保護など
4. **CORSヘッダー設定** - APIエンドポイント用のCORSヘッダー

### バックエンドAPI URL設定

フロントエンドの`lib/api.ts`でバックエンドAPI URLを環境変数から取得：

```typescript
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 
  (import.meta.env.PROD ? '' : 'http://localhost:5001');
```

**本番環境での動作:**
- 環境変数`VITE_API_BASE_URL`が設定されている場合、そのURLを使用
- 設定されていない場合、相対パス（空文字列）を使用

## セキュリティ考慮事項

### 完全公開モードの注意点

1. **CORS設定**: すべてのオリジンを許可（*）しているため、任意のドメインからAPIにアクセス可能
2. **認証**: 適切な認証・認可を実装することを推奨
3. **レート制限**: APIのレート制限を実装することを推奨
4. **HTTPS**: すべての通信をHTTPSで行うことを推奨

### 推奨セキュリティ設定

1. **環境変数でCORSオリジンを制限**:
   ```env
   CORS_ORIGINS=https://your-app.vercel.app,https://your-custom-domain.com
   ```

2. **認証・認可の実装**:
   - Spring Securityを使用
   - JWTトークン認証
   - ロールベースアクセス制御

3. **レート制限の実装**:
   - Spring Cloud Gateway
   - Redisベースのレート制限

## トラブルシューティング

### CORSエラー

**症状**: ブラウザコンソールにCORSエラーが表示される

**解決方法**:
1. バックエンドのCORS設定を確認
2. 環境変数`CORS_ORIGINS`が正しく設定されているか確認
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
- [Render Documentation](https://render.com/docs)
- [Fly.io Documentation](https://fly.io/docs)
- [Vite Deployment Guide](https://vitejs.dev/guide/static-deploy.html)
- [Spring Boot CORS Configuration](https://spring.io/guides/gs/rest-service-cors/)

