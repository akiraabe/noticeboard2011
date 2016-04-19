# Twitterとの連携方法の説明 #

Twitterとの連携方法を説明する。


# 詳細 #

## 機能の概念図 ##
![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-1922E.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-1922E.png)

  * ユーザと@noticeboard2011(Twitter上のid)は相互にフォローしていることが前提。
  * ユーザは、@noticeboard2011に対して、ダイレクトメッセージを送る。
    * メッセージの書式は、[Absent|Present|Meeting] + 半角スペース + 自由記入
  * noticeboard2011のGAEアプリは、Twitterに対して自分宛のダイレクトメッセージをポーリングし、新しいメッセージが届いていたら、その内容に応じてボードを更新する。
    * この処理は、GAEのcron機能で定期的に実行している。

## TwitterのAPI利用 ##
TwitterはAPIを公開しているので、連携は比較的容易である。<br />
APIの実装は色々あるが、Twitter4Jを使用した。<br />
http://twitter4j.org/ja/index.html

## ソースコード説明 ##
Twitter連携は以下のようにシンプルな処理である。
<br />
（ソースコード中に認証情報をハードコーディングしている関係上、svnにはupしていない。）
```
package org.noticeboard2011.controller.cron;

//import文は省略

public class GetDirectMessagesController extends Controller {
    static Logger logger =
        Logger.getLogger(GetDirectMessagesController.class.getName());
    private PersonService personService = new PersonService();

    static String consumerKey = "????????";
    static String consumerSecret = "????????";

    static String accessToken =
        "????????";
    static String accessTokenSecret =
        "????????";

    @Override
    public Navigation run() throws Exception {

        logger.fine("GetDirectMessagesController start...");
        
        // Twitter連携
        TwitterFactory TwitterFactory = new TwitterFactory();
        Twitter twitter = TwitterFactory.getOAuthAuthorizedInstance(consumerKey, consumerSecret, new AccessToken(accessToken, accessTokenSecret));
        
        List<DirectMessage> directMessages =twitter.getDirectMessages();
        
        for (DirectMessage directMessage : directMessages) {
            logger.fine("senderScreenName : " + directMessage.getSenderScreenName());
            logger.fine("id     : " + directMessage.getId());
            logger.fine("text   : " + directMessage.getText());
            personService.updatePlaceFromTwitter(directMessage.getSenderScreenName(),directMessage.getText());
            twitter.destroyDirectMessage(directMessage.getId());
        }
        
        return null;
    }
}
```