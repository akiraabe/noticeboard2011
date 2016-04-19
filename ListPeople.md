コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ CreatePerson
<br />
次のページへ UpdatePlace


&lt;hr/&gt;


# 行き先表示板の出力機能の説明 #

行き先表示板出力機能の説明

## 前提知識 ##
ここでは、以下の要素技術を使っている。詳細は、後述のURLを参照。
  * jQuery
  * flexigrid(jQuery Plugin)
  * jQuery UI Dialog
  * JSON
  * JSONIC（JSONのパーサ）

### jQuery ###
本家 http://jquery.com/
<br />
日本語リファレンス http://semooh.jp/jquery/

### flexigrid ###
本家 http://www.flexigrid.info/
<br />
マイコミジャーナルの紹介記事 http://journal.mycom.co.jp/articles/2008/06/25/flexigrid/index.html

### jQuery UI Dialog ###
http://jqueryui.com/demos/dialog/
<br />
jQuery UIの紹介記事（技術評論社） http://gihyo.jp/dev/feature/01/jquery-ajax/0005
### JSON ###
JSONの紹介 http://www.json.org/json-ja.html

### JSONIC（JSONのパーサ） ###
http://jsonic.sourceforge.jp/
<br />
マイコミジャーナルの紹介記事 http://journal.mycom.co.jp/articles/2008/04/09/jsonic/index.html

## 処理の流れ ##
![https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-41E41.png](https://cacoo.com/diagrams/BsUU6L59vT0eFsoc-41E41.png)
  1. アクターは、http://noticeboard2011.appspot.com/ のURLでシステムにリクエストする。
    1. index.htmlはflexigridによりhtmlのテーブルのDOMオブジェクト(#tabledata)を生成する。
  1. #tabledataは、IndexControllerへpostする。
  1. IndexControllerは以下の操作を順に行う。
    1. privateメソッドのgetPersonListを呼び出し、人の一覧を取得する。
      1. PersonServiceのgetPersonListメソッドに委譲する。
    1. privateメソッドのgenerateJsonDataを呼び出し、JSON形式のデータ（ValueObject）を生成する。
    1. Superクラスのjsonメソッドを呼び出し、JSON形式のデータを出力する。
  1. index.htmlのpreProcess()は、サーバサイドから受け取ったJSON形式データを加工し、行き先欄をリストボックス形式に変換する。
  1. index.htmlのpostProcess()は、行き先の背景色を設定する。

## ソースコード ##
### index.html（説明用のコメントを追記してある。） ###
```
001: <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
002: <html xmlns="http://www.w3.org/1999/xhtml">
003: <head>
004: <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
005: <title>noticeboard2011</title>
006: <meta http-equiv="Content-Style-Type" content="text/css">
007: <meta http-equiv="Content-Script-Type" content="text/javascript">
008: <link href="css/flexigrid/flexigrid.css" rel="stylesheet"
009: 	type="text/css">
010: <link type="text/css" href="css/smoothness/jquery-ui-1.8.7.custom.css"
011: 	rel="stylesheet" />
012: <script type="text/javascript" src="js/jquery-1.4.4.min.js"></script> <script
013: 	type="text/javascript" src="js/jquery-ui-1.8.7.custom.min.js"></script>
014: <script type="text/javascript" src="js/flexigrid.pack.js"></script> <script
015: 	type="text/javascript">
016: <!--
017: /*  
018:  * ページのLoad時に呼び出される処理
019:  */
020: $(document).ready(function() {
021: 	$("#datatable").flexigrid({ // id="datatable"のtableタグに対して、flexigridを適用する
022: 		autoload: true, // HTML表示時に自動的に読み込みを開始するかどうか。
023: 		url: "index", // Ajax でリクエストされるURL。XML もしくは JSON 出力
024: 		method: "post", // 送信メソッド（デフォルトはpost）
025: 		dataType: "json", // 読み込むデータのタイプ。（json or xml）
026: 		/*
027: 		colModel
028: 			列の設定オブジェクトをもつ配列
029: 			display：列の表示名
030: 			name：列のユニークID（テーブルカラム名）必須
031: 			width：列幅
032: 			sortable：列のソートを有効にするかどうか
033: 			align：文字の配置
034: 			hide：非表示にするかどうか
035: 		*/
036: 		colModel: [
037: 					{display: "id", name : "id", width : 30, sortable : false, align: "center", hide : false},
038: 					{display: "version", name : "version", width : 50, sortable : false, align: "center", hide : true},
039: 					{display: "ファーストネーム", name : "firstName", width : 150, sortable : true, align: "center"},
040: 					{display: "ラストネーム", name : "lastName", width : 150, sortable : false, align: "center"},
041: 					{display: "行き先", name : "place", width : 100, sortable : false, align: "center"},
042: 					{display: "hidden", name : "place2", width : 10, sortable : false, align: "center", hide: true}
043: 		],
044: 		/*
045: 		buttons
046: 			ヘッダにボタンを表示する際のボタン情報を格納した配列
047: 			name：ボタン表示文字
048: 			bclass：ボタンの表示クラス
049: 			onpress：ボタンを押された際に呼び出されるメソッド
050: 			separator：セパレータを表示
051: 		*/
052: 		buttons : [
053: 					{name: 'Add', bclass: 'add', onpress : test},
054: 					{name: 'Edit', bclass: 'edit', onpress : editPressed},
055: 					{name: 'Delete', bclass: 'delete', onpress : deletePressed},
056: 					{separator: true}
057: 		],
058: 		/*
059: 		searchitems
060: 			検索対象となる列の配列
061: 			display：検索対象を選択するセレクトボックスの表示文字
062: 			name：列のユニークID（テーブルカラム名）必須
063: 			isdefault：初期選択されているかどうか。
064: 		*/
065: 		searchitems : [  
066:              {display: 'ファーストネーム', name : 'firstName'}
067:         ],  
068: 		usepager: true, // ページャーを使用するかどうか。
069: 		title: "noticeboard", // テーブルのキャプション。設定するとタイトル行が表示される。
070: 		useRp: true, // 1ページに表示する行数を変更できるセレクトボックスを表示するかどうか。
071: 		rp: 10, // 1ページに表示する行数。
072: 		rpOptions: [3,5,10,20,30], // useRp を有効にした際のセレクトボックス値の配列。
073: 		pagestat: "{from} ～ {to} を表示 (総数: {total} )", // 現在表示しているページの説明文フォーマット。表示開始行：{from}、表示終了行：{to}、総数：{total}が利用できる。
074: 		procmsg: "しばらくお待ちください……", // 読み込み中時フッターに表示されるメッセージ。
075: 		nomsg: "項目はありません。", // 検索データが存在しなかった場合のメッセージ。
076: 		showTableToggleBtn: true, // テーブルの表示非表示ボタンを設置するかどうか。title に文字が設定されている場合のみ有効。
077: 		singleSelect: true, // 行の複数選択を可能にするかどうか。
078: 		width: 500, // テーブルの横幅。数値もしくは auto が利用可能。auto に設定した際は横のリサイズは無効。
079: 		height: "auto", // テーブルの高さ。数値もしくは auto が利用可能。auto に設定した際は縦のリサイズは無効。
080: 		preProcess: preProcess // データを受信した直後にデータを成形するため（と思われる）に呼び出される。
081: 		,onSuccess: postProcess // 表示が成功した際に呼び出されるイベントハンドラメソッド。
082: 	});
083: 
084: 	//リストボックスで値を変更したら、update関数を呼び出す。
085: 	$("#datatable").change(update);
086: 
087: 	//編集ダイアログの準備
088: 	$("#edit-dialog").dialog({ // id="edit-dialog"のdivタグに対して、dialogを適用する
089: 		modal:true, // モーダル画面にするかどうか。
090: 		autoOpen:false, // 自動的にダイアログを開くかどうか。
091: 		resizable:false, // リサイズを可能とするかどうか。
092: 		draggable:true, // ドラッグを可能とするかどうか。
093: 		title:"編集入力",
094: 		width: 450,
095: 		show:"scale", // ダイアログを表示するときの効果。
096: 		hide:"scale", // ダイアログを閉じるときの効果。
097: 		buttons: { // ボタンの配置と押したときの振る舞いを定義する。
098: 		"OK": function() {
099: 			// ダイアログに入力された値を変数に保存
100: 			var id = $("#id").val();
101: 			var firstName = $("#firstNameInput").val();
102: 			var lastName = $("#lastNameInput").val();
103: 			//alert('id:' + id + ' name:' + firstName + lastName);
104: 			jQuery.post("person/update", // jQueryのajax通信をpostメソッドで行う。引数は、url,data,handler,dataTypeである。
105: 					{"command":"PROPERTY",
106: 				     "id":id, 
107: 				     "firstName":firstName, 
108: 				     "lastName":lastName},
109: 				     function(data, status) {
110: 				    	 //alert(status);
111: 				    	 //alert(data.returncode);
112: 				    	 if (data.returncode == 'INVALID') {
113: 					    	 alert('VALIDATION ERROR');
114: 				    	 }
115: 				    	 $("#datatable").flexReload();
116: 				     },
117: 				     'json');
118: 			$(this).dialog("close"); // ダイアログを閉じる。
119: 		},
120: 		"Cancel": function() {
121: 			$(this).dialog("close");
122: 		}
123: 	}
124: 	});
125: 
126: });
127: 
128: /*
129:  * 行き先の更新処理
130:  */ 
131: function update(e) {
132: 	var target = $(e.target); // 更新された対象のオブジェクトを取得
133:     var col = target.parents("tr"); // 更新対象オブジェクトの親のtrタグの要素を取得する。（つまりテーブルの更新対象の行を取得したこととなる。）
134:     var cell = col[0].children; // 上記の子要素の配列[0]の要素を取得する。
135:     var id = $(cell[0]).text(); // 上記の要素の文字列を取得する。（つまり、テーブルのidの値が取得される）
136:     var firstName = $(cell[2]).text(); // テーブルの行の配列[2]の要素を取得する。（つまり、ファーストネームが取得される）
137:     //alert($(cell[2]).text() + 'の行き先を' + target.context.value + 'に変更しました。');
138:     jQuery.post("person/update", // jQueryのajax通信をpostメソッドで行う。（詳細は前述）
139:     	    {"command":"PLACE", 
140: 	         "firstName":firstName,
141: 	         "id":id,
142: 	         "place":target.context.value}
143: 	         );
144: 
145:     // 背景色更新（更新対象の値に応じて、cssの更新を行い、背景色を更新する）
146:     switch(target.context.value) {
147:     case 'Absent':
148: 		$(e.target).css("background-color","red");
149: 		break;
150: 	case 'Present':
151: 		$(e.target).css("background-color","yellow");
152: 		break;
153: 	case 'Meeting':
154: 		$(e.target).css("background-color","pink");
155: 	}
156: }
157: 
158: function test() {
159: 	alert('未実装です！');
160: 	$.get('person/create');
161: }
162: 
163: /*
164:  * 編集ボタンが押されたときの処理
165:  */
166: var editPressed = function(command, grid) {
167: 	var items = $('.trSelected', grid);
168: 	var itemlist = [];
169: 	var id = $(items[0].cells[0]).text();
170: 	//TODO 以下の２行は列の並べ替えをされると正しく動かなくなる。
171: 	var firstName = $(items[0].cells[2]).text();
172: 	var lastName = $(items[0].cells[3]).text();
173: 	$("#id").val(id);
174: 	$("#firstNameInput").val(firstName);
175: 	$("#lastNameInput").val(lastName);
176: 	$("#edit-dialog").dialog("open");
177: }
178: 
179: /*
180:  * 削除ボタンが押された時の処理
181:  */
182: var deletePressed = function(command, grid) {
183:     var items = $('.trSelected',grid);
184:     var itemlist = [];
185:     var param = $(items[0].cells[0]).text();
186:     $('#delete-confirm-dialog').text($(items[0].cells[2]).text() + 'を削除しますか？');
187:     $('#delete-confirm-dialog').dialog({
188:     	title:'削除確認',
189:     	width:400,
190:     	buttons:{
191:     		'はい':function(){
192:     			items.fadeOut('slow')
193:         		$.post('person/delete', // エラーハンドリングが必要ならば$.ajaxを使う
194:              		  { method:'delete',
195:               		   deleteItems:param},
196:             		   function(data, status) {
197:                 		   if (data.returncode == 'NOTIMPLEMENT') {
198:                     		   alert('削除処理はサーバサイドでまだ実装されていません。');
199:                 		   }
200:                 		   $("#datatable").flexReload();
201:                 	   },
202:          		      'json');
203:     			$(this).dialog("close");
204: 				},
205:     		'いいえ':function() { $(this).dialog("close"); }
206:     	},
207:     	modal:true
208:     });
209: }
210: 
211: /*
212:  * flexigridの初期処理（JSONデータを受け取った後で、かつ、テーブルを表示する前に呼ばれる）
213:  */
214: function preProcess( data ) {
215: 	$.each (data.rows,
216: 		function(i, val) {
217: 			val.cell[5] = val.cell[4];
218: 			val.cell[4] = '<select id="sel" class="sel"><option value="undef">---</option><option value="Absent">Absent</option><option value="Present">Present</option><option value="Meeti219: ng">Meeting</option>';
220: 		}
221: 	);
222: 	return data;
223: }
224: 
225: /*
226:  * flexigridの表が表示された後の処理
227:  */
228: function postProcess()  {
229: 
230: 	// 行き先の背景色を設定する
231:     //var elements  = document.getElementsByClassName("drop");
232:     var elements  = $(".sel");
233:     //alert(elements.length);
234: 	for (var i = 0; i < elements.length ; i++) {
235: 		//[5]の値を抽出
236: 		var col = $(elements[i]).parents("tr");
237: 		var cell = col[0].children;
238: 		var param = $(cell[5]).text();
239: 		$(elements[i]).val(param);
240: 		switch(param) {
241: 		case 'Absent':
242: 			$(elements[i]).css("background-color","red");
243: 			break;
244: 		case 'Present':
245: 			$(elements[i]).css("background-color","yellow");
246: 			break;
247: 		case 'Meeting':
248: 			$(elements[i]).css("background-color","pink");
249: 		}
250: 	}
251: }
252: 
253: //-->
254: </script>
255: </head>
256: <body>
257: <p>
258: <h3>noticeboard2011</h3>
259: </p>
260: <table id="datatable" style="display: none"></table>
261: <br />
262: <a href="person/create">create</a>
263: <div id="delete-confirm-dialog"></div>
264: <div id="edit-dialog">編集内容を入力してください。 <input type="hidden" id="id" />
265: <br />
266: FirstName <input type="text" id="firstNameInput" size="20" /> <br />
267: LastName <input type="text" id="lastNameInput" size="20" /></div>
268: </body>
269: </html>
```

### IndexController.java ###
```
package org.noticeboard2011.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.noticeboard2011.model.Person;
import org.noticeboard2011.service.PersonService;
import org.noticeboard2011.vo.ResultVo;
import org.slim3.controller.Navigation;

public class IndexController extends AbstractJsonController {

    static Logger logger = Logger.getLogger(IndexController.class.getName());

    private PersonService personService = new PersonService();

    @Override
    protected Navigation run() throws Exception {

        logger.fine("IndexController#run start.");

        // requestパラメータの取得
        int page = new Integer(request.getParameter("page"));
        int rp = new Integer(request.getParameter("rp"));
        String sortorder = request.getParameter("sortorder");
        String qtype = request.getParameter("qtype");
        String query = request.getParameter("query");
        logger.fine("page : " + page);
        logger.fine("rp : " + rp);
        logger.fine("sortorder : " + sortorder);
        logger.fine("qtype : " + qtype);
        logger.fine("query : " + query);

        // ページング用の開始位置の算出
        int startIndex = rp * (page - 1);

        // Datastoreからのデータ取得
        List<Person> people = getPersonList(rp, sortorder, startIndex, query);

        // JSON形式のデータ作成(for flexigrid)
        ResultVo data = generateJsonData(people);

        // JSON形式データの出力処理
        json(data);

        return null;
    }

    private List<Person> getPersonList(int rp, String sortorder, int startIndex, String query) {
       return personService.getPersonList(startIndex, rp, query, sortorder);
    }
    
    private ResultVo generateJsonData(List<Person> people) {
        ResultVo data = new ResultVo();
        data.setPage((String) request.getAttribute("page"));
        data.setTotal(personService.count(request.getParameter("query")));

        // データが入っている配列を作成する
        List array = new ArrayList();

        for (Person person : people) {
            String[] strArray = new String[5];
            strArray[0] = new Long(person.getKey().getId()).toString();
            strArray[1] = person.getVersion().toString();
            strArray[2] = person.getFirstName();
            strArray[3] = person.getLastName();
            strArray[4] = person.getPlace();
            array.add(strArray);
        }

        for (int i = 0; i < array.size(); i++) {
            data.addRow(i, (String[]) array.get(i));
        }
        return data;
    }

}

```

### 関連事項 ###
JsonDataSample JSON形式のデータ



&lt;hr/&gt;


コード説明のTOPへ ExplanationOfSourceCode
<br />
前のページへ CreatePerson
<br />
次のページへ UpdatePlace