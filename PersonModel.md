コード説明のTOPへ ExplanationOfSourceCode
<br />
次のページへ CreatePerson


&lt;hr/&gt;


# PersonModelのソースコード説明 #

行き先表示板アプリは、"Person"（人の情報）をモデルとして保有する。<br />
モデルはDatastoreのカインド（RDBのエンティティ）に対応する。
<br />
slim3のモデルは、Datastoreへの永続化やDatastoreからの検索が可能になっている。<br />
Personモデルは、現時点では、以下の項目を保有している。
  * Key (Datastoreのキー）
  * firstName （名）
  * lastName　（姓）
  * place　（居場所）
  * version　（排他制御用のバージョン番号）


# ソースコード #

```
package org.noticeboard2011.model;

import java.io.Serializable;

import org.noticeboard2011.util.StringUtil;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

import com.google.appengine.api.datastore.Key;

/**
 * Personモデル
 * 
 * @author akiraabe
 *
 */
@Model(schemaVersion = 1)
public class Person implements Serializable {

    private static final long serialVersionUID = 3492243894122238997L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private String firstName;
    private String lastName;
    private String place;

    // View層がjspとは限らないので、サニタイジングが必要なので、
    // getterでサニタイジングをするようにした。
    // 以下は、firstNameのgetterの例。
    public String getFirstName() {
        return StringUtil.htmlEscape(firstName);
    }

    // この他にも、フィールドのsetter,getterなどのメソッドがあるが省略。

}

```

## 説明 ##

ソースコードを見ると分かるとおり、モデルは通常のJavaBeansにアノテーション（@Model、@Attribute）を追加したものである。<br />
@Modelアノテーションは、このクラスがslim3のモデルであることを表す。<br />
@Attributeアノテーションは、そのパラメータにより、primaryKeyであること、version番号用のフィールドであることなどを表す。

## 用例 ##

Datastoreへの永続化の場合には以下のようにコーディングする。
```
        // モデルのインスタンス生成
        Person person = new Person();
        // フィールドへの値のセット（keyとversionは自動設定）
        person.setFirstName("James");
        person.setLastName("Brown");
        person.setPlace("Heaven?");

        // Transactionを開始して、Datastore.putにより永続化
        Transaction tx = Datastore.beginTransaction();
        Datastore.put(tx, person);
        tx.commit();
```

Datastoreからの取得（key指定）は以下のようにコーディングする。
```
    // PersonMetaをインスタンス変数に保有
    private PersonMeta personMeta = PersonMeta.get();

    // keyとバージョン番号を取得し、Datastoreからgetする
    public Person get(Key key, Long version) {
        return Datastore.get(personMeta, key, version);
    }
```

## slim3を用いた開発手順 ##
build.xmlの"gen-model"ターゲットを実行することにより、コードのテンプレートが自動生成される。（自動生成せずに完全に手作業でコーディングすることも可能だが、自動生成する方が楽である。）手順の詳細は、以下のURLを参照。<br />
http://sites.google.com/site/slim3documentja/getting-started/creating-form-1
<br />
また、モデルを生成すると、Annotation Processing Toolによってモデルのメタデータが自動的に生成される。（eclipseの機能を使って自動的に実行・生成される。）メタデータについては、以下のURLを参照。<br />
http://sites.google.com/site/slim3documentja/documents/slim3-datastore/defining-data-classes#TOC-2
<br />
http://sites.google.com/site/slim3documentja/documents/slim3-datastore
<br />
メタデータを簡単に説明すると、モデルとエンティティのマッピングメソッドが含まれているクラスである。

## その他参考(URL) ##
http://sites.google.com/site/slim3documentja/documents/slim3-datastore/defining-data-classes
<br />
http://sites.google.com/site/slim3documentja/documents/slim3-datastore/creating-getting-and-deleting-model#TOC-1
<br />


&lt;hr/&gt;


コード説明のTOPへ ExplanationOfSourceCode
<br />
次のページへ CreatePerson