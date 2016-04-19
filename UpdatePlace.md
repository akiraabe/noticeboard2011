コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ ListPeople
<br />
次のページへ UpdateProperty


&lt;hr/&gt;


# 行き先の更新機能の説明 #

ここでは行き先の更新機能について説明する。<br />

## 前提知識 ##
特になし。（その他の機能で得られた知識で理解可能）

## 処理の流れ ##
![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-8B1B1.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-8B1B1.png)

事前条件として、行き先表示板が表示されていること。

  1. アクターは、http://noticeboard2011.appspot.com/ で表示される「行き先表示板」上で行き先欄のリストを選択（変更）する。
    1. index.html（のjavascript）は、リストの変更を検知し、DOMオブジェクト(#tabledata)のchangeイベントを発生させる。
      1. index.htmlはupdate()を呼び出す。
    1. index.htmlは、サーバサイドのUpdateControllerのrunメソッドを呼び出す。（サーバへpostする）
      1. UpdateControllerは、リクエストパラメータの精査を行う。
      1. UpdateControllerは、精査がOK（Valid）の場合には、PersonServiceのupdatePlaceメソッドを呼び出し、Datastoreの更新処理を委譲する。
      1. UpdateControllerは、精査結果をJSON形式のデータとしてindex.htmlへ返却する。
    1. index.htmlは、行き先欄の背景色の更新を行う。


## ソースコード ##
### UpdateController.java ###
```
package org.noticeboard2011.controller.person;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.noticeboard2011.controller.AbstractJsonController;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Navigation;
import org.slim3.controller.validator.Validators;

/**
 * {basePath}/update のURLによって呼ばれるコントローラ<br/>
 * <li>入力内容のバリデーションを行う</li>
 * <li>PersonのEntityを更新する</li>
 * <li>非同期処理なので画面遷移は行わない</li>
 * 
 * @author akiraabe
 */
public class UpdateController extends AbstractJsonController {
    
    static Logger logger = Logger.getLogger(UpdateController.class.getName());
    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {
        
        logger.fine("UpdateController#run start.");
        logger.fine("command : " + request.getParameter("command"));
        logger.fine("firstName : " + request.getParameter("firstName"));
        logger.fine("lastName : " + request.getParameter("lastName"));
        logger.fine("id : " + request.getParameter("id"));
        logger.fine("place : " + request.getParameter("place"));
        
        Map<String, String> map = new HashMap<String, String>();
        if (!validate() && "PROPERTY".equals(request.getParameter("command"))) {
            //TODO エラー処理
            map.put("returncode", "INVALID");
            logger.warning("VALIDATION ERROR");
            json(map);
            return null;
        } else {
            map.put("returncode", "VALID");
        }
        
        if ("PLACE".equals(request.getParameter("command")))
            personService.updatePlace(new Long(request.getParameter("id")), request.getParameter("place"));
        
        if ("PROPERTY".equals(request.getParameter("command")))
            personService.updateProperty(new Long(request.getParameter("id")), request.getParameter("firstName"), request.getParameter("lastName"));

        json(map);
        return null;
    }
    
    private boolean validate() {
        Validators v = new Validators(request);
        v.add("firstName", v.required());
        v.add("lastName", v.required());
        return v.validate();
    }

}

```
UpdateControllerは、行き先の更新機能と、人の属性（姓名）の更新機能を共有している。<br />
この２つは、リクエストパラメータの"command"の内容で区別される。
commandが"PLACE"の時に、行き先の更新処理を行っている。<br />
なお、このコードは可読性が低いので、リファクタリング予定である。


&lt;hr/&gt;


コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ ListPeople
<br />
次のページへ UpdateProperty