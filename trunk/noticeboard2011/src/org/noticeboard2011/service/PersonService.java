package org.noticeboard2011.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.noticeboard2011.meta.PersonMeta;
import org.noticeboard2011.model.Person;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;
import org.slim3.util.BeanUtil;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

/**
 * Personモデルに対するCRUD操作を提供するクラス。
 * 
 * @author akiraabe
 * 
 */
public class PersonService {

    private PersonMeta personMeta = PersonMeta.get();

    /**
     * キーとバージョン番号を指定して、PersonモデルをDatastoreから読み込む。
     * 
     * @param key
     * @param version
     * @return Person
     */
    public Person get(Key key, Long version) {
        return Datastore.get(personMeta, key, version);
    }

    /**
     * idを指定して、PersonモデルをDatastoreから読み込む。
     * 
     * @param id
     * @return Person
     */
    public Person get(Long id) {
        return Datastore.get(Person.class, KeyFactory.createKey("Person", id));
    }

    /**
     * パラメータに従ってDatastoreから読込み、複数件のPersonをList形式で返却する。
     * ページング処理を前提としているので、offsetとlimitの指定は必須である。
     * 
     * @param offset
     *            取得開始行数（必須であり省略不可）
     * @param limit
     *            取得する行数（必須であり省略不可）
     * @param query
     *            検索条件の文字列（firstNameがこの文字列で始まるものが検索対象。空文字の場合には、filterをかけない。）
     * @param sortorder
     *            並び順（昇順か降順かを示す。空文字の場合は、昇順扱いとする。）
     * @return Personのリスト
     */
    public List<Person> getPersonList(int offset, int limit, String query,
            String sortorder) {
        ModelQuery<Person> modelQuery =
            Datastore.query(personMeta).offset(offset).limit(limit);
        if (!"".equals(query)) {
            // 検索文字列が指定されていたら、filterを追加する
            modelQuery =
                modelQuery.filter(personMeta.firstName.startsWith(query));
        }
        if ("desc".equals(sortorder)) {
            // 降順
            modelQuery = modelQuery.sort(personMeta.firstName.desc);
        } else {
            // 昇順
            modelQuery = modelQuery.sort(personMeta.firstName.asc);
        }
        return modelQuery.asList();
    }

    /**
     * 検索条件に応じた総件数を返す。
     * 
     * @param query
     * @return 総件数
     */
    public Integer count(String query) {
        if (!"".equals(query)) {
            return Datastore.query(personMeta).filter(
                personMeta.firstName.startsWith(query)).count();
        } else {
            return Datastore.query(personMeta).count();
        }

    }

    /**
     * 入力値（精査済であることが条件）をDatastoreに永続化する。
     * 
     * @param input
     * @return Person（キー取得済）
     */
    public Person insert(Map<String, Object> input) {
        Person person = new Person();
        BeanUtil.copy(input, person);
        person.setMemo("---");
        person.setTwitterId("xxxx");
        person.setMailAddress("yyy");

        Transaction tx = Datastore.beginTransaction();
        Datastore.put(tx, person);
        tx.commit();

        return person;
    }

    /**
     * 行き先を更新する。
     * 
     * @param id
     * @param place
     */
    public void updatePlace(Long id, String key, String value, String user) {
        Person person =
            Datastore.get(Person.class, KeyFactory.createKey("Person", id));
        if (key.equals("sel")) {
            person.setPlace(value);
        } else {
            person.setMemo(value);
        }
        person.setUpdateBy(user);
        person.setUpdateAt(new Date());
        Datastore.put(person);
    }

    /**
     * Personの属性（姓名）を更新する。
     * 
     * @param id
     * @param firstName
     * @param lastName
     */
    public void updateProperty(Long id, String firstName, String lastName) {
        Person person =
            Datastore.get(Person.class, KeyFactory.createKey("Person", id));
        person.setFirstName(firstName);
        person.setLastName(lastName);
        Datastore.put(person);
    }

    /**
     * 全件分の行き先を初期化する。 早朝に、cronで自動実行することを想定している。
     */
    public void initialize() {
        List<Person> list = Datastore.query(personMeta).asList();
        for (Person person : list) {
            person.setPlace("undefined");
        }
        Datastore.put(list);
    }

    public void updatePlaceFromTwitter(String senderScreenName, String text) {
        Person person =
            Datastore.query(personMeta).filter(
                personMeta.twitterId.equal(senderScreenName)).asSingle();
        Map<String, String> map = parseText(text);
        if (!"".equals(map.get("place"))) {
            person.setPlace(map.get("place"));
        }
        person.setMemo(map.get("memo"));
        Datastore.put(person);
    }

    /**
     * プロトコルに従ってTweetを解析する
     * 
     * @param text
     * @return map
     */
    Map<String, String> parseText(String text) {
        int length = text.indexOf(" ");
        if (length == -1) {
            length = 0;
        }
        Map<String, String> map = new HashMap<String, String>();
        String firstString = text.substring(0, length);
        if ("Absent".equals(firstString)
            || "Present".equals(firstString)
            || "Meeting".equals(firstString)
            || "undefined".equals(firstString)) {
            map.put("place", firstString);
            map.put("memo", text.substring(length + 1));
        } else {
            map.put("place", "");
            map.put("memo", text);
        }
        return map;
    }

    /**
     * List all person
     * 
     * @return　List of person
     */
    public List<Person> getPersonListAll() {
        return Datastore.query(personMeta).asList();
    }

    public List<Person> findByMailAddress(String mailAddress) {
        return Datastore.query(personMeta).filter(
            personMeta.mailAddress.equal(mailAddress)).asList();
    }

    /**
     * Googleカレンダーから取得したデータによる更新処理
     * @param senderScreenName
     * @param text
     * @param expirationDate
     * @param 今Googleカレンダーにイベントがあるか否か
     */
    public void updateFromGoogleCalendar(String senderScreenName, String text,
            Date expirationDate, Boolean eventNow) {
        Person person =
            Datastore.query(personMeta).filter(
                personMeta.twitterId.equal(senderScreenName)).asSingle();
        Map<String, String> map = parseText(text);
        if (!"".equals(map.get("place"))) {
            person.setPlace(map.get("place"));
        }
        person.setMemo(map.get("memo"));
        person.setExpirationDate(expirationDate);
        person.setEventNow(eventNow);
        Datastore.put(person);
    }
}
