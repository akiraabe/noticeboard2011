package jsonic4gae.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jsonic4gae.model.Person;
import jsonic4gae.service.PersonService;
import jsonic4gae.vo.ResultVo;
import net.arnx.jsonic.JSON;

import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.ThrowableUtil;

public class HelloController extends Controller {

    private PersonService personService = new PersonService();

    @Override
    protected Navigation run() throws Exception {
        
//        System.out.println("Hello!!!!");

        // TODO　パラメータ"rp"を判定してページングする処理はサーバ側で書かないといけない。 ⇒一応書いた
        // TODO こういう処理を行うユーティリティがあるかも知れないので探す｡｡｡
        // TODO その他にサーバ側もは、sort処理も課せられている。

        // Datastoreからのデータ取得
        // データの取り出し
        int page = new Integer((String) request.getAttribute("page"));
        int rp = new Integer((String) request.getAttribute("rp")); // 通常は３である。
        int startIndex = rp * (page - 1);
        
        List<Person> people = null;
        if ("desc".equals((String) request.getAttribute("sortorder"))) {
            people = personService.getPersonListDesc(startIndex,rp);
        } else {
            people = personService.getPersonList(startIndex,rp);
        }

        ResultVo data = new ResultVo();
        data.setPage((String) request.getAttribute("page"));
 
        // 総件数の取得をする
        data.setTotal(personService.count());

        // データが入っている配列を作成する
        List array = new ArrayList();

        for (Person person : people) {
            String[] strArray = new String[3];
            strArray[0] = person.getFirstName();
            strArray[1] = person.getLastName();
            strArray[2] = person.getPlace();
            array.add(strArray);
        }

        for (int i = 0; i < array.size(); i++) {
            data.addRow(i, (String[]) array.get(i));
        }

        this.json(data);

        return null;
    }

    private void json(Object data) {
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        response.setContentType("application/json; charset=" + encoding);
        try {
            PrintWriter out = null;
            try {
                out =
                    new PrintWriter(new OutputStreamWriter(response
                        .getOutputStream(), encoding));
                out.print(JSON.encode(data));

            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            ThrowableUtil.wrapAndThrow(e);
        }
    }
}
