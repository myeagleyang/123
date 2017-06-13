If $CmdLine[0]<2 Then
	Exit
EndIf
;$CmdLine[0]-参数的数量
;$CmdLine[1]-第一个参数;(脚本名称后面)
;$CmdLine[2];第二个参数
handleUpload($CmdLine[1], $CmdLine[2])
;$browser:浏览器名;$uploadfile要上传的文件路径
Func handleUpload($browser, $uploadfile)
	Dim $title

	If $browser="chrome" Then
		$title="打开"
	ElseIf $browser="firefox" Then
		$title="上传文件"
	EndIf

	If WinWait($title, "", 5) Then
		WinActivate($title)
		ControlSetText($title, "", "Edit1", $uploadfile)
		Sleep(200)
		ControlClick($title, "", "Button1")
	Else
		Return False
	EndIf
EndFunc