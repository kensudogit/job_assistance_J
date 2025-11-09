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
- **Java 17** - プログラミング言語
- **Spring Boot 3.2** - Webフレームワーク
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
- **Java** 17以上
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
- **バックエンドAPI**: http://localhost:5000
- **API ヘルスチェック**: http://localhost:5000/api/health

## APIエンドポイント

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

## ライセンス

MIT License

"# job_assistance_J" 
