If $CmdLine[0]<2 Then
	Exit
EndIf
;$CmdLine[0]-����������
;$CmdLine[1]-��һ������;(�ű����ƺ���)
;$CmdLine[2];�ڶ�������
handleUpload($CmdLine[1], $CmdLine[2])
;$browser:�������;$uploadfileҪ�ϴ����ļ�·��
Func handleUpload($browser, $uploadfile)
	Dim $title

	If $browser="chrome" Then
		$title="��"
	ElseIf $browser="firefox" Then
		$title="�ϴ��ļ�"
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