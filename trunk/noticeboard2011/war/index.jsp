<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>noticeboard2011</title>
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<link href="css/flexigrid/flexigrid.css" rel="stylesheet"
	type="text/css">
<link type="text/css" href="css/smoothness/jquery-ui-1.8.7.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="js/jquery-1.4.4.min.js"></script> <script
	type="text/javascript" src="js/jquery-ui-1.8.7.custom.min.js"></script>
<script type="text/javascript" src="js/flexigrid.pack.js"></script> <script
	type="text/javascript">
<!--
/*  
 * ページのLoad時に呼び出される処理
 */
$(document).ready(function() {
	//flexigridによる表の作成
	$("#datatable").flexigrid({
		autoload: true,
		url: "person/list",
		method: "post",
		dataType: "json",
		colModel: [
					{display: "id", name : "id", width : 30, sortable : false, align: "center", hide : false},
					{display: "version", name : "version", width : 50, sortable : false, align: "center", hide : true},
					{display: "ファーストネーム", name : "firstName", width : 150, sortable : true, align: "center"},
					{display: "ラストネーム", name : "lastName", width : 150, sortable : false, align: "center"},
					{display: "行き先", name : "place", width : 100, sortable : false, align: "center"},
					{display: "備考欄", name : "memo", width : 225, sortable : false, align: "center"},
					{display: "hidden", name : "place2", width : 10, sortable : false, align: "center", hide: true},
					{display: "グループ", name : "group", width : 150, sortable : true, align: "center"},
					{display: "hiddenGroup", name : "hiddenGroup", width : 10, sortable : false, align: "center", hide: true}
					
					
		],
		buttons : [
					{name: 'Add', bclass: 'add', onpress : addPressed},
					{name: 'Edit', bclass: 'edit', onpress : editPressed},
					{name: 'Delete', bclass: 'delete', onpress : deletePressed},
					{separator: true}
		],
		searchitems : [  
             {display: 'ファーストネーム', name : 'firstName'}
        ],  
		usepager: true,
		title: "noticeboard",
		useRp: true,
		rp: 10,
		rpOptions: [3,5,10,20,30],
		pagestat: "{from} ～ {to} を表示 (総数: {total} )",
		procmsg: "しばらくお待ちください……",
		nomsg: "項目はありません。",
		showTableToggleBtn: true,
		singleSelect: true,
		width: 950,
		height: "auto",
		preProcess: preProcess
		,onSuccess: postProcess
	});

	//リストボックスで値を変更したら、update関数を呼び出す。
	$("#datatable").change(update);

	//編集ダイアログの準備
	$("#edit-dialog").dialog({
		modal:true,
		autoOpen:false,
		resizable:false,
		draggable:true,
		title:"編集入力",
		width: 450,
		show:"scale",
		hide:"scale",
		buttons: {
		"OK": function() {
			var id = $("#id").val();
			var firstName = $("#firstNameInput").val();
			var lastName = $("#lastNameInput").val();
			//alert('id:' + id + ' name:' + firstName + lastName);
			jQuery.post("person/update",
					{"command":"PROPERTY",
				     "id":id, 
				     "firstName":firstName, 
				     "lastName":lastName},
				     function(data, status) {
				    	 //alert(status);
				    	 //alert(data.returncode);
				    	 if (data.returncode == 'INVALID') {
					    	 alert('VALIDATION ERROR');
				    	 }
				    	 $("#datatable").flexReload();
				     },
				     'json');
			$(this).dialog("close");
		},
		"Cancel": function() {
			$(this).dialog("close");
		}
	}
	});

});

/*
 * 行き先の更新処理
 */ 
function update(e) {
	var target = $(e.target);
    var col = target.parents("tr");
    var cell = col[0].children;
    var id = $(cell[0]).text();
    var firstName = $(cell[2]).text();
    //alert($(cell[2]).text() + 'の' + target.context.id + 'を' + target.context.value + 'に変更しました。');
    //
    jQuery.post("person/update",
    	    {"command":"PLACE", 
	         "firstName":firstName,
	         "id":id,
	         "key":target.context.id,
	         "value":target.context.value}
	         );

    // 背景色更新
    switch(target.context.value) {
    case 'Absent':
		$(e.target).css("background-color","red");
		break;
	case 'Present':
		$(e.target).css("background-color","yellow");
		break;
	case 'Meeting':
		$(e.target).css("background-color","pink");
	}
}

/**
 * 追加ボタンが押されたときの処理
 */
function addPressed() {
	location.href = "person/create";
}

/*
 * 編集ボタンが押されたときの処理
 */
var editPressed = function(command, grid) {
	var items = $('.trSelected', grid);
	var itemlist = [];
	var id = $(items[0].cells[0]).text();
	//TODO 以下の２行は列の並べ替えをされると正しく動かなくなる。
	var firstName = $(items[0].cells[2]).text();
	var lastName = $(items[0].cells[3]).text();
	$("#id").val(id);
	$("#firstNameInput").val(firstName);
	$("#lastNameInput").val(lastName);
	$("#edit-dialog").dialog("open");
}

/*
 * 削除ボタンが押された時の処理
 */
var deletePressed = function(command, grid) {
    var items = $('.trSelected',grid);
    var itemlist = [];
    var param = $(items[0].cells[0]).text();
    $('#delete-confirm-dialog').text($(items[0].cells[2]).text() + 'を削除しますか？');
    $('#delete-confirm-dialog').dialog({
    	title:'削除確認',
    	width:400,
    	buttons:{
    		'はい':function(){
    			items.fadeOut('slow')
        		$.post('person/delete', // エラーハンドリングが必要ならば$.ajaxを使う
             		  { method:'delete',
              		   deleteItems:param},
            		   function(data, status) {
                		   if (data.returncode == 'NOTIMPLEMENT') {
                    		   alert('削除処理はサーバサイドでまだ実装されていません。');
                		   }
                		   $("#datatable").flexReload();
                	   },
         		      'json');
    			$(this).dialog("close");
				},
    		'いいえ':function() { $(this).dialog("close"); }
    	},
    	modal:true
    });
}

/*
 * flexigridの初期処理（JSONデータを受け取った後で、かつ、テーブルを表示する前に呼ばれる）
 */
function preProcess( data ) {
	$.each (data.rows,
		function(i, val) {
			var list = '<select id="sel2" class="sel2">';
			<c:forEach var="e" items="${groups}">
			    list = list + '<option value="${f:h(e.name)}">${f:h(e.name)}</option>';
			</c:forEach>
			val.cell[8] = val.cell[6];
			val.cell[6] = val.cell[4];
			val.cell[4] = '<select id="sel" class="sel"><option value="undef">---</option><option value="Absent">Absent</option><option value="Present">Present</option><option value="Meeting">Meeting</option>';
			val.cell[5] = '<input id="inputfield" type="text name="memo" value="' + val.cell[5] + '" size="30" maxlength="50" />'
			val.cell[7] = list;
		}
	);
	return data;
}

/*
 * flexigridの表が表示された後の処理
 */
function postProcess()  {
	
	// 行き先の背景色を設定する
    //var elements  = document.getElementsByClassName("drop");
    var elements  = $(".sel");
    //alert(elements.length);
	for (var i = 0; i < elements.length ; i++) {
		//[6]の値を抽出
		var col = $(elements[i]).parents("tr");
		var cell = col[0].children;
		var param = $(cell[6]).text();
		$(elements[i]).val(param);
		switch(param) {
		case 'Absent':
			$(elements[i]).css("background-color","red");
			break;
		case 'Present':
			$(elements[i]).css("background-color","yellow");
			break;
		case 'Meeting':
			$(elements[i]).css("background-color","pink");
		}
	}

	// グループの選択値の設定をする
    //var elements  = document.getElementsByClassName("drop");
    var elements  = $(".sel2");
    //alert(elements.length);
	for (var i = 0; i < elements.length ; i++) {
		//[8]の値を抽出
		var col = $(elements[i]).parents("tr");
		var cell = col[0].children;
		var param = $(cell[8]).text();
		$(elements[i]).val(param);
	}
}

//-->
</script>
</head>
<body>
<div align="right">
<b>${f:h(email)}</b>

<a href="logout"> logout</a>
</div>
<hr/>
<p>
<h3>noticeboard2011</h3>
</p>
<table id="datatable" style="display: none"></table>
<br />
<div id="delete-confirm-dialog"></div>
<div id="edit-dialog">編集内容を入力してください。 <input type="hidden" id="id" />
<br />
FirstName <input type="text" id="firstNameInput" size="20" /> <br />
LastName <input type="text" id="lastNameInput" size="20" /></div>
</body>
</html>