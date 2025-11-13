# job_assistance_J

外国人向け就労支援システム（Java版）  
外国人就労者の属性管理と進捗管理機能を提供する、現代的で斬新なデザインのWebアプリケーションです。

## 特長

- 🌍 **多言語対応** - 日本語、英語、中国語、ベトナム語に対応
- 👥 **就労者属性管理** - 国籍、在留資格、日本語レベルなど詳細な属性管理
- 📊 **進捗管理** - 面談、研修、就労支援の進捗を詳細に記録・管理
- 🇯🇵 **日本語能力管理** - JLPT、JFT-Basic、BJTなどの試験結果と4技能スコア管理
- 🎓 **日本語学習記録** - 学習内容、学習時間、単語数、文法項目の詳細管理
- 🔧 **技能訓練管理** - 建設、製造、介護、農業、外食などの技能訓練プログラム管理
- 📈 **技能訓練記録** - 訓練内容、進捗率、指導者フィードバック、自己評価の管理
- ✈️ **来日前支援** - ビザ申請、契約書確認、事前研修などの来日前支援管理
- 📄 **ドキュメント管理** - 履歴書、在留資格証明書、パスポートなどの書類管理
- 🔔 **通知・リマインダー** - 重要な予定や期限の通知機能
- 🎓 **研修・トレーニング管理** - 研修プログラムの管理と受講状況の追跡
- 📅 **カレンダー機能** - イベント、面談、研修などのスケジュール管理
- ⭐ **評価・フィードバック** - 就労者への定期的な評価とフィードバック
- 💬 **メッセージング** - スタッフと就労者間のコミュニケーション
- 🎨 **現代的UI** - グラスモーフィズム、グラデーション、アニメーションを活用した斬新なデザイン
- 🔄 **RESTful API** - Spring BootベースのRESTful API
- 🗄️ **PostgreSQL対応** - 本番環境対応のデータベース
- 🐳 **Docker統合** - コンテナ化による簡単な環境構築

## 技術スタック

### フロントエンド
- **Next.js 16** - Reactフレームワーク
- **TypeScript** - 型安全な開発
- **Tailwind CSS** - ユーティリティファーストのCSS
- **React i18next** - 多言語対応
- **Vitest** - テストフレームワーク
- **Vite** - 高速ビルドツール

### バックエンド
- **Java 21 LTS** - プログラミング言語
- **Spring Boot 3.2.0** - Webフレームワーク
- **Spring Data JPA** - ORM
- **PostgreSQL** - データベース
- **Gradle 8.5** - ビルドツール

## セットアップ

### 前提条件

#### Dockerを使用する場合（推奨）
- **Docker** 20.10以上
- **Docker Compose** 2.0以上

#### ローカル環境を使用する場合
- **Node.js** 18以上
- **Java** 21 LTS以上
- **Gradle** 8.5以上（またはGradle Wrapperを使用）
- **PostgreSQL** 12以上

### 環境変数の設定

`.env`ファイルを作成し、以下の環境変数を設定してください：

```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_NAME=job_assistance
DB_USER=postgres
DB_PASSWORD=postgres

# Spring Boot Configuration
SERVER_PORT=5000
SPRING_PROFILES_ACTIVE=development
```

## Dockerを使用したセットアップ（推奨）

### クイックスタート

```powershell
# 全サービスを起動（本番環境）
docker-compose up -d

# ログを確認
docker-compose logs -f

# サービスを停止
docker-compose down

# データベースを含めて完全にクリーンアップ
docker-compose down -v
```

### Docker Composeサービス

- **db** (PostgreSQL): `localhost:5434`
- **backend** (Spring Boot API): `http://localhost:5000`
- **frontend** (Next.js): `http://localhost:3000`

## 手動セットアップ

### 1. データベースの初期化

PostgreSQLデータベースを作成します：

```sql
CREATE DATABASE job_assistance;
```

### 2. バックエンド（Spring Boot）の起動

```powershell
# Gradleで依存関係をインストール
gradlew build

# アプリケーションを起動
gradlew bootRun
```

バックエンドAPIは `http://localhost:5000` で起動します。

### 3. フロントエンドの起動（別のターミナル）

```powershell
# 依存関係のインストール
npm install

# 開発サーバーの起動
npm run dev
```

フロントエンドは `http://localhost:3000` で起動します。

## アクセスURL

- **フロントエンド**: http://localhost:3000
- **バックエンドAPI**: http://localhost:5001
- **API ヘルスチェック**: http://localhost:5001/api/health
- **Swagger UI**: http://localhost:5001/swagger-ui.html
- **API ドキュメント**: http://localhost:5001/api-docs
- **PostgreSQL**: localhost:5435

## APIエンドポイント

### ヘルスチェック

- `GET /api/health` - ヘルスチェック

### 就労者管理

- `GET /api/workers` - 就労者一覧取得
- `POST /api/workers` - 就労者登録
- `GET /api/workers/:id` - 就労者詳細取得
- `PUT /api/workers/:id` - 就労者更新
- `DELETE /api/workers/:id` - 就労者削除

### 進捗管理

- `GET /api/workers/:id/progress` - 進捗一覧取得
- `POST /api/workers/:id/progress` - 進捗登録
- `GET /api/workers/:id/progress/:progress_id` - 進捗詳細取得
- `PUT /api/workers/:id/progress/:progress_id` - 進捗更新
- `DELETE /api/workers/:id/progress/:progress_id` - 進捗削除

### ドキュメント管理

- `GET /api/workers/:id/documents` - ドキュメント一覧取得
- `POST /api/workers/:id/documents` - ドキュメント登録
- `GET /api/workers/:id/documents/:id` - ドキュメント詳細取得
- `PUT /api/workers/:id/documents/:id` - ドキュメント更新
- `DELETE /api/workers/:id/documents/:id` - ドキュメント削除

### 日本語能力管理

- `GET /api/workers/:id/japanese-proficiency` - 日本語能力一覧取得
- `POST /api/workers/:id/japanese-proficiency` - 日本語能力登録
- `GET /api/workers/:id/japanese-proficiency/:id` - 日本語能力詳細取得
- `PUT /api/workers/:id/japanese-proficiency/:id` - 日本語能力更新
- `DELETE /api/workers/:id/japanese-proficiency/:id` - 日本語能力削除

### 技能訓練管理

- `GET /api/workers/:id/skill-training` - 技能訓練一覧取得
- `POST /api/workers/:id/skill-training` - 技能訓練登録
- `GET /api/workers/:id/skill-training/:id` - 技能訓練詳細取得
- `PUT /api/workers/:id/skill-training/:id` - 技能訓練更新
- `DELETE /api/workers/:id/skill-training/:id` - 技能訓練削除

### 日本語学習記録

- `GET /api/workers/:id/japanese-learning` - 日本語学習記録一覧取得
- `POST /api/workers/:id/japanese-learning` - 日本語学習記録登録
- `GET /api/workers/:id/japanese-learning/:id` - 日本語学習記録詳細取得
- `PUT /api/workers/:id/japanese-learning/:id` - 日本語学習記録更新
- `DELETE /api/workers/:id/japanese-learning/:id` - 日本語学習記録削除

### 来日前支援

- `GET /api/workers/:id/pre-departure-support` - 来日前支援一覧取得
- `POST /api/workers/:id/pre-departure-support` - 来日前支援登録
- `GET /api/workers/:id/pre-departure-support/:id` - 来日前支援詳細取得
- `PUT /api/workers/:id/pre-departure-support/:id` - 来日前支援更新
- `DELETE /api/workers/:id/pre-departure-support/:id` - 来日前支援削除

### 訓練メニュー管理

- `GET /api/training-menus` - 訓練メニュー一覧取得
- `POST /api/training-menus` - 訓練メニュー登録
- `GET /api/training-menus/:id` - 訓練メニュー詳細取得
- `PUT /api/training-menus/:id` - 訓練メニュー更新
- `DELETE /api/training-menus/:id` - 訓練メニュー削除

### 訓練セッション管理

- `GET /api/training-sessions` - 訓練セッション一覧取得
- `POST /api/training-sessions` - 訓練セッション登録
- `GET /api/training-sessions/:id` - 訓練セッション詳細取得
- `PUT /api/training-sessions/:id` - 訓練セッション更新
- `DELETE /api/training-sessions/:id` - 訓練セッション削除

### 通知管理

- `GET /api/notifications` - 全員向け通知一覧取得
- `POST /api/notifications` - 全員向け通知登録
- `GET /api/workers/:id/notifications` - 就労者向け通知一覧取得
- `POST /api/workers/:id/notifications` - 就労者向け通知登録

### カレンダー管理

- `GET /api/calendar` - 全員向けカレンダーイベント一覧取得
- `POST /api/calendar` - 全員向けカレンダーイベント登録
- `GET /api/workers/:id/calendar` - 就労者向けカレンダーイベント一覧取得
- `POST /api/workers/:id/calendar` - 就労者向けカレンダーイベント登録

### 研修管理

- `GET /api/trainings` - 研修一覧取得
- `POST /api/trainings` - 研修登録
- `GET /api/trainings/:id` - 研修詳細取得
- `PUT /api/trainings/:id` - 研修更新
- `DELETE /api/trainings/:id` - 研修削除

### 評価管理

- `GET /api/workers/:id/evaluations` - 評価一覧取得
- `POST /api/workers/:id/evaluations` - 評価登録
- `GET /api/workers/:id/evaluations/:id` - 評価詳細取得
- `PUT /api/workers/:id/evaluations/:id` - 評価更新
- `DELETE /api/workers/:id/evaluations/:id` - 評価削除

### メッセージ管理

- `GET /api/workers/:id/messages` - メッセージ一覧取得
- `POST /api/workers/:id/messages` - メッセージ登録
- `GET /api/workers/:id/messages/:id` - メッセージ詳細取得
- `PUT /api/workers/:id/messages/:id` - メッセージ更新
- `DELETE /api/workers/:id/messages/:id` - メッセージ削除

### 就労者向け訓練セッション管理

- `GET /api/workers/:id/training-sessions` - 就労者向け訓練セッション一覧取得

### 管理者機能

- `GET /api/admin/summary` - 管理者用サマリー取得

### 認証機能

- `POST /api/auth/login` - ログイン
- `POST /api/auth/logout` - ログアウト
- `GET /api/auth/current` - 現在のユーザー情報取得

### ユーザー管理

- `GET /api/users` - ユーザー一覧取得
- `POST /api/users` - ユーザー登録
- `GET /api/users/:id` - ユーザー詳細取得
- `PUT /api/users/:id` - ユーザー更新
- `DELETE /api/users/:id` - ユーザー削除

### マイルストーン管理

- `GET /api/workers/:id/milestones` - マイルストーン一覧取得
- `POST /api/workers/:id/milestones` - マイルストーン登録
- `PUT /api/workers/:id/milestones/:milestone_id` - マイルストーン更新
- `DELETE /api/workers/:id/milestones/:milestone_id` - マイルストーン削除

### キャリアパス管理

- `GET /api/workers/:id/career-paths` - キャリアパス一覧取得
- `POST /api/workers/:id/career-paths` - キャリアパス登録
- `PUT /api/workers/:id/career-paths/:path_id` - キャリアパス更新
- `DELETE /api/workers/:id/career-paths/:path_id` - キャリアパス削除

### 建設シミュレーター訓練管理

- `GET /api/workers/:id/simulator-training` - 建設シミュレーター訓練一覧取得
- `POST /api/workers/:id/simulator-training` - 建設シミュレーター訓練登録
- `PUT /api/workers/:id/simulator-training/:training_id` - 建設シミュレーター訓練更新
- `DELETE /api/workers/:id/simulator-training/:training_id` - 建設シミュレーター訓練削除

### 統合成長管理

- `GET /api/workers/:id/integrated-growth` - 統合成長記録一覧取得
- `POST /api/workers/:id/integrated-growth` - 統合成長記録登録
- `PUT /api/workers/:id/integrated-growth/:growth_id` - 統合成長記録更新
- `DELETE /api/workers/:id/integrated-growth/:growth_id` - 統合成長記録削除

### 特定技能移行管理

- `GET /api/workers/:id/specific-skill-transition` - 特定技能移行記録一覧取得
- `POST /api/workers/:id/specific-skill-transition` - 特定技能移行記録登録
- `PUT /api/workers/:id/specific-skill-transition/:transition_id` - 特定技能移行記録更新
- `DELETE /api/workers/:id/specific-skill-transition/:transition_id` - 特定技能移行記録削除

### キャリア目標管理

- `GET /api/workers/:id/career-goals` - キャリア目標一覧取得
- `POST /api/workers/:id/career-goals` - キャリア目標登録
- `PUT /api/workers/:id/career-goals/:goal_id` - キャリア目標更新
- `DELETE /api/workers/:id/career-goals/:goal_id` - キャリア目標削除

### 統合ダッシュボード

- `GET /api/workers/:id/dashboard/integrated` - 統合ダッシュボード取得

### 証拠レポート

- `GET /api/workers/:id/evidence-report` - 証拠レポート取得

### MFA（多要素認証）

- `POST /api/auth/mfa/setup` - MFAセットアップ（シークレット生成）
- `POST /api/auth/mfa/enable` - MFA有効化
- `POST /api/auth/mfa/disable` - MFA無効化
- `POST /api/auth/mfa/backup-codes` - バックアップコード生成

### CSRFトークン

- `GET /api/auth/csrf-token` - CSRFトークン取得

### ファイルアップロード

- `POST /api/workers/screenshot` - スクリーンショットアップロード
- `POST /api/workers/:id/documents/upload` - ドキュメントアップロード

### Unity統合

- `POST /api/unity/training-session` - Unity訓練セッション作成
- `POST /api/unity/command` - Unityコマンド処理

### リプレイ

- `GET /api/replay/:sessionId` - リプレイセッションデータ取得

### 訓練メニュー割り当て管理

- `GET /api/workers/:id/training-menu-assignments` - 訓練メニュー割り当て一覧取得
- `POST /api/workers/:id/training-menu-assignments` - 訓練メニュー割り当て登録
- `GET /api/workers/:id/training-menu-assignments/:id` - 訓練メニュー割り当て詳細取得
- `PUT /api/workers/:id/training-menu-assignments/:id` - 訓練メニュー割り当て更新
- `DELETE /api/workers/:id/training-menu-assignments/:id` - 訓練メニュー割り当て削除

### レポート管理

- `GET /api/reports` - 全体レポート一覧取得
- `POST /api/reports` - 全体レポート登録
- `GET /api/workers/:id/reports` - 就労者向けレポート一覧取得
- `POST /api/workers/:id/reports` - 就労者向けレポート登録
- `GET /api/reports/:id` - レポート詳細取得
- `PUT /api/reports/:id` - レポート更新
- `DELETE /api/reports/:id` - レポート削除

## プロジェクト構成

```
job_assistance_J/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/jobassistance/
│   │   │       ├── config/          # 設定クラス
│   │   │       ├── controller/      # RESTコントローラー
│   │   │       ├── entity/          # JPAエンティティ
│   │   │       ├── repository/      # リポジトリ
│   │   │       ├── service/          # サービスクラス
│   │   │       ├── dto/             # データ転送オブジェクト
│   │   │       ├── exception/       # 例外ハンドラー
│   │   │       └── JobAssistanceApplication.java
│   │   └── resources/
│   │       └── application.properties
├── build.gradle.kts                  # Gradle設定
├── settings.gradle.kts               # Gradle設定
├── gradlew                           # Gradle Wrapper (Unix)
├── gradlew.bat                       # Gradle Wrapper (Windows)
├── Dockerfile.backend               # バックエンドDockerfile
├── docker-compose.yml               # Docker Compose設定
└── README.md                        # このファイル
```

## 開発

### ビルド

```powershell
# Gradleでビルド
gradlew build

# テストをスキップしてビルド
gradlew build -x test
```

### テスト実行

```powershell
# すべてのテストを実行
gradlew test

# 特定のテストクラスを実行
gradlew test --tests "WorkerControllerTest"
```

### APIドキュメント

Swagger UIを使用してAPIドキュメントを確認できます：

- **Swagger UI**: http://localhost:5000/swagger-ui.html
- **API ドキュメント (JSON)**: http://localhost:5000/api-docs

### バリデーション

APIリクエストのバリデーションが実装されています：
- `@Valid`アノテーションによる入力検証
- エラーメッセージの自動生成
- バリデーション例外のハンドリング

## Vercelデプロイ（完全公開モード）

フロントエンドをVercelにデプロイする場合、以下の手順に従ってください。

### 前提条件

- Vercelアカウント（https://vercel.com）
- バックエンドAPIのデプロイ先（Railway、Render、Fly.ioなど）

### デプロイ手順

1. **バックエンドAPIのデプロイ**
   - Railway、Render、Fly.ioなどのサービスにバックエンドをデプロイ
   - バックエンドAPIのURLを取得（例: `https://your-backend-api.railway.app`）

2. **Vercelにフロントエンドをデプロイ**
   - Vercel CLIを使用する場合:
     ```powershell
     npm install -g vercel
     vercel login
     vercel env add VITE_API_BASE_URL production
     # プロンプトでバックエンドAPIのURLを入力
     vercel --prod
     ```
   - Vercel Web UIを使用する場合:
     - https://vercel.com にアクセス
     - GitHubリポジトリを接続
     - 環境変数 `VITE_API_BASE_URL` を設定
     - デプロイ

3. **CORS設定（完全公開モード）**
   - バックエンドの`application-prod.properties`でCORSを設定:
     ```properties
     # 完全公開モード: すべてのオリジンを許可
     spring.web.cors.allowed-origins=*
     spring.web.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS,PATCH
     spring.web.cors.allowed-headers=*
     spring.web.cors.allow-credentials=true
     spring.web.cors.max-age=86400
     ```
   - または、環境変数で設定:
     ```env
     CORS_ORIGINS=*
     ```

**注意**: 完全公開モードではすべてのオリジンからのアクセスを許可します。本番環境では、特定のオリジンを指定することを推奨します。

詳細な手順は `DEPLOY_VERCEL.md` または `DEPLOY_PUBLIC.md` を参照してください。

## ライセンス

MIT License 
