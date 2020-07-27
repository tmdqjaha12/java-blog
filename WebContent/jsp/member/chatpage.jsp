
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/jsp/part/head.jspf"%>

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>


<style type="text/css">
	*{
		font-family: 나눔고딕;
	}
	#messageWindow{
		background: black;
		color: greenyellow;
	}
	#inputMessage{
		width:500px;
		height:20px
	}
	#btn-submit{
		background: white;
		background: #F7E600;
		width:60px;
		height:30px;
		color:#607080;
		border:none;
	}
	
	#main-container{
		width:600px;
		height:680px;
		border:1px solid black;
		margin:10px;
		display: inline-block;
		
	}
	#chat-container{
		vertical-align: bottom;
		border: 1px solid black;
		margin:10px;
		min-height: 600px;
		max-height: 600px;
		overflow: scroll;
		overflow-x:hidden;
		background: #9bbbd4;
	}
	
	.chat{
		font-size: 20px;
		color:black;
		margin: 5px;
		min-height: 20px;
		padding: 5px;
		min-width: 50px;
		text-align: left;
        height:auto;
        word-break : break-all;
        background: #ffffff;
        width:auto;
        display:inline-block;
        border-radius: 10px 10px 10px 10px;
	}
	
	.notice{
		color:#607080;
		font-weight: bold;
		border : none;
		text-align: center;
		background-color: #9bbbd4;
		display: block;
	}

	.my-chat{
		text-align: right;
		background: #F7E600;
		border-radius: 10px 10px 10px 10px;
	}
	
	#bottom-container{
		margin:10px;
	}
	
	.chat-info{
		color:#556677;
		font-size: 10px;
		text-align: right;
		padding: 5px;
		padding-top: 0px;

	}
	
	.chat-box{
		text-align:left;
	}
	.my-chat-box{
		text-align: right;
	}
	
	
	
</style>
</head>
	<div id="main-container" style="display:block; margin:0 auto;">
		<div id="chat-container">
			
		</div>
		<div id="bottom-container">
			<input id="inputMessage" type="text">
			<input id="btn-submit" type="submit" value="전송" >
		</div>
	</div>
<script type="text/javascript">
	

	var textarea = document.getElementById("messageWindow");
	//var webSocket = new WebSocket('ws://site36.iu.gy/java-blog/webChatServer');
	var webSocket = new WebSocket('ws://localhost:8081/java-blog/webChatServer');
	
	// 로컬에서 테스트할 때 사용하는 URL입니다.
// 	var webSocket = new WebSocket('ws://localhost/DevEricServers/webChatServer');
	var inputMessage = document.getElementById('inputMessage');
	
	webSocket.onerror = function(e){
		onError(e);
	};
	webSocket.onopen = function(e){
		onOpen(e);
	};
	webSocket.onmessage = function(e){
		onMessage(e);
	};
	
	
	function onMessage(e){
		var chatMsg = event.data;
		var date = new Date();
		var dateInfo = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		if(chatMsg.substring(0,6) == 'server'){
			var $chat = $("<div class='chat notice'>" + chatMsg + "</div>");
			$('#chat-container').append($chat);
		}else{
			var $chat = $("<div class='chat-box'><div class='chat'>" + chatMsg + "</div><div class='chat-info chat-box'>"+ dateInfo +"</div></div>");
			$('#chat-container').append($chat);
		}
		
		
		$('#chat-container').scrollTop($('#chat-container')[0].scrollHeight+20);
	}
	
	function onOpen(e){
		
	}
	
	function onError(e){
		alert(e.data);
	}
	
	function send(){
		var chatMsg = inputMessage.value;
		if(chatMsg == ''){
			return;
		}
		var date = new Date();
		var dateInfo = date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
		var $chat = $("<div class='my-chat-box'><div class='chat my-chat'>" + chatMsg + "</div><div class='chat-info'>"+ dateInfo +"</div></div>");
		$('#chat-container').append($chat);
		webSocket.send(chatMsg);
		inputMessage.value = "";
		$('#chat-container').scrollTop($('#chat-container')[0].scrollHeight+20);
	}
	
</script>

<script type="text/javascript">
	$(function(){
		$('#inputMessage').keydown(function(key){
			if(key.keyCode == 13){
				$('#inputMessage').focus();
				send();
			}
		});
		$('#btn-submit').click(function(){
			send();
		});
		
	})
</script>

<%@ include file="/jsp/part/foot.jspf"%>