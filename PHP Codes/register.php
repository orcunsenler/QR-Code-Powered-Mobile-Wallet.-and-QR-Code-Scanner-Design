<?php

	$con=new mysqli("localhost","root","","wallet");
	$st_check=$con->prepare("select * from users where username=?");
	$st_check->bind_param("s", $_GET["username"]);
	$st_check->execute();
	$rs=$st_check->get_result();
	if(mysqli_num_rows($rs)==0)
	{
		
		$st=$con->prepare("insert into users values(?,?,?,?,?,?)");
		$st->bind_param("ssssss", $_GET["name"],$_GET["surname"],$_GET["username"],$_GET["password"],$_GET["amount"],$_GET["encrypted"]);
		$st->execute();
		echo "1";
	}
	else
		echo "0"; //Name already exists
	
	
?>