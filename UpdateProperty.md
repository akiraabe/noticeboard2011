コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ UpdatePlace


&lt;hr/&gt;


# 人の属性の更新機能 の説明 #

ここでは人の属性の更新機能 について説明する。<br />

## 前提知識 ##
特になし。（その他の機能で得られた知識で理解可能）

## 処理の流れ ##
![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-B0A6B.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-B0A6B.png)
事前条件として、行き先表示板が表示されていること。

  1. アクターは、http://noticeboard2011.appspot.com/ で表示される「行き先表示板」の上部にある「Edit」ボタンを押下する。
  1. index.html（のjavascript）は、DOMオブジェクト(#tabledata)のEditの検知をする。
  1. #tabledataはindex.htmlのeditPressed()を呼び出す。（Callbackする）
    1. DOMオブジェクト#edit-dialogをopenする。（アクターからは編集用ダイアログが開いたように見える）
  1. アクターは、編集用ダイアログから変更内容を入力する。
  1. アクターが「OK」ボタンを押下した場合は、以下の処理を行う。
    1. #edit-dialogは、UpdateControllerのrunメソッドを呼び出す。
      1. UpdateControllerはprivateメソッドのvalidate()を呼び出し、精査を行う。
      1. 精査結果がOK（Valid）の場合には、PersonServiceのupdatePropertyメソッドに委譲し、Datastoreの更新を行う。
      1. UpdateControllerは、精査結果をJSON形式のデータとしてindex.htmlへ返却する。
    1. #edit-dialogは、#tabledataのflexReload()を呼び出し、行き先表示板をReloadする。
    1. #edit-dialogは、close()メソッドを自己呼び出しして、ダイアログを閉じる。
  1. アクターが「Cancel」ボタンを押下した場合は、以下の処理を行う。
    1. #edit-dialogは、close()メソッドを自己呼び出しして、ダイアログを閉じる。




&lt;hr/&gt;


コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ UpdatePlace