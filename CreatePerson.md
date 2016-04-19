コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ PersonModel
<br />
次のページへ ListPeople


&lt;hr/&gt;


# 人の登録機能の説明 #

ここでは人の登録機能について説明する。<br />

## 前提知識 ##
### URLマッピングについて ###
人の登録機能は、<br />
http://noticeboard2011.appspot.com/person/create
<br />
というURLにより呼び出しが可能である。
<br />
<br />
Slim3の場合には、リクエストURLパスをコントローラに自動的にマッピングしている。この例の場合には、上記URLから、{ルートパッケージ}.controller.person.CreateControllerにマッピングされる。<br />
マッピングルールの詳細は以下を参照。<br />
http://sites.google.com/site/slim3documentja/documents/slim3-controller/url-mapping

### Slim3のコントローラ ###
Slim3のコントローラはサーブレットの薄いラッパーである。従って、サーブレットによる開発の知識がそのまま適用できる。

## 処理の流れ ##
![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-FA655.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-FA655.png)
  1. アクターは、http://noticeboard2011.appspot.com/person/create のURLでシステムにリクエストする。システムは、上記リクエストにより{ルートパッケージ}.controller.person.CreateControllerに制御を移す。
    1. CreateControllerは、create.jsp（入力用の画面）を表示する。
  1. アクターは、入力用画面に対して必要項目を入力する。
  1. アクターは、「登録」ボタンを押下する。
    1. システムは登録リクエストを受付、InsertControllerに制御を移す。
      1. InsertControllerはvalidateメソッドを呼び出し、入力内容を精査する。
      1. OKであれば、PersonServiceのinsertメソッドに委譲しDatastoreへの永続化を行い、行き先表示画面に遷移（リダイレクト）する。
      1. 精査NGであれば、エラーメッセージとともに入力用の画面に戻る。

## ソースコード ##
### CreateController.java ###
```
package org.noticeboard2011.controller.person;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

/**
 * {basePath}/create のURLによって呼ばれるコントローラ<br/>
 * <li>create.jspに遷移する</li>
 * @author akiraabe
 */
public class CreateController extends Controller {

    @Override
    public Navigation run() throws Exception {
        return forward("/person/create.jsp");
    }
}
```

CreateControllerのrunメソッドが呼ばれる。
ここでは、単純にcreate.jspに遷移（forward）しているだけ。

### create.jsp ###
```
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>person Create</title>
</head>
<body>
<p>Personの新規作成</p>
<hr/>
<ul>
<c:forEach var="e" items="${f:errors()}">
<li>${f:h(e)}</li>
</c:forEach>
</ul>

<form method="post" action="insert">
<table>
	<tr>
		<td> ファーストネーム </td>
		<td>
			<input type="text" ${f:text("firstName")} class="${f:errorClass('firstName', 'err')}" size="40" maxlength="20" />
		</td>	
	</tr>
	<tr>
		<td> ラストネーム </td>
		<td>
			<input type="text" ${f:text("lastName")} class="${f:errorClass('lastName', 'err')}" size="40" maxlength="20" />
		</td>	
	</tr>
	<tr>
		<td> 行き先 </td>
		<td>
			<input type="text" ${f:text("place")} class="${f:errorClass('place', 'err')}" size="40" maxlength="20" />
		</td>	
	</tr>
</table>
<input type="submit" value="登録"/>
</form>
<br/>
<hr/>
<a href="${f:url('/')}">index</a>
</body>
</html>
```

上記コードの、
```
<ul>
<c:forEach var="e" items="${f:errors()}">
<li>${f:h(e)}</li>
</c:forEach>
</ul>
```
の部分により、バリデーションエラー時のメッセージが表示される。

### InsertController.java ###
```
package org.noticeboard2011.controller.person;


import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;
import org.slim3.util.RequestMap;

/**
 * {basePath}/insert のURLによって呼ばれるコントローラ<br/>
 * <li>入力内容のバリデーションを行う</li>
 * <li>PersonのEntityを生成する</li>
 * <li>Top画面に遷移（redirect）する</li>
 * 
 * @author akiraabe
 */
public class InsertController extends Controller {
    
    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {
        if (!validate()) {
            return forward("create.jsp");
        }
        Person person = personService.insert(new RequestMap(request));
        return redirect("/");
    }
    
    private boolean validate() {
        Validators v = new Validators(request);
        v.add("firstName", v.required());
        v.add("lastName", v.required());
        v.add("place", v.required());
        return v.validate();
    }
}

```

まず最初に、privateメソッドのvalidate()を呼び出す。<br />
エラーがある場合には、もとの画面（create.jsp）に遷移する。<br />
エラーが無い場合には、PersonServiceのinsertメソッドを呼び出す。引数は、requestパラメータのMapである。<br />
その後に、ルート（行き先表示画面）にリダイレクトしている。

### PersonService.java ###



&lt;hr/&gt;


コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ PersonModel
<br />
次のページへ ListPeople