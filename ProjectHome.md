GAE上で稼動する「行き先表示板」アプリケーション

## News ##
<b>2011/5/8：削除機能を追加しました。</b>
<br />
Release0.2のタグを作成しました。<br />
http://code.google.com/p/noticeboard2011/source/browse/tags/#tags%2FRelease_0.2
<br />
旧バージョンのタグを作成しました。<br />
http://code.google.com/p/noticeboard2011/source/browse/#svn%2Ftags%2FRelease_0.1
<br /><br />
今後（2011/2/8以降）trunkには最新版をコミットします。
<br />
ReleaseNote

## URL ##
http://noticeboard2011.appspot.com/
<br />
<br />
旧バージョンはこちら<br />
http://3.latest.noticeboard2011.appspot.com/

![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-FFDEF.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-FFDEF.png)

機能：
  * 人を追加出来ます。
  * 行き先欄をセレクトボックスで選択すると更新されます。
  * 人の名前の変更が出来ます。
  * 人の名前（ファーストネーム）で前方一致検索が出来ます。
  * 朝4時に全員の行き先を自動的に初期化します。
  * Login（使う場合には、Googleアカウントを登録する必要があります。）
  * Twitter連携（ダイレクトメッセージ機能で行き先と備考欄を更新できます。）
  * Mailでの行き先更新
  * スケジュール連携
  * 備考欄追加（直接編集可能）
  * 人の削除機能

リリース準備中：
  * 特になし

実装予定の機能：
  * 翌朝の行き先の予約入力
  * 日付表示


アーキテクチャ説明：
SystemArchi

## 利用技術 ##

以下の技術を利用しています。

  * Google App Engine
  * Slim3 (GAEに特化したフレームワーク）
  * Ajax
  * jQuery
  * Flexigrid （jQueryのプラグイン。リッチなテーブルを表現する。）
  * JSONIC (JSONのパースを行う。）

## リリース計画 ##
以下にリリース計画を記述しました。
ReleasePlan
## ソースコードの説明 ##
以下にソースコードの説明を記述しました。
ExplanationOfSourceCode

## 利用イメージ図 ##
cacooで利用イメージ図を描きました。
https://cacoo.com/diagrams/BsUU6L59vT0eFsoc