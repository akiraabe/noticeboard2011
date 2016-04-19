# e-mailからの更新 #

e-mail送信により行き先の更新を行う。<br />
GAEアプリはメールの送受信が可能である。<br />
今回は、メールの受信機能を用いている。

# 詳細 #

## 機能概要 ##

![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-7344F.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-7344F.png)

  * GAE宛てのメールはサーブレットのリクエストに変換される。
  * request.getInputStream()でメールの内容を取り出せる。

## ソースコード ##

```
package org.noticeboard2011.controller.mail;

//import文は省略してある。

public class ReceiveController extends Controller {
    
    static Logger logger =
        Logger.getLogger(ReceiveController.class.getName());
    private PersonService personService = new PersonService();
    
    @Override
    public Navigation run() throws Exception {
        
        logger.fine("ReceiveController start...");
        
        // ユーザ情報取得
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            logger.fine("user : " + user.getEmail());
        }
        
        // mail 受信
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session, request.getInputStream());
        
        logger.fine("subject : " + message.getSubject());
        logger.fine("from : " + message.getFrom()[0].toString());
        logger.fine("to : " + request.getParameter("address"));
        
        String toAddress = request.getParameter("address");
        int length = toAddress.indexOf("@");
        personService.updatePlaceFromTwitter(toAddress.substring(0,length), message.getSubject());
        return null;
    }
}

```