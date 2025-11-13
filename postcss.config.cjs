/**
 * PostCSS設定ファイル
 * CSSの処理設定（Tailwind CSSとAutoprefixer）
 */
module.exports = {
  plugins: {
    tailwindcss: {},             // Tailwind CSS v3のPostCSSプラグイン
    autoprefixer: {},            // ベンダープレフィックスを自動追加
  },
};

