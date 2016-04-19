# Googleカレンダー連携 #

Googleカレンダーに登録してある情報を自動的にチェックし、行き先の更新を行う。

# 詳細 #

カレンダーをまめに入力している人にとっては、noticeboard2011への転記は無駄な作業である。<br />
従って、Googleカレンダーの内容を、自動的に行き先に反映する機能を開発した。

## 機能概要 ##
![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-F7163.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-F7163.png)

## ソースコード ##
コードが長く可読性が低いため、今後、クラスの分割、メソッド抽出などのリファクタリングを行う予定である。
```
package org.noticeboard2011.controller.cron;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

/**
 * GoogleCalendarから現在の居場所を取得し更新する。
 * 
 * @author akiraabe
 */
public class RetrieveGoogleCalendarController extends Controller {

    private final String GOOGLE_ACCOUNT = "管理者のメールアドレス";
    private final String GOOGLE_PASSWORD = "************";

    static Logger logger =
        Logger.getLogger(RetrieveGoogleCalendarController.class.getName());

    private PersonService personService = new PersonService();

    @Override
    public Navigation run() throws Exception {

        // GoogleCalendar連携
        try {

            Person person =
                personService.get(new Long(request.getParameter("id")));

            if (person.isPreparedForGoogleCalendar()) {

                // Googleカレンダーからのeventデータ取得
                CalendarEventFeed resultFeed = getCalendarEventFeed(person);

                // 取得件数チェック
                logger.fine("***** カレンダー取得結果 *****");
                logger.fine("イベント数:" + resultFeed.getEntries().size());
                // TODOイベントが複数取れたらどうする？
                logger.fine("タイトル");

                // //////////////////////////////////////
                // 編集用の変数定義
                String title = null;
                String startTime = null;
                String endTime = null;
                Date expirationDate = null;
                String whereString = null;

                // Event内容をparse
                Iterator<CalendarEventEntry> it =
                    resultFeed.getEntries().iterator();
                while (it.hasNext()) {
                    CalendarEventEntry entry = it.next();
                    title = entry.getTitle().getPlainText();
                    logger.fine("TITLE : " + title);

                    for (When when : entry.getTimes()) {
                        startTime =
                            when.getStartTime().toString().substring(11, 16);
                        endTime =
                            when.getEndTime().toString().substring(11, 16);
                        logger.fine("startTime: " + startTime);
                        logger.fine("endTime: " + endTime);
                        expirationDate = new Date(when.getEndTime().getValue());
                    }

                    for (Where where : entry.getLocations()) {
                        whereString = where.getValueString();
                        logger.fine("where: " + where.getValueString());
                    }
                    // logger.fine("content: " + entry.getPlainTextContent());

                }
                if (expirationDate == null) {
                    expirationDate = new Date(DateTime.now().getValue());
                }
                // //////////////////////////////////////

                // イベント件数により処理が異なる
                if (resultFeed.getEntries().size() > 0) {

                    String memo =
                        "Absent "
                            + title
                            + ", "
                            + whereString
                            + ", "
                            + startTime
                            + "~"
                            + endTime;

                    updatePerson(person, expirationDate, memo, true);
                } else {

                    logger.fine("イベント0件");
                    String memo = "undefined 未設定です。";
                    if (person.hasEventNow()) {
                        logger.fine("イベント0件かつ、hasEventNow==True なので更新に行きます。");
                        updatePerson(person, expirationDate, memo, false);
                    } else {
                        logger.fine("イベント0件かつ、hasEventNow==False なので更新しません。");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("ERROR");
            logger.severe(e.getMessage());
        }

        return null;
    }

    /**
     * Googleカレンダーからのeventデータ取得
     * 
     * @param person
     * @return
     * @throws MalformedURLException
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServiceException
     */
    private CalendarEventFeed getCalendarEventFeed(Person person)
            throws MalformedURLException, AuthenticationException, IOException,
            ServiceException {

        // CalendarQueryの作成
        CalendarQuery myQuery = createCalendarQuery(person);

        // CalendarServiceに委譲しCalendarEventFeed取得
        CalendarEventFeed resultFeed = doQuery(myQuery);

        return resultFeed;
    }

    /**
     * CalendarQueryを作成する
     * 
     * @param person
     * @return
     * @throws MalformedURLException
     */
    private CalendarQuery createCalendarQuery(Person person)
            throws MalformedURLException {

        String googleCalUrl =
            "http://www.google.com/calendar/feeds/"
                + person.getMailAddress()
                + "/private/full";

        logger.fine("RetrieveGoogleCalendarController start...");
        logger.fine("url : " + googleCalUrl);

        URL feedUrl = new URL(googleCalUrl);
        CalendarQuery myQuery = new CalendarQuery(feedUrl);

        myQuery.setMaximumStartTime(DateTime.now());
        myQuery.setMinimumStartTime(DateTime.now());

        return myQuery;
    }

    /**
     * CalendarServiceに委譲しCalendarEventFeed取得
     * 
     * @param myQuery
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServiceException
     */
    private CalendarEventFeed doQuery(CalendarQuery myQuery)
            throws AuthenticationException, IOException, ServiceException {
        CalendarService myService = new CalendarService("calendertest");
        myService.setUserCredentials(GOOGLE_ACCOUNT, GOOGLE_PASSWORD);

        long start = System.currentTimeMillis();

        CalendarEventFeed resultFeed =
            myService.query(myQuery, CalendarEventFeed.class);

        long end = System.currentTimeMillis();
        logger.fine("処理時間:");
        logger.fine(end - start + "ms");
        return resultFeed;
    }
    
    /**
     * Personの更新処理
     * 
     * @param person
     * @param expirationDate
     * @param memo
     * @param hasEventNow
     */
    private void updatePerson(Person person, Date expirationDate, String memo,
            Boolean hasEventNow) {
        
        logger.fine("expirationDate : " + expirationDate);
        logger.fine("memo : " + memo);
        logger.fine("hasEventNow : " + hasEventNow);
        
        if (person.getExpirationDate() == null
            || expirationDate.after(person.getExpirationDate())) {
            personService.updateFromGoogleCalendar(
                person.getTwitterId(),
                memo,
                expirationDate,
                hasEventNow);
            logger.fine("期限後なのでリフレッシュしました！");
        } else {
            logger.fine("期限前なのでリフレッシュしませんでした");
        }
    }
}


```